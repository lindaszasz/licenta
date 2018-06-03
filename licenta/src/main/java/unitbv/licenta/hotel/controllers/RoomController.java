package unitbv.licenta.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import unitbv.licenta.hotel.models.Room;
import unitbv.licenta.hotel.repositories.RoomRepository;

@Controller
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	private RoomRepository roomRepository;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getRooms(@RequestParam(required = false) String roomType) {
		Iterable<Room> rooms;
		if (roomType != null && roomType != "") {
			rooms = roomRepository.getByRoomType(roomType.trim());
		} else {
			rooms = roomRepository.findAll();
		}
		return new ModelAndView("admin/rooms", "rooms", rooms);
	}

	@RequestMapping(path = "show", method = RequestMethod.GET)
	public ModelAndView getRoom(@RequestParam(value = "id", required = false) Long id) {
		Room room = id != null ? roomRepository.findOne(id) : new Room();
		System.out.println(room.getId());
		return new ModelAndView("admin/room", "room", room);
	}

	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String addRoom(@RequestParam(value = "roomType", defaultValue = "test") String roomType,
			@RequestParam(value = "nrAdults") int nrAdults, @RequestParam(value = "nrChildrens") int nrChildrens,
			@RequestParam(value = "priceNight") double priceNight) {

		Room room = new Room();
		room.setRoomType(roomType);
		room.setNrAdults(nrAdults);
		room.setNrChildrens(nrChildrens);
		room.setPriceNight(priceNight);

		roomRepository.save(room);
		return "redirect:/rooms";
		// return "Room was saved";
	}

	@RequestMapping(path = "/update", method = RequestMethod.GET)
	public String updateRoom(@RequestParam(value = "id") long id, @RequestParam(value = "roomType") String roomType,
			@RequestParam(value = "nrAdults") int nrAdults, @RequestParam(value = "nrChildrens") int nrChildrens,
			@RequestParam(value = "priceNight") double priceNight) {

		Room room = roomRepository.findOne(id);
		room.setRoomType(roomType);
		room.setNrAdults(nrAdults);
		room.setNrChildrens(nrChildrens);
		room.setPriceNight(priceNight);

		roomRepository.save(room);
		return "redirect:/rooms";
		// return "Room was updated";

	}

	@RequestMapping(path = "/delete", method = RequestMethod.GET)
	public String deleteRoom(@RequestParam(value = "id") long id) {
		roomRepository.delete(id);
		return "redirect:/rooms";
		// return "Room was deleted";

	}

}
