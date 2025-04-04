public abstract class Employe {
    private String nom;
    private String prenom;
    private String poste;

    public Employe(String nom, String prenom, String poste) {
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getPoste() {
        return poste;
    }

    @Override
    public String toString() {
        return poste + " : " + prenom + " " + nom;
    }

    public abstract void travailler();
}

class Serveur extends Employe {
    
    public Serveur(String nom, String prenom) {
        super(nom, prenom, "Serveur");
    }

    @Override
    public void travailler() {
        System.out.println("Le serveur " + getPrenom() + " " + getNom() + " prend les commandes et les envoie en cuisine.");
    }
    
    public void prendreCommande(Commande commande) {
        System.out.println(getPrenom() + " " + getNom() + " a pris une nouvelle commande.");
    }
    
    public void servirCommande(Commande commande) {
        System.out.println(getPrenom() + " " + getNom() + " sert la commande.");
    }
}

class Cuisinier extends Employe {
    private Stock stock;
    
    public Cuisinier(String nom, String prenom, Stock stock) {
        super(nom, prenom, "Cuisinier");
        this.stock = stock;
    }

    @Override
    public void travailler() {
        System.out.println("Le cuisinier " + getPrenom() + " " + getNom() + " prépare les plats et met à jour le stock.");
    }
    
    public boolean preparerPlat(Plat plat) {
        if (stock.verifierDisponibilite(plat)) {
            System.out.println(getPrenom() + " " + getNom() + " prépare le plat: " + plat.getNom());
            stock.miseAJourStock(plat);
            return true;
        } else {
            System.out.println("Impossible de préparer " + plat.getNom() + ": ingrédients insuffisants.");
            return false;
        }
    }
}

class Gerant extends Employe {
    private Stock stock;
    
    public Gerant(String nom, String prenom, Stock stock) {
        super(nom, prenom, "Gérant");
        this.stock = stock;
    }

    @Override
    public void travailler() {
        System.out.println("Le gérant " + getPrenom() + " " + getNom() + " supervise le restaurant et gère le stock.");
    }
    
    public void verifierStock() {
        System.out.println(getPrenom() + " " + getNom() + " vérifie l'état du stock:");
        stock.afficherStock();
    }
    
    public void ajouterAuStock(String ingredient, int quantite) {
        stock.ajouterIngredient(ingredient, quantite);
        System.out.println(getPrenom() + " " + getNom() + " a ajouté au stock: " + ingredient + " (" + quantite + " unités)");
    }
    
    public void gererEmployes() {
        System.out.println(getPrenom() + " " + getNom() + " gère les employés du restaurant.");
    }
}