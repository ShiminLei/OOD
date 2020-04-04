package restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Restaurant {
    private List<Table> tables;
    private List<Meal> menu;

    public Restaurant()
    {
        tables = new ArrayList<Table>();
        menu = new ArrayList<Meal>();
    }

    public void findTable(Party p) throws NoTableException
    {
        for(Table t: tables)
        {
            if(t.isAvailable())
            {
                if(t.getCapacity() >= p.getSize())
                {
                    t.markUnavailable();
                    return;
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

    public String restaurantDescription()
    {
        // Keep them, don't modify.
        String description = "";
        for(int i = 0; i < tables.size(); i++)
        {
            Table table = tables.get(i);
            description += ("restaurant.Table: " + i + ", table size: " + table.getCapacity() + ", isAvailable: " + table.isAvailable() + ".");
            if(table.getCurrentOrder() == null)
                description += " No current order for this table";
            else
                description +=  " restaurant.Order price: " + table.getCurrentOrder().getBill();

            description += ".\n";
        }
        description += "*****************************************\n";
        return description;
    }
}