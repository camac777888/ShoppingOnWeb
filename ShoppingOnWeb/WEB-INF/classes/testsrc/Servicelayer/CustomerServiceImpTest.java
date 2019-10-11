package Servicelayer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shopping.dao.CustomerDao;
import shopping.dao.imp.CustomerDaoImpJdbc;
import shopping.domain.Customer;
import shopping.service.CustomerService;
import shopping.service.ServiceException;
import shopping.service.Imp.CustomerServiceImp;

class CustomerServiceImpTest {
	CustomerService dao ;
	CustomerDao cusdao;
	@BeforeEach
	void setUp() throws Exception {
		dao = new CustomerServiceImp();
		cusdao = new CustomerDaoImpJdbc();
	}

	@AfterEach
	void tearDown() throws Exception {
		dao= null;
		cusdao= null;
	}

	@Test
	//測試登入成功的情況
	void testLogin() {
		
		Customer customer = new Customer();
		customer.setId("tony");
		customer.setPassword("12345");
		assertTrue(dao.login(customer));
		Customer dbcustomer = cusdao.findByPk("tony"); 
		assertEquals(dbcustomer.getId(), customer.getId());
		assertEquals(dbcustomer.getAddress(), customer.getAddress());
		assertEquals(dbcustomer.getName(), customer.getName());
		assertEquals(dbcustomer.getPhone(), customer.getPhone());
		assertEquals(dbcustomer.getBirthday(), customer.getBirthday());
		
	}
	@Test
	//測試登入失敗的情況
	void testLoginFalse() {
		Customer customer = new Customer();
		customer.setId("tony");
		customer.setPassword("5155");
		assertFalse(dao.login(customer));
	}
	
	@Test
	void testRegister() throws ServiceException {
		Customer customer = new Customer();
		customer.setId("camac");
		customer.setPassword("12345");
		customer.setAddress("台北公園");
		customer.setBirthday(new Date(22252L));
		customer.setName("觀書要");
		customer.setPhone("0918051022");
		
			dao.register(customer);
			Customer dbcustomer = cusdao.findByPk(customer.getId());
			assertEquals(dbcustomer.getId(), customer.getId());
			assertEquals(dbcustomer.getAddress(), customer.getAddress());
			assertEquals(dbcustomer.getName(), customer.getName());
			assertEquals(dbcustomer.getPhone(), customer.getPhone());
			assertEquals(dbcustomer.getBirthday(), customer.getBirthday());
		
		
	}
	@Test
	void testRegisterFalse() {
		Customer customer = new Customer();
		customer.setId("camac");
		customer.setPassword("12345");
		
		assertThrows(ServiceException.class, ()->dao.register(customer));
			
		
		}
	

}
