package TphonesShop.model;

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
	private double cost;
	private double totalCost;
	private String product;
	private String username;
	private boolean isbuyed = false;
	private String image;
	@CurrentTimestamp
	private LocalDateTime time;

	public Order() {
	}

	public Order(String user, String products, int quantity, double cost, String image) {
		super();
		this.product = products;
		this.username = user;
		this.quantity = quantity;
		this.cost = cost;
		this.image = image;
		setTotalCost(cost);
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

	public double getCost() {
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

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = cost * quantity;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Order [time=" + time + ", product=" + product + ", user=" + username + "]";
	}
}
