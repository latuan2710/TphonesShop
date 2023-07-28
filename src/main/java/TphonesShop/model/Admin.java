package TphonesShop.model;

import TphonesShop.security.PasswordSecurity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private long id;
	private String username;
	private String password;

	public Admin() {
	}

	public Admin(String username, String password) {
		super();
		this.username = username;
		setPassword(password);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		PasswordSecurity passwordSecurity = new PasswordSecurity();
		return passwordSecurity.decode(password);

	}

	public void setPassword(String password) {
		PasswordSecurity passwordSecurity = new PasswordSecurity();
		this.password = passwordSecurity.encode(password);
	}

}
