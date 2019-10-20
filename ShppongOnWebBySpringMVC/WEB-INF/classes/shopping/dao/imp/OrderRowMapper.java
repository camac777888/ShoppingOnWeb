package shopping.dao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import shopping.domain.Customer;
import shopping.domain.Orders;

public class OrderRowMapper implements RowMapper<Orders>  {
	
	public Orders mapRow(ResultSet rs, int rowNum) throws SQLException {  

		Orders orders =new Orders();
		orders.setId(rs.getString("id"));
		orders.setOrderDate(new Date(rs.getLong("order_date")));
		orders.setStatus(rs.getInt("status"));
		orders.setTotal(rs.getDouble("total"));
		return orders;
    }  
}
