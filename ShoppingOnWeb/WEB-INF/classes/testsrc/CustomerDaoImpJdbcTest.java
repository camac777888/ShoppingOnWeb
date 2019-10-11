import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shopping.dao.CustomerDao;
import shopping.dao.imp.CustomerDaoImpJdbc;
import shopping.domain.Customer;
/*/在測試過程中將中文字插入數據庫中時遇到數據庫顯示亂碼問題，
 * 可以透過配置文件設定useUnicode=true characterEncoding=UTF-8 並檢查與數據庫編碼是否一致 得以解決
 * 
 */
class CustomerDaoImpJdbcTest {
	CustomerDao dao;
	@BeforeEach
	void setUp() throws Exception {
		dao = new CustomerDaoImpJdbc(); 
	}

	@AfterEach
	void tearDown() throws Exception {
		dao = null;
	}

	@Test
	void testFindByPk() {
		
		Customer customer = dao.findByPk("tony");
		assertNotNull(customer);
		assertEquals("tony", customer.getId());
		assertEquals("程瑀軒", customer.getName());
		assertEquals("12345", customer.getPassword());
		assertEquals("麥金路", customer.getAddress());
		assertEquals("886918052", customer.getPhone());
		assertEquals(19920918, customer.getBirthday().getTime());
	}

	@Test
	void testFindAll() {
		List<Customer> list = dao.findAll();
		assertEquals(list.size(), 1);
	}

	@Test
	void testCreate() {			
		Customer customer = new Customer();
		customer.setId("Ben");
		customer.setName("王大牛");
		customer.setPassword("11111");
		customer.setAddress("非洲");
		customer.setPhone("88655862");
		customer.setBirthday(new Date(12121313L));
		dao .create(customer);
		
		Customer customer1 = dao.findByPk("Ben");
		assertEquals("Ben", customer1.getId());
		assertEquals("王大牛", customer1.getName());
		assertEquals("11111", customer1.getPassword());
		assertEquals("非洲", customer1.getAddress());
		assertEquals("88655862", customer1.getPhone());
		assertEquals(12121313, customer1.getBirthday().getTime());
		
	}

	@Test
	void testModify() {
		Customer customer = new Customer();
		customer.setName("王大牛");
		customer.setPassword("11111");
		customer.setAddress("非洲");
		customer.setPhone("8865526262862");
		customer.setBirthday(new Date(12121313L));
		customer.setId("Ben");
		dao.modify(customer);
		
		Customer customer1 = dao.findByPk("Ben");
		assertEquals("Ben", customer1.getId());
		assertEquals("王大牛", customer1.getName());
		assertEquals("11111", customer1.getPassword());
		assertEquals("非洲", customer1.getAddress());
		assertEquals("8865526262862", customer1.getPhone());
		assertEquals(12121313, customer1.getBirthday().getTime());
	}

	@Test
	void testRemove() {
		dao.remove("Ben");
		
		Customer customer1 = dao.findByPk("Ben");
		assertNull(customer1);
		
	}

}
