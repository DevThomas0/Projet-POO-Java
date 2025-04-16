import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<Plat> plats;

    public Menu() {
        plats = new ArrayList<>();
    }

    public void ajouterP(Plat plat) {
        plats.add(plat);
    }

    public List<Plat> getPlats() {
        return plats;
    }

    public void montreM() {
        System.out.println("----- Menu -----");
        int index = 1;
        for (Plat plat : plats) {
            System.out.println(index + ". " + plat);
            index++;
        }
    }

    public Plat platParIdx(int index) {
        if (index >= 1 && index <= plats.size()) {
            return plats.get(index - 1);
        }
        return null;
    }

    public Plat rechercherPlat(String nom) {
        for (Plat plat : plats) {
            if (plat.getNom().equalsIgnoreCase(nom)) {
                return plat;
            }
        }
        return null;
    }

}
