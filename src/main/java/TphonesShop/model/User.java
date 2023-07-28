package TphonesShop.model;

import TphonesShop.security.PasswordSecurity;
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
	private String address;
	private String username;
	private String password;
	private String email;

	public User() {
	}

	public User(String address, String username, String password, String email) {
		super();
		this.address = address;
		this.username = username;
		setPassword(password);
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		PasswordSecurity passwordSecurity = new PasswordSecurity();
		return passwordSecurity.decode(this.password);
	}

	public void setPassword(String password) {
		PasswordSecurity passwordSecurity = new PasswordSecurity();
		this.password = passwordSecurity.encode(password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "user:" + id;
	}

}
