package TphonesShop.model;

import java.text.DecimalFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "brand_id")
	@JsonIgnore
	private Brand brand;
	private int quantity;
	private double price;
	private double final_price;
	private double discount = 0;
	@Column(columnDefinition = "LONGTEXT")
	private String summary;
	@Column(columnDefinition = "LONGTEXT")
	private String description;
	private String featuredImage;

	public Product() {
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

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getFinal_price() {
		return final_price;
	}

	public String showFinal_price() {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(final_price);
	}

	public void setFinal_price() {
		if (discount == 0) {
			final_price = price;
		} else {
			final_price = price * ((100 - discount) / 100);
		}
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFeaturedImage() {
		return featuredImage;
	}

	public void setFeaturedImage(String image) {
		this.featuredImage = image;
	}

}
