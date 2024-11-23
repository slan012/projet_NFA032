package app;

import java.io.File;

public class Files {
	
	// Remplacez les valeurs des constantes suivantes par le chemin absolu vers vos fichiers annuaire et comptes (.txt ou .csv au choix)

	// public static final String COMPTES = "path_to_file_compte";
    public static final String COMPTES = new File("").getAbsolutePath() + "/src/data/comptes.csv";
    // public static final String ANNUAIRE = "path_to_file_annuaire";
    public static final String ANNUAIRE = new File("").getAbsolutePath() + "/src/data/annuaire.csv";
    
}
