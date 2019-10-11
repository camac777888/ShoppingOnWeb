package Servicelayer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shopping.domain.Inventory;
import shopping.service.InventoryService;
import shopping.service.Imp.InventoryServiceImp;

class InventoryServiceImpTst {
	InventoryService inventoryService ;
	@BeforeEach
	void setUp() throws Exception {
		inventoryService =new InventoryServiceImp();
	}

	@AfterEach
	void tearDown() throws Exception {
		inventoryService=null;
	}

	@Test
	void testSearchInventoryAll() {
		List<Inventory> list = inventoryService.SearchInventoryAll();
		assertEquals(34,list.size() );
		Inventory inventory=list.get(0);
		assertEquals(1, inventory.getId());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機",inventory.	getName());
		assertEquals(3399, inventory.getPrice());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機(八代i3-8100 8G 1T 四年上門 有線鍵鼠 FHD寬屏)21.5英吋 ", inventory.getDescription());
		
		
		
	}

	@Test
	void testSearchByStartEnd() {
		List<Inventory> list = inventoryService.SearchByStartEnd(0, 10);
		assertEquals(10,list.size() );
		
		Inventory inventory=list.get(9);
		assertEquals(10, inventory.getId());
		assertEquals("惠普690-076ccn",inventory.	getName());
		assertEquals(6488, inventory.getPrice());
		assertEquals("惠普（HP）光影精靈II代 吃雞遊戲台式電腦主機(八代i7-8700 高頻8G 128GSSD+1TB GTX1060 6G獨顯 WiFi藍芽) ",inventory.getDescription());
	}

	@Test
	void testInventoryDetail() {
		Inventory inventory=inventoryService.InventoryDetail(2);
		assertEquals(2, inventory.getId());
		assertEquals("酷耶（Cooyes） i5四核/GTX1050ti獨顯4G/台式機",inventory.	getName());
		assertEquals(2499, inventory.getPrice());
		assertEquals("酷耶（Cooyes） i5四核/GTX1050ti獨顯4G/台式機電腦主機整機全套組裝家用辦公遊戲 GTX750獨顯主機+23英吋顯示器 ", inventory.getDescription());
	}

}
