package org.mdissjava.mdisscore.model.pojo;

import java.util.Date;

import com.google.code.morphia.annotations.Embedded;

/**
 * A Vote representing who voted, when and how many points. Used to represents
 * votes of photos and cameras.
 * 
 * @author mdiss_java
 * 
 */
@Embedded
public class Vote {

	/** The points given by the user to the element. */
	private Integer points;

	/**
	 * The date when the vote was performed, used to do statistics and rankings
	 * based on time .
	 */
	private Date date;

	/** The id of the user who voted. */
	private String idUser;

	/**
	 * Returns the points of the vote.
	 * 
	 * @return the points
	 */
	public Integer getPoints() {
		return this.points;
	}

	/**
	 * Sets the points.
	 * 
	 * @param points
	 *            the number of points given
	 */
	public void setPoints(Integer points) {
		this.points = points;
	}

	/**
	 * Gets the date when the vote was performed.
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return this.date;
	}

	/**
	 * Sets the date.
	 * 
	 * @param date
	 *            the date when the vote was performed
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the id user who voted.
	 * 
	 * @return the id of the user who voted
	 */
	public String getIdUser() {
		return this.idUser;
	}

	/**
	 * Sets the id user.
	 * 
	 * @param idUser
	 *            the new id user
	 */
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

}
