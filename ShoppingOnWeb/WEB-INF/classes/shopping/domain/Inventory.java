package shopping.domain;

	public class Inventory {
	private long id;
	private String name;
	private double price;
	private String description;
	private String brand;
	private String cpuBrand;
	private String cpuType;
	private String memoryCapacity;
	private String hdCapacity;
	private String cardModel;
	private String displaysize;
	private String image;
	
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCpuBrand() {
		return cpuBrand;
	}
	public void setCpuBrand(String cpuBrand) {
		this.cpuBrand = cpuBrand;
	}
	public String getCpuType() {
		return cpuType;
	}
	public void setCpuType(String cpuType) {
		this.cpuType = cpuType;
	}
	public String getMemoryCapacity() {
		return memoryCapacity;
	}
	public void setMemoryCapacity(String memoryCapacity) {
		this.memoryCapacity = memoryCapacity;
	}
	public String getHdCapacity() {
		return hdCapacity;
	}
	public void setHdCapacity(String hdCapacity) {
		this.hdCapacity = hdCapacity;
	}
	public String getCardModel() {
		return cardModel;
	}
	public void setCardModel(String cardModel) {
		this.cardModel = cardModel;
	}
	public String getDisplaysize() {
		return displaysize;
	}
	public void setDisplaysize(String displaysize) {
		this.displaysize = displaysize;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	
	}
