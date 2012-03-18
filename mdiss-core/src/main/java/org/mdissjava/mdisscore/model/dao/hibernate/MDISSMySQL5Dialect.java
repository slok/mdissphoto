package org.mdissjava.mdisscore.model.dao.hibernate;

import java.sql.Types;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * There is a problem with the new hibernate way of getting the boolean values.
 * To fix this problem the normal MySQL5Dialect is extended adn chage the 
 * way Hibernate maps the boolean values from MySQL
 * 
 * @author MDISS Java team 2011-2012 University of Deusto
 *
 */
public class MDISSMySQL5Dialect extends MySQL5Dialect{ 

	public MDISSMySQL5Dialect() {
		super();
		registerColumnType( Types.BOOLEAN, "bit(1)" ); 
	} 
}