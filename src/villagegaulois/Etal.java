package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}


	public String libererEtal() {
		try {
			etalOccupe = false;
			StringBuilder chaine = new StringBuilder("Le vendeur " + vendeur.getNom() + " quitte son étal, ");
			int produitVendu = quantiteDebutMarche - quantite;
			if (produitVendu > 0) {
				chaine.append(
						"il a vendu " + produitVendu + " " + produit + " parmi les " + quantiteDebutMarche + " " + produit + " qu'il voulait vendre.\n");
			} else {
				chaine.append("il n'a malheureusement rien vendu.\n");
			}
			return chaine.toString();
		} catch(Exception e ) {
			throw new NullPointerException("");
		}
	}



	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	
	
	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
	   
	    if (quantiteAcheter < 1){
	        throw new IllegalArgumentException("La quantité doit être positive");
	    }

	    
	    if (acheteur == null) {
	        throw new IllegalStateException("L’acheteur ne doit pas être null");
	    }

	    
	    if (quantite == 0) {
	        throw new IllegalStateException("L’étal doit être occupé");
	    }

	    StringBuilder chaine = new StringBuilder();
	    chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter + " " + produit + " à " + vendeur.getNom());

	    // Gérer l'achat
	    if (quantiteAcheter > quantite) {
	        chaine.append(", malheureusement il n'y en a plus !");
	        quantiteAcheter = quantite;
	    }

	    quantite -= quantiteAcheter;
	    chaine.append(". " + acheteur.getNom() + " est ravi de tout trouver sur l'étal de " + vendeur.getNom() + "\n");
	    
	    return chaine.toString();
	}


	

	public boolean contientProduit(String produit) {
		return produit.equals(this.produit);
	}

}
