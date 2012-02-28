package org.mdissjava.mdisscore.model.pojo;

import java.util.List;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/**
 * Represents a generic Camera of the market with the brand, specific model and
 * the votes of the users.
 * 
 * @author mdiss_java
 */
@Entity
public class Camera {

	/** The unique id who represents a camera */
	@Id
	private String id;

	/** The brand of the camera. */
	private String brand;

	/** The model of the camera. */
	private String model;

	/** The list of votes received from the users. */
	@Embedded
	private List<Vote> votes;

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the brand.
	 * 
	 * @return the brand
	 */
	public String getBrand() {
		return this.brand;
	}

	/**
	 * Sets the brand.
	 * 
	 * @param brand
	 *            the new brand
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * Gets the model.
	 * 
	 * @return the model
	 */
	public String getModel() {
		return this.model;
	}

	/**
	 * Sets the model.
	 * 
	 * @param model
	 *            the new model
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * Gets the votes.
	 * 
	 * @return the votes
	 */
	public List<Vote> getVotes() {
		return this.votes;
	}

	/**
	 * Sets the votes.
	 * 
	 * @param votes
	 *            the new votes
	 */
	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}

}
