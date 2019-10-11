import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shopping.dao.OrderDao;
import shopping.dao.imp.OrderDaoImpJdbc;
import shopping.domain.Orders;

class OrderDaoImpJdbcTest {
	OrderDao dao;
	@BeforeEach
	void setUp() throws Exception {
		dao = new OrderDaoImpJdbc();
	}

	@AfterEach
	void tearDown() throws Exception {
		dao=null;
	}

	@Test
	void testFindByPk() {
		Orders order = dao.findByPk("raw");
		assertNotNull(order);
		assertEquals("raw",order.getId());
		assertEquals(2541L, order.getOrderDate().getTime());
		assertEquals(1, order.getStatus());
		assertEquals(2626,order.getTotal());
	}

	@Test
	void testFindAll() {
		List<Orders> list = dao.findAll();
		assertEquals(1, list.size());
		
		Orders order = list.get(0);
		assertNotNull(order);
		assertEquals("raw",order.getId());
		assertEquals(2541L, order.getOrderDate().getTime());
		assertEquals(1, order.getStatus());
		assertEquals(2626,order.getTotal());
		
	}

//	@Test
//	void testCreate() {
//		Orders order = new Orders();
//		order.setId("tom");
//		order.setOrderDate(new Date(25462L));
//		order.setStatus(1);
//		order.setTotal(545166);
//		dao.create(order);
//		
//		Orders order1 = dao.findByPk("tom");
//		assertNotNull(order1);
//		assertEquals("tom",order1.getId());
//		assertEquals(25462L, order1.getOrderDate().getTime());
//		assertEquals(1, order1.getStatus());
//		assertEquals(545166,order1.getTotal());
//	}
//
//	@Test
//	void testModify() {
//		Orders order = new Orders();
//		order.setId("tom");
//		order.setOrderDate(new Date(555555L));
//		order.setStatus(0);
//		order.setTotal(77777);
//		dao.modify(order);
//		
//		Orders order1 = dao.findByPk("tom");
//		assertNotNull(order1);
//		assertEquals("tom",order1.getId());
//		assertEquals(555555L, order1.getOrderDate().getTime());
//		assertEquals(0, order1.getStatus());
//		assertEquals(77777,order1.getTotal());
//		
//	}

	@Test
	void testRemove() {
		dao.remove("tom");
		Orders order = dao.findByPk("tom");
		assertNull(order);
		
	}

}
