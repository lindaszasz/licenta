package unitbv.licenta.hotel.repositories;

import org.springframework.data.repository.CrudRepository;


import unitbv.licenta.hotel.models.Room;
public interface RoomRepository extends CrudRepository<Room, Long>{
	
	Iterable<Room> getByRoomType(String roomType);

}
