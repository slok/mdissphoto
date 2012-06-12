package org.mdissjava.mdisscore.solr.pojo;

import org.apache.solr.client.solrj.beans.Field;

import com.google.code.morphia.annotations.Entity;

@Entity
public class paises {
	
	@Field
	private int id;
	@Field
	private String nombre;
	@Field
	private float x;
	@Field
	private float y;
	
	public int getId() {
		return id;
	}
	public String getNombre() {
		return nombre;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setX(float x) {
		this.x = x;
	}
	public void setY(float y) {
		this.y = y;
	}

}
