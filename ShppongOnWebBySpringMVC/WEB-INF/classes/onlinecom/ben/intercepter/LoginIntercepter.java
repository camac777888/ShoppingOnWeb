package onlinecom.ben.intercepter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import shopping.domain.Customer;
import shopping.service.CustomerService;
import shopping.service.Imp.CustomerServiceImp;

public class LoginIntercepter implements HandlerInterceptor{
	@Resource
	Customer customer ;
	@Resource
	CustomerService customerService ;
	
	
	//在controller完成之後執行
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

		
	}
	//在controller方法之後執行
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	
	}
//在controller方法之前執行
	/**
	 * 該方法返回值代表是否繼續執行controller方法
	 * true 繼續執行
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		
		customer.setId(request.getParameter("id"));
		customer.setPassword(request.getParameter("password"));
		if (customerService.login(customer)) {
			return true;
		}else {
		List<String> error = new ArrayList<>();
		error.add("帳號密碼不正確");
		request.setAttribute("errors", error);
		request.getRequestDispatcher("/login.jsp").forward(request, response);
		return false;	
		}
			}
		}
