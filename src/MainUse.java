import java.util.Scanner;

public class MainUse {
    public static void main(String[] args) {

        Menu menu = new Menu();
        menu.ajouterP(new Plat("Margherita", 8.50, "Pizza"));
        menu.ajouterP(new Plat("Spaghetti Carbonara", 12.00, "Pâtes"));
        menu.ajouterP(new Plat("Tiramisu", 6.00, "Dessert"));
        menu.ajouterP(new Plat("Coca-Cola", 3.00, "Boisson"));

        Commande commande = new Commande();

        Scanner scanner = new Scanner(System.in);
        int choix = 0;

        do {
            System.out.println("\n----- Application de gestion du restaurant La Bella Tavola -----");
            System.out.println("1. Afficher le menu");
            System.out.println("2. Ajouter un plat à la commande");
            System.out.println("3. Afficher la commande");
            System.out.println("4. Quitter");
            System.out.print("Votre choix: ");

            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entre un nombre valide.");
                continue;
            }

            switch (choix) {
                case 1:
                    menu.montreM();
                    break;
                case 2:
                    menu.montreM();
                    System.out.print("Entre numéro plat: ");
                    int numPlat = Integer.parseInt(scanner.nextLine());
                    Plat platChoisi = menu.platParIdx(numPlat);

                    if (platChoisi != null) {
                        commande.ajoutePlat(platChoisi);
                        System.out.println("Plat ajouté");
                    } else {
                        System.out.println("Numéro invalide");
                    }
                    break;
                case 3:
                    commande.afficherCommande();
                    break;
                case 4:
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Choix invalide");
            }
        } while (choix != 4);

        scanner.close();
    }
}
