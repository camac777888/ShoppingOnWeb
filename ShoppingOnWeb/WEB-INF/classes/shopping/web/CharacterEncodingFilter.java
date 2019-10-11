package shopping.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


public class CharacterEncodingFilter implements Filter {

	String encoding;
	
	public void init(FilterConfig fConfig) throws ServletException {
		 encoding = fConfig.getInitParameter("encoding");
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (encoding!=null) {
			request.setCharacterEncoding(encoding);
		}
		chain.doFilter(request, response);
	}


	public void destroy() {
	// TODO Auto-generated method stub
	}

	}