package app;

public enum Profil {
	AUDITEUR("Auditeur"),
	ENSEIGNANT("Enseignant"),
	DIRECTION("Direction");
	
	private final String profil;
	
	private Profil(String profil) {
		this.profil = profil;
	}
	
	public String getProfil() {
		return this.profil;
	}
}
