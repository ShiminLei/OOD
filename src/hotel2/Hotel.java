package hotel2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Reservation makeReservation(ReservationRequest request)
    {
        Reservation reservation = new Reservation(request.getStartDate(), request.getEndDate());

        SearchRequest search = new SearchRequest(request.getStartDate(), request.getEndDate());

        Map<RoomType, List<Room>> roomsAvailable = getAvailableRooms(search);

        Map<RoomType, Integer> roomsNeeded = request.getRoomsNeeded();

        for(Map.Entry<RoomType, Integer> entry : roomsNeeded.entrySet())
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

    public void cancelReservation(Reservation reservation)
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
