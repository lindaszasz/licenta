package unitbv.licenta.hotel.models;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Accomodation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room_id")
	private Room room;

	@Column
	private String checkIn;

	@Column
	private String checkOut;

	@Column
	private int nrAdults;

	@Column
	private int nrChildrens;

	@Column
	private double priceAccomodation;

	public Accomodation() {

	}

	public Accomodation(long id, User user, Room room, String checkIn, String checkOut, int nrAdults, int nrChildrens,
			double priceAccomodation) {
		super();
		this.id = id;
		this.user = user;
		this.room = room;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.nrAdults = nrAdults;
		this.nrChildrens = nrChildrens;
		this.priceAccomodation = priceAccomodation;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

	public String getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}

	public int getNrAdults() {
		return nrAdults;
	}

	public void setNrAdults(int nrAdults) {
		this.nrAdults = nrAdults;
	}

	public int getNrChildrens() {
		return nrChildrens;
	}

	public void setNrChildrens(int nrChildrens) {
		this.nrChildrens = nrChildrens;
	}

	public double getPriceAccomodation() {
		return priceAccomodation;
	}

	public void setPriceAccomodation(double priceAccomodation) {
		this.priceAccomodation = priceAccomodation;
	}

	@Override
	public String toString() {
		return "Accomodation [id=" + id + ", user=" + user + ", room=" + room + ", checkIn=" + checkIn + ", checkOut="
				+ checkOut + ", nrAdults=" + nrAdults + ", nrChildrens=" + nrChildrens + ", priceAccomodation="
				+ priceAccomodation + "]";
	}

}
