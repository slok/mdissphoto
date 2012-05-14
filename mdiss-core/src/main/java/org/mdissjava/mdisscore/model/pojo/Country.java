package org.mdissjava.mdisscore.model.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="paises")
public class Country {

	@Id private short id;
	private String nombre;
	private float x;
	private float y;
	
	public Country(){
		id=0;
		nombre="Unknow";
	}
	
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
}
