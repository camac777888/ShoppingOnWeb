package shopping.dao;

import java.util.List;

import shopping.domain.Orders;

public interface OrderDao {
	Orders findByPk (String pk);
	
	List<Orders> findAll();

	void create(Orders orders);
	
	void modify(Orders orders);
	
	void remove(String pk);
	
}
