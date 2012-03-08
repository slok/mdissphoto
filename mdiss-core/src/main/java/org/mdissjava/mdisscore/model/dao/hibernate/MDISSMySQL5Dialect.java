package org.mdissjava.mdisscore.model.dao.hibernate;

import java.sql.Types;

import org.hibernate.dialect.MySQL5Dialect;

public class MDISSMySQL5Dialect extends MySQL5Dialect{ 
public MDISSMySQL5Dialect() {
super();
registerColumnType( Types.BOOLEAN, "bit(1)" ); 
} 
}