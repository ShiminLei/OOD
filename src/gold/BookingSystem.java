package gold;

import java.util.*;
import java.util.Map.Entry;

/**
 * 预定类 Hotel OOD
 */
public class BookingSystem {
    private List<Hotel> hotels;

    public BookingSystem()
    {
        hotels = new ArrayList<>();
    }

    public List<Hotel> searchHotel(SearchHotelRequest request)
    {
        List<Hotel> availableHotels = new ArrayList<>();
        for(Hotel hotel : hotels)
        {
            SearchRequest searchRequest = new SearchRequest(request.getStartDate(), request.getEndDate());
            Map<RoomType, List<Room>> searchRes = hotel.handleSearchResult(searchRequest);
            int availableCapacity = 0;
            for(Entry<RoomType, List<Room>> entry : searchRes.entrySet())
            {
                availableCapacity += entry.getKey().getCapacity() * entry.getValue().size();
            }
            if(availableCapacity >= request.getGroupSize())
            {
                availableHotels.add(hotel);
            }
        }
        return availableHotels;
    }

    public gold.restaurant2.Reservation makeReservation(Hotel hotel, ReservationRequest request)
    {
        return hotel.makeReservation(request);
    }

    public void cancelReservation(gold.restaurant2.Reservation reservation)
    {
        reservation.getHotel().cancelReservation(reservation);
    }

    public List<Hotel> getHotels()
    {
        return hotels;
    }
}

class Hotel {
    public static final int DAY = 1*24*60*60*1000;

    private int id;
    private List<Room> rooms;
    private LRUCache cache;

    public Hotel(int id)
    {
        this.id = id;
        cache = new LRUCache(2);
        rooms = new ArrayList<>();
    }

    public int getId()
    {
        return this.id;
    }

    public gold.restaurant2.Reservation makeReservation(ReservationRequest request)
    {
        gold.restaurant2.Reservation reservation = new gold.restaurant2.Reservation(request.getStartDate(), request.getEndDate());

        SearchRequest search = new SearchRequest(request.getStartDate(), request.getEndDate());

        Map<RoomType, List<Room>> roomsAvailable = getAvailableRooms(search);

        Map<RoomType, Integer> roomsNeeded = request.getRoomsNeeded();

        for(Entry<RoomType, Integer> entry : roomsNeeded.entrySet())
        {
            RoomType roomType = entry.getKey();
            int roomCount = entry.getValue();

            List<Room> rooms = roomsAvailable.get(roomType);

            //Not enough rooms
            if(entry.getValue() > rooms.size())
            {
                cache.put(search, roomsAvailable);
                return null;
            }

            for(int i = 0; i < roomCount; i++)
            {
                Room room = rooms.remove(0);
                reservation.getRooms().add(room);
            }

            roomsAvailable.put(entry.getKey(), rooms);
        }

        cache.put(search, roomsAvailable);

        for(Room room : reservation.getRooms())
        {
            room.makeReservation(reservation.getStartDate(), reservation.getEndDate());
        }

        return reservation;
    }

    public Map<RoomType, List<Room>> handleSearchResult(SearchRequest request)
    {
        if(cache.containsKey(request))
        {
            return cache.get(request);
        }

        Map<RoomType, List<Room>> res = getAvailableRooms(request);

        cache.put(request, res);

        return res;
    }

    public void cancelReservation(gold.restaurant2.Reservation reservation)
    {
        for(Room room : reservation.getRooms())
        {
            room.cancelReservation(reservation);
        }
    }

    public List<Room> getRooms()
    {
        return rooms;
    }

    private Map<RoomType, List<Room>> getAvailableRooms(SearchRequest request)
    {
        Map<RoomType, List<Room>> res = new HashMap<>();

        res.put(RoomType.SINGLE, new ArrayList<>());
        res.put(RoomType.DOUBLE, new ArrayList<>());

        for(Room room : rooms)
        {
            if(room.isValidRequest(request))
            {
                List<Room> roomList = res.get(room.getRoomType());
                roomList.add(room);
                res.put(room.getRoomType(), roomList);
            }
        }

        return res;
    }

    public String printCache()
    {
        return "Hotel Id: " + getId() + "\nPrinting Cache ...\n" + cache.toString() +
                "*****************************\n";
    }
}

class LRUCache extends LinkedHashMap<SearchRequest, Map<RoomType, List<Room>>> {

    private static final long serialVersionUID = 1L;
    private int capacity;

