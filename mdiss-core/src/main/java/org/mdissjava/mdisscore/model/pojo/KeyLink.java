package org.mdissjava.mdisscore.model.pojo;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.mdissjava.mdisscore.model.dao.impl.KeyLinkDaoImpl;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

/*
 * Represents the the parameter of the unique link for reseting a password or verifying the email
 */
/**
 * The Class KeyLink represents the unique link which is sent for email
 * validation and reseting the password.
 */
@Entity
public class KeyLink {

	public static final int EMAIL_VALIDATION = 1;
	public static final int PASSWORD_RESET = 2;

	/** The id is the parameter that will be sent in the URL. */
	@Id
	private String id;

	/** The user id. */
	private int userId;

	/**
	 * The expiration date. This link only must work for 1 day and get
	 * invalidated after that moment
	 */
	@Embedded
	private Date expireDate;

	/**
	 * The unique type represets if the link is for the email validation or
	 * password reset
	 */
	private int uniqueType;

	/**
	 * Instantiates a new key link.
	 * 
	 * @param userId
	 *            the user id
	 * @param type
	 *            the type
	 */
	public KeyLink(int userId, int type) {
		this.id = UUID.randomUUID().toString();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		expireDate = calendar.getTime();
		this.userId = userId;
		this.uniqueType = type;

	}

	public KeyLink() {

	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the user id.
	 * 
	 * @return the user id
	 */
	public int getUserId() {
		return this.userId;
	}

	/**
	 * Gets the expire date.
	 * 
	 * @return the expire date
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * Gets the unique type.
	 * 
	 * @return the unique type
	 */
	public int getUniqueType() {
		return uniqueType;
	}

	/**
	 * Return the userId of the link if the link doesn't expired yet. if the
	 * link expired or the link doesn't exist will return -1.
	 * 
	 * @param link
	 *            the link
	 * @return the users id
	 */
	public static int retrieveUserFromValidationLink(String link) {
		// TODO: KeyLinkDaoImpl how to create it?
		KeyLinkDaoImpl keyLinkDaoImpl = new KeyLinkDaoImpl(null);
		KeyLink keyLink = keyLinkDaoImpl.findKeyLink(link);
		if (keyLink == null) {
			throw new IllegalArgumentException();
		} else {
			Date now = new Date();
			if (keyLink.getExpireDate().before(now)) {
				return keyLink.getUserId();
			} else {
				return -1;
			}
		}
	}

}
