package unitbv.licenta.hotel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import unitbv.licenta.hotel.models.Client;
import unitbv.licenta.hotel.repositories.ClientRepository;

@Controller
@RequestMapping("/clients")
public class ClientController {

	@Autowired
	private ClientRepository clientRepository;

	@RequestMapping
	public ModelAndView getClients(@RequestParam(required = false) String name) {
		Iterable<Client> clients;
		if (name != null && name != "") {
			name = name.trim();
			clients = clientRepository.getByLastNameOrFirstName(name, name);
		} else {
			clients = clientRepository.findAll();
		}

		return new ModelAndView("clients", "clients", clients);
	}

	@RequestMapping(path = "show", method = RequestMethod.GET)
	public ModelAndView getClient(@RequestParam(value = "id", required = false) Long id) {
		Client client = id != null ? clientRepository.findOne(id) : new Client();
		return new ModelAndView("client", "client", client);
	}

	@RequestMapping(path = "/add", method = RequestMethod.GET)
	public String addClient(@RequestParam(value = "firstName", defaultValue = "test") String firstName,
			@RequestParam(value = "lastName", defaultValue = "") String lastName,
			@RequestParam(value = "cnp", required = false) String cnp, @RequestParam(value = "phone") String phone,
			@RequestParam(value = "email") String email) {

		Client client = new Client();
		client.setFirstName(firstName);
		client.setLastName(lastName);
		client.setCnp(cnp);
		client.setPhone(phone);
		client.setEmail(email);

		clientRepository.save(client);
		return "redirect:/clients";
		// return "Client was saved";
	}

	@RequestMapping(path = "/update", method = RequestMethod.GET)
	public String updateClient(@RequestParam(value = "id") long id, @RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName, @RequestParam(value = "cnp") String cnp,
			@RequestParam(value = "phone") String phone, @RequestParam(value = "email") String email) {

		Client client = clientRepository.findOne(id);

		client.setFirstName(firstName);
		client.setLastName(lastName);
		client.setCnp(cnp);
		client.setPhone(phone);
		client.setEmail(email);

		clientRepository.save(client);
		return "redirect:/clients";
		// return "Client was updated";

	}

	@RequestMapping(path = "/delete", method = RequestMethod.GET)
	public String deleteClient(@RequestParam(value = "id") long id) {
		clientRepository.delete(id);
		return "redirect:/clients";
		// return "Client was deleted";
	}

}
