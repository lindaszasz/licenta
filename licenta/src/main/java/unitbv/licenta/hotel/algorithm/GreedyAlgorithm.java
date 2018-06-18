package unitbv.licenta.hotel.algorithm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import unitbv.licenta.hotel.models.Accomodation;
import unitbv.licenta.hotel.models.BookingRequestForm;

public class GreedyAlgorithm {

	public List<Accomodation> selectBestOption(Iterable<BookingRequestForm> bookingRequests) {

		int n = 0;

		Iterator<BookingRequestForm> it = bookingRequests.iterator();
		while (it.hasNext()) {
			n++;
			it.next();
		}

		String s[] = new String[n];
		String t[] = new String[n];
		int o[] = new int[n];

		Long id[] = new Long[n];
		Long user[] = new Long[n];
		Long room[] = new Long[n];
		int nrAdults[] = new int[n];
		int nrChildrens[] = new int[n];
		Long service[] = new Long[n];

		int howManyDays[] = new int[n];
		double amount[] = new double[n];
		double income = 0;

		int i = 0;
		for (BookingRequestForm bq : bookingRequests) {

			DateTimeFormatter formatter = verifyDateFormat(bq.getCheckIn().charAt(1));
			DateTimeFormatter formatter2 = verifyDateFormat(bq.getCheckOut().charAt(1));

			s[i] = LocalDate.parse(bq.getCheckIn(), formatter).toString();
			t[i] = LocalDate.parse(bq.getCheckOut(), formatter2).toString();
			
			amount[i] = bq.getPriceAccomodation();
			o[i] = i;

			id[i] = bq.getId();
			user[i] = bq.getUser().getId();
			room[i] = bq.getRoom().getId();
			nrAdults[i] = bq.getNrAdults();
			nrChildrens[i] = bq.getNrChildrens();
			service[i] = bq.getService().getId();

			int days = 0;
			String sourceDate = s[i];
			while (sourceDate.compareTo(t[i]) < 0) {
				sourceDate = LocalDate.parse(sourceDate).plusDays(1).toString();
				days++;
			}

			howManyDays[i] = days;
			System.out.println();
			i++;
		}

		quicksort(t, s, o, amount, id, user, room, nrAdults, nrChildrens, service, howManyDays, 0, n - 1);

		int j = 0;
		int k = 0;
		String verifyCheckIn[] = new String[n];
		String verifyCheckOut[] = new String[n];
		List<String> finalReservations = new ArrayList<String>();

		List<Accomodation> acceptedReservations = new ArrayList<Accomodation>();
		Accomodation accomodation = new Accomodation();
		accomodation.setId(id[0]);
		for (BookingRequestForm bookingRequest : bookingRequests) {
			if (bookingRequest.getUser().getId() == user[0]) {
				accomodation.setUser(bookingRequest.getUser());
			}

			if (bookingRequest.getRoom().getId() == room[0]) {
				accomodation.setRoom(bookingRequest.getRoom());
			}

			if (bookingRequest.getService().getId() == service[0]) {
				accomodation.setService(bookingRequest.getService());
			}
		}
		accomodation.setCheckIn(s[0]);
		accomodation.setCheckOut(t[0]);
		accomodation.setNrAdults(nrAdults[0]);
		accomodation.setNrChildrens(nrChildrens[0]);
		accomodation.setPriceAccomodation(amount[0]);

		acceptedReservations.add(accomodation);

		finalReservations.add("Rezervarea " + o[0]);
		income += amount[0];
		verifyCheckIn[0] = s[0];
		verifyCheckOut[0] = t[0];

		boolean ok;
		for (int a = 1; a <= n - 1; a++) {
			if (t[a].compareTo(s[j]) <= 0 || s[a].compareTo(t[j]) >= 0) {
				ok = true;
				for (int index = 0; index < verifyCheckIn.length; index++) {
					if (verifyCheckIn[index] != null) {
						if (s[a].compareTo(verifyCheckIn[index]) > 0 && t[a].compareTo(verifyCheckOut[index]) < 0) {
							ok = false;
						}
					} else
						break;
				}

				if (ok) {
					k++;
					verifyCheckIn[k] = s[a];
					verifyCheckOut[k] = t[a];
					income += amount[a];
					Accomodation anotherAccomodation = new Accomodation();
					anotherAccomodation.setId(id[a]);
					for (BookingRequestForm bookingRequest : bookingRequests) {
						if (bookingRequest.getUser().getId() == user[a]) {
							anotherAccomodation.setUser(bookingRequest.getUser());
						}

						if (bookingRequest.getRoom().getId() == room[a]) {
							anotherAccomodation.setRoom(bookingRequest.getRoom());
						}

						if (bookingRequest.getService().getId() == service[a]) {
							anotherAccomodation.setService(bookingRequest.getService());
						}
					}
					anotherAccomodation.setCheckIn(s[a]);
					anotherAccomodation.setCheckOut(t[a]);
					anotherAccomodation.setNrAdults(nrAdults[a]);
					anotherAccomodation.setNrChildrens(nrChildrens[a]);
					anotherAccomodation.setPriceAccomodation(amount[a]);
					acceptedReservations.add(anotherAccomodation);
					finalReservations.add("Rezervarea " + o[a]);
				}

				j = a;
			}
		}

		for (String reservation : finalReservations) {
			System.out.println(reservation);
		}

		System.out.println();
		System.out.println("Income: " + income);

		// for (Accomodation acc : acceptedReservations) {
		// System.out.println(acc);
		// }

		return acceptedReservations;
	}

