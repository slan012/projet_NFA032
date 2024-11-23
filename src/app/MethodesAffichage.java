package app;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MethodesAffichage {

	final Scanner sc;

	public MethodesAffichage(Scanner sc) {
		this.sc = sc;
	}

	public void choisir() {
		System.out.println("Bienvenue dans l'annuaire NFA032 \n");
		System.out.println("Adminitrateur:");
		System.out.println("1. Ajouter une personne");
		System.out.println("    A. Ajouter un Admin");
		System.out.println("    B. Ajouter un particulier");
		System.out.println();
		System.out.println("Particulier:");
		System.out.println("2. Rechercher un ou des particuliers");
		System.out.println("    A. Par nom");
		System.out.println("    B. Par email");
		System.out.println("    C. Par profil");
		System.out.println("3. Modifier mes informations personnelles");
		System.out.println();
		System.out.print("Faites votre choix (1, 2 ou 3) : ");
		String choixOperation1 = sc.nextLine();
		try {
			verificationReponse1(choixOperation1);
		} catch (ErreurSaisieException error) {
			System.out.println("Une erreur s'est produite: ");
		}
		switch (choixOperation1) {
			case "1" -> {
				String mail;
				Form form = new Form();
				System.out.println("\nVous devez vous identifier en tant qu'administrateur pour accéder à ce menu : ");
				
				do {
					System.out.print("Saisissez votre adresse email : ");
					mail = sc.nextLine();
				} while (!form.validateEmailFormat(mail));
				
				System.out.print("Saisissez votre mot de passe : ");
				String passwordProposed = sc.nextLine();
				if (Administrateur.authUser(mail, passwordProposed)) {
					afficherAjout(mail);
				} else {
				System.out.println("Ce compte administrateur n'existe pas ou le mot de passe est incorrect!");
				}
			}
				
			case "2" -> afficherRecherche();
			case "3" -> modifierAnnuaire();
			default -> {
				System.out.println("Le choix n'est pas valide, veuillez recommencer");
				choisir();
			}
		}

	}

	void verificationReponse1(String choixOperation1) throws ErreurSaisieException {
		if (!choixOperation1.equals("1") && !choixOperation1.equals("2") && !choixOperation1.equals("3")) {
			throw new ErreurSaisieException("Le choix n'est pas valide");
		} else {
			System.out.println("Choix valide");
		}
	}

	void verificationReponseAjout(String choixAdministrateur) throws ErreurSaisieException {
		if (!"A".equalsIgnoreCase(choixAdministrateur) && !"B".equalsIgnoreCase(choixAdministrateur)) {
			throw new ErreurSaisieException("Le choix n'est pas valide");
		} else {
			System.out.println("Choix valide");
		}
	}

	void verificationReponseRecherche(String choixRecherche) throws ErreurSaisieException {
		if (!"A".equalsIgnoreCase(choixRecherche) && !"B".equalsIgnoreCase(choixRecherche)
				&& !"C".equalsIgnoreCase(choixRecherche)) {
			throw new ErreurSaisieException("Le choix n'est pas valide");
		} else {
			System.out.println("Choix valide");
		}
	}

	void afficherAjout(String mail) {

		Administrateur admin = (Administrateur) Utilisateur.getUtilisateur(mail);
		System.out.println("\nVous êtes un administrateur, que souhaitez-vous faire?");
		System.out.println("    A. Ajouter un Admin");
		System.out.println("    B. Ajouter un particulier");
		System.out.print("\nVotre réponse : ");
		String choixOperationAdministrateur = sc.nextLine();

		try {
			verificationReponseAjout(choixOperationAdministrateur);
		} catch (ErreurSaisieException error) {
			System.out.println("Une erreur s'est produite: ");
		}
		if ("A".equalsIgnoreCase(choixOperationAdministrateur)) {
			addCompte(admin);
		} else if ("B".equalsIgnoreCase(choixOperationAdministrateur)) {
			addUser(admin);
		} else {
			System.out.println("Le choix n'est pas valide, veuillez recommencer");
			afficherAjout(mail);
		}
	
	}

	// Méthode Odile
	public void addCompte(Administrateur admin) {
		String mail, password = null;
		Form form = new Form();
		System.out.println("Veuillez entrer les données du nouvel administrateur dans le fichier Comptes :");

		do {
			System.out.print("Saisir l'adresse email : ");
			mail = sc.nextLine();
		} while (!form.validateEmail(mail));

		do {
			System.out.print("Saisir un mot de passe pour l'utilisateur : ");
			password = sc.nextLine();
		} while (!form.validatePassword(password));

		Administrateur userToAdd = new Administrateur(mail, password);
		admin.ajoutComptes(userToAdd);

		System.out.println("\nLe nouveau compte a bien été enregistré.\n");

	}

	// Méthode Ethan
	void addUser(Administrateur admin) {

		Form form = new Form();
		String lastName, firstName, mail, address, birthDate, profil, password = null;
		
		// Saisi des données à enregistrer
		do {
			System.out.print("\nSaisir le nom : ");
			lastName = sc.nextLine();
		} while (!form.validateNom(lastName));

		do {
			System.out.print("Saisir le prénom : ");
			firstName = sc.nextLine();
		} while (!form.validateNom(firstName));

		do {
			System.out.print("Saisir l'adresse email : ");
			mail = sc.nextLine();
		} while (!form.validateEmail(mail));

		do {
			System.out.print("Saisir l'adresse postale : ");
			address = sc.nextLine();
		} while (!form.validateAdress(address));

		do {
			System.out.print("Saisir la date de naissance (JJ/MM/AAAA) : ");
			birthDate = sc.nextLine();
		} while (!form.validateDate(birthDate));

		do {
			System.out.print("Saisir le profil : ");
			profil = sc.nextLine();
		} while (!form.validateProfil(profil));

		do {
			System.out.print("Saisir un mot de passe pour l'utilisateur : ");
			password = sc.nextLine();
		} while (!form.validatePassword(password));

		// Ajout du particulier
		admin.addUser(lastName, firstName, mail, address, birthDate, profil, password);

	}

	void afficherRecherche() {
		System.out.println("Vous êtes un particulier, vous souhaitez rechercher un particulier, à l'aide de...");
		System.out.println("    A. Son nom");
		System.out.println("    B. Son email");
		System.out.println("    C. Son profil");
		String choixOperationRecherche = sc.nextLine();
		try {
			verificationReponseRecherche(choixOperationRecherche);
		} catch (ErreurSaisieException error) {
			System.out.println("Une erreur s'est produite: ");
		}
		
		// Méthodes Anthony
		switch (choixOperationRecherche) {
			case "A", "a" -> { 
				// méthode rechercherNom
				System.out.println("\nRecherche par Nom :");
		        System.out.print("Entrez le nom à rechercher : ");
		        String nomRecherche = sc.nextLine();

		        List<Particulier> résultats = Recherche.rechercherDansCSVParNom(nomRecherche);

		        Recherche.afficherResultats(résultats);
			}
			case "B", "b" -> {
				// méthode rechercheEmail
				Form form = new Form();
				String emailRecherche;
				System.out.println("\nRecherche par Email :");
				
				do {
					System.out.print("Entrez l'e-mail à rechercher : ");
					emailRecherche = sc.nextLine();
				} while (!form.validateEmailFormat(emailRecherche));
				
		        Particulier resultat = Recherche.rechercherDansCSVParEmail(emailRecherche);

		        if (resultat != null) {
		            System.out.println("Résultat trouvé :");
		            System.out.println(resultat);
		        } else {
		            System.out.println("\nAucun resultat trouvé.\n");
		        }
			}
			case "C", "c" -> { 
				System.out.println("\nRecherche par Profil :");
		        System.out.print("Entrez le profil à rechercher : ");
		        String profilRecherche = sc.nextLine();

		        List<Particulier> resultats = Recherche.rechercherDansCSVParProfil(profilRecherche);

		        Recherche.afficherResultats(resultats);
			}
			default -> {
				System.out.println("Le choix n'est pas valide, veuillez recommencer");
				afficherRecherche();
			}
		}

	}

	// Méthode Etienne
	void modifierAnnuaire() {
		String userMail= null;
		Form form = new Form();
		System.out.println("\n3 - Vous souhaitez modifier vos informations personnelles : ");

		// Authentification
		System.out.println("    Vous devez vous identifier pour accéder à ce menu : ");
		do {
			System.out.print("Saisir votre adresse email : ");
			userMail = sc.nextLine();
		} while (!form.validateEmailFormat(userMail));
		
		System.out.print("Saisissez votre mot de passe : ");
		String userPasswd = sc.nextLine();

		if (Utilisateur.authUser(userMail, userPasswd)) {

			// Récupération utilisateur
			// user = utilisateur du programme
			Utilisateur user = Utilisateur.getUtilisateur(userMail);
			System.out.println("\nVous êtes un " + user.getRole().toLowerCase() + "\n");

			String oldEmail; // oldEmail correspond au mail actuel de la ligne que l'on souhaite modifier dans l'annuaire
			
			if (user.isAdmin()) {
				System.out.print("Entrez l'email de la ligne à modifier : ");
				oldEmail = sc.nextLine();
			} else {
				oldEmail = user.getEmail();
			}

			if (Utilisateur.userExists(oldEmail) && Utilisateur.getRole(oldEmail).equals("particulier")) {

				// oldData = objet Particulier contenant les données actuelles à modifier
				Particulier oldData = (Particulier) Utilisateur.getUtilisateur(oldEmail);
				
				System.out.println("\nInformations actuelles de la fiche à modifier : ");
    			System.out.println(oldData);

				String newNom, newPrenom, newEmail, newAdressePostale, newDateNaissance, newProfil, newMdp = null;

				System.out.println("\nLaissez le champ vide si vous souhaitez garder la valeur actuelle :");
				do {
					System.out.print("Saisir le nouveau nom : ");
					newNom = sc.nextLine();
					if (newNom.length() == 0) {
						newNom = oldData.getName();
					}
				} while (!form.validateNom(newNom));

				do {
					System.out.print("Saisir le nouveau prénom : ");
					newPrenom = sc.nextLine();
					if (newPrenom.length() == 0) {
						newPrenom = oldData.getFirstName();
					}
				} while (!form.validateNom(newPrenom));

				do {
					System.out.print("Saisir la nouvelle adresse email : ");
					newEmail = sc.nextLine();
					if (newEmail.length() == 0) {
						newEmail = oldData.getEmail();
					}
				} while (!form.validateEmail(newEmail, oldEmail));

				do {
					System.out.print("Saisir la nouvelle adresse postale : ");
					newAdressePostale = sc.nextLine();
					if (newAdressePostale.length() == 0) {
						newAdressePostale = oldData.getAdress();
					}
				} while (!form.validateAdress(newAdressePostale));

				do {
					System.out.print("Saisir la nouvelle date de naissance (JJ/MM/AAAA): ");
					newDateNaissance = sc.nextLine();
					if (newDateNaissance.length() == 0) {
						newDateNaissance = oldData.getBirthDate();
					}
				} while (!form.validateDate(newDateNaissance));

				do {
					System.out.print("Saisir le nouveau profil (Auditeur, Enseignant, Direction): ");
					newProfil = sc.nextLine();
					if (newProfil.length() == 0) {
						newProfil = oldData.getProfile();
					}
				} while (!form.validateProfil(newProfil));

				if (user.isAdmin()) {
					System.out.print("Saisir le nouveau mot de passe : ");
					newMdp = sc.nextLine();
					if (newMdp.length() == 0) {
						newMdp = oldData.getPassword();
					}
				}

				// newData = objet Utilisateur temporaire contenant les nouvelles données
				Particulier newData = new Particulier(newEmail, newMdp, "particulier", newNom, newPrenom,
						newAdressePostale, newDateNaissance, newProfil, LocalDate.now().toString(),
						oldData.getAddDate());

				// Modification de la fiche et du compte
				user.modifierLigneAnnuaire(oldData, newData);

			} else {
				System.out.println("Cet utilisateur n'existe pas dans l'annuaire");
			}

		} else {
			System.out.println("\nEmail ou mot de passe incorrect!");
		}
	}

	public boolean finir() {
		System.out.println("Avez-vous terminé? Oui ou Non");
		String end = sc.nextLine();
		boolean ended;
		if ("oui".equalsIgnoreCase(end)) {
			ended = true;
		} else if ("non".equalsIgnoreCase(end)) {
			ended = false;
		} else {
			ended = false;
			System.out.println("Le choix n'est pas valide, veuillez recommencer");
		}
		return ended;
	}
}
