package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Particulier extends Utilisateur {

    private String name, firstName, adress, birthDate, addDate, modificationDate, profile;
    
    // Constructeur récupérant les données compte + annuaire
    public Particulier(String email, String password, String role, String name, String firstName, String adress, String birthDate, String profile, String addDate, String modificationDate) {
        super(email, password, "particulier");
        this.name = name;
        this.firstName = firstName;
        this.adress = adress;
        this.birthDate = birthDate;
        this.addDate = addDate;
        this.modificationDate = modificationDate;
        this.profile = profile;
    }
    
    // Constructeur récupérant les données annuaire uniquement
    public Particulier(String name, String firstName, String email, String adress, String birthDate, String profile, String addDate, String modificationDate) {
        super.setEmail(email);
    	this.name = name;
        this.firstName = firstName;
        this.adress = adress;
        this.birthDate = birthDate;
        this.addDate = addDate;
        this.modificationDate = modificationDate;
        this.profile = profile;
    }
    
    // Constructeur récupérant les données compte uniquement
    public Particulier(String email, String password, String role) {
        super(email, password, role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getInfoAnnuaire() {
        return this.name + ";"
                + this.firstName + ";"
                + super.getEmail() + ";"
                + this.adress + ";"
                + this.birthDate + ";"
                + this.profile + ";"
                + this.addDate + ";"
                + this.modificationDate;
    }
    
    // Modification de la fiche annuaire d'un particulier
    public boolean modifierLigneAnnuaire(Particulier oldData, Particulier newData) {

		StringBuilder annuaireContent = new StringBuilder(); // Objet permettant de construire les lignes du fichier
		
		boolean modifiedUser = false;

		// Modification annuaire
		try {
			BufferedReader bra = new BufferedReader(new FileReader(Files.ANNUAIRE));

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
			
			BufferedWriter bwa = new BufferedWriter(new FileWriter(Files.ANNUAIRE));
			bwa.write(annuaireContent.toString());
			bwa.flush();
			bwa.close();

			// Modification dans le compte pour prendre en compte le nouveau mail si modifié
			if (!oldData.getEmail().equals(newData.getEmail())) {
				
				File comptes = new File(Files.COMPTES);
				BufferedReader brc = new BufferedReader(new FileReader(comptes));
				StringBuilder comptesContent = new StringBuilder();
	
				String[] userTab;
				String lineCompte;
				while ((lineCompte = brc.readLine()) != null) {
					userTab = lineCompte.split(";");
					if (userTab[0].equals(oldData.getEmail())) {
						comptesContent.append(newData.getEmail() + ";" + oldData.getPassword()+ ";" + oldData.getRole());
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
			System.out.println("\nVotre fiche a bien été modifiée.\n");
		}
		return modifiedUser;
	}
    
    public boolean isAdmin() {
		return false;
	}
    
    @Override
	public String toString() {
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY"); // Permet de formatter la date selon le pattern voulu
		
    	String str = "\n     Nom : " + this.name;
		str += "\n     Prenom : " + this.firstName;
		str += "\n     Email : " + this.getEmail(); 
		str += "\n     Adresse : " + this.adress;
		str += "\n     Date de naissance : " + this.birthDate;
		str += "\n     Profil : " + this.profile;
		str += "\n     Date ajout : " + formatter.format(LocalDate.parse(this.addDate));
		str += "\n     Date modif : " + formatter.format(LocalDate.parse(this.modificationDate)) + "\n";
		
		return str;
	}
}
