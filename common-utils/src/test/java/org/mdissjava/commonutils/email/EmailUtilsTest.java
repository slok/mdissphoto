package org.mdissjava.commonutils.email;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.mail.EmailException;
import org.junit.Test;

public class EmailUtilsTest {

	@Test
	public void TemplateEmailTest() {
		/*try {
			EmailUtils.sendValidationEmail("slok69@gmail.com", "Xabier Larrakoetxea", "http://localhost:8080/mdissphoto/p/validate/" + UUID.randomUUID().toString());
		} catch (EmailException e) {
			fail("Exception ocurred sending email");
		} catch (IOException e) {
			fail("Exception ocurred reading the properties");
		}
		assertTrue("Correct", true);*/
	}
	
	@Test
	public void TemplateFollowerEmailTest() {
		/*try {
			EmailUtils.sendFollowerEmail("maitefru@gmail.com", "maifrup", "cerealguy");
		} catch (EmailException e) {
			fail("Exception ocurred sending email");
		} catch (IOException e) {
			fail("Exception ocurred reading the properties");
		}
		assertTrue("Correct", true);*/
	}

}
