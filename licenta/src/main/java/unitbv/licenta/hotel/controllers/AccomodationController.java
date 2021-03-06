package unitbv.licenta.hotel.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import unitbv.licenta.hotel.mail.SmtpMailSender;

import unitbv.licenta.hotel.algorithm.GreedyAlgorithm;
import unitbv.licenta.hotel.models.Accomodation;
import unitbv.licenta.hotel.models.AccomodationDeluxe;
import unitbv.licenta.hotel.models.AccomodationGolden;
import unitbv.licenta.hotel.models.AccomodationJunior;
import unitbv.licenta.hotel.models.AccomodationPrestige;
import unitbv.licenta.hotel.models.BookingRequestForm;
import unitbv.licenta.hotel.models.Room;
import unitbv.licenta.hotel.models.Service;
import unitbv.licenta.hotel.models.User;
import unitbv.licenta.hotel.repositories.AccomodationDeluxeRepository;
import unitbv.licenta.hotel.repositories.AccomodationGoldenRepository;
import unitbv.licenta.hotel.repositories.AccomodationJuniorRepository;
import unitbv.licenta.hotel.repositories.AccomodationPrestigeRepository;
import unitbv.licenta.hotel.repositories.AccomodationRepository;
import unitbv.licenta.hotel.repositories.BookingRequestRepository;
import unitbv.licenta.hotel.repositories.RoomRepository;
import unitbv.licenta.hotel.repositories.ServiceRepository;
import unitbv.licenta.hotel.repositories.UserRepository;
import unitbv.licenta.hotel.service.RequestService;
import unitbv.licenta.hotel.service.UserService;

@Controller
@RequestMapping("/accomodations")
public class AccomodationController {

	@Autowired
	private AccomodationRepository accomodationRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private ServiceRepository serviceRepository;

	@Autowired
	private BookingRequestRepository bookingRequestRepository;

	@Autowired
	private AccomodationPrestigeRepository prestigeRepository;

	@Autowired
	private AccomodationDeluxeRepository deluxeRepository;

	@Autowired
	private AccomodationJuniorRepository juniorRepository;

	@Autowired
	private AccomodationGoldenRepository goldenRepository;

	@Autowired
	private SmtpMailSender smtpMailSender;

	@Autowired
	private UserService userService;

	private DateTimeFormatter formatter;

