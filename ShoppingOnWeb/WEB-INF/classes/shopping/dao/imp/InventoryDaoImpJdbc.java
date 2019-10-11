package shopping.dao.imp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shopping.core.jdbcTemplate;
import shopping.dao.InventoryDao;
import shopping.domain.Inventory;

public class InventoryDaoImpJdbc implements InventoryDao{
	jdbcTemplate  jdbcTemplate = new jdbcTemplate(); 
	
	@Override
	public Inventory findByPk(long pk) {
		String sql = "select id,name,price,description,brand,cpu_brand,cpu_type,memory_capacity,hd_capacity,card_model,displaysize,image from inventory where id=?";
		List <Inventory> list = new ArrayList<Inventory>();

		jdbcTemplate.search(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1,pk);
			return ps;
		}, rs -> {
			populate(list, rs);
		});
		if (list.size()==1) {
			return list.get(0);
		}else {
			return null;
		}
		
	}

	@Override
	public List<Inventory> findStartEnd(int start, int end) {
		
		List <Inventory> list = new ArrayList<Inventory>();
		StringBuffer sql = new StringBuffer("select id,name,price,description,brand,cpu_brand,cpu_type,memory_capacity,hd_capacity,card_model,displaysize,image from inventory ");
		sql.append(" limit ").append(end-start);
		sql.append(" offset ").append(start);
		
		

		jdbcTemplate.search(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql.toString()); //轉為字串輸出
			return ps;
		}, rs -> {
			populate(list, rs);
		});
			return list;
		
		
	}

	@Override
	public List<Inventory> findAll() {

		String sql = "select id,name,price,description,brand,cpu_brand,cpu_type,memory_capacity,hd_capacity,card_model,displaysize,image from inventory ";
		List <Inventory> list = new ArrayList<Inventory>();

		jdbcTemplate.search(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql);
			return ps;
		}, rs -> {
			populate(list, rs);
		});
			return list;
	}

	private void populate(List<Inventory> list, ResultSet rs) throws SQLException {
		Inventory inventory = new Inventory();
		inventory.setId(rs.getLong("id"));
		inventory.setName(rs.getString("name"));
		inventory.setPrice(rs.getDouble("price"));
		inventory.setDescription(rs.getString("description"));
		inventory.setBrand(rs.getString("brand"));
		inventory.setCpuBrand(rs.getString("cpu_brand"));
		inventory.setCpuType(rs.getString("cpu_type"));
		inventory.setMemoryCapacity(rs.getString("memory_capacity"));
		inventory.setHdCapacity(rs.getString("hd_capacity"));
		inventory.setCardModel(rs.getString("card_model"));
		inventory.setDisplaysize(rs.getString("displaysize"));
		inventory.setImage(rs.getString("image"));
		list.add(inventory);
	}

	@Override
	public void create(Inventory inventory) {
		
		String sql = "insert into inventory (id,name,price,description,brand,cpu_brand,cpu_type,memory_capacity,hd_capacity,card_model,displaysize,image)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
		jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setLong(1,inventory.getId());
			ps.setString(2, inventory.getName());
			ps.setDouble(3, inventory.getPrice());
			ps.setString(4, inventory.getDescription());
			ps.setString(5, inventory.getBrand());
			ps.setString(6, inventory.getCpuBrand());
			ps.setString(7, inventory.getCpuType());
			ps.setString(8, inventory.getMemoryCapacity());
			ps.setString(9, inventory.getHdCapacity());
			ps.setString(10, inventory.getCardModel());
			ps.setString(11, inventory.getDisplaysize());
			ps.setString(12, inventory.getImage());
			
			return ps;
		});
		
		
	}

	@Override
	public void modify(Inventory inventory) {

		String sql = "update  inventory set name=?,price=?,description=?,brand=?,cpu_brand=?,cpu_type=?,memory_capacity=?,hd_capacity=?,card_model=?,displaysize=?,image=? where id=?";
		jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			
			ps.setString(1, inventory.getName());
			ps.setDouble(2, inventory.getPrice());
			ps.setString(3, inventory.getDescription());
			ps.setString(4, inventory.getBrand());
			ps.setString(5, inventory.getCpuBrand());
			ps.setString(6, inventory.getCpuType());
			ps.setString(7, inventory.getMemoryCapacity());
			ps.setString(8, inventory.getHdCapacity());
			ps.setString(9, inventory.getCardModel());
			ps.setString(10, inventory.getDisplaysize());
			ps.setString(11, inventory.getImage());
			ps.setLong	(12,inventory.getId());
			return ps;
		});
		
		
		
	}

	@Override
	public void remove(long pk) {
		String sql = "delete from inventory where id=?";
		jdbcTemplate.update(conn -> {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setLong(1,pk);
			return ps;
		});
		
	}

}
