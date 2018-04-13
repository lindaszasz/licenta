package unitbv.licenta.hotel.models;

import java.util.List;

import javax.persistence.*;

@Entity
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String cnp;

	@Column
	private String phone;

	@Column
	private String email;

	
	@OneToMany(mappedBy = "client")
	private List<Accomodation> bookings;

	public Client() {

	}

	public Client(long id, String firstName, String lastName, String cnp, String phone, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cnp = cnp;
		this.phone = phone;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCnp() {
		return cnp;
	}

	public void setCnp(String cnp) {
		this.cnp = cnp;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Accomodation> getBookings() {
		return bookings;
	}

	public void setBookings(List<Accomodation> bookings) {
		this.bookings = bookings;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", cnp=" + cnp + ", phone="
				+ phone + ", email=" + email + ", bookings=" + bookings + "]";
	}

}
