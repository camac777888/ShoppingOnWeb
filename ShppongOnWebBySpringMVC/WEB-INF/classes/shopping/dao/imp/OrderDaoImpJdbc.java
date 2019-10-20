package shopping.dao.imp;


import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import shopping.dao.OrderDao;
import shopping.domain.Orders;

public class OrderDaoImpJdbc implements OrderDao {
	
JdbcTemplate tem ;
	
	public void setTem(JdbcTemplate tem) {
		this.tem = tem;
	}
	@Override
	public Orders findByPk(String pk) {
		Orders orders = (Orders) tem.queryForObject("select * from orders where id=?", new OrderRowMapper(),pk);
		return orders;
	}

	@Override
	public List<Orders> findAll() {
		List<Orders> list = tem.query("select * from orders",new OrderRowMapper());
			return list;
			}
	

	@Override
	public void create(Orders orders) {
		
		tem.update("insert into orders (id,order_date,status,total)values(?,?,?,?)",
				orders.getId(),orders.getOrderDate().getTime(),orders.getStatus(),orders.getTotal());	
	}

	@Override
	public void modify(Orders orders) {

		tem.update("update orders set order_date=?,status=?,total=? where id=?",
	orders.getOrderDate().getTime(),orders.getStatus(),orders.getTotal(),orders.getId());		
	}

	@Override
	public void remove(String pk) {
		tem.update("delete from orders where id=?",pk);	
	}

}
