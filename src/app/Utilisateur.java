package app;

import java.io.*;

public abstract class Utilisateur {
    private String email;
    private String password;
    private String role;

    public Utilisateur(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Utilisateur() { };

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
    	return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

    public String getInfoCompte() {
        return this.email + ";" + this.password + ";" + this.role;
    }

    public static boolean authUser(String mail, String password) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Files.COMPTES));
            String line = br.readLine();

            while (true) {
                if(line == null) break;

                String[] user = line.split(";");
                if (user[0].equals(mail) && user[1].equals(password)) {
                    br.close();
                    return true;
                }

                line = br.readLine();
            }

            br.close();
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
        return false;
    }

    // Vérification si utilisateur déjà existant
    public static boolean userExists(String email) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(Files.COMPTES));
            String line = br.readLine();

            while (true) {
                if (line == null) break;

                String[] user = line.split(";");
                if(email.equals(user[0])) {
                	br.close();
                	return true;
                }

                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Erreur d'accès au fichier : " + e.getMessage());
        }

        return false;
    }

	// Retourne un object Admin ou Particulier en fonction du mail passé en paramètre
 	// Si aucun utilisateur ne correspond retourne null
 	public static Utilisateur getUtilisateur(String mail){
 		try {
 			
 			// Reader pour comptes.csv
 			BufferedReader brc = new BufferedReader(new FileReader(Files.COMPTES));
 			
 			String [] user;
 			String ligneCompte = brc.readLine();
 			
 			while (ligneCompte != null) {
 				user = ligneCompte.split(";");
 				if (user[0].equals(mail)) {
 					if (user[2].equals("admin")) {
 						brc.close();
 						return new Administrateur(user[0], user[1]);

 					} else if (user[2].equals("particulier")) {
 						BufferedReader bra = new BufferedReader(new FileReader(Files.ANNUAIRE));
 						
 						String[] particulier;
 						String ligneAnnuaire = bra.readLine();
 						while (ligneAnnuaire != null) {
 							particulier = ligneAnnuaire.split(";");
 							if (particulier[2].equals(mail)) {
 								// On retourne un objet Particulier
 								bra.close();
 								return new app.Particulier(user[0], user[1], user[2], particulier[0], particulier[1],
 										particulier[3], particulier[4], particulier[5], particulier[6], particulier[7]);
 							}
 							ligneAnnuaire = bra.readLine();
 						}
 						bra.close();
 						brc.close();
 					}
 				}
 				ligneCompte = brc.readLine();
 			}
 			brc.close();

 		} catch (IOException | NullPointerException exc) {
 			System.out.print("Erreur durant l'ouverture du fichier : " + exc.getMessage());

 		}
 		return null;

 	}
 	
 	
 	// Récupère le role d'un utilisateur sans instancier d'objet Utilisateur
 	public static String getRole(String mail) {
 		try {
 			
 			BufferedReader br = new BufferedReader(new FileReader(Files.COMPTES));
 			String line = br.readLine();
 			
 			while (line != null) {
 				String[] user = line.split(";");
 				if (user[0].equals(mail)) {
 					br.close();
 					return user[2];
 				} else {
 					line = br.readLine();
 				}
 			}
 			br.close();
 		
 		} catch (IOException exc) {
 			System.out.print("Erreur durant l'ouverture du fichier : " + exc.getMessage());
 		}
 		return null;
 		
 	}

    public String toString() {
        return this.email +this.password +this.role;
    }

	protected abstract boolean modifierLigneAnnuaire(Particulier oldData, Particulier newData);
	public abstract boolean isAdmin();
}



