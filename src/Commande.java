public class Commande {
    private int id;
    private Plat[] plats;
    private int nbPlats;
    private int capaciteMax;
    private String etat;

    
    public Commande() {
        capaciteMax = 50;
        plats = new Plat[capaciteMax];
        nbPlats = 0;
        this.etat = "En attente";
    }

    public void ajoutePlat(Plat plat) {
        if (nbPlats < capaciteMax) {
            plats[nbPlats] = plat;
            nbPlats++;
        } else {
            System.out.println("La commande est pleine, impossible d'ajouter plus de plats.");
        }
    }

    public void retirerPlat(Plat plat) {
        boolean trouve = false;
        for (int i = 0; i < nbPlats; i++) {
            if (trouve) {
                plats[i-1] = plats[i];
            } else if (plats[i].getNom().equals(plat.getNom())) {
                trouve = true;
            }
        }
        
        if (trouve) {
            nbPlats--; 
            plats[nbPlats] = null;
        }
    }

    public double calculTot() {
        double total = 0;
        for (int i = 0; i < nbPlats; i++) {
            total = total + plats[i].getPrix();
        }
        return total;
    }

    private int compterOccurrencesPlat(Plat plat) {
        int compteur = 0;
        for (int i = 0; i < nbPlats; i++) {
            if (plats[i].getNom().equals(plat.getNom())) {
                compteur++;
            }
        }
        return compteur;
    }

    public void afficherCommande() {
        System.out.println("----- Détails de la commande -----");
        if (nbPlats == 0) {
            System.out.println("Aucun plat dans la commande");
        } else {
            Plat[] platsAffichés = new Plat[nbPlats];
            int nbPlatsAffichés = 0;
            
            for (int i = 0; i < nbPlats; i++) {
                boolean dejaAffiche = false;
                
                for (int j = 0; j < nbPlatsAffichés; j++) {
                    if (plats[i].getNom().equals(platsAffichés[j].getNom())) {
                        dejaAffiche = true;
                        break;
                    }
                }
                
                if (!dejaAffiche) {
                    int quantite = compterOccurrencesPlat(plats[i]);
                    System.out.println(plats[i] + " x" + quantite);
                    
                    platsAffichés[nbPlatsAffichés] = plats[i];
                    nbPlatsAffichés++;
                }
            }
            
            System.out.printf("Total: %.2f €%n", calculTot());
            System.out.println("État: " + etat);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Plat[] getPlats() {
        return plats;
    }
    
    public int getNbPlats() {
        return nbPlats;
    }
    
    public String getEtat() {
        return etat;
    }
    
    public void setEtat(String etat) {
        this.etat = etat;
    }
}
