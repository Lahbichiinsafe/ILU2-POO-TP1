package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
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

	public String afficherVillageois() {
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
		
		
		
		private int trouverEtatLibre() {
			for (int i =0 ; i<etals.length ; i++) {
				if(etals[i].getVendeur() == null)
					return i;
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
		
		
		
		public String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			for(int i=0 ; i<etals.length ; i++) {
				if(etals[i].getVendeur() != null) {
					chaine.append(etals[i]).append("\n");
				}
				else {
					chaine.append("Il reste ").append( (etals.length) - (chaine.length()) ).append(" �tals non utilis�s dans le march�.\n");
				}
			}
			return chaine.toString();
		}		
   }   
}

