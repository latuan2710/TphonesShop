package TphonesShop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String brand;
	private double cost;
	private double final_cost;
	private boolean isSale;
	private int discount;
	private String description;
	private String image;

	public Product() {
	}

	public Product(String name, String brand, double cost, boolean isSale, int discount,
			String description, String image) {
		super();
		this.name = name;
		this.brand = brand;
		this.cost = cost;
		this.isSale = isSale;
		this.discount = discount;
		this.description = description;
		this.image = image;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getFinal_cost() {
		return final_cost;
	}

	public void setFinal_cost() {
		if (isSale) {
			this.final_cost = this.cost * (discount / 100);
		} else {
			this.final_cost = this.cost;
		}
	}

	public boolean isSale() {
		return isSale;
	}

	public void setSale(boolean isSale) {
		this.isSale = isSale;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", brand=" + brand + ", cost=" + cost
				+ ", isSale=" + isSale + ", discount=" + discount + ", description=" + description + ", image=" + image
				+ "]";
	}

}
