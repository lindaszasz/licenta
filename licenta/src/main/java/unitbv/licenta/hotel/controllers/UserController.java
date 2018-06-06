package unitbv.licenta.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import unitbv.licenta.hotel.models.User;
import unitbv.licenta.hotel.repositories.UserRepository;

@Controller
@RequestMapping("/clients")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping
	public ModelAndView getUsers(@RequestParam(required = false) String name) {
		Iterable<User> users;
		if (name != null && name != "") {
			name = name.trim();
			users = userRepository.getByLastNameOrFirstName(name, name);
		} else {
			users = userRepository.findAll();
		}

		return new ModelAndView("admin/clients", "users", users);	
		
	}

	@RequestMapping(path = "show", method = RequestMethod.GET)
	public ModelAndView getUser(@RequestParam(value = "id", required = false) Long id) {
		User user = id != null ? userRepository.findOne(id) : new User();
		return new ModelAndView("admin/client", "user", user);
	}
	
	@RequestMapping(path = "showForHeader", method = RequestMethod.GET)
	public ModelAndView getUserForHeader(@RequestParam(value = "id", required = false) Long id) {
		User user = userRepository.findOne(id);
		return new ModelAndView("admin/headerAdmin", "user", user);
	}

	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String addUser(@RequestParam(value = "firstName", defaultValue = "test") String firstName,
			@RequestParam(value = "lastName", defaultValue = "") String lastName,
			@RequestParam(value = "cnp", required = false) String cnp, @RequestParam(value = "phone") String phone,
			@RequestParam(value = "email") String email, @RequestParam(value = "password") String password) {

		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setCnp(cnp);
		user.setPhone(phone);
		user.setEmail(email);
		user.setPassword(password);

		userRepository.save(user);
		return "redirect:/clients";
		// return "Client was saved";
	}

	@RequestMapping(path = "/update", method = RequestMethod.GET)
	public String updateUser(@RequestParam(value = "id") long id, @RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName, @RequestParam(value = "cnp") String cnp,
			@RequestParam(value = "phone") String phone, @RequestParam(value = "email") String email
			) {
		//@RequestParam(value = "password", required=false) String password
		User user = userRepository.findOne(id);

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setCnp(cnp);
		user.setPhone(phone);
		user.setEmail(email);
		//user.setPassword(password);

		userRepository.save(user);
		return "redirect:/clients";
		// return "Client was updated";

	}

	@RequestMapping(path = "/delete", method = RequestMethod.GET)
	public String deleteUser(@RequestParam(value = "id") long id) {
		userRepository.delete(id);
		return "redirect:/clients";
		// return "Client was deleted";
	}

}
