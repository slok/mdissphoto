package org.mdissjava.api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
/**
 * This is neccessary for the jboss 7 Resteasy bundled jar 
 * 
 * @author slok
 *
 */
@ApplicationPath("/api/1.0")
public class MdissAPI extends Application
{
}