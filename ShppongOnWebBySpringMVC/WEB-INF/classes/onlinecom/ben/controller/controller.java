package onlinecom.ben.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import shopping.domain.Customer;
import shopping.domain.Inventory;
import shopping.service.CustomerService;
import shopping.service.InventoryService;
import shopping.service.ServiceException;

@RequestMapping("/customer")
@Controller
public class controller {
	
	@Resource
	private CustomerService customerService;
	@Resource
	private InventoryService inventoryService;
	
	private int totalPageNumber = 0;  	//	總頁數
    private int pageSize =20;			//	每頁行數
    private int currentPage = 1;		//	當前頁數默認為第一頁開始
	
	
/**
 * 註冊 
 * 用customer接收參數，由攔截器判定是否格式正確
 * @param customer
 * @param model
 */
@RequestMapping(value="/reg",method= RequestMethod.POST)	
public String reg(Customer customer,Model model) {		
	List<String> errors = new ArrayList<>();
		try {	 
			customerService.register(customer);
			return "forward:/login.jsp";
		} catch (ServiceException e) {
			errors.add("用戶id已存在");
			model.addAttribute("errors", errors);
			return "forward:/customer_reg.jsp";	
		}							
	}				
/**
 * 登入
 * 由攔截器判定帳號密碼是否與數據庫資料相符
 * @param customer
 * @param session
 * @return
 */
@RequestMapping(value="/login",method=RequestMethod.POST)	
public String login(Customer customer,HttpSession session) {

	session.setAttribute("customer", customer);
	return "forward:/main.jsp";
}
/**
 * 跳轉至註冊頁面
 * @return
 */
@RequestMapping(value="/reg_in",method= RequestMethod.GET)	
public String reg_in() {
	return "forward:/customer_reg.jsp";
	
}
/**
 * 主頁面進入商品列表
 * 從資料庫取得商品總數，列出有限的商品清單
 * @param model
 * @return
 */
@RequestMapping(value="/list",method= RequestMethod.GET)	
public String list(Model model) {
	List<Inventory> goodlist = inventoryService.SearchInventoryAll();
	
	if((goodlist.size()%pageSize)==0) {					//	總頁數 =資料總數/每頁行數  若整除則為總頁數 若不整除總頁數則加一
		totalPageNumber=goodlist.size()/pageSize;
	}else {
		totalPageNumber=(goodlist.size()/pageSize)+1;
	}
	model.addAttribute("totalPageNumber", totalPageNumber);
	model.addAttribute("currentPage", currentPage);
	
	int start = (currentPage-1)*pageSize;				//	(當前頁數-1)*每頁行數
	int end = currentPage*pageSize;						//	當前頁數*每頁行數 
	if (currentPage==totalPageNumber) {					// 最後一頁(避免超出邊界問題)
		end= goodlist.size();  
	}	
	model.addAttribute("goodlist", goodlist.subList(start,end));  
	return "forward:/goods_list.jsp";	
}
/**
 * 商品列表分頁
 * @param page
 * @param model
 * @return
 */
@RequestMapping(value="/paging/{page}",method= RequestMethod.GET)	
public String paging(@PathVariable(value="page") String page,Model model) {
	switch(page) {
	case "prev":
		if(currentPage>1) {					//當前頁數大於第一頁則執行
			currentPage--;
		}break;		
	case "next":
		if(currentPage<totalPageNumber) { 	//當前頁數小於總頁數則執行
		currentPage++;
		}break;		
	default:
		currentPage = Integer.valueOf(page);
	}
int start = (currentPage-1)*pageSize;		
int end = currentPage*pageSize;				
List<Inventory> goodlist = inventoryService.SearchByStartEnd(start, end);

model.addAttribute("goodlist", goodlist); 
model.addAttribute("totalPageNumber", totalPageNumber);
model.addAttribute("currentPage", currentPage);		
return "forward:/goods_list.jsp";	
}
/**
 * 查看商品詳細資訊
 * @param goodsid
 * @param model
 * @return
 */
@RequestMapping(value="/detail/{id}",method= RequestMethod.GET)	
public String detail(@PathVariable(value="id")long goodsid,Model model) {

	Inventory inventory= inventoryService.InventoryDetail(goodsid);
	model.addAttribute("inventory",inventory);
	return "forward:/goods_detail.jsp";
	}


@RequestMapping(value="/add/{pagename}/{id}/{price}",method= RequestMethod.GET)
public String add(@PathVariable(value="pagename") String pagename,@PathVariable(value="id") Long id,
		@PathVariable(value="price")Double price,Model model, HttpSession session) {
	
	Inventory inventory= inventoryService.InventoryDetail(id);
	List<Map<String,Object>> cart =(List<Map<String, Object>>) session.getAttribute("cart");	
		
	if (cart==null) { 								//	第一次加入購物車
		cart = new ArrayList<Map<String,Object>>();
	session.setAttribute("cart", cart);
	}
		int flag = 0;								//判定購物車內是否有同種商品
	 
        for (Map<String, Object> item : cart) {	
        	
            Long goodsid2=Long.valueOf(String.valueOf(item.get("goodsid")));     
            if (id.equals(goodsid2)) {		//購物車中有同種商品
                Integer quantity = (Integer) item.get("quantity");	
                quantity++;
                item.put("quantity", quantity);
                flag++;
            }
        }
        	if (flag == 0) {										//	購物車中沒有同種商品
            Map<String, Object> item = new HashMap<>();				// item結構為Map [商品id，商品名稱，價格，數量]											
            item.put("goodsid", id);
            item.put("goodsname",inventory.getName());
            item.put("quantity", 1);
            item.put("price",price);
            cart.add(item); 		
        }
        	 System.out.println(cart);	//	後台觀察
	
        	 if (pagename.equals("list")) {							//	從商品列表頁面請求
	                int start = (currentPage - 1) * pageSize;
	                int end = currentPage * pageSize;

	                List<Inventory> goodlist = inventoryService.SearchByStartEnd(start,end);
	                model.addAttribute("goodlist", goodlist); 
	                model.addAttribute("totalPageNumber", totalPageNumber);
	                model.addAttribute("currentPage", currentPage);		
	                return "forward:/goods_list.jsp";
	                
	                
	            } else if (pagename.equals("detail")) {				//	從商商品詳細資訊頁面請求
	            	
	            	model.addAttribute("inventory",inventory);		//需要得到完整資訊來輸出商品資料
	            	return "forward:/goods_detail.jsp";

	            }
	            
	             
	return null;
	
}

}	
