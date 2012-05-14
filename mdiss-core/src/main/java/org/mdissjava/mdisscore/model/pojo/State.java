package org.mdissjava.mdisscore.model.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="regiones")
public class State {

	@Id private short id;
	private short id_pais;
	private String nombre;
	private float x;
	private float y;
	
	public State(){
		id=0;
		nombre="Unknow";
	}
	
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public short getId_pais() {
		return id_pais;
	}
	public void setId_pais(short id_pais) {
		this.id_pais = id_pais;
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
