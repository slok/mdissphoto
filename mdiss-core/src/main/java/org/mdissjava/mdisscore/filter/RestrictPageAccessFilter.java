package org.mdissjava.mdisscore.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ocpsoft.pretty.PrettyContext;

public class RestrictPageAccessFilter implements Filter{

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		//Get the current logged user's username
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUser = auth.getName();
		
		//get the user access page
		String username = request.getParameter("user");
	    //TODO: Check if the requested user exists in DB. ¿If it doesn't exist send to 404 Error page?
	    
		//TODO:
	    //Check if the loggeduser is following the user (this means that the user has accepted the loggedIUser request)
	    /*if (!loggedUser.equals(requestedUser))
	    {
	    	//If they don't match send the naughty user to error page.
	    	this.logger.error("FORBIDDEN ACCESS EVENT: User {} tried to access restricted area.", loggedUser);   	
	    	HttpServletResponse httpResponse=(HttpServletResponse)response; 	
	    	httpResponse.sendRedirect("/e/user");  	
	    }*/
	    
	    chain.doFilter(request,response);
	    
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.logger.info("APPLYING RestrictPageAccessFilter");
		
	}

}
