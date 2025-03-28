public class Plat {
    private String nom;
    private double prix;
    private String type;

    public Plat(String nom, double prix, String type) {
        this.nom = nom;
        this.prix = prix;
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Plat: " + nom + " (" + type + ") - " + prix + " €";
    }
}