    public LRUCache(int capacity)
    {
        super(capacity);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Entry<SearchRequest, Map<RoomType, List<Room>>> eldest) {
        // TODO Auto-generated method stub
        return size() > this.capacity;
    }

    private String printAvailableRooms(Map<RoomType, List<Room>> rooms)
    {
        String res = "";
        for(Entry<RoomType, List<Room>> entry : rooms.entrySet())
        {
            res += "For room type: " + entry.getKey() + ", available rooms are: ";
            for(Room room : entry.getValue())
            {
                res += room.getId() + "; ";
            }
            res += ". ";
        }
        return res;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub

        String res = "";

        for(Entry<SearchRequest, Map<RoomType, List<Room>>> entry : super.entrySet())
        {
            res += ("Search Request -> " + entry.getKey().toString() + "\n");
            res += ("Value -> " + printAvailableRooms(entry.getValue()) + "\n");
            res += "\n";
        }

        return res;
    }
}

class Reservation {
    private Hotel hotel = null;
    private Date startDate;
    private Date endDate;
    private List<Room> rooms;

    public Reservation(Date startDate, Date endDate)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        rooms = new ArrayList<>();
    }

    public void setHotel(Hotel hotel)
    {
        this.hotel = hotel;
    }

    public Hotel getHotel()
    {
        return hotel;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public List<Room> getRooms()
    {
        return rooms;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub


        String res = "Hotel is: " + hotel.getId() + ", start date is: " + startDate.toLocaleString() + ", End date is: " + endDate.toLocaleString()
                + ", rooms are: ";

        for(Room room : rooms)
        {
            res += room.getId() + "; ";
        }
        res += ". ";

        return res;
    }
}

class ReservationRequest {
    private Date startDate;
    private Date endDate;
    private Map<RoomType, Integer> roomsNeeded;

    public ReservationRequest(Date startDate, Date endDate, Map<RoomType, Integer> roomsNeeded) {
        // TODO Auto-generated constructor stub
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomsNeeded = roomsNeeded;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public Map<RoomType, Integer> getRoomsNeeded()
    {
        return roomsNeeded;
    }
}

enum RoomType {
    SINGLE(1),
    DOUBLE(2);

    private int capacity;

    RoomType(int capacity)
    {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}

class Room {
    public static final int DAY = 1*24*60*60*1000;

    private int id;
    private RoomType roomType;
    private Set<Date> reservations;

    public Room(int id, RoomType roomType)
    {
        this.id = id;
        this.roomType = roomType;
        reservations = new HashSet<Date>();
    }

    public boolean isValidRequest(SearchRequest request)
    {
        Date date = new Date(request.getStartDate().getTime());
        for (; date.before(request.getEndDate()); date.setTime(date.getTime() + DAY))
        {
            Date tempDate = new Date(date.getTime());
            if(reservations.contains(tempDate))
            {
                return false;
            }
        }
        return true;
    }

    public void makeReservation(Date startDate, Date endDate)
    {
        Date date = new Date(startDate.getTime());
        for (; date.before(endDate); date.setTime(date.getTime() + DAY))
        {
            Date tempDate = new Date(date.getTime());
            reservations.add(tempDate);
        }
    }

    public void cancelReservation(gold.restaurant2.Reservation reservation)
    {
        Date date = new Date(reservation.getStartDate().getTime());
        for (; date.before(reservation.getEndDate()); date.setTime(date.getTime() + DAY))
        {
            Date tempDate = new Date(date.getTime());
            reservations.remove(tempDate);
        }
    }

    public RoomType getRoomType()
    {
        return roomType;
    }

    public int getId()
    {
        return this.id;
    }
}

class SearchHotelRequest {
    private Date startDate;
    private Date endDate;
    private int groupSize;

    public SearchHotelRequest(Date startDate, Date endDate, int groupSize)
    {
        this.startDate = startDate;
        this.endDate = endDate;
        this.groupSize = groupSize;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public int getGroupSize()
    {
        return groupSize;
    }
}

class SearchRequest {
    private Date startDate;
    private Date endDate;

    public SearchRequest(Date startDate, Date endDate) {
        // TODO Auto-generated constructor stub
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub

        String res = "Start date is: " + startDate.toLocaleString() + ", End date is: " + endDate.toLocaleString();

        return res;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        if(obj == this) return true;
        if(!(obj instanceof SearchRequest)) return false;

        SearchRequest request = (SearchRequest) obj;

        return request.startDate == this.startDate && request.endDate == this.endDate;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        int result = 17;
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        return result;
    }
}