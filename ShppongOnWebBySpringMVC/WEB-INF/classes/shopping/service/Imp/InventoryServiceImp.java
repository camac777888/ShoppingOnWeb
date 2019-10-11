package shopping.service.Imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import shopping.dao.InventoryDao;
import shopping.dao.imp.InventoryDaoImpJdbc;
import shopping.domain.Inventory;
import shopping.service.InventoryService;
@Service(value="inventoryService")
public class InventoryServiceImp implements InventoryService{
	@Resource
	InventoryDao inventorydao ;
	
	@Override 
	public List<Inventory> SearchInventoryAll() {
		
		return inventorydao.findAll();
	}

	@Override
	public List<Inventory> SearchByStartEnd(int start, int end) {
		return inventorydao.findStartEnd(start, end);
		
	}

	@Override
	public Inventory InventoryDetail(long Goodsid) {
		
		return inventorydao.findByPk(Goodsid);
	}

}
