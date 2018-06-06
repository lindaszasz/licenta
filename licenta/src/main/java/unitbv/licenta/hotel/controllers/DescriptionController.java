package unitbv.licenta.hotel.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DescriptionController {
	
	@RequestMapping("/roomsDescription")
	public String root() {
		return "roomsC";
	}
	
	@RequestMapping("/golden") 
	public String infoGoldenRoom() {
		return "rooms/golden";
	}
    
	@RequestMapping("/deluxe")
	public String infoDeluxeRoom() {
		return "rooms/deluxe";
	}
	
	@RequestMapping("/prestige")
    public String infoPrestigeRoom() {
		return "rooms/prestige";
	}
	
	@RequestMapping("/junior")
	public String infoJuniorRoom() {
		return "rooms/junior";
	}
	
	@RequestMapping("/deluxe/gallery")
	public String galleryDeluxe() {
		return "rooms/deluxeGallery";
	}
	
	@RequestMapping("/golden/gallery")
	public String galleryGolden() {
		return "rooms/goldenGallery";
	}
	
	@RequestMapping("/prestige/gallery")
	public String galleryPrestige() {
		return "rooms/prestigeGallery";
	}
	
	@RequestMapping("/junior/gallery")
	public String galleryJunior() {
		return "rooms/juniorGallery";
	}
}
