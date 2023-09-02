package TphonesShop.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private long id;
	@Column(name = "Username", unique = true)
	private String username;
	@Column(name = "Password")
	private String password;
	@Column(name = "full_name")
	private String fullName;
	@Column(name = "phone", unique = true)
	private String phone;
	@Column(name = "email", unique = true)
	private String email;
	@Column(name = "address")
	private String address;
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	@Column(name = "status")
	private boolean status = true;
	@Column(name = "type")
	private String type;

	public User() {
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(final Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(final boolean status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

}
