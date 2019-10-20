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
import org.springframework.web.servlet.ModelAndView;

import shopping.domain.Customer;
import shopping.domain.Inventory;
import shopping.service.CartService;
import shopping.service.CustomerService;
import shopping.service.InventoryService;
import shopping.service.OrderService;
import shopping.service.ServiceException;

@RequestMapping("/customer")
@Controller
public class controller {
	
	@Resource
	private CustomerService customerService;
	@Resource
	private InventoryService inventoryService;
	@Resource
	private OrderService orderService;
	@Resource
	private CartService cartService;
	
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
/**
 * 加入購物車
 * 判定購物車內是否有同種商品做出相對應行為
 * 判定由哪個發出請求並跳轉
 * @param pagename
 * @param id
 * @param model
 * @param session
 * @return
 */

@RequestMapping(value="/add/{pagename}/{id}",method= RequestMethod.GET)
public String add(@PathVariable(value="pagename") String pagename,
		@PathVariable(value="id") Long id,Model model, HttpSession session) {	
	Inventory inventory= inventoryService.InventoryDetail(id);
	List<Map<String,Object>> cart =(List<Map<String, Object>>) session.getAttribute("cart");		
	
		if (cart==null) { 							//	第一次加入購物車
		cart = new ArrayList<Map<String,Object>>();
		session.setAttribute("cart", cart);
		}
		cartService.cartjudgement(cart,id,inventory);
		 System.out.println(cart);					//	後台觀察

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
/**
 * 購物車頁面
 * @param session
 * @param model
 * @return
 */
@RequestMapping(value="/cart",method = RequestMethod.GET)
public String cart(HttpSession session,Model model) {
	List<Map<String,Object>> cart =(List<Map<String,Object>>) session.getAttribute("cart");
	double	total = 0.0;
	if (cart !=null) {
		
		for (Map<String, Object> item : cart) {
           	 Integer quantity = (Integer) item.get("quantity");
           	Double price = (Double) item.get("price");
               double subtotal = price * quantity;
               total +=subtotal;
		 }

		model.addAttribute("total", total);
		
	}
	return "forward:/cart.jsp";
}	
/**
 * 購物車頁面中刪除單項商品
 * 取出session中的cart，並進行比對商品id，取出過程放入另一個cart1
 * 刪除原有的cart，以新的cart代替
 * @param id
 * @param session
 * @param model
 * @return
 */

@RequestMapping(value="/delete/{goodsid}",method=RequestMethod.GET)
public String delete(@PathVariable(value="goodsid")Long id,HttpSession session,Model model) {	
	List<Map<String,Object>> cart =(List<Map<String, Object>>) session.getAttribute("cart");
	double	total = 0.0;
	List<Map<String,Object>> cart1 = cartService.cartdelete(cart, id);
	
	 for (Map<String, Object> item : cart1) {
		   	 Integer quantity = (Integer) item.get("quantity");
	        	Double price = (Double) item.get("price");
	            double subtotal = price * quantity;
	            total +=subtotal;
		 }  
	
	 session.removeAttribute("cart");
		model.addAttribute("total", total);	
		session.setAttribute("cart", cart1);
	return "forward:/cart.jsp";
}

/**
 * 提交訂單
 * @param session
 * @param model
 * @return
 */
@RequestMapping(value="/sub_ord",method=RequestMethod.POST)
public String sub_ord(HttpSession session,Model model) {
	List<Map<String,Object>> cart =(List<Map<String, Object>>) session.getAttribute("cart");		
	String orderid = orderService.SubmitOrder(cart);
	model.addAttribute("orderid", orderid);
	session.removeAttribute("cart");					//	清空購物車
	return "forward:/order_finish.jsp";
	}
/**
 * 跳轉至主頁面
 * @return
 */
@RequestMapping(value="/main",method= RequestMethod.GET)	
public String mian() {
		return "forward:/main.jsp";
	}	
/**
 * 登出
 * @param session
 * @return
 */
@RequestMapping(value="/logout",method= RequestMethod.GET)	
public String logout(HttpSession session) {
	session.removeAttribute("cart");
	session.removeAttribute("customer");
	return "forward:/login.jsp";	
}

}		




