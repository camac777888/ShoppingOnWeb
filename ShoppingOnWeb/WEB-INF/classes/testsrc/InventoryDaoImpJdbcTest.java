import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shopping.dao.InventoryDao;
import shopping.dao.imp.InventoryDaoImpJdbc;
import shopping.domain.Inventory;

class InventoryDaoImpJdbcTest {
	InventoryDao dao;
	@BeforeEach
	void setUp() throws Exception {
		dao = new InventoryDaoImpJdbc();
	}

	@AfterEach
	void tearDown() throws Exception {
		dao = null;
	}

	@Test
	void testFindByPk() {
		Inventory inventory =dao.findByPk(1L);
		assertNotNull(inventory);
		assertEquals(1L, inventory.getId());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機", inventory.getName());
		assertEquals(3399, inventory.getPrice());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機(八代i3-8100 8G 1T 四年上門 有線鍵鼠 FHD寬屏)21.5英吋 ", inventory.getDescription());
		assertEquals("",inventory.getBrand());
		assertEquals("",inventory.getCpuBrand());
		assertEquals("",inventory.getCpuType());
		assertEquals("",inventory.getHdCapacity());
		assertEquals("",inventory.getCardModel());
		assertEquals("",inventory.getDisplaysize());
		assertEquals("5ae00211N25afad2c.jpg", inventory.getImage());
		
	}
/* 注意起始索引與結束索引的問題
 *    起始=0 結束=10 則輸出9筆資料(不包含第10筆)，與字串擷取的方法subString()雷同
 */
	@Test
	void testFindStartEnd() {
		List<Inventory> list = dao.findStartEnd(0,9);
		assertEquals(9,list.size());
		
		Inventory inventory =list.get(0);
		assertNotNull(inventory);
		assertEquals(1L, inventory.getId());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機", inventory.getName());
		assertEquals(3399, inventory.getPrice());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機(八代i3-8100 8G 1T 四年上門 有線鍵鼠 FHD寬屏)21.5英吋 ", inventory.getDescription());
		assertEquals("",inventory.getBrand());
		assertEquals("",inventory.getCpuBrand());
		assertEquals("",inventory.getCpuType());
		assertEquals("",inventory.getHdCapacity());
		assertEquals("",inventory.getCardModel());
		assertEquals("",inventory.getDisplaysize());
		assertEquals("5ae00211N25afad2c.jpg", inventory.getImage());
		
	}
	
	@Test
	void testFindAll() {
		//檢查總數量
		List<Inventory> list = dao.findAll();
		assertEquals(list.size(), 34);
		//檢查單一訊息
		Inventory inventory =list.get(0);
		assertNotNull(inventory);
		assertEquals(1L, inventory.getId());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機", inventory.getName());
		assertEquals(3399, inventory.getPrice());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機(八代i3-8100 8G 1T 四年上門 有線鍵鼠 FHD寬屏)21.5英吋 ", inventory.getDescription());
		assertEquals("",inventory.getBrand());
		assertEquals("",inventory.getCpuBrand());
		assertEquals("",inventory.getCpuType());
		assertEquals("",inventory.getHdCapacity());
		assertEquals("",inventory.getCardModel());
		assertEquals("",inventory.getDisplaysize());
		assertEquals("5ae00211N25afad2c.jpg", inventory.getImage());
	}

//	@Test
//	void testCreate() {
//		Inventory inventory = new Inventory();
//		inventory.setId(50L);
//		inventory.setName("無敵鐵金剛");
//		inventory.setPrice(2522);
//		inventory.setDescription("能變換各種形體");
//		inventory.setBrand("");
//		inventory.setCpuBrand("");
//		inventory.setCpuType("");
//		inventory.setHdCapacity("");
//		inventory.setCardModel("");
//		inventory.setDisplaysize("");
//		inventory.setImage("");
//		dao.create(inventory);
//		
//		Inventory inventory1 = dao.findByPk(50L);
//		assertNotNull(inventory1);
//		assertEquals(50L, inventory1.getId());
//		assertEquals("無敵鐵金剛", inventory1.getName());
//		assertEquals(2522, inventory1.getPrice());
//		assertEquals("能變換各種形體", inventory1.getDescription());
//		assertEquals("",inventory1.getBrand());
//		assertEquals("",inventory1.getCpuBrand());
//		assertEquals("",inventory1.getCpuType());
//		assertEquals("",inventory1.getHdCapacity());
//		assertEquals("",inventory1.getCardModel());
//		assertEquals("",inventory1.getDisplaysize());
//		assertEquals("", inventory1.getImage());
//	}

	@Test
	void testModify() {
		Inventory inventory = new Inventory();
		inventory.setId(50L);
		inventory.setName("無敵寂寞阿");
		inventory.setPrice(2522);
		inventory.setDescription("能變換各種形體");
		inventory.setBrand("機甲變身");
		inventory.setCpuBrand("中二上身");
		inventory.setCpuType("愛卿平身");
		inventory.setHdCapacity("");
		inventory.setCardModel("");
		inventory.setDisplaysize("");
		inventory.setImage("");
		dao.modify(inventory);
		
		Inventory inventory1 = dao.findByPk(50L);
		assertNotNull(inventory1);
		assertEquals(50L, inventory1.getId());
		assertEquals("無敵寂寞阿", inventory1.getName());
		assertEquals(2522, inventory1.getPrice());
		assertEquals("能變換各種形體", inventory1.getDescription());
		assertEquals("機甲變身",inventory1.getBrand());
		assertEquals("中二上身",inventory1.getCpuBrand());
		assertEquals("愛卿平身",inventory1.getCpuType());
		assertEquals("",inventory1.getHdCapacity());
		assertEquals("",inventory1.getCardModel());
		assertEquals("",inventory1.getDisplaysize());
		assertEquals("", inventory1.getImage());
	}

	@Test
	void testRemove() {
		dao.remove(50);
		Inventory inventory1 = dao.findByPk(50);
		assertNull(inventory1);
	}

}
