package org.mdissjava.notifier.event;

import static org.junit.Assert.assertEquals;

import java.util.Observer;

import org.junit.Test;
import org.mdissjava.notifier.event.observable.VerifyAccountObservable;
import org.mdissjava.notifier.event.observer.EmailObserver;

public class ObserverObservableTest {

	
	
	
	@Test
	public void VerifyAccountEventTest() {
		
		//create all the observers
		Observer verifyAccountObservers[] = {new EmailObserver(),};
		VerifyAccountObservable vao = new VerifyAccountObservable();
		
		//register all the observers
		for (Observer i: verifyAccountObservers)
			vao.addObserver(i);
		
		//register, this will call all the observers
		String username = "slok";
		vao.userRegister(username);
		
		//check the observers
		for (Observer i: verifyAccountObservers)
			assertEquals(username, ((EmailObserver)i).getUserNick());
		
		
		
	}

}
