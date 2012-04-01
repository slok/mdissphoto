package org.mdissjava.mdisscore.model.bo;

import org.mdissjava.mdisscore.model.pojo.Configuration;

public class ConfigurationBo {

	private boolean showNick;
	private boolean	showName;
	private boolean showSurname;
	private boolean showEmail;
	private boolean showBirthdate;
	private boolean showPhone;
	private boolean showGender;
	private boolean showCity;
	private boolean showCountry;
	private boolean showAddress;	
	private boolean showZip;	
	private boolean showCameras;
	
	public ConfigurationBo(){}
	
	public ConfigurationBo( Configuration conf){
		showNick = conf.isShowNick();
		showName = conf.isShowName();
		showSurname = conf.isShowEmail();
		showEmail = conf.isShowEmail();
		showBirthdate = conf.isShowBirthdate();
		showPhone = conf.isShowPhone();
		showGender = conf.isShowGender();
		showCity= conf.isShowCity();
		showCountry = conf.isShowCountry();
		showAddress = conf.isShowAddress();
		showZip = conf.isShowZip();
		showCameras = conf.isShowCameras();
	}
	
	public boolean isShowNick() {
		return showNick;
	}
	public void setShowNick(boolean showNick) {
		this.showNick = showNick;
	}
	public boolean isShowName() {
		return showName;
	}
	public void setShowName(boolean showName) {
		this.showName = showName;
	}
	public boolean isShowSurname() {
		return showSurname;
	}
	public void setShowSurname(boolean showSurname) {
		this.showSurname = showSurname;
	}
	public boolean isShowEmail() {
		return showEmail;
	}
	public void setShowEmail(boolean showEmail) {
		this.showEmail = showEmail;
	}
	public boolean isShowBirthdate() {
		return showBirthdate;
	}
	public void setShowBirthdate(boolean showBirthDate) {
		showBirthdate = showBirthDate;
	}
	public boolean isShowPhone() {
		return showPhone;
	}
	public void setShowPhone(boolean showPhone) {
		this.showPhone = showPhone;
	}
	public boolean isShowGender() {
		return showGender;
	}
	public void setShowGender(boolean showGender) {
		this.showGender = showGender;
	}
	public boolean isShowCity() {
		return showCity;
	}
	public void setShowCity(boolean showCity) {
		this.showCity = showCity;
	}
	public boolean isShowCountry() {
		return showCountry;
	}
	public void setShowCountry(boolean showCountry) {
		this.showCountry = showCountry;
	}
	public boolean isShowAddress() {
		return showAddress;
	}
	public void setShowAddress(boolean showAddress) {
		this.showAddress = showAddress;
	}
	public boolean isShowZip() {
		return showZip;
	}
	public void setShowZip(boolean showZip) {
		this.showZip = showZip;
	}
	public boolean isShowCameras() {
		return showCameras;
	}
	public void setShowCameras(boolean showCameras) {
		this.showCameras = showCameras;
	}	
	
	
	public boolean equals(ConfigurationBo conf)
	{
		if(!(showNick==conf.isShowNick()))return false;
		if(!(showName==conf.isShowName()))return false;
		if(!(showSurname==conf.isShowSurname()))return false;
		if(!(showEmail==conf.isShowEmail()))return false;
		if(!(showBirthdate==conf.isShowBirthdate()))return false;
		if(!(showPhone==conf.isShowPhone()))return false;
		if(!(showGender==conf.isShowGender()))return false;
		if(!(showCity==conf.isShowCity()))return false;
		if(!(showCountry==conf.isShowCountry()))return false;
		if(!(showAddress==conf.isShowAddress()))return false;
		if(!(showZip==conf.isShowZip()))return false;
		if(!(showCameras==conf.isShowCameras()))return false;
		return true;
		
		
	}
	
}
