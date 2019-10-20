package shopping.dao.imp;


import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import shopping.dao.ShoppingCartDao;
import shopping.domain.ShoppingCart;


public class ShoppingCartDaoImpJdbc implements ShoppingCartDao  {
	JdbcTemplate  tem ; 
	public void setTem(JdbcTemplate tem) {
		this.tem = tem;
	}
	@Override
	public ShoppingCart findByPk(long pk) {
		
		ShoppingCart shoppingCart = 
				(ShoppingCart) tem.queryForObject("select * from shoppingcart where id=?", new
						BeanPropertyRowMapper(ShoppingCart.class),pk);
			return shoppingCart;
	}

	@Override
	public List<ShoppingCart> findAll() {
		
		List<ShoppingCart> list=tem.query("select * from inventory ",new
				BeanPropertyRowMapper(ShoppingCart.class));

		return list;
		}
	@Override
	public void create(ShoppingCart shoppingCart) {
		 String sql = "insert into shoppingcart(id,goodsid,orderid,quantity,sub_total) values (?,?,?,?,?)";
		 
		 tem.update(sql,shoppingCart.getId(),shoppingCart.getGoods().getId(),shoppingCart.getOrders().getId(),
				 shoppingCart.getQuantity(),shoppingCart.getSubTotal());
		}
		 
	@Override
	public void modify(ShoppingCart shoppingCart) {
		
		String sql = "update shoppingcart set goodsid=?,orderid=?,quantity=?,sub_total=? where id=?";
tem.update(sql,shoppingCart.getGoods().getId(),shoppingCart.getOrders().getId(),
		shoppingCart.getQuantity(),shoppingCart.getSubTotal(),shoppingCart.getId());				
	}

	@Override
	public void remove(int pk) {
		 tem.update("delete from shoppingcart where id=?",pk);
	       
	    }

}
