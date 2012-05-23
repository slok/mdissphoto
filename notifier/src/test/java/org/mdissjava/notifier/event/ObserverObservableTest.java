package org.mdissjava.notifier.event;

import static org.junit.Assert.assertEquals;

import java.util.Observable;
import java.util.Observer;

import org.junit.Test;
import org.mdissjava.notifier.event.manager.NotificationManager;
import org.mdissjava.notifier.event.observable.PhotoUploadedObservable;
import org.mdissjava.notifier.event.observable.VerifyAccountObservable;
import org.mdissjava.notifier.event.observer.EmailObserver;
import org.mdissjava.notifier.event.observer.LoggerObserver;

public class ObserverObservableTest {

	
	
	
	//@Test
	public void VerifyAccountEventTest() {
		
		//create all the observers
		Observer verifyAccountObservers[] = {new EmailObserver(),};
		VerifyAccountObservable vao = new VerifyAccountObservable();
		
		//register all the observers
		for (Observer i: verifyAccountObservers)
			vao.addObserver(i);
		
		//register, this will call all the observers
		String username = "slok";
		String email = "slok69@gmail.com";
		String key = "www.google.es";
		vao.userRegister(username, email, key);
		
		//check the observers
		for (Observer i: verifyAccountObservers)
			assertEquals(username, ((EmailObserver)i).getUserNick());
	}
	
	
	@Test
	public void PhotoUploadedEventTest() {
		
		//create all the observers
		Observer photoUploadedObservers[] = {new LoggerObserver()};
		PhotoUploadedObservable puo = new PhotoUploadedObservable();
		
		//register all the observers
		for (Observer i: photoUploadedObservers)
			puo.addObserver(i);
		
		//register, this will call all the observers
		String username = "slok";
		String photoId = "123456785345678";
		puo.photoUploaded(username, photoId);
		
		//check the observers
		//No checking for now, the logger now is the slf4j simple logger (only std out)	
		
	}
	
	@Test
	public void PhotoUploadedWithNotifierManagerEventTest() {
		
		//create all the observers
		NotificationManager notifier = NotificationManager.getInstance();
		PhotoUploadedObservable puo = notifier.getPhotoUploadedObservable();
		
		//register, this will call all the observers
		String username = "slok";
		String photoId = "123456785345678";
		puo.photoUploaded(username, photoId);
		
		//check the observers
		//No checking for now, the logger now is the slf4j simple logger (only std out)	
		
	}

}
