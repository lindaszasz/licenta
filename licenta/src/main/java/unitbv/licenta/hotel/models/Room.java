package unitbv.licenta.hotel.models;

import javax.persistence.*;

@Entity
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String roomType;

	@Column
	private int nrAdults;

	@Column
	private int nrChildrens;

	@Column
	private double priceNight;

	public Room() {

	}

	public Room(long id, String roomType, int nrAdults, int nrChildrens, double priceNight) {
		super();
		this.id = id;
		this.roomType = roomType;
		this.nrAdults = nrAdults;
		this.nrChildrens = nrChildrens;
		this.priceNight = priceNight;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
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

	public double getPriceNight() {
		return priceNight;
	}

	public void setPriceNight(double priceNight) {
		this.priceNight = priceNight;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", roomType=" + roomType + ", nrAdults=" + nrAdults + ", nrChildrens=" + nrChildrens
				+ ", priceNight=" + priceNight + "]";
	}

}
