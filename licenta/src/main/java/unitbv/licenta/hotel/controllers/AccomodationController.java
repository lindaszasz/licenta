package unitbv.licenta.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import unitbv.licenta.hotel.models.Accomodation;
import unitbv.licenta.hotel.models.Room;
import unitbv.licenta.hotel.models.User;
import unitbv.licenta.hotel.repositories.AccomodationRepository;
import unitbv.licenta.hotel.repositories.RoomRepository;
import unitbv.licenta.hotel.repositories.UserRepository;

@Controller
@RequestMapping("/accomodations")
public class AccomodationController {

	@Autowired
	private AccomodationRepository accomodationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoomRepository roomRepository;

	@RequestMapping
	public ModelAndView getAccomodations(@RequestParam(required = false) Long id) {

		Iterable<Accomodation> accomodations;
		if (id != null) {
			accomodations = accomodationRepository.getByUser(id);
		} else {
			accomodations = accomodationRepository.findAll();
		}
		return new ModelAndView("admin/accomodations", "accomodations", accomodations);
	}

	@RequestMapping(path = "/show")
	public ModelAndView getAccomodation(@RequestParam(value = "id", required = false) Long id) {
		Accomodation accomodation = id != null ? accomodationRepository.findOne(id) : new Accomodation();

		ModelAndView modelAndView = new ModelAndView("admin/accomodation");
		modelAndView.addObject("accomodation", accomodation);
		modelAndView.addObject("users", userRepository.findAll());
		modelAndView.addObject("rooms", roomRepository.findAll());
		return modelAndView;
	}

	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String addAccomodation(@RequestParam(value = "user.id") long idUser,
			@RequestParam(value = "room.id") long idRoom, @RequestParam(value = "checkIn") String checkIn,
			@RequestParam(value = "checkOut") String checkOut, @RequestParam(value = "nrAdults") int nrAdults,
			@RequestParam(value = "nrChildrens", required = false) int nrChildrens,
			@RequestParam(value = "priceAccomodation", required = false) double priceAccomodation) {

		User user = userRepository.findOne(idUser);
		Room room = roomRepository.findOne(idRoom);

		Accomodation accomodation = new Accomodation();
		accomodation.setUser(user);
		accomodation.setRoom(room);
		accomodation.setCheckIn(checkIn);
		accomodation.setCheckOut(checkOut);
		accomodation.setNrAdults(nrAdults);
		accomodation.setNrChildrens(nrChildrens);
		accomodation.setPriceAccomodation(priceAccomodation);

		accomodationRepository.save(accomodation);
		return "redirect:/accomodations";
		// return "Reservation was saved";

	}

	@RequestMapping(path = "/update", method = RequestMethod.GET)
	public String updateAccomodation(@RequestParam(value = "id") long id,
			@RequestParam(value = "user.id") long idUser, @RequestParam(value = "room.id") long idRoom,
			@RequestParam(value = "checkIn") String checkIn, @RequestParam(value = "checkOut") String checkOut,
			@RequestParam(value = "nrAdults") int nrAdults, @RequestParam(value = "nrChildrens") int nrChildrens,
			@RequestParam(value = "priceAccomodation") double priceAccomodation) {

		Accomodation accomodation = accomodationRepository.findOne(id);
		User user = userRepository.findOne(idUser);
		Room room = roomRepository.findOne(idRoom);

		accomodation.setUser(user);
		accomodation.setRoom(room);
		accomodation.setCheckIn(checkIn);
		accomodation.setCheckOut(checkOut);
		accomodation.setNrAdults(nrAdults);
		accomodation.setNrChildrens(nrChildrens);
		accomodation.setPriceAccomodation(priceAccomodation);

		accomodationRepository.save(accomodation);
		return "redirect:/accomodations";
		// return "Reservation was updated";

	}

	@RequestMapping(path = "/delete", method = RequestMethod.GET)
	public String deleteAccomodation(@RequestParam(value = "id") long id) {
		accomodationRepository.delete(id);
		return "redirect:/accomodations";
		// return "Reservation was deleted";

	}

}
