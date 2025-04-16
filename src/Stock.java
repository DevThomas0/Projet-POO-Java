import java.util.ArrayList;
import java.util.List;

public class Stock {
    private List<String> nomsIngredients;
    private List<Integer> quantitesIngredients;
    private List<String> platsRecettes;
    private List<List<String>> recettesIngredients;

    public Stock() {
        nomsIngredients = new ArrayList<>();
        quantitesIngredients = new ArrayList<>();
        platsRecettes = new ArrayList<>();
        recettesIngredients = new ArrayList<>();
    }

    public void ajouterIngredient(String nomIngredient, int quantite) {
        int index = nomsIngredients.indexOf(nomIngredient);
        if (index != -1) {
            int ancienneQuantite = quantitesIngredients.get(index);
            quantitesIngredients.set(index, ancienneQuantite + quantite);
        } else {
            nomsIngredients.add(nomIngredient);
            quantitesIngredients.add(quantite);
        }
    }

    public void definirRecette(String nomPlat, List<String> ingredientsNecessaires) {
        int index = platsRecettes.indexOf(nomPlat);
        if (index != -1) {
            recettesIngredients.set(index, ingredientsNecessaires);
        } else {
            platsRecettes.add(nomPlat);
            recettesIngredients.add(ingredientsNecessaires);
        }
    }

    public boolean verifierDisponibilite(Plat plat) {
        int index = platsRecettes.indexOf(plat.getNom());
        if (index == -1) {
            return false;
        }

        List<String> ingredientsNecessaires = recettesIngredients.get(index);
        for (String ingredient : ingredientsNecessaires) {
            int indexIngredient = nomsIngredients.indexOf(ingredient);
            if (indexIngredient == -1 || quantitesIngredients.get(indexIngredient) <= 0) {
                return false;         
            }
        }
        return true;
    }

    public boolean miseAJourStock(Plat plat) {
        if (!verifierDisponibilite(plat)) {
            return false;
        }

        int index = platsRecettes.indexOf(plat.getNom());
        List<String> ingredientsNecessaires = recettesIngredients.get(index);
        
        for (String ingredient : ingredientsNecessaires) {
            int indexIngredient = nomsIngredients.indexOf(ingredient);
            int nouvelleQuantite = quantitesIngredients.get(indexIngredient) - 1;
            quantitesIngredients.set(indexIngredient, nouvelleQuantite);
        }
        return true;
    }

    public void afficherStock() {
        System.out.println("----- État du stock -----");
        for (int i = 0; i < nomsIngredients.size(); i++) {
            System.out.println(nomsIngredients.get(i) + " : " + quantitesIngredients.get(i) + " unités");
        }
    }


    public List<String> getNomsIngredients() {
        return nomsIngredients;
    }
    
    public List<Integer> getQuantitesIngredients() {
        return quantitesIngredients;
    }
    
    public int indexOf(String nomIngredient) {
        return nomsIngredients.indexOf(nomIngredient);
    }
}