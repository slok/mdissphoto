package org.mdissjava.mdisscore.filter;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mdissjava.mdisscore.controller.bll.PhotoManager;
import org.mdissjava.mdisscore.controller.bll.impl.PhotoManagerImpl;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.view.params.ParamsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.url.QueryString;

public class PublicPhotoFilter implements Filter{

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		//get the params
		String photoId = request.getParameter("photo");
		String token = request.getParameter("token");
		
		//get the photo
		PhotoManager photoManager = new PhotoManagerImpl();
		Photo photoHelper = new Photo();
		photoHelper.setPhotoId(photoId);
		Photo photo = photoManager.findPhoto(photoHelper).get(0);
		String validToken = photo.getPublicToken();

		this.logger.info("Photo {} with public token: {}", photoId, token);	    
	    
	    //Check if the token is ok
	    if (!token.equals(validToken))
	    {
	    	//If they don't match send the naughty user to error page.
	    	this.logger.error("FORBIDDEN ACCESS EVENT");   	
	    	HttpServletResponse httpResponse=(HttpServletResponse)response; 	
	    	httpResponse.sendRedirect("/mdissphoto/user/error/");  	
	    }
	    
	    chain.doFilter(request,response);
	    
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.logger.info("APPLYING PublicPhotoFilter");
		
	}

}
