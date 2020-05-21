package web;
import javax.servlet.*;

public class CharacterFilter implements Filter {
	@Override
	public void init(FilterConfig cfg) {
		
	}

	@Override 
	public void doFilter(
					ServletRequest request, 
					ServletResponse response, 
					FilterChain next) 
	{
		try {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			next.doFilter(request, response);
		} catch (Exception e) { }
	}
}
