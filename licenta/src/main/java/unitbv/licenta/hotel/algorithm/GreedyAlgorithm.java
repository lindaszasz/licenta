package unitbv.licenta.hotel.algorithm;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GreedyAlgorithm {

	public void selectBestOption(){
		
		Scanner sc = new Scanner(System.in);

		System.out.print("n=");
		int n = sc.nextInt();

		String s[] = new String[n];
		String t[] = new String[n];
		int o[] = new int[n];
		int howManyDays[] = new int[n];
		double amount[] = new double[n];
		double income = 0;

		System.out.println("Timpurile rezervarilor:");
		for (int i = 0; i <= n - 1; i++) {
			System.out.print("Data sosirii a rezervarii " + i + ":");
			s[i] = sc.next();
			System.out.print("Data plecarii a rezervarii " + i + ":");
			t[i] = sc.next();
			System.out.print("Pret rezervarii " + i + ":");
			amount[i] = sc.nextDouble();
			o[i] = i;

			int days = 0;
			String sourceDate = s[i];
			while (sourceDate.compareTo(t[i]) < 0) {
				sourceDate = LocalDate.parse(sourceDate).plusDays(1).toString();
				days++;
			}

			howManyDays[i] = days;
			System.out.println();
		}

		quicksort(t, s, o, amount, howManyDays, 0, n - 1);

		int j = 0;
		int k = 0;
		String verifyCheckIn[] = new String[n];
		String verifyCheckOut[] = new String[n];
		List<String> finalReservations = new ArrayList<String>();
		// int finalReservations[] = new int[n];

		// System.out.print("Rezervarea " + o[0]);
		finalReservations.add("Rezervarea " + o[0]);
		income += amount[0];
		verifyCheckIn[0] = s[0];
		verifyCheckOut[0] = t[0];

		// finalReservations[0] = o[0];

		boolean ok;
		for (int i = 1; i <= n - 1; i++) {
			if (t[i].compareTo(s[j]) <= 0 || s[i].compareTo(t[j]) >= 0) {
				ok = true;
				for (int index = 0; index < verifyCheckIn.length; index++) {
					if (verifyCheckIn[index] != null) {
						if (s[i].compareTo(verifyCheckIn[index]) > 0 && t[i].compareTo(verifyCheckOut[index]) < 0) {
							ok = false;
						}
					} else
						break;
				}

				if (ok) {
					k++;
					verifyCheckIn[k] = s[i];
					verifyCheckOut[k] = t[i];
					income += amount[i];
					finalReservations.add("Rezervarea " + o[i]);
					// finalReservations[k] = o[i];

					// if (k != 1) {
					// k++;
					// verifyCheckIn[k] = s[i];
					// verifyCheckOut[k] = t[i];
					// income += amount[i];
					// finalReservations[k] = o[i];
					// } else {
					// verifyCheckIn[k] = s[i];
					// verifyCheckOut[k] = t[i];
					// income += amount[i];
					// k++;
					// finalReservations[--k] = o[i];
					// System.out.println(k);
					// }
				}

				j = i;
			}
		}

		for (String reservation : finalReservations) {
			System.out.println(reservation);
		}
		// for (int i = 1; i < finalReservations.length; i++) {
		// System.out.print(" Rezervarea " + finalReservations[i]);
		// }

		System.out.println();
		System.out.println("Income: " + income);
	}

	public void quicksort(String t[], String s[], int o[], double amount[], int howManyDays[], int p, int u) {
		if (p < u) {
			int m = partitionare(t, s, o, amount, howManyDays, p, u);
			quicksort(t, s, o, amount, howManyDays, p, m);
			quicksort(t, s, o, amount, howManyDays, m + 1, u);
		}
	}

	public int partitionare(String t[], String s[], int o[], double amount[], int howManyDays[], int p, int u) {
		int piv = howManyDays[p];
		int i = p;
		int j = u;
		String aux;
		int aux2;
		double aux1;

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

}
