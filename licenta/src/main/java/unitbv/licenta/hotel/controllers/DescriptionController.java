package unitbv.licenta.hotel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DescriptionController {
	
	@RequestMapping("/roomsDescription")
	public String root() {
		return "roomsC";
	}

}
