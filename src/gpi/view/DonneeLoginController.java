package gpi.view;

import gpi.exception.LoginException;

import java.util.Properties;

import utils.Propriete;

public class DonneeLoginController {
	private Properties p;
	public DonneeLoginController(){
		this.p = Propriete.getInstance().getProperties();
	}
	
	public void controllerLoginEtMotDePasse(String login,String motDePasse) throws LoginException{
		if(!login.equals(p.getProperty("loginApplication"))){
			throw new LoginException("Login incorrect");
		}
		if(!motDePasse.equals(p.getProperty("motDePasseApplication"))){
			throw new LoginException("Mot de passe incorrect");
		}
	}
}
