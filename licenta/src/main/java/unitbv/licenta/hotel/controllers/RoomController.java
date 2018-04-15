package unitbv.licenta.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import unitbv.licenta.hotel.models.Room;
import unitbv.licenta.hotel.repositories.RoomRepository;

@Controller
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	private RoomRepository roomRepository;

	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String addRoom(@RequestParam(value = "roomType", defaultValue = "test") String roomType,
			@RequestParam(value = "nrAdults") int nrAdults,
			@RequestParam(value = "nrChildrens") int nrChildrens,
			@RequestParam(value = "priceNight") double priceNight) {

		Room room = new Room();
		room.setRoomType(roomType);
		room.setNrAdults(nrAdults);
		room.setNrChildrens(nrChildrens);
		room.setPriceNight(priceNight);

		roomRepository.save(room);
		return "Room was saved";
		// return "redirect:/rooms";
	}

	@RequestMapping(path = "/update", method = RequestMethod.GET)
	public String updateRoom(@RequestParam(value = "id") long id,
			@RequestParam(value = "roomType") String roomType,
			@RequestParam(value = "nrAdults") int nrAdults,
			@RequestParam(value = "nrChildrens") int nrChildrens,
			@RequestParam(value = "priceNight") double priceNight) {

		Room room = roomRepository.findOne(id);
		room.setRoomType(roomType);
		room.setNrAdults(nrAdults);
		room.setNrChildrens(nrChildrens);
		room.setPriceNight(priceNight);

		roomRepository.save(room);
		return "Room was updated";
		// return "redirect:/rooms";

	}

	@RequestMapping(path = "/delete", method = RequestMethod.GET)
	public String deleteRoom(@RequestParam(value = "id") long id) {
		roomRepository.delete(id);
		return "Room was deleted";
		// return "redirect:/rooms";
	}

}
