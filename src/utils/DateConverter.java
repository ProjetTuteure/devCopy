package utils;

import java.time.LocalDate;

public class DateConverter {
	
	/**
	 * Retourne la date sous la forme JJ/MM/AAAA
	 * @param date la date à convertir en AAAA-MM-JJ
	 * @return la chaîne correspondant à la date JJ/MM/AAAA
	 */
	public static String getFrenchDate(String date){
		LocalDate dateMaintenance;
		dateMaintenance=LocalDate.parse(date);
	 	String chaine1="";
        String chaine2="";
        if(dateMaintenance.getDayOfMonth()<10){
            chaine1="0";
        }
        if(dateMaintenance.getMonthValue()<10){
            chaine2="0";
        }
        return chaine1+dateMaintenance.getDayOfMonth()+"/"+chaine2+dateMaintenance.getMonthValue()+"/"+dateMaintenance.getYear();
	}
}
