package shopping.dao;

import java.util.List;

import shopping.domain.ShoppingCart;

public interface ShoppingCartDao {
	ShoppingCart findByPk (long pk);
	
	List<ShoppingCart> findAll();

	void create(ShoppingCart shoppingCart);
	
	void modify(ShoppingCart shoppingCart);
	
	void remove(int pk);
}
