package be.vinci.pae.domain;

public class Page {
	private int id;
	private String titre;
	private String uri;
	private String contenu;
	private User auteur;
	private String statusDePublication;
	private final String[] statusPossibles = { "hidden", "published" };

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public User getAuteur() {
		return auteur;
	}

	public void setAuteur(User auteur) {
		this.auteur = auteur;
	}

	public String getStatusDePublication() {
		return statusDePublication;
	}

	public void setStatusDePublication(String statusDePublication) {
		for (String string : statusPossibles) {
			if (string.equals(statusDePublication.toLowerCase())) {
				this.statusDePublication = statusDePublication;
			}
		}
		return;
	}

}
