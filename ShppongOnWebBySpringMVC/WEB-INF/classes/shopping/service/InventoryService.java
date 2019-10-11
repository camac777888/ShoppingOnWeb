package shopping.service;

import java.util.List;

import shopping.domain.Inventory;

public interface InventoryService {
//商品列表搜尋
List<Inventory>	SearchInventoryAll();
	
	
//商品條件式列表搜尋
List<Inventory> SearchByStartEnd(int start, int end);	
	
	
//商品詳細資訊搜尋
Inventory InventoryDetail (long Goodsid);
	
	
}
