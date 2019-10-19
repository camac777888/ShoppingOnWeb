package shopping.dao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import shopping.domain.Customer;

public class CustomerRowMapper implements RowMapper<Customer>  {
	
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {  

        Customer customer = new Customer(); 
		customer.setId(rs.getString("id"));
		customer.setPassword(rs.getString("password"));
		customer.setName(rs.getString("name"));
		customer.setAddress(rs.getString("address"));
		customer.setPhone(rs.getString("phone"));
		customer.setBirthday(new Date (rs.getLong("birthday")));  
		return customer;  
    }  
}
