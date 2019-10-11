package shopping.domain;

public class ShoppingCart {
	
    private long id;
  
    private int quantity;

    private double subTotal;
   
    private Orders orders; 

    private Inventory goods;

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

	public double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public Inventory getGoods() {
		return goods;
	}

	public void setGoods(Inventory goods) {
		this.goods = goods;
	}

}
