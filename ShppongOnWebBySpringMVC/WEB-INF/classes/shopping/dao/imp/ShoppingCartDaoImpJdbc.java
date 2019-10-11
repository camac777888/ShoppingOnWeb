package shopping.dao.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shopping.core.PreparedStatementCreator;
import shopping.core.jdbcTemplate;
import shopping.dao.ShoppingCartDao;
import shopping.domain.Inventory;
import shopping.domain.Orders;
import shopping.domain.ShoppingCart;


public class ShoppingCartDaoImpJdbc implements ShoppingCartDao  {
	jdbcTemplate  jdbcTemplate = new jdbcTemplate(); 
	@Override
	public ShoppingCart findByPk(long pk) {
		List<ShoppingCart> list = new ArrayList<ShoppingCart>();
		String sql = "select id,quantity,goodsid,orderid,sub_total from shoppingcart where id =?";
		jdbcTemplate.search(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1, pk);
			return ps;
		}, rs -> {
			populate(list, rs);			 			 			 			 
		});
		if (list.size()==1) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ShoppingCart> findAll() {
		List<ShoppingCart> list = new ArrayList<ShoppingCart>();
		String sql = "select id,quantity,goodsid,orderid,sub_total from shoppingcart";
		jdbcTemplate.search(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql);
			return ps;
		}, rs -> {
			populate(list, rs); 
		});
			return list;
		}
		

	private void populate(List<ShoppingCart> list, ResultSet rs) throws SQLException {
		ShoppingCart shoppingCart = new ShoppingCart();
		 shoppingCart.setId(rs.getLong("id"));
		 shoppingCart.setQuantity(rs.getInt("quantity"));
		 shoppingCart.setSubTotal(rs.getFloat("sub_total"));
		
		 Orders orders =new Orders();
		 orders.setId(rs.getString("orderid"));
		 shoppingCart.setOrders(orders);
		
		 Inventory inventory =new Inventory();
		 inventory.setId(rs.getLong("goodsid"));
		 shoppingCart.setGoods(inventory);
		 
		 list.add(shoppingCart);
	}
	

	@Override
	public void create(ShoppingCart shoppingCart) {
		 String sql = "insert into shoppingcart(id,goodsid,orderid,quantity,sub_total) values (?,?,?,?,?)";
		 jdbcTemplate.update(conn -> {
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setLong(1, shoppingCart.getId());
	            ps.setLong(2, shoppingCart.getGoods().getId());
	            ps.setString(3, shoppingCart.getOrders().getId());
	            ps.setInt(4, shoppingCart.getQuantity());
	            ps.setDouble(5, shoppingCart.getSubTotal());

	            return ps;
	        });
	    }
	

	@Override
	public void modify(ShoppingCart shoppingCart) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			String sql = "update shoppingcart set goodsid=?,orderid=?,quantity=?,sub_total=? where id=?";
			@Override
			public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(sql);
				
				 ps.setLong(1, shoppingCart.getGoods().getId());				
				ps.setString(2, shoppingCart.getOrders().getId());
		        ps.setInt(3, shoppingCart.getQuantity());
		        ps.setDouble(4, shoppingCart.getSubTotal());		    				
				ps.setLong(5, shoppingCart.getId());
				return ps;
			}
		});
		
	}

	@Override
	public void remove(int pk) {
		 String sql = "delete from shoppingcart where id=?";

	        jdbcTemplate.update(conn -> {
	            PreparedStatement ps = conn.prepareStatement(sql);
	            ps.setInt(1, pk);

	            return ps;
	        });
	    }

}
