package shopping.dao;

import java.util.List;

import shopping.domain.Customer;

public interface CustomerDao {
	Customer findByPk (String pk);
	
	List<Customer> findAll();

	void create(Customer customer);
	
	void modify(Customer customer);
	
	void remove(String pk);
	
}
