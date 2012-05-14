package org.mdissjava.mdisscore.model.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="localidades")
public class City {

	@Id private int id;
	private short id_pais;
	private short id_region;
	private String nombre;
	private float x;
	private float y;
	
	public City(){
		id=0;
		nombre="Unknow";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_pais() {
		return id_pais;
	}
	public void setId_pais(short id_pais) {
		this.id_pais = id_pais;
	}
	public int getId_region() {
		return id_region;
	}
	public void setId_region(short id_region) {
		this.id_region = id_region;
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
