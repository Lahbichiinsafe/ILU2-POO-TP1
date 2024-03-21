package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}
	

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}
	
	@SuppressWarnings("serial")
	public class VillageSansChefException extends RuntimeException {
		  public VillageSansChefException(String s) {
		    super(s);
		  }
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}


   public String afficherVillageois() throws VillageSansChefException {
		try {
			chef.getNom();
		} catch (Exception e) {
			throw new VillageSansChefException("Il n'y aucun chef dans ce village !");
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	private static class Marche {
		private Etal[] etals ;
		
		
		
		private Marche(int nbEtals) {
			this.etals = new Etal[nbEtals];
			
			for (int i = 0; i<nbEtals;i++) {
				this.etals[i] = new Etal();
			}
		}
		
		
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			if ( indiceEtal >=0 && indiceEtal<etals.length && etals[indiceEtal].getVendeur()== null ) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
		}
		
		
		
        private int trouverEtalLibre() {
            for (int i = 0; i < etals.length; i++) {
                if (etals[i].getVendeur() == null) {
                    return i;
                }
            }
            return -1;
        }
		
		
		
		private Etal[] trouverEtals(String produit) {
			int Prodlength=0;
			for( int i=0 ; i<etals.length ; i++) {
				if(etals[i].contientProduit(produit) == true) {
					Prodlength ++;
				}
			}
			
			Etal[] resultat = new Etal[Prodlength];
			
			int j = 0;
			for(int i =0 ; i<etals.length ; i++) {
				if(etals[i].contientProduit(produit) == true) {
					resultat[j]=etals[i];
					j++;
				}	
			}
			
			return resultat;
		}
		
		
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0 ; i<etals.length ; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		
		
		private String afficherMarche() {
			int nbEtalVide =0;
			StringBuilder chaine = new StringBuilder();
			for(int i=0;i<etals.length;i++) {
				if ("L'étal est libre".equals(etals[i].afficherEtal())) {nbEtalVide++;}
			}
			chaine.append( "Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			return chaine.toString();
		}
	}	
	
	
	
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		int premierEtalNonOccupee = marche.trouverEtalLibre();
		StringBuilder chaine = new StringBuilder();

		
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + " " + ".\nLe vendeur " + vendeur.getNom());
		
		
		if (premierEtalNonOccupee!=-1){
			marche.utiliserEtal(premierEtalNonOccupee, vendeur, produit, nbProduit);
			premierEtalNonOccupee++;
			chaine.append(" des " + produit + " à l'étal n°" + premierEtalNonOccupee + ".\n");
			
		} else {
			chaine.append(" n'a pas trouver d'étal pour vendre ses " + produit + ".\n");
		}
		return chaine.toString();
	}
	
	
	

	public String rechercherVendeursProduit(String produit) {
		StringBuilder c = new StringBuilder();
		
		c.append("Les vendeurs qui proposent des " + produit + " sont :\n");
		
		
		Etal[] listeEtals = marche.trouverEtals(produit);
		
		
		for (int i=0;i<listeEtals.length;i++) {
			Etal etalActuel = listeEtals[i];
			if (etalActuel.contientProduit(produit))
				c.append("- " + (etalActuel.getVendeur()).getNom()+"\n");
		}
		return c.toString(); 
	}
	
	
	
	public Etal rechercherEtal(Gaulois vendeur) {
		Etal[] listeEtals = marche.etals;
		for (int i=0;i<marche.etals.length;i++) {
			if (listeEtals[i].getVendeur() == vendeur) {
				return listeEtals[i];
			}
		}
		return null;
	}
	
	
	
	public String partirVendeur(Gaulois vendeur) {
		StringBuilder chaine = new StringBuilder();
		Etal etalActuel = rechercherEtal(vendeur);
		chaine.append(etalActuel.libererEtal());
		return chaine.toString();
	}
	
	
	
    public String afficherMarche() {
        return marche.afficherMarche();
    }


}   


