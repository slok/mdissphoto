package org.mdissjava.mdisscore.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mdissjava.mdisscore.model.dao.PhotoDao;
import org.mdissjava.mdisscore.model.dao.UserDao;
import org.mdissjava.mdisscore.model.dao.factory.MorphiaDatastoreFactory;
import org.mdissjava.mdisscore.model.dao.impl.PhotoDaoImpl;
import org.mdissjava.mdisscore.model.dao.impl.UserDaoImpl;
import org.mdissjava.mdisscore.model.pojo.Photo;
import org.mdissjava.mdisscore.model.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ocpsoft.pretty.PrettyContext;
import com.ocpsoft.pretty.faces.config.mapping.UrlMapping;
import com.ocpsoft.pretty.faces.util.PrettyURLBuilder;

public class PhotoSettingsFilter  implements Filter {

	
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		this.logger.info("APPLYING PhotoSettingsFilter");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUser = auth.getName();
		String photoId = request.getParameter("photo");
		
		Photo requestedPhoto = new Photo();
		requestedPhoto.setPhotoId(photoId);
		
		PhotoDao pdao = new PhotoDaoImpl(MorphiaDatastoreFactory.getDatastore("mdissphoto"));
				
		
		if((pdao.findPhoto(requestedPhoto).size()>0)&&( pdao.findPhoto(requestedPhoto).get(0).getAlbum().getUserNick().equals(loggedUser)))
		{
		    chain.doFilter(request,response);
		
		}
		else
		{
			this.logger.error("FORBIDDEN ACCESS EVENT: User {} tried to edit other user's photo", loggedUser);   	
			PrettyContext context = PrettyContext.getCurrentInstance((HttpServletRequest)request);
			PrettyURLBuilder builder = new PrettyURLBuilder();
			
			UrlMapping mapping = context.getConfig().getMappingById("photosettings-error");
			String targetURL = builder.build(mapping, true, new HashMap<String, String[]>());
	    	
	    	HttpServletResponse httpResponse=(HttpServletResponse)response;
	    	httpResponse.sendRedirect("/mdissphoto" + targetURL);
		}
		
	}

	@Override
	public void destroy() {
		
	}

}
