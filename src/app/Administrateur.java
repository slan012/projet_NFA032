package app;

import java.io.*;
import java.time.LocalDate;



public class Administrateur extends Utilisateur {
    
	public Administrateur(String email, String password) {
        super(email, password, "admin");
    }

    public Administrateur() { };

    // Authentification utilisateur 
    public static boolean authUser(String mail, String password) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(Files.COMPTES));
            String line = br.readLine();

            while (true) {
                if(line == null) break;

                String[] user = line.split(";");
                if (user[0].equals(mail) && user[1].equals(password) && user[2].equalsIgnoreCase("admin")) {
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

    //Ajout particulier dans annuaire + compte
    public void addUser(String lastName, String firstName, String mail, String address, String birthDate, String profil, String password) {
    	
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(Files.ANNUAIRE, true));
            BufferedWriter bw2 = new BufferedWriter(new FileWriter(Files.COMPTES, true));

            //SI particulier n'existe pas -> ajout fichiers ANNUAIRE et COMPTE
            if (!userExists(mail)) {
                LocalDate addDate = LocalDate.now();
                LocalDate editDate = addDate;

                bw.write(lastName + ";" + firstName + ";" + mail + ";" + address + ";" + birthDate + ";" + profil + ";" + addDate + ";" + editDate + "\n");
                bw.flush();
                bw.close();

                bw2.write(mail + ";" + password + ";" + "particulier" + "\n");
                bw2.flush();
                bw2.close();

                System.out.println("\nL'utilisateur a bien été enregistré\n");
            } else {
                System.out.println("Un compte rattaché à cette adresse email existe déjà");
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    //Ajour d'un compte dans le fichier comptes
    public void ajoutComptes(Utilisateur user) {

        // Ajout de l'objet Utilisateur dans le fichier de Comptes

        File comptes = new File (Files.COMPTES);

        if (!comptes.exists()) {
            try {
                comptes.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
            else {
                try {
                    FileWriter fw = new FileWriter(comptes, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(user.getEmail() + ";" + user.getPassword()+ ";" + user.getRole());
                    bw.newLine();

                    bw.close();
                    fw.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    // Modification d'une fiche annuaire
	public boolean modifierLigneAnnuaire(Particulier oldData, Particulier newData) {

		StringBuilder annuaireContent = new StringBuilder(); // Objet permettant de construire les lignes du fichier

		boolean modifiedUser = false;

		// Modification annuaire
		try {
			File annuaire = new File(Files.ANNUAIRE);
			BufferedReader bra = new BufferedReader(new FileReader(annuaire));

			String[] tab;
			String line;
			while ((line = bra.readLine()) != null) {
				tab = line.split(";");
				if (tab[2].equals(oldData.getEmail())) {

					annuaireContent.append(newData.getName() + ";" + newData.getFirstName() + ";" + newData.getEmail() + ";" + newData.getAdress() + ";" + newData.getBirthDate()
					+ ";" + newData.getProfile() + ";" + oldData.getAddDate() + ";" + LocalDate.now());
					annuaireContent.append("\n");
					modifiedUser = true;
				} else {
					annuaireContent.append(line);
					annuaireContent.append("\n");
				}
			}
			bra.close();
			File annuaireModif = new File(Files.ANNUAIRE);
			BufferedWriter bwa = new BufferedWriter(new FileWriter(annuaireModif));
			bwa.write(annuaireContent.toString());
			bwa.flush();
			bwa.close();

			// Modification dans le compte pour prendre en compte le nouveau mail ou mot de passe
			if (!oldData.getEmail().equals(newData.getEmail()) || !oldData.getPassword().equals(newData.getPassword())) {
				File comptes = new File(Files.COMPTES);
				BufferedReader brc = new BufferedReader(new FileReader(comptes));
				StringBuilder comptesContent = new StringBuilder();

				String[] userTab;
				String lineCompte;
				while ((lineCompte = brc.readLine()) != null) {
					userTab = lineCompte.split(";");
					if (userTab[0].equals(oldData.getEmail())) {
						comptesContent.append(newData.getEmail() + ";" + newData.getPassword()+ ";" + oldData.getRole());
						comptesContent.append("\n");
					} else {
						comptesContent.append(lineCompte);
						comptesContent.append("\n");
					}
				}
				brc.close();
				File comptesModif = new File(Files.COMPTES);
				BufferedWriter bwc = new BufferedWriter(new FileWriter(comptesModif));
				bwc.write(comptesContent.toString());
				bwc.flush();
				bwc.close();
			}

		} catch (IOException exc) {
			System.out.print(exc.getMessage());
			return false;
		}
		if (modifiedUser) {
			System.out.println("\nLa fiche annuaire a bien été modifiée.");
		}
		return modifiedUser;
	}
	
	public boolean isAdmin() {
		return true;
	}
}
