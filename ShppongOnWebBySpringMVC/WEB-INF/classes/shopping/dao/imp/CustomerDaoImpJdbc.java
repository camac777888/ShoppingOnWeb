package shopping.dao.imp;

import shopping.dao.imp.CustomerRowMapper;
import java.util.List;
import shopping.dao.imp.DBConnect;
import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import shopping.dao.CustomerDao;
import shopping.domain.Customer;
@Repository
public class CustomerDaoImpJdbc implements CustomerDao {
	
	JdbcTemplate temp =DBConnect.temp;
	
	

	public Customer findByPk(String pk) {
		Customer customer = 
				(Customer) temp.queryForObject("select * from customers where id=?", new CustomerRowMapper(),pk);
	System.out.println(customer);

			return customer;
	}
	
	public List<Customer> findAll() {
		List<Customer> list=temp.query("select * from customers",new CustomerRowMapper());

			return list;
	}
	

	
	public void create(Customer customer) {
		temp.update("insert into customers (id,name,password,address,phone,birthday)values(?,?,?,?,?,?)",
	customer.getId(),customer.getName(),customer.getPassword(),customer.getAddress(),customer.getPhone(),
	customer.getBirthday().getTime());

		
	}

	public void modify(Customer customer) {
		temp.update("update customers set name=?,password=?,address=?,phone=?,birthday=? where id=?",
				customer.getName(),customer.getPassword(),customer.getAddress(),customer.getPhone(),
				customer.getBirthday().getTime(),customer.getId());
	
	}

	public void remove(String pk) {
		temp.update("delete from customers where id=?",pk);	
	}
	
	
}