	private DateTimeFormatter formatter2;

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
		modelAndView.addObject("services", serviceRepository.findAll());
		return modelAndView;
	}

	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String addAccomodation(@RequestParam(value = "user.id") long idUser,
			@RequestParam(value = "room.id") long idRoom, @RequestParam(value = "checkIn") String checkIn,
			@RequestParam(value = "checkOut") String checkOut, @RequestParam(value = "nrAdults") int nrAdults,
			@RequestParam(value = "nrChildrens", required = false) int nrChildrens,
			@RequestParam(value = "service.id") long idService,
			@RequestParam(value = "priceAccomodation", required = false) double priceAccomodation) {

		User user = userRepository.findOne(idUser);
		Room room = roomRepository.findOne(idRoom);
		Service service = serviceRepository.findOne(idService);

		Accomodation accomodation = new Accomodation();
		accomodation.setUser(user);
		accomodation.setRoom(room);
		accomodation.setCheckIn(checkIn);
		accomodation.setCheckOut(checkOut);
		accomodation.setNrAdults(nrAdults);
		accomodation.setNrChildrens(nrChildrens);
		accomodation.setService(service);

		RequestService requestService = new RequestService();
		priceAccomodation = requestService.calculatePrice(checkIn, checkOut, room.getPriceNight());
		accomodation.setPriceAccomodation(priceAccomodation);

		accomodationRepository.save(accomodation);
		return "redirect:/accomodations";
		// return "Reservation was saved";

	}

	@RequestMapping(path = "/update", method = RequestMethod.GET)
	public String updateAccomodation(@RequestParam(value = "id") long id, @RequestParam(value = "user.id") long idUser,
			@RequestParam(value = "room.id") long idRoom, @RequestParam(value = "checkIn") String checkIn,
			@RequestParam(value = "checkOut") String checkOut, @RequestParam(value = "nrAdults") int nrAdults,
			@RequestParam(value = "nrChildrens") int nrChildrens, @RequestParam(value = "service.id") long idService,
			@RequestParam(value = "priceAccomodation") double priceAccomodation) {

		Accomodation accomodation = accomodationRepository.findOne(id);
		User user = userRepository.findOne(idUser);
		Room room = roomRepository.findOne(idRoom);
		Service service = serviceRepository.findOne(idService);

		accomodation.setUser(user);
		accomodation.setRoom(room);
		accomodation.setCheckIn(checkIn);
		accomodation.setCheckOut(checkOut);
		accomodation.setNrAdults(nrAdults);
		accomodation.setNrChildrens(nrChildrens);
		accomodation.setService(service);

		RequestService requestService = new RequestService();
		priceAccomodation = requestService.calculatePrice(checkIn, checkOut, room.getPriceNight());
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

	@RequestMapping(path = "/requests")
	public ModelAndView getBookingRequests(@RequestParam(required = false) String name) {

		Iterable<User> users = userRepository.getByLastNameOrFirstName(name, name);

		Iterable<BookingRequestForm> bookingRequests = null;

		if (name != null && name != "") {
			for (User user : users) {
				bookingRequests = bookingRequestRepository.getByUserId(user.getId());
			}
		} else {
			bookingRequests = bookingRequestRepository.findAll();
		}
		return new ModelAndView("admin/bookingRequests", "bookingRequests", bookingRequests);
	}

	@RequestMapping(path = "/show/request")
	public ModelAndView getBookingRequest(@RequestParam(value = "id", required = false) Long id) {
		BookingRequestForm bookingRequestForm = id != null ? bookingRequestRepository.findOne(id)
				: new BookingRequestForm();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());

		ModelAndView modelAndView = new ModelAndView("booking-request-form");
		modelAndView.addObject("bookingRequest", bookingRequestForm);
		modelAndView.addObject("user", user);
		modelAndView.addObject("rooms", roomRepository.findAll());
		modelAndView.addObject("services", serviceRepository.findAll());
		return modelAndView;
	}

	@RequestMapping(path = "/user")
	public ModelAndView getUserBookingRequestsAndAccommodations() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());

		ModelAndView modelAndView = new ModelAndView("user-requests-accomodations");
		modelAndView.addObject("bookingRequests", bookingRequestRepository.getByUserId(user.getId()));
		modelAndView.addObject("accomodations", accomodationRepository.getByUserId(user.getId()));

		return modelAndView;

	}

	@RequestMapping(path = "/request/add", method = RequestMethod.GET)
	public String addBookingRequest(@RequestParam(value = "user.id") long idUser,
			@RequestParam(value = "room.id") long idRoom, @RequestParam(value = "checkIn") String checkIn,
			@RequestParam(value = "checkOut") String checkOut, @RequestParam(value = "nrAdults") int nrAdults,
			@RequestParam(value = "nrChildrens", required = false) int nrChildrens,
			@RequestParam(value = "service.id") long idService,
			@RequestParam(value = "priceAccomodation", required = false) double priceAccomodation) {

		User user = userRepository.findOne(idUser);
		Room room = roomRepository.findOne(idRoom);
		Service service = serviceRepository.findOne(idService);

		BookingRequestForm bookingRequestForm = new BookingRequestForm();
		bookingRequestForm.setUser(user);
		bookingRequestForm.setRoom(room);
		bookingRequestForm.setCheckIn(checkIn);
		bookingRequestForm.setCheckOut(checkOut);
		bookingRequestForm.setNrAdults(nrAdults);
		bookingRequestForm.setNrChildrens(nrChildrens);
		bookingRequestForm.setService(service);

		RequestService requestService = new RequestService();
		priceAccomodation = requestService.calculatePrice(checkIn, checkOut, room.getPriceNight());
		bookingRequestForm.setPriceAccomodation(priceAccomodation);

		bookingRequestRepository.save(bookingRequestForm);

		return "redirect:/accomodations/user";
	}

	@RequestMapping(path = "/request/update", method = RequestMethod.GET)
	public String updateBookingRequest(@RequestParam(value = "id") long id,
			@RequestParam(value = "user.id") long idUser, @RequestParam(value = "room.id") long idRoom,
			@RequestParam(value = "checkIn") String checkIn, @RequestParam(value = "checkOut") String checkOut,
			@RequestParam(value = "nrAdults") int nrAdults,
			@RequestParam(value = "nrChildrens", required = false) int nrChildrens,
			@RequestParam(value = "service.id") long idService,
			@RequestParam(value = "priceAccomodation", required = false) double priceAccomodation) {

		BookingRequestForm bookingRequestForm = bookingRequestRepository.findOne(id);
		User user = userRepository.findOne(idUser);
		Room room = roomRepository.findOne(idRoom);
		Service service = serviceRepository.findOne(idService);

		bookingRequestForm.setUser(user);
		bookingRequestForm.setRoom(room);
		bookingRequestForm.setCheckIn(checkIn);
		bookingRequestForm.setCheckOut(checkOut);
		bookingRequestForm.setNrAdults(nrAdults);
		bookingRequestForm.setNrChildrens(nrChildrens);
		bookingRequestForm.setService(service);

		RequestService requestService = new RequestService();
		priceAccomodation = requestService.calculatePrice(checkIn, checkOut, room.getPriceNight());
		bookingRequestForm.setPriceAccomodation(priceAccomodation);

		bookingRequestRepository.save(bookingRequestForm);

		return "redirect:/accomodations/user";
	}

	@RequestMapping(path = "/accepted", method = RequestMethod.GET)
	public String selectReservations() {

		Iterable<BookingRequestForm> bookingRequests = null;
		bookingRequests = bookingRequestRepository.findAll();
		GreedyAlgorithm greedy = new GreedyAlgorithm();
		List<Accomodation> acceptedReservations = greedy.selectBestOption(bookingRequests);

		for (Accomodation accomodation : acceptedReservations) {
			Accomodation ac = new Accomodation();
			ac.setId(accomodation.getId());
			ac.setUser(accomodation.getUser());
			ac.setRoom(accomodation.getRoom());
			ac.setCheckIn(accomodation.getCheckIn());
			ac.setCheckOut(accomodation.getCheckOut());
			ac.setNrAdults(accomodation.getNrAdults());
			ac.setNrChildrens(accomodation.getNrChildrens());
			ac.setService(accomodation.getService());
			ac.setPriceAccomodation(accomodation.getPriceAccomodation());
			accomodationRepository.save(ac);
		}

		return "redirect:/accomodations";
	}

	@RequestMapping(path = "/accepted/reservations/prestige", method = RequestMethod.GET)
	public String selectReservationsPrestige() throws MessagingException {

		Iterable<BookingRequestForm> bookingRequests = null;
		bookingRequests = bookingRequestRepository.getByRoomRoomType("Prestige Suite");
		GreedyAlgorithm greedy = new GreedyAlgorithm();
		List<Accomodation> acceptedReservations = greedy.selectBestOption(bookingRequests);
		if (acceptedReservations != null) {

			Iterable<AccomodationPrestige> acceptedInPastAccommodations = prestigeRepository.findAll();
			for (Accomodation accomodation : acceptedReservations) {
				AccomodationPrestige prestige = new AccomodationPrestige();
				prestige.setId(accomodation.getId());
				prestige.setUser(accomodation.getUser());
				prestige.setRoom(accomodation.getRoom());
				prestige.setCheckIn(accomodation.getCheckIn());
				prestige.setCheckOut(accomodation.getCheckOut());
				prestige.setNrAdults(accomodation.getNrAdults());
				prestige.setNrChildrens(accomodation.getNrChildrens());
				prestige.setService(accomodation.getService());
				prestige.setPriceAccomodation(accomodation.getPriceAccomodation());

				boolean ok = true;
				if (acceptedInPastAccommodations != null) {
					for (AccomodationPrestige acceptedPrestige : acceptedInPastAccommodations) {
						if (prestige.getCheckIn().compareTo(acceptedPrestige.getCheckIn()) >= 0
								&& prestige.getCheckOut().compareTo(acceptedPrestige.getCheckOut()) <= 0) {
							ok = false;
							break;
						}
					}
				}

				Accomodation ac = new Accomodation();
				ac.setId(accomodation.getId());
				ac.setUser(accomodation.getUser());
				ac.setRoom(accomodation.getRoom());
				ac.setCheckIn(accomodation.getCheckIn());
				ac.setCheckOut(accomodation.getCheckOut());
				ac.setNrAdults(accomodation.getNrAdults());
				ac.setNrChildrens(accomodation.getNrChildrens());
				ac.setService(accomodation.getService());
				ac.setPriceAccomodation(accomodation.getPriceAccomodation());

				if (ok) {
					prestigeRepository.save(prestige);
					accomodationRepository.save(ac);

					smtpMailSender.send(prestige.getUser().getEmail(), "Accomodation request for Prestige Suite",
							"Dear " + prestige.getUser().getFirstName() + "," + System.lineSeparator()
									+ System.lineSeparator() + "Your reservation between " + prestige.getCheckIn()
									+ " and " + prestige.getCheckOut() + " was ACCEPTED." + System.lineSeparator()
									+ System.lineSeparator() + "Best regards," + System.lineSeparator()
									+ "Four Seasons Hotel Team");
				}

			}

			String checkIn, checkOut;

			for (BookingRequestForm request : bookingRequests) {
				formatter = verifyDateFormat(request.getCheckIn().charAt(1));
				formatter2 = verifyDateFormat(request.getCheckOut().charAt(1));
				checkIn = LocalDate.parse(request.getCheckIn(), formatter).toString();
				checkOut = LocalDate.parse(request.getCheckOut(), formatter2).toString();
				for (Accomodation accomodation : acceptedReservations) {
					if (checkIn.compareTo(accomodation.getCheckIn()) == 0
							&& checkOut.compareTo(accomodation.getCheckOut()) == 0) {
						bookingRequestRepository.delete(request);
					}

				}
			}

			Iterable<BookingRequestForm> notAcceptedReservations = bookingRequestRepository.findAll();
			for (BookingRequestForm request : notAcceptedReservations) {
				smtpMailSender.send(request.getUser().getEmail(), "Accomodation request for Prestige Suite",
						"Dear " + request.getUser().getFirstName() + "," + System.lineSeparator()
								+ System.lineSeparator() + "Unfortunetly, your reservation between "
								+ request.getCheckIn() + " and " + request.getCheckOut() + " was NOT accepted."
								+ System.lineSeparator() + System.lineSeparator() + "Best regards,"
								+ System.lineSeparator() + "Four Seasons Hotel Team");
			}

			bookingRequestRepository.delete(bookingRequestRepository.getByRoomRoomType("Prestige Suite"));
		}
		return "redirect:/accomodations/prestige";
	}

	@RequestMapping(path = "/accepted/reservations/deluxe", method = RequestMethod.GET)
	public String selectReservationsDeluxe() throws MessagingException {

		Iterable<BookingRequestForm> bookingRequests = null;
		bookingRequests = bookingRequestRepository.getByRoomRoomType("Deluxe");
		GreedyAlgorithm greedy = new GreedyAlgorithm();
		List<Accomodation> acceptedReservations = greedy.selectBestOption(bookingRequests);
		if (acceptedReservations != null) {
			Iterable<AccomodationDeluxe> acceptedInPastAccommodations = deluxeRepository.findAll();
			for (Accomodation accomodation : acceptedReservations) {
				AccomodationDeluxe deluxe = new AccomodationDeluxe();
				deluxe.setId(accomodation.getId());
				deluxe.setUser(accomodation.getUser());
				deluxe.setRoom(accomodation.getRoom());
				deluxe.setCheckIn(accomodation.getCheckIn());
				deluxe.setCheckOut(accomodation.getCheckOut());
				deluxe.setNrAdults(accomodation.getNrAdults());
				deluxe.setNrChildrens(accomodation.getNrChildrens());
				deluxe.setService(accomodation.getService());
				deluxe.setPriceAccomodation(accomodation.getPriceAccomodation());
				boolean ok = true;
				if (acceptedInPastAccommodations != null) {
					for (AccomodationDeluxe acceptedDeluxe : acceptedInPastAccommodations) {
						if (deluxe.getCheckIn().compareTo(acceptedDeluxe.getCheckIn()) >= 0
								&& deluxe.getCheckOut().compareTo(acceptedDeluxe.getCheckOut()) <= 0) {
							ok = false;
							break;
						}
					}
				}

				Accomodation ac = new Accomodation();
				ac.setId(accomodation.getId());
				ac.setUser(accomodation.getUser());
				ac.setRoom(accomodation.getRoom());
				ac.setCheckIn(accomodation.getCheckIn());
				ac.setCheckOut(accomodation.getCheckOut());
				ac.setNrAdults(accomodation.getNrAdults());
				ac.setNrChildrens(accomodation.getNrChildrens());
				ac.setService(accomodation.getService());
				ac.setPriceAccomodation(accomodation.getPriceAccomodation());
				if (ok) {
					deluxeRepository.save(deluxe);
					accomodationRepository.save(ac);

					smtpMailSender.send(deluxe.getUser().getEmail(), "Accomodation request for Deluxe",
							"Dear " + deluxe.getUser().getFirstName() + "," + System.lineSeparator()
									+ System.lineSeparator() + "Your reservation between " + deluxe.getCheckIn()
									+ " and " + deluxe.getCheckOut() + " was ACCEPTED." + System.lineSeparator()
									+ System.lineSeparator() + "Best regards," + System.lineSeparator()
									+ "Four Seasons Hotel Team");
				}

			}

			String checkIn, checkOut;

			for (BookingRequestForm request : bookingRequests) {
				formatter = verifyDateFormat(request.getCheckIn().charAt(1));
				formatter2 = verifyDateFormat(request.getCheckOut().charAt(1));
				checkIn = LocalDate.parse(request.getCheckIn(), formatter).toString();
				checkOut = LocalDate.parse(request.getCheckOut(), formatter2).toString();
				for (Accomodation accomodation : acceptedReservations) {
					if (checkIn.compareTo(accomodation.getCheckIn()) == 0
							&& checkOut.compareTo(accomodation.getCheckOut()) == 0) {
						bookingRequestRepository.delete(request);
					}

				}
			}
			Iterable<BookingRequestForm> notAcceptedReservations = bookingRequestRepository.findAll();

			for (BookingRequestForm request : notAcceptedReservations) {
				smtpMailSender.send(request.getUser().getEmail(), "Accomodation request for Deluxe",
						"Dear " + request.getUser().getFirstName() + "," + System.lineSeparator()
								+ System.lineSeparator() + "Unfortunetly, your reservation between "
								+ request.getCheckIn() + " and " + request.getCheckOut() + " was NOT accepted."
								+ System.lineSeparator() + System.lineSeparator() + "Best regards,"
								+ System.lineSeparator() + "Four Seasons Hotel Team");
			}

			bookingRequestRepository.delete(bookingRequests);
		}
		return "redirect:/accomodations/deluxe";
	}

	@RequestMapping(path = "/accepted/reservations/junior", method = RequestMethod.GET)
	public String selectReservationsJunior() throws MessagingException {

		Iterable<BookingRequestForm> bookingRequests = null;
		bookingRequests = bookingRequestRepository.getByRoomRoomType("Junior Suite");
		GreedyAlgorithm greedy = new GreedyAlgorithm();
		List<Accomodation> acceptedReservations = greedy.selectBestOption(bookingRequests);
		if (acceptedReservations != null) {
			Iterable<AccomodationJunior> acceptedInPastAccommodations = juniorRepository.findAll();
			for (Accomodation accomodation : acceptedReservations) {
				AccomodationJunior junior = new AccomodationJunior();
				junior.setId(accomodation.getId());
				junior.setUser(accomodation.getUser());
				junior.setRoom(accomodation.getRoom());
				junior.setCheckIn(accomodation.getCheckIn());
				junior.setCheckOut(accomodation.getCheckOut());
				junior.setNrAdults(accomodation.getNrAdults());
				junior.setNrChildrens(accomodation.getNrChildrens());
				junior.setService(accomodation.getService());
				junior.setPriceAccomodation(accomodation.getPriceAccomodation());
				boolean ok = true;
				if (acceptedInPastAccommodations != null) {
					for (AccomodationJunior acceptedJunior : acceptedInPastAccommodations) {
						if (junior.getCheckIn().compareTo(acceptedJunior.getCheckIn()) >= 0
								&& junior.getCheckOut().compareTo(acceptedJunior.getCheckOut()) <= 0) {
							ok = false;
							break;
						}
					}
				}

				Accomodation ac = new Accomodation();
				ac.setId(accomodation.getId());
				ac.setUser(accomodation.getUser());
				ac.setRoom(accomodation.getRoom());
				ac.setCheckIn(accomodation.getCheckIn());
				ac.setCheckOut(accomodation.getCheckOut());
				ac.setNrAdults(accomodation.getNrAdults());
				ac.setNrChildrens(accomodation.getNrChildrens());
				ac.setService(accomodation.getService());
				ac.setPriceAccomodation(accomodation.getPriceAccomodation());
				if (ok) {
					accomodationRepository.save(ac);
					juniorRepository.save(junior);

					smtpMailSender.send(junior.getUser().getEmail(), "Accomodation request for Junior Suite",
							"Dear " + junior.getUser().getFirstName() + "," + System.lineSeparator()
									+ System.lineSeparator() + "Your reservation between " + junior.getCheckIn()
									+ " and " + junior.getCheckOut() + " was ACCEPTED." + System.lineSeparator()
									+ "Best regards," + System.lineSeparator() + "Four Seasons Hotel Team");

				}
			}

			String checkIn, checkOut;

			for (BookingRequestForm request : bookingRequests) {
				formatter = verifyDateFormat(request.getCheckIn().charAt(1));
				formatter2 = verifyDateFormat(request.getCheckOut().charAt(1));
				checkIn = LocalDate.parse(request.getCheckIn(), formatter).toString();
				checkOut = LocalDate.parse(request.getCheckOut(), formatter2).toString();
				for (Accomodation accomodation : acceptedReservations) {
					if (checkIn.compareTo(accomodation.getCheckIn()) == 0
							&& checkOut.compareTo(accomodation.getCheckOut()) == 0) {
						bookingRequestRepository.delete(request);
					}

				}
			}

			Iterable<BookingRequestForm> notAcceptedReservations = bookingRequestRepository.findAll();
			System.out.println(notAcceptedReservations);
			for (BookingRequestForm request : notAcceptedReservations) {
				smtpMailSender.send(request.getUser().getEmail(), "Accomodation request for Junior Suite",
						"Dear " + request.getUser().getFirstName() + "," + System.lineSeparator()
								+ System.lineSeparator() + "Unfortunetly, your reservation between "
								+ request.getCheckIn() + " and " + request.getCheckOut() + " was NOT accepted."
								+ System.lineSeparator() + System.lineSeparator() + "Best regards,"
								+ System.lineSeparator() + "Four Seasons Hotel Team");
			}

			bookingRequestRepository.delete(bookingRequests);
		}

		return "redirect:/accomodations/junior";
	}

	@RequestMapping(path = "/accepted/reservations/golden", method = RequestMethod.GET)
	public String selectReservationsGolden() throws MessagingException {

		Iterable<BookingRequestForm> bookingRequests = null;
		bookingRequests = bookingRequestRepository.getByRoomRoomType("Golden Suite");
		GreedyAlgorithm greedy = new GreedyAlgorithm();
		List<Accomodation> acceptedReservations = greedy.selectBestOption(bookingRequests);
		if (acceptedReservations != null) {
			Iterable<AccomodationGolden> acceptedInPastAccommodations = goldenRepository.findAll();
			for (Accomodation accomodation : acceptedReservations) {
				AccomodationGolden golden = new AccomodationGolden();
				golden.setId(accomodation.getId());
				golden.setUser(accomodation.getUser());
				golden.setRoom(accomodation.getRoom());
				golden.setCheckIn(accomodation.getCheckIn());
				golden.setCheckOut(accomodation.getCheckOut());
				golden.setNrAdults(accomodation.getNrAdults());
				golden.setNrChildrens(accomodation.getNrChildrens());
				golden.setService(accomodation.getService());
				golden.setPriceAccomodation(accomodation.getPriceAccomodation());
				boolean ok = true;
				if (acceptedInPastAccommodations != null) {
					for (AccomodationGolden acceptedGolden : acceptedInPastAccommodations) {
						if (golden.getCheckIn().compareTo(acceptedGolden.getCheckIn()) >= 0
								&& golden.getCheckOut().compareTo(acceptedGolden.getCheckOut()) <= 0) {
							ok = false;
							break;
						}
					}
				}

				Accomodation ac = new Accomodation();
				ac.setId(accomodation.getId());
				ac.setUser(accomodation.getUser());
				ac.setRoom(accomodation.getRoom());
				ac.setCheckIn(accomodation.getCheckIn());
				ac.setCheckOut(accomodation.getCheckOut());
				ac.setNrAdults(accomodation.getNrAdults());
				ac.setNrChildrens(accomodation.getNrChildrens());
				ac.setService(accomodation.getService());
				ac.setPriceAccomodation(accomodation.getPriceAccomodation());
				if (ok) {
					accomodationRepository.save(ac);
					goldenRepository.save(golden);

					smtpMailSender.send(golden.getUser().getEmail(), "Accomodation request for Golden Suite",
							"Dear " + golden.getUser().getFirstName() + "," + System.lineSeparator()
									+ System.lineSeparator() + "Your reservation between " + golden.getCheckIn()
									+ " and " + golden.getCheckOut() + " was ACCEPTED." + System.lineSeparator()
									+ System.lineSeparator() + "Best regards," + System.lineSeparator()
									+ "Four Seasons Hotel Team");
				}
			}

			String checkIn, checkOut;

			for (BookingRequestForm request : bookingRequests) {
				formatter = verifyDateFormat(request.getCheckIn().charAt(1));
				formatter2 = verifyDateFormat(request.getCheckOut().charAt(1));
				checkIn = LocalDate.parse(request.getCheckIn(), formatter).toString();
				checkOut = LocalDate.parse(request.getCheckOut(), formatter2).toString();
				for (Accomodation accomodation : acceptedReservations) {
					if (checkIn.compareTo(accomodation.getCheckIn()) == 0
							&& checkOut.compareTo(accomodation.getCheckOut()) == 0) {
						bookingRequestRepository.delete(bookingRequests);
					}

				}
			}

			Iterable<BookingRequestForm> notAcceptedReservations = bookingRequestRepository.findAll();
			for (BookingRequestForm request : notAcceptedReservations) {
				smtpMailSender.send(request.getUser().getEmail(), "Accomodation request for Golden Suite",
						"Dear " + request.getUser().getFirstName() + "," + System.lineSeparator()
								+ System.lineSeparator() + "Unfortunetly, your reservation between "
								+ request.getCheckIn() + " and " + request.getCheckOut() + " was NOT accepted."
								+ System.lineSeparator() + System.lineSeparator() + "Best regards,"
								+ System.lineSeparator() + "Four Seasons Hotel Team");
			}

			bookingRequestRepository.delete(bookingRequests);
		}
		return "redirect:/accomodations/golden";
	}

	@RequestMapping("/prestige")
	public ModelAndView accomodationsPrestige() {
		Iterable<AccomodationPrestige> accomodations = prestigeRepository.findAll();

		return new ModelAndView("admin/accomodations-prestige", "accomodations", accomodations);
	}

	@RequestMapping("/deluxe")
	public ModelAndView accomodationsDeluxe() {
		Iterable<AccomodationDeluxe> accomodations = deluxeRepository.findAll();
		return new ModelAndView("admin/accomodations-deluxe", "accomodations", accomodations);
	}

	@RequestMapping("/junior")
	public ModelAndView accomodationsJunior() {
		Iterable<AccomodationJunior> accomodations = juniorRepository.findAll();
		return new ModelAndView("admin/accomodations-junior", "accomodations", accomodations);
	}

	@RequestMapping("/golden")
	public ModelAndView accomodationsGolden() {
		Iterable<AccomodationGolden> accomodations = goldenRepository.findAll();
		return new ModelAndView("admin/accomodations-golden", "accomodations", accomodations);
	}

	public DateTimeFormatter verifyDateFormat(Character character) {

		DateTimeFormatter formatter;
		int res;
		res = character.compareTo(' ');
		if (res == 0) {
			formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy", Locale.ENGLISH);
		} else {
			formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy", Locale.ENGLISH);
		}

		return formatter;

	}
}
