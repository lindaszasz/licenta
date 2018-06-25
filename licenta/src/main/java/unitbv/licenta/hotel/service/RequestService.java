package unitbv.licenta.hotel.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class RequestService {
	
	public double calculatePrice(String checkIn, String checkOut, double priceRoomType) {
		
		DateTimeFormatter formatter = verifyDateFormat(checkIn.charAt(1));
		DateTimeFormatter formatter2 = verifyDateFormat(checkOut.charAt(1));
		
		String arrivalDate = LocalDate.parse(checkIn, formatter).toString();
		String departureDate = LocalDate.parse(checkOut, formatter2).toString();
		int days = 0;
		
		while (arrivalDate.compareTo(departureDate) < 0) {
			arrivalDate = LocalDate.parse(arrivalDate).plusDays(1).toString();
			days++;
		}
		
		double price = days * priceRoomType;
		return price;
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
