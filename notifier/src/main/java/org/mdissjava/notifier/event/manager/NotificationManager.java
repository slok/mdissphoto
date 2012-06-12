package org.mdissjava.notifier.event.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.mdissjava.notifier.event.observable.DirectMessageObservable;
import org.mdissjava.notifier.event.observable.NewFollowerObservable;
import org.mdissjava.notifier.event.observable.PhotoUploadedObservable;
import org.mdissjava.notifier.event.observable.ReportPhotoObservable;
import org.mdissjava.notifier.event.observable.VerifyAccountObservable;
import org.mdissjava.notifier.event.observer.EmailObserver;
import org.mdissjava.notifier.event.observer.LoggerObserver;
import org.mdissjava.notifier.event.observer.PersistenceObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton class that is used to get/manage the observables
 * and registers at the creation all the observers in the observables
 * 
 * @author slok
 *
 */
public class NotificationManager {
	
	//the names of the observables to manage internally
	private enum ObservableNames {
										VERIFY_ACCOUNT,
										PHOTO_UPLOADED,
										NEW_FOLLOWER,
										REPORT_PHOTO,
										DIRECT_MESSAGE,
										
					};
	
	static private NotificationManager instance = null;
	private Map<Enum<ObservableNames>, Observable> observables;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	private NotificationManager(){
		//register all teh observers
		this.logger.info("Creating Notification manager");
		observables = new HashMap<Enum<ObservableNames>, Observable>();
		this.registerAllObservers();
		
		
	}
	
	/**
	 * Singleton instance getter
	 * 
	 * @return Singleton inscance of NotificationManager
	 */
	static public NotificationManager getInstance(){
		
		if (instance == null)
			instance = new NotificationManager();

		return instance;
		
	}
	
	/**
	 * Calls all the register*Observers methods and adds the resultant observables
	 * in the observables map
	 * 
	 */
	private void registerAllObservers()
	{
		this.observables.put(ObservableNames.VERIFY_ACCOUNT, this.registerVerifyAccountObservers());
		this.observables.put(ObservableNames.PHOTO_UPLOADED, this.registerPhotoUploadObservers());
		this.observables.put(ObservableNames.NEW_FOLLOWER, this.registerNewFollowerObservers());
		this.observables.put(ObservableNames.REPORT_PHOTO, this.registerReportPhotoObservers());
		this.observables.put(ObservableNames.DIRECT_MESSAGE, this.registerDirectMessageObservers());
	}

	
	public VerifyAccountObservable getVerifyAccountObservable()
	{
		//we cast this way the user can use in VerifyAccount mode or observable mode if he/she wants
		return (VerifyAccountObservable)this.observables.get(ObservableNames.VERIFY_ACCOUNT);
	}
	
	public DirectMessageObservable getDirectMessageObservable()
	{
		//we cast this way the user can use in DirectMessage mode or observable mode if he/she wants
		return (DirectMessageObservable)this.observables.get(ObservableNames.DIRECT_MESSAGE);
	}
	
	public PhotoUploadedObservable getPhotoUploadedObservable()
	{
		//we cast this way the user can use in VerifyAccount mode or observable mode if he/she wants
		return (PhotoUploadedObservable)this.observables.get(ObservableNames.PHOTO_UPLOADED);
	}
	
	public NewFollowerObservable getNewFollowerObservable()
	{
		return (NewFollowerObservable)this.observables.get(ObservableNames.NEW_FOLLOWER);
	}
	
	public ReportPhotoObservable getReportPhotoObservable()
	{
		return (ReportPhotoObservable)this.observables.get(ObservableNames.REPORT_PHOTO);
	}
	
	
	/**
	 * Registers all the observers in the VerifyAccount observable
	 * 
	 * @return the observable object with all the observers registered
	 */
	private Observable registerVerifyAccountObservers()
	{
		this.logger.info("Registering verify account observers");
		
		//create all the observers
		Observer verifyAccountObservers[] = {
												new EmailObserver(),
											};
		
		VerifyAccountObservable vao = new VerifyAccountObservable();
		
		//register all the observers
		for (Observer i: verifyAccountObservers)
			vao.addObserver(i);
		
		return vao;
	}
	
	/**
	 * Registers all the observers in the VerifyAccount observable
	 * 
	 * @return the observable object with all the observers registered
	 */
	private Observable registerDirectMessageObservers()
	{
		this.logger.info("Registering verify account observers");
		
		//create all the observers
		Observer directMessageObservers[] = {
												new EmailObserver(),
											};
		
		DirectMessageObservable dmo = new DirectMessageObservable();
		
		//register all the observers
		for (Observer i: directMessageObservers)
			dmo.addObserver(i);
		
		return dmo;
	}
	
	/**
	 *  Registers all the observers in the PhotoUpload observable
	 * 
	 * @return
	 */
	private Observable registerPhotoUploadObservers()
	{
		this.logger.info("Registering photo upload observers");
		
		//create all the observers
		Observer photoUploadedObservers[] = {
												new LoggerObserver(),
												new PersistenceObserver(),
											};
		
		PhotoUploadedObservable puo = new PhotoUploadedObservable();
		
		//register all the observers
		for (Observer i: photoUploadedObservers)
			puo.addObserver(i);
		
		return puo;
	}
	
	private Observable registerNewFollowerObservers()
	{
		this.logger.info("Registering new follower observers");
		
		//create all the observers
		Observer newFollowerObservers[] = {
												new EmailObserver(),
												new PersistenceObserver(),
											};
		
		NewFollowerObservable nfo = new NewFollowerObservable();
		
		//register all the observers
		for (Observer i: newFollowerObservers)
			nfo.addObserver(i);
		
		return nfo;
	}
	
	private Observable registerReportPhotoObservers()
	{
		this.logger.info("Registering report photo observers");
		
		//create all the observers
		Observer reportPhotoObservers[] = {
												new EmailObserver(),
												new PersistenceObserver(),
											};
		
		ReportPhotoObservable rpo = new ReportPhotoObservable();
		
		//register all the observers
		for (Observer i: reportPhotoObservers)
			rpo.addObserver(i);
		
		return rpo;
		
	}
}
