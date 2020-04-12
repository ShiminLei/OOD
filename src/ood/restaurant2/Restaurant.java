package ood.restaurant2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Restaurant {
    private List<Table> tables;
    private List<Meal> menu;
    public static final int MAX_DINEHOUR = 2;
    public static final long HOUR = 3600*1000;

    public Restaurant()
    {
        tables = new ArrayList<Table>();
        menu = new ArrayList<Meal>();
    }

    public void findTable(Party p) throws NoTableException
    {
        Date currentDate = new Date();
        for(Table t: tables)
        {
            if(t.isAvailable())
            {
                if(t.getCapacity() >= p.getSize())
                {
                    if(t.noFollowReservation(currentDate))
                    {
                        t.markUnavailable();
                        return;
                    }
                }
            }
        }
        throw new NoTableException(p);
    }

    public void takeOrder(Table t, Order o)
    {
        t.setOrder(o);
    }

    public float checkOut(Table t)
    {
        float bill = 0;
        if(t.getCurrentOrder() != null)
        {
            bill = t.getCurrentOrder().getBill();
        }

        t.markAvailable();
        t.setOrder(null);

        return bill;
    }

    public List<Meal> getMenu()
    {
        return menu;
    }

    public void addTable(Table t)
    {
        tables.add(t);
        Collections.sort(tables);
    }

    public Reservation findTableForReservation(Party p, Date date)
    {
        for(Table table : tables)
        {
            if(table.getCapacity() >= p.getSize())
            {
                if(table.reserveForDate(date))
                {
                    return new Reservation(table, date);
                }
            }
        }
        return null;
    }

    public void cancelReservation(Reservation r)
    {
        Date date = r.getDate();
        r.getTable().removeReservation(date);
    }

    public void redeemReservation(Reservation r)
    {
        Date date = r.getDate();
        Table table = r.getTable();

        table.markUnavailable();
        table.removeReservation(date);
    }

    public String restaurantDescription()
    {
        String description = "";
        for(int i = 0; i < tables.size(); i++)
        {
            Table table = tables.get(i);
            description += ("Table: " + table.getId() + ", table size: " + table.getCapacity() + ", isAvailable: " + table.isAvailable() + ".");
            if(table.getCurrentOrder() == null)
                description += " No current order for this table";
            else
                description +=  " Order price: " + table.getCurrentOrder().getBill();

            description += ". Current reservation dates for this table are: ";

            for(Date date : table.getReservation())
            {
                description += date.toGMTString() + " ; ";
            }

            description += ".\n";
        }
        description += "*****************************************\n";
        return description;
    }
}