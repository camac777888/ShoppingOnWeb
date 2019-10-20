package shopping.dao.imp;


import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import shopping.dao.InventoryDao;
import shopping.domain.Inventory;
@Repository
public class InventoryDaoImpJdbc implements InventoryDao{
	
	JdbcTemplate tem ;
	
	public void setTem(JdbcTemplate tem) {
		this.tem = tem;
	}
	
	@Override
	public Inventory findByPk(long pk) {
		Inventory inventory = 
				(Inventory) tem.queryForObject("select * from inventory where id=?", new
						BeanPropertyRowMapper(Inventory.class),pk);
			return inventory;
	}

	@Override
	public List<Inventory> findStartEnd(int start, int end) {
		List<Inventory> list=tem.query("select * from inventory limit ? offset ?",new
				BeanPropertyRowMapper(Inventory.class),end-start,start);

		return list;
}
	
	@Override
	public List<Inventory> findAll() {
		
		List<Inventory> list=tem.query("select * from inventory ",new
				BeanPropertyRowMapper(Inventory.class));

		return list;
}

	@Override
	public void create(Inventory inventory) {
		
		String sql = "insert into inventory (id,name,price,description,brand,cpu_brand,cpu_type,memory_capacity,hd_capacity,card_model,displaysize,image)"
			+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
		tem.update(sql,inventory.getId(),inventory.getName(),inventory.getPrice(),inventory.getDescription(),
				inventory.getBrand(),inventory.getCpuBrand(),inventory.getCpuType(),inventory.getMemoryCapacity(),
				inventory.getCardModel(),inventory.getDisplaysize(),inventory.getImage());
	}

	@Override
	public void modify(Inventory inventory) {

		String sql = "update  inventory set name=?,price=?,description=?,brand=?,cpu_brand=?,cpu_type=?,memory_capacity=?,hd_capacity=?,card_model=?,displaysize=?,image=? where id=?";
		tem.update(sql,inventory.getName(),inventory.getPrice(),inventory.getDescription(),
				inventory.getBrand(),inventory.getCpuBrand(),inventory.getCpuType(), inventory.getMemoryCapacity(),
				inventory.getHdCapacity(),inventory.getCardModel(),inventory.getDisplaysize(),inventory.getImage(),
				inventory.getId());		
	}

	@Override
	public void remove(long pk) {
	
		tem.update("delete from inventory where id=?",pk);
		
	}

}
