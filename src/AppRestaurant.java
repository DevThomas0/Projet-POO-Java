import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppRestaurant {
    private static ConnexionBD bd;
    private static Menu menu;
    private static Stock stock;
    private static List<Employe> employes;
    private static Scanner scanner;

    public static void main(String[] args) {

        scanner = new Scanner(System.in);
        bd = new ConnexionBD();
        stock = new Stock();
        menu = new Menu();
        employes = new ArrayList<>();
        
        if (bd.connecter()) {
            System.out.println("Connexion à la base de données réussie!");
            
            bd.creerTables();
            
            List<Plat> platsCharges = bd.chargerPlats();
            for (int i = 0; i < platsCharges.size(); i++) {
                menu.ajouterP(platsCharges.get(i));
            }
            
            bd.chargerIngredients(stock);
            
            for (int i = 0; i < menu.getPlats().size(); i++) {
                Plat plat = menu.getPlats().get(i);
                List<String> ingredients = bd.chargerIngredientsRecette(plat.getNom());
                stock.definirRecette(plat.getNom(), ingredients);
            }
            
            employes = bd.chargerEmployes(stock);
            
            if (menu.getPlats().isEmpty()) {
                initialiserDonnees();
            }
            
            menuInteractif();
            
            bd.deconnecter();
        } else {
            System.out.println("Impossible de se connecter à la base de données!");
            
            initialiserDonnees();
            menuInteractif();
        }
    }
    
    private static void initialiserDonnees() {

        Plat margherita = new Plat("Margherita", 8.50, "Pizza");
        Plat carbonara = new Plat("Spaghetti Carbonara", 12.00, "Pâtes");
        Plat tiramisu = new Plat("Tiramisu", 6.00, "Dessert");
        Plat coca = new Plat("Coca-Cola", 3.00, "Boisson");
        
        menu.ajouterP(margherita);
        menu.ajouterP(carbonara);
        menu.ajouterP(tiramisu);
        menu.ajouterP(coca);
        
        stock.ajouterIngredient("Farine", 1000);
        stock.ajouterIngredient("Tomate", 500);
        stock.ajouterIngredient("Mozzarella", 300);
        stock.ajouterIngredient("Œufs", 50);
        stock.ajouterIngredient("Pancetta", 200);
        stock.ajouterIngredient("Parmesan", 250);
        stock.ajouterIngredient("Mascarpone", 300);
        stock.ajouterIngredient("Café", 100);
        stock.ajouterIngredient("Cacao", 150);
        stock.ajouterIngredient("Pâtes", 500);
        
        List<String> ingredientsMargherita = new ArrayList<>();
        ingredientsMargherita.add("Farine");
        ingredientsMargherita.add("Tomate");
        ingredientsMargherita.add("Mozzarella");
        
        List<String> ingredientsCarbonara = new ArrayList<>();
        ingredientsCarbonara.add("Pâtes");
        ingredientsCarbonara.add("Oeufs");
        ingredientsCarbonara.add("Pancetta");
        ingredientsCarbonara.add("Parmesan");
        
        List<String> ingredientsTiramisu = new ArrayList<>();
        ingredientsTiramisu.add("Mascarpone");
        ingredientsTiramisu.add("Café");
        ingredientsTiramisu.add("Cacao");
        ingredientsTiramisu.add("Œufs");
        
        stock.definirRecette(margherita.getNom(), ingredientsMargherita);
        stock.definirRecette(carbonara.getNom(), ingredientsCarbonara);
        stock.definirRecette(tiramisu.getNom(), ingredientsTiramisu);
        
        employes.add(new Serveur("Dupont", "Jean"));
        employes.add(new Cuisinier("Martin", "Sophie", stock));
        employes.add(new Gerant("Dubois", "Pierre", stock));
        
        if (bd.connecter()) {

            bd.sauvegarderPlat(margherita);
            bd.sauvegarderPlat(carbonara);
            bd.sauvegarderPlat(tiramisu);
            bd.sauvegarderPlat(coca);
            
            for (int i = 0; i < stock.getNomsIngredients().size(); i++) {
                String nom = stock.getNomsIngredients().get(i);
                int quantite = stock.getQuantitesIngredients().get(i);
                bd.sauvegarderIngredient(nom, quantite);
            }
            
            for (String ingredient : ingredientsMargherita) {
                bd.sauvegarderRecette(margherita.getNom(), ingredient);
            }
            
            for (String ingredient : ingredientsCarbonara) {
                bd.sauvegarderRecette(carbonara.getNom(), ingredient);
            }
            
            for (String ingredient : ingredientsTiramisu) {
                bd.sauvegarderRecette(tiramisu.getNom(), ingredient);
            }
            
            for (int i = 0; i < employes.size(); i++) {
                bd.sauvegarderEmploye(employes.get(i));
            }
        }
        
        System.out.println("Données de test initialisées avec succès!");
    }
    
    private static void menuInteractif() {
        int choix = 0;
        
        do {
            System.out.println("\n----- Application de gestion du restaurant La Bella Tavola -----");
            System.out.println("1. Afficher le menu");
            System.out.println("2. Créer une commande");
            System.out.println("3. Afficher l'état du stock");
            System.out.println("4. Ajouter des ingrédients au stock");
            System.out.println("5. Afficher les employés");
            System.out.println("6. Quitter");
            System.out.print("Votre choix: ");
            
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                continue;
            }
            
            switch (choix) {
                case 1:
                    menu.montreM();
                    break;
                case 2:
                    creerCommande();
                    break;
                case 3:
                    stock.afficherStock();
                    break;
                case 4:
                    ajouterIngredients();
                    break;
                case 5:
                    afficherEmployes();
                    break;
                case 6:
                    System.out.println("Merci d'avoir utilisé l'application!");
                    break;
                default:
                    System.out.println("Choix invalide!");
            }
        } while (choix != 6);
    }
    
    private static void creerCommande() {
        Commande commande = new Commande();
        boolean commander = true;
        
        while (commander) {
            menu.montreM();
            System.out.println("\n0. Terminer la commande");
            System.out.print("Entrez le numéro du plat à ajouter (0 pour terminer): ");
            
            try {
                int choixPlat = Integer.parseInt(scanner.nextLine());
                
                if (choixPlat == 0) {
                    commander = false;
                } else {
                    Plat plat = menu.platParIdx(choixPlat);
                    
                    if (plat != null) {
                        if (stock.verifierDisponibilite(plat)) {
                            commande.ajoutePlat(plat);
                            System.out.println("Plat ajouté à la commande!");
                            
                            stock.miseAJourStock(plat);
                        } else {
                            System.out.println("Désolé, les ingrédients pour ce plat ne sont pas disponibles.");
                        }
                    } else {
                        System.out.println("Numéro de plat invalide!");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
        
        commande.afficherCommande();
    }
    
    private static void ajouterIngredients() {
        System.out.println("\n----- Ajout d'ingrédients au stock -----");
        System.out.print("Nom de l'ingrédient: ");
        String nom = scanner.nextLine();
        
        System.out.print("Quantité à ajouter: ");
        try {
            int quantite = Integer.parseInt(scanner.nextLine());
            if (quantite > 0) {
                stock.ajouterIngredient(nom, quantite);
                System.out.println(quantite + " unités de " + nom + " ajoutées au stock.");
                
                if (bd.connecter()) {
                    bd.mettreAJourIngredient(nom, stock.getQuantitesIngredients().get(stock.getNomsIngredients().indexOf(nom)));
                }
            } else {
                System.out.println("La quantité doit être positive!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer un nombre valide.");
        }
    }
    
    private static void afficherEmployes() {
        System.out.println("\n----- Liste des employés -----");
        for (int i = 0; i < employes.size(); i++) {
            System.out.println((i+1) + ". " + employes.get(i).toString());
        }
    }
}