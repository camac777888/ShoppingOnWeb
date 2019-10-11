package shopping.dao;

import java.util.List;

import shopping.domain.Inventory;

public interface InventoryDao {
	Inventory findByPk (long pk);
//	提供分頁查詢
//	@param start 開始索引	索引從0開始	
//	@Param end 	  結束索引	從0開始		
//	@return 商品列表	
	List<Inventory> findStartEnd(int start, int end);
	
	List<Inventory> findAll();

	void create(Inventory inventory);
	
	void modify(Inventory inventory);
	
	void remove(long pk);
}
