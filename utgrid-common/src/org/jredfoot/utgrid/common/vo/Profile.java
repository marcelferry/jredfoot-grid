package org.jredfoot.utgrid.common.vo;

import java.util.ArrayList;
import java.util.List;

import org.jredfoot.utgrid.cert.pki.Credential;

public class Profile extends User {
	
	/** Define o timezone do usuario */
	String timezone;
	/** Define o locale do usuario*/
	String locale;
	/** Define as credenciais do usuario */
	List<Credential> credenciais;
	
	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}
	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}
	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}
	/**
	 * @return the credenciais
	 */
	public List<Credential> getCredenciais() {
		return credenciais;
	}
	/**
	 * @param credenciais the credenciais to set
	 */
	public void setCredenciais(List<Credential> credenciais) {
		this.credenciais = credenciais;
	}
	
	public void addCrendencial(Credential credencial){
		
		if(this.credenciais == null){
			this.credenciais = new ArrayList<Credential>();
		}
		
		
	}
	
	
}
