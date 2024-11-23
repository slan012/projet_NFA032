package app;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Form {
	
	private Pattern nomsPattern = Pattern.compile("\\b[^\\d\\W]+\\b");
	private Pattern datePattern = Pattern.compile("^(?:0[1-9]|[12]\\d|3[01])([\\/.-])(?:0[1-9]|1[012])\\1(?:19|20)\\d\\d$");
	private Pattern adressePattern = Pattern.compile("[0-9]*\\s*[A-Za-z\\s]+[0-9]*");
	private Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9].[a-zA-Z0-9\\._%\\+\\-]{0,63}@[a-zA-Z0-9\\.\\-]+\\.[a-zA-Z]{2,30}$");
	
	static boolean match; // true si l'entrée utlisateur correspond au pattern demandé

	// Validation noms, prénoms, etc...
	public boolean validateNom(String str) {
		Matcher nomMatcher = nomsPattern.matcher(str);
		match = nomMatcher.find();
		if (!match) {
			System.out.println("Nom invalide...Entrez votre nom en lettres.\n");
			return false;
		}
		return true;
	}
	
	// Validation du format du mail uniquement
		public boolean validateEmailFormat(String email) {
			
			Matcher emailMatcher = emailPattern.matcher(email);
			match = emailMatcher.find();
			
			if (!match) {
				System.out.println("Email invalide...\n");
				return false;
			}
			return true;
		}
	
	// Validation du format du mail + vérification si mail existant pour modification
	public boolean validateEmail(String newEmail, String oldEmail) {
		
		Matcher emailMatcher = emailPattern.matcher(newEmail);
		match = emailMatcher.find();
		
		if (!match) {
			System.out.println("Email invalide...\n");
			return false;
		}
		if (Utilisateur.userExists(newEmail) && !newEmail.equals(oldEmail)) {
			System.out.println("Cet email appartient à un autre utilisateur!\n");
			return false;
		}
		return true;
	}
	
	// Validation du format du mail + vérification si mail existant pour ajout
	public boolean validateEmail(String newEmail) {
		
		Matcher emailMatcher = emailPattern.matcher(newEmail);
		match = emailMatcher.find();
		
		if (!match) {
			System.out.println("Email invalide...\n");
			return false;
		}
		if (Utilisateur.userExists(newEmail)) {
			System.out.println("Cet email appartient à un autre utilisateur!\n");
			return false;
		}
		return true;
	}
	
	// Validation adresse postale
	public boolean validateAdress(String adress) {
		Matcher adresseMatcher = adressePattern.matcher(adress);
		match = adresseMatcher.find();
		if (!match) {
			System.out.println("Adresse invalide...\n");
			return false;
		}
		return true;
	}
	
	// Validation date de naissance
	public boolean validateDate(String date) {
		
		Matcher dateMatcher = datePattern.matcher(date);
		match = dateMatcher.find();
		if (!match) {
			System.out.println("Date invalide...Saisir la date au format JJ/MM/AAAA\n");
			return false;
		}
		LocalDate lDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		if (lDate.isAfter(LocalDate.now())) {
			System.out.println("Saisir une date antérieure à aujourd'hui...\n");
			return false;
		}
		return true;
	}
	
	// Validation du profil utilisateur
	public boolean validateProfil (String profil) {
		for (Profil p : Profil.values()) {
			if (p.getProfil().equals(profil)) {
				return true;
			}
		}
		System.out.println("Erreur! Sélectionnez un profil parmi : Auditeur, Enseignant, Direction...\n");
		return false;
	}
	
	// Validation du mot de passe utilisateur pour ajout
	public boolean validatePassword (String pwd) {
		if ((pwd.length() > 0)) {
			return true;
		} else {
			System.out.println("Erreur! Le mot de passe ne peut pas être vide...\n");
			return false;
		}
	}

}
