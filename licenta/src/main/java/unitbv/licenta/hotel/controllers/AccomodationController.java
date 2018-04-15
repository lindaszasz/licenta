package unitbv.licenta.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import unitbv.licenta.hotel.models.Accomodation;
import unitbv.licenta.hotel.models.Client;
import unitbv.licenta.hotel.models.Room;
import unitbv.licenta.hotel.repositories.AccomodationRepository;
import unitbv.licenta.hotel.repositories.ClientRepository;
import unitbv.licenta.hotel.repositories.RoomRepository;

@Controller
@RequestMapping("/accomodations")
public class AccomodationController {

	@Autowired
	private AccomodationRepository accomodationRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private RoomRepository roomRepository;

	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String addAccomodation(@RequestParam(value = "client.id") long idClient,
			@RequestParam(value = "room.id") long idRoom, @RequestParam(value = "checkIn") String checkIn,
			@RequestParam(value = "checkOut") String checkOut, @RequestParam(value = "nrAdults") int nrAdults,
			@RequestParam(value = "nrChildrens", required = false) int nrChildrens,
			@RequestParam(value = "priceAccomodation", required = false) double priceAccomodation) {

		Client client = clientRepository.findOne(idClient);
		Room room = roomRepository.findOne(idRoom);

		Accomodation accomodation = new Accomodation();
		accomodation.setClient(client);
		accomodation.setRoom(room);
		accomodation.setCheckIn(checkIn);
		accomodation.setCheckOut(checkOut);
		accomodation.setNrAdults(nrAdults);
		accomodation.setNrChildrens(nrChildrens);
		accomodation.setPriceAccomodation(priceAccomodation);

		accomodationRepository.save(accomodation);
		return "Reservation was saved";
		// return "redirect:/accomodations";
	}

	@RequestMapping(path = "/update", method = RequestMethod.GET)
	public String updateAccomodation(@RequestParam(value = "id") long id,
			@RequestParam(value = "client.id") long idClient, @RequestParam(value = "room.id") long idRoom,
			@RequestParam(value = "checkIn") String checkIn, @RequestParam(value = "checkOut") String checkOut,
			@RequestParam(value = "nrAdults") int nrAdults, @RequestParam(value = "nrChildrens") int nrChildrens,
			@RequestParam(value = "priceAccomodation") double priceAccomodation) {

		Accomodation accomodation = accomodationRepository.findOne(id);
		Client client = clientRepository.findOne(idClient);
		Room room = roomRepository.findOne(idRoom);

		accomodation.setClient(client);
		accomodation.setRoom(room);
		accomodation.setCheckIn(checkIn);
		accomodation.setCheckOut(checkOut);
		accomodation.setNrAdults(nrAdults);
		accomodation.setNrChildrens(nrChildrens);
		accomodation.setPriceAccomodation(priceAccomodation);

		accomodationRepository.save(accomodation);
		return "Reservation was updated";

		// return "redirect:/accomodations";

	}

	@RequestMapping(path = "/delete", method = RequestMethod.GET)
	public String deleteAccomodation(@RequestParam(value = "id") long id) {
		accomodationRepository.delete(id);
		return "Reservation was deleted";
		// return "redirect:/accomodations";
	}

}
