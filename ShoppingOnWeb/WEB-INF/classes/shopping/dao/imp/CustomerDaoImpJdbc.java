package shopping.dao.imp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import shopping.core.jdbcTemplate;
import shopping.dao.CustomerDao;
import shopping.domain.Customer;

public class CustomerDaoImpJdbc implements CustomerDao {
	jdbcTemplate  jdbcTemplate = new jdbcTemplate(); //模板實例化
	
	public Customer findByPk(String pk) {
		String sql = "select id,name,password,address,phone,birthday from customers where id=? ";
		List <Customer> list = new ArrayList<Customer>();
		jdbcTemplate.search(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1,pk);
			return ps;
		}, rs -> {
			populate(list,rs);
		});
		
		if (list.size()==1) {	
			return list.get(0);
		}else {
			return null;
		}
	}
	
	public List<Customer> findAll() {
		String sql = "select id,name,password,address,phone,birthday from customers ";
		List <Customer> list = new ArrayList<Customer>();
		jdbcTemplate.search(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql);
			return ps;
		}, rs -> {
			populate(list,rs);
		});
			return list;
	}
	
	private  void populate (List<Customer> list, ResultSet rs) throws SQLException {
		Customer customer = new Customer(); 
		customer.setId(rs.getString("id"));
		customer.setPassword(rs.getString("password"));
		customer.setName(rs.getString("name"));
		customer.setAddress(rs.getString("address"));
		customer.setPhone(rs.getString("phone"));
		customer.setBirthday(new Date (rs.getLong("birthday")));  
		list.add(customer);
	}
	
	public void create(Customer customer) {
		String sql = "insert into customers (id,name,password,address,phone,birthday)values(?,?,?,?,?,?) ";
		jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1,customer.getId());
			ps.setString(2, customer.getName());
	        ps.setString(3, customer.getPassword());
	        ps.setString(4, customer.getAddress());
	        ps.setString(5, customer.getPhone());
	        ps.setLong	(6, customer.getBirthday().getTime()); //getBirthday()為date透過getTime()轉為long
			
	        return ps;
			});
		
	}

	public void modify(Customer customer) {
		String sql = "update Customers set name=?,password=?,address=?,phone=?,birthday=? where id=?";
		jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, customer.getName());
		    ps.setString(2, customer.getPassword());
		    ps.setString(3, customer.getAddress());
		    ps.setString(4, customer.getPhone());
		    ps.setLong	(5, customer.getBirthday().getTime()); 
		    ps.setString(6,customer.getId());
			return ps;
		});
		
	}


	public void remove(String pk) {
		String sql = "delete from Customers where id=?";
		jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql);
		    ps.setString(1,pk);
			return ps;
		});
		
	}
	
	
}
