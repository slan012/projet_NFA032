package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Recherche {

    public static void afficherResultats(List<Particulier> resultats) {
        if (resultats.isEmpty()) {
            System.out.println("\nAucun resultat trouvé.\n");
        } else {
            Collections.sort(resultats, new NameComparator());

            System.out.println("Résultats :");
            for (Particulier resultat : resultats) {
                System.out.println(resultat);
            }
        }
    }

    // Recherche par nom
    public static List<Particulier> rechercherDansCSVParNom(String nomRecherche) {
        List<Particulier> resultats = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(Files.ANNUAIRE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String nom = parts[0];

                if (nomRecherche.equalsIgnoreCase(nom)) {
                    Particulier utilisateur = creerUtilisateurAPartirDeCSV(parts);
                    resultats.add(utilisateur);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(resultats, (p1, p2) -> p2.getAddDate().compareTo(p1.getAddDate()));

        return resultats.stream().limit(10).collect(Collectors.toList());
    }
    
    // Recherche par mail
    public static Particulier rechercherDansCSVParEmail(String emailRecherche) {
        try (BufferedReader reader = new BufferedReader(new FileReader(Files.ANNUAIRE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String email = parts[2];

                if (emailRecherche.equalsIgnoreCase(email)) {
                    return creerUtilisateurAPartirDeCSV(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

 // Recherche par profil
    public static List<Particulier> rechercherDansCSVParProfil(String profilRecherche) {
        List<Particulier> resultats = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(Files.ANNUAIRE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String profil = parts[5];

                if (profilRecherche.equalsIgnoreCase(profil)) {
                    Particulier utilisateur = creerUtilisateurAPartirDeCSV(parts);
                    resultats.add(utilisateur);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(resultats, (p1, p2) -> p2.getAddDate().compareTo(p1.getAddDate()));
        return resultats.stream().limit(10).collect(Collectors.toList());
    }
    
  
    private static Particulier creerUtilisateurAPartirDeCSV(String[] parts) {
        String name = parts[0];
        String firstname = parts[1];
        String email = parts[2];
        String adress = parts[3];
        String birthdate = parts[4];
        String role = parts[5];
        String addDate = parts[6];
        String updateDate = parts[7];

        return new Particulier(name, firstname, email, adress, birthdate, role, addDate, updateDate);
    }
}
