package CSE310.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.hibernate.annotations.CurrentTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private int quantity;
	private int cost;
	@CurrentTimestamp
	private LocalDateTime time;
	private String product;
	private String username;
	private boolean isbuyed;

	public Order() {
	}

	public Order(String user, String products, int quantity, int cost) {
		super();
		this.product = products;
		this.username = user;
		this.quantity = quantity;
		this.cost = cost * quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getTime() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		return time.format(format);
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public boolean isIsbuyed() {
		return isbuyed;
	}

	public void setIsbuyed(boolean isbuyed) {
		this.isbuyed = isbuyed;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Order [time=" + time + ", product=" + product + ", user=" + username + "]";
	}
}
