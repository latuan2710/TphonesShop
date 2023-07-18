package TphonesShop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private int quantity;
	private int cost;
	private String description;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] image;
	private String brand;

	public Product() {
		super();
	}

	public Product(String name, int quantity, int cost, String description) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.cost = cost;
		this.description = description;
	}

	public Product(String name, int quantity, int cost, String description, byte[] imageData, String brand) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.cost = cost;
		this.description = description;
		this.image = imageData;
		this.brand = brand;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getImageData() {
		return image;
	}

	public void setImageData(byte[] imageData) {
		this.image = imageData;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", quantity=" + quantity + ", cost=" + cost + ", description="
				+ description + ", brand=" + brand + "]";
	}
}
