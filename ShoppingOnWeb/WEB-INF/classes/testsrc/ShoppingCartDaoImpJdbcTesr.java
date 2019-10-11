import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shopping.dao.ShoppingCartDao;
import shopping.dao.imp.ShoppingCartDaoImpJdbc;
import shopping.domain.Inventory;
import shopping.domain.Orders;
import shopping.domain.ShoppingCart;

class ShoppingCartDaoImpJdbcTesr {
	ShoppingCartDao dao;
	@BeforeEach
	void setUp() throws Exception {
		dao = new ShoppingCartDaoImpJdbc();
	}

	@AfterEach
	void tearDown() throws Exception {
		dao=null;
	}

	@Test
	void testFindByPk() {
		ShoppingCart shoppingCart =dao.findByPk(1);
		assertNotNull(shoppingCart);
		assertEquals(1, shoppingCart.getId());
		assertEquals(3, shoppingCart.getGoods().getId());
		assertEquals("10", shoppingCart.getOrders().getId());
		assertEquals(2, shoppingCart.getQuantity());
		assertEquals(5543, shoppingCart.getSubTotal());
		
		
		
	}
//	
	@Test
	void testFindAll() {
		List<ShoppingCart> list =dao.findAll();
		ShoppingCart shoppingCart =list.get(0);
		assertEquals(list.size(), 1);
		assertNotNull(shoppingCart);
		assertEquals(1, shoppingCart.getId());
		assertEquals(3, shoppingCart.getGoods().getId());
		assertEquals("10", shoppingCart.getOrders().getId());
		assertEquals(2, shoppingCart.getQuantity());
		assertEquals(5543, shoppingCart.getSubTotal());
		
	}

//	@Test
//	void testCreate() {
//		ShoppingCart shoppingCart =new ShoppingCart();
//		shoppingCart.setId(2);
//		shoppingCart.setQuantity(3);
//		shoppingCart.setSubTotal(2020);
//		
//		Orders orders = new Orders();
//		orders.setId("23");			//要預先建立orderid=23
//		shoppingCart.setOrders(orders);
//		
//		Inventory inventory =new Inventory();
//		inventory.setId(5);			//要預先建立inventoryid=5
//		shoppingCart.setGoods(inventory);
//		
//		dao.create(shoppingCart);
//		ShoppingCart shoppingCart2 =dao.findByPk(2);
//		assertEquals(2, shoppingCart2.getId());
//		assertEquals(5, shoppingCart2.getGoods().getId());
//		assertEquals("23", shoppingCart2.getOrders().getId());
//		assertEquals(3, shoppingCart2.getQuantity());
//		assertEquals(2020, shoppingCart2.getSubTotal());
//		
//	}

	@Test
	void testModify() {
		ShoppingCart shoppingCart =new ShoppingCart();
		shoppingCart.setId(2);
		shoppingCart.setQuantity(5);
		shoppingCart.setSubTotal(22520);
		
		Orders orders = new Orders();
		orders.setId("23");			//要預先建立orderid=23
		shoppingCart.setOrders(orders);
		
		Inventory inventory =new Inventory();
		inventory.setId(5);			//要預先建立inventoryid=5
		shoppingCart.setGoods(inventory);
		dao.modify(shoppingCart);
		
		ShoppingCart shoppingCart2 =dao.findByPk(2);
		assertEquals(2, shoppingCart2.getId());
		assertEquals(5, shoppingCart2.getGoods().getId());
		assertEquals("23", shoppingCart2.getOrders().getId());
		assertEquals(5, shoppingCart2.getQuantity());
		assertEquals(22520, shoppingCart2.getSubTotal());
		
	}

	@Test
	void testRemove() {
		dao.remove(2);
	ShoppingCart shoppingCart2 =dao.findByPk(2);
	assertNull(shoppingCart2);
	}
}