	public void quicksort(String t[], String s[], int o[], double amount[], Long[] id, Long[] user, Long[] room,
			int nrAdults[], int nrChildrens[], Long[] service, int howManyDays[], int p, int u) {
		if (p < u) {
			int m = partitionare(t, s, o, amount, id, user, room, nrAdults, nrChildrens, service, howManyDays, p, u);
			quicksort(t, s, o, amount, id, user, room, nrAdults, nrChildrens, service, howManyDays, p, m);
			quicksort(t, s, o, amount, id, user, room, nrAdults, nrChildrens, service, howManyDays, m + 1, u);
		}
	}

	public int partitionare(String t[], String s[], int o[], double amount[], Long[] id, Long[] user, Long[] room,
			int nrAdults[], int nrChildrens[], Long[] service, int howManyDays[], int p, int u) {
		int piv = howManyDays[p];
		int i = p;
		int j = u;
		String aux;
		double aux1;
		int aux2;
		Long aux3;

		while (i < j) {
			while (howManyDays[i] > piv) {
				i++;
			}

			while (howManyDays[j] < piv) {
				j--;
			}

			if (i < j) {
				aux = t[i];
				t[i] = t[j];
				t[j] = aux;

				aux = s[i];
				s[i] = s[j];
				s[j] = aux;

				aux2 = o[i];
				o[i] = o[j];
				o[j] = aux2;

				aux1 = amount[i];
				amount[i] = amount[j];
				amount[j] = aux1;

				aux3 = id[i];
				id[i] = id[j];
				id[j] = aux3;

				aux3 = user[i];
				user[i] = user[j];
				user[j] = aux3;

				aux3 = room[i];
				room[i] = room[j];
				room[j] = aux3;

				aux2 = nrAdults[i];
				nrAdults[i] = nrAdults[j];
				nrAdults[j] = aux2;

				aux2 = nrChildrens[i];
				nrChildrens[i] = nrChildrens[j];
				nrChildrens[j] = aux2;

				aux3 = service[i];
				service[i] = service[j];
				service[j] = aux3;

				aux2 = howManyDays[i];
				howManyDays[i] = howManyDays[j];
				howManyDays[j] = aux2;

				i++;
				j--;
			}
		}

		if (howManyDays[j] > piv)
			return j--;

		return j;
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