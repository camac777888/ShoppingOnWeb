package Servicelayer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shopping.dao.OrderDao;
import shopping.dao.ShoppingCartDao;
import shopping.dao.imp.OrderDaoImpJdbc;
import shopping.dao.imp.ShoppingCartDaoImpJdbc;
import shopping.domain.Orders;
import shopping.domain.ShoppingCart;
import shopping.service.OrderService;
import shopping.service.Imp.OrderServiceImp;

class OrderServiceImpTest {
	OrderService orderService ;
	OrderDao orderdao = new OrderDaoImpJdbc();
	ShoppingCartDao shoppingCartdao  = new ShoppingCartDaoImpJdbc();
	@BeforeEach
	void setUp() throws Exception {
		orderService = new OrderServiceImp();
		
		
		
	}

	@AfterEach
	void tearDown() throws Exception {
		orderService=null;
	}

	@Test
	void testSubmitOrder() {
		
		List<Map<String, Object>> cart = new ArrayList<Map<String, Object>>();//建立購物車
		Map<String, Object> item = new HashMap<String, Object>();
		item.put("goodsid",3L);
		item.put("quantity",2);
		cart.add(item);
		
		Map<String, Object> item1 = new HashMap<String, Object>();
		item1.put("goodsid",8L);
		item1.put("quantity",3);
		cart.add(item1);
		
		String orderid =orderService.SubmitOrder(cart); //提交定單生成訂單id
		assertNotNull(orderid);
		
		Orders orders = orderdao.findByPk(orderid);//從資料庫中提取訂單id的資料
		assertNotNull(orders);
		assertEquals(1, orders.getStatus());
		 
			double total = 3099 * 2 + 1888 * 3;
	        assertEquals(total, orders.getTotal());


	        List<ShoppingCart> list = shoppingCartdao.findAll();
	        List<ShoppingCart> ShoppingCartItemList = new ArrayList<ShoppingCart>();
	        for (ShoppingCart lineItem : list) {
	            if (lineItem.getOrders().getId().equals(orderid)) {
	            	ShoppingCartItemList.add(lineItem);

	                if (lineItem.getGoods().getId() == 3L) {
	                    assertEquals(2, lineItem.getQuantity());
	                    assertEquals(3099 * 2, lineItem.getSubTotal());
	                }

	                if (lineItem.getGoods().getId() == 8L) {
	                    assertEquals(3, lineItem.getQuantity());
	                    assertEquals(1888 * 3, lineItem.getSubTotal());
	                }
	            }
	        }

	        assertEquals(2, ShoppingCartItemList.size());


	    }
	}