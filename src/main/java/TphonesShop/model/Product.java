package TphonesShop.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrderDetail> orderDetails;

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
		this.orderDetails = new ArrayList<>();
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
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		String formattedValueString = decimalFormat.format(final_price);
		return Double.parseDouble(formattedValueString);
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

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

}
