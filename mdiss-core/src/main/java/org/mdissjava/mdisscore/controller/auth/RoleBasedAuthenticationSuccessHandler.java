package org.mdissjava.mdisscore.controller.auth;

import java.io.IOException;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

public class RoleBasedAuthenticationSuccessHandler implements AuthenticationSuccessHandler 
{
    
	private Map<String, String> roleUrlMap;
	final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void onAuthenticationSuccess(HttpServletRequest request,HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	
    	/* Check what type of role has the authenticated user in order to redirect him/her by default to either:
    	*  				/admin/ --> if the user is ROLE_ADMIN
    	*				/login/ --> if the user is ROLE_USER
    	*/
    	
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String role = userDetails.getAuthorities().isEmpty() ? null : userDetails.getAuthorities().toArray()[0].toString();
            
            String redirectURL = request.getContextPath() + roleUrlMap.get(role);
            
            // This chunk of code checks if the user asked for a certain URL before losing the session.
            // If this happens, after authenticating again the user is redirected to the initial target url instead of the default URL mentioned above. 
            HttpSession session = request.getSession(false);
            if(session != null) {
                SavedRequest savedRequest = (SavedRequest) session.getAttribute(WebAttributes.SAVED_REQUEST);
                if(savedRequest != null) {             
                    redirectURL = savedRequest.getRedirectUrl();
                }
            }
            this.logger.info("Redirecting to: {}", redirectURL);
            response.sendRedirect(redirectURL);
        }
    }

    public void setRoleUrlMap(Map<String, String> roleUrlMap) {
        this.roleUrlMap = roleUrlMap;
    }
}