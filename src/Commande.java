import java.util.ArrayList;
import java.util.List;

public class Commande {
    private List<Plat> plats;

    public Commande() {
        plats = new ArrayList<>();
    }

    public void ajoutePlat(Plat plat) {
        plats.add(plat);
    }

    public double calculTot() {
        double total = 0;
        for (Plat plat : plats) {
            total += plat.getPrix();
        }
        return total;
    }

    public void afficherCommande() {
        System.out.println("----- Détails de la commande -----");
        if (plats.isEmpty()) {
            System.out.println("Aucun plat");
        } else {
            for (Plat plat : plats) {
                System.out.println(plat);
            }
            System.out.printf("Total:", calculTot());
        }
    }
}
