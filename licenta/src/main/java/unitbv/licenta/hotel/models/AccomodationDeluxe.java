package unitbv.licenta.hotel.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AccomodationDeluxe {
	
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
	
	@ManyToOne	
	@JoinColumn(name = "service_id")
	private Service service;

	@Column
	private double priceAccomodation;
	
	public AccomodationDeluxe() {
		
	}

	public AccomodationDeluxe(long id, User user, Room room, String checkIn, String checkOut, int nrAdults,
			int nrChildrens, Service service, double priceAccomodation) {
		super();
		this.id = id;
		this.user = user;
		this.room = room;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.nrAdults = nrAdults;
		this.nrChildrens = nrChildrens;
		this.service = service;
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

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public double getPriceAccomodation() {
		return priceAccomodation;
	}

	public void setPriceAccomodation(double priceAccomodation) {
		this.priceAccomodation = priceAccomodation;
	}

	@Override
	public String toString() {
		return "AccomodationDeluxe [id=" + id + ", user=" + user + ", room=" + room + ", checkIn=" + checkIn
				+ ", checkOut=" + checkOut + ", nrAdults=" + nrAdults + ", nrChildrens=" + nrChildrens + ", service="
				+ service + ", priceAccomodation=" + priceAccomodation + "]";
	}
	
	


}
