import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnexionBD {
    private static final String URL = "jdbc:mysql://localhost:3307/restaurantbella";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private Connection connexion;

    public ConnexionBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur: Driver MySQL non trouvé!");
            e.printStackTrace();
        }
    }

    public boolean connecter() {
        try {
            connexion = DriverManager.getConnection(URL, USER, PASSWORD);
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données: " + e.getMessage());
            return false;
        }
    }

    public void deconnecter() {
        if (connexion != null) {
            try {
                connexion.close();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la déconnexion: " + e.getMessage());
            }
        }
    }

    public void creerTables() {
        try {
            Statement stmt = connexion.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS plats (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nom VARCHAR(100) UNIQUE NOT NULL, " +
                    "prix DOUBLE NOT NULL, " +
                    "type VARCHAR(50) NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS ingredients (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nom VARCHAR(100) UNIQUE NOT NULL, " +
                    "quantite INT NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS recettes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "plat_nom VARCHAR(100) NOT NULL, " +
                    "ingredient_nom VARCHAR(100) NOT NULL, " +
                    "FOREIGN KEY (plat_nom) REFERENCES plats(nom) ON DELETE CASCADE, " +
                    "FOREIGN KEY (ingredient_nom) REFERENCES ingredients(nom) ON DELETE CASCADE)");

            stmt.execute("CREATE TABLE IF NOT EXISTS employes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nom VARCHAR(100) NOT NULL, " +
                    "prenom VARCHAR(100) NOT NULL, " +
                    "poste VARCHAR(50) NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS commandes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "date_commande TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "etat VARCHAR(50) NOT NULL, " +
                    "total DOUBLE NOT NULL)");

            stmt.execute("CREATE TABLE IF NOT EXISTS details_commandes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "commande_id INT NOT NULL, " +
                    "plat_nom VARCHAR(100) NOT NULL, " +
                    "quantite INT NOT NULL, " +
                    "FOREIGN KEY (commande_id) REFERENCES commandes(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (plat_nom) REFERENCES plats(nom) ON DELETE CASCADE)");

            stmt.close();
            System.out.println("Tables créées avec succès!");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création des tables: " + e.getMessage());
        }
    }

    public void sauvegarderPlat(Plat plat) {
        String requete = "INSERT INTO plats (nom, prix, type) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = connexion.prepareStatement(requete);
            pstmt.setString(1, plat.getNom());
            pstmt.setDouble(2, plat.getPrix());
            pstmt.setString(3, plat.getType());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sauvegarde du plat: " + e.getMessage());
        }
    }

    public List<Plat> chargerPlats() {
        List<Plat> plats = new ArrayList<>();
        String requete = "SELECT * FROM plats";
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(requete);

            while (rs.next()) {
                String nom = rs.getString("nom");
                double prix = rs.getDouble("prix");
                String type = rs.getString("type");

                plats.add(new Plat(nom, prix, type));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des plats: " + e.getMessage());
        }
        return plats;
    }

    public void sauvegarderIngredient(String nom, int quantite) {
        String requete = "INSERT INTO ingredients (nom, quantite) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = connexion.prepareStatement(requete);
            pstmt.setString(1, nom);
            pstmt.setInt(2, quantite);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sauvegarde de l'ingrédient: " + e.getMessage());
        }
    }

    public void mettreAJourIngredient(String nom, int quantite) {
        String requete = "UPDATE ingredients SET quantite = ? WHERE nom = ?";
        try {
            PreparedStatement pstmt = connexion.prepareStatement(requete);
            pstmt.setInt(1, quantite);
            pstmt.setString(2, nom);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de l'ingrédient: " + e.getMessage());
        }
    }

    public void chargerIngredients(Stock stock) {
        String requete = "SELECT * FROM ingredients";
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(requete);

            while (rs.next()) {
                String nom = rs.getString("nom");
                int quantite = rs.getInt("quantite");

                stock.ajouterIngredient(nom, quantite);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des ingrédients: " + e.getMessage());
        }
    }

    public void sauvegarderRecette(String nomPlat, String nomIngredient) {
        String requete = "INSERT INTO recettes (plat_nom, ingredient_nom) VALUES (?, ?)";
        try {
            PreparedStatement pstmt = connexion.prepareStatement(requete);
            pstmt.setString(1, nomPlat);
            pstmt.setString(2, nomIngredient);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sauvegarde de la recette: " + e.getMessage());
        }
    }

    public List<String> chargerIngredientsRecette(String nomPlat) {
        List<String> ingredients = new ArrayList<>();
        String requete = "SELECT ingredient_nom FROM recettes WHERE plat_nom = ?";
        try {
            PreparedStatement pstmt = connexion.prepareStatement(requete);
            pstmt.setString(1, nomPlat);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ingredients.add(rs.getString("ingredient_nom"));
            }

            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des ingrédients de la recette: " + e.getMessage());
        }
        return ingredients;
    }

    public void sauvegarderEmploye(Employe employe) {
        String requete = "INSERT INTO employes (nom, prenom, poste) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = connexion.prepareStatement(requete);
            pstmt.setString(1, employe.getNom());
            pstmt.setString(2, employe.getPrenom());
            pstmt.setString(3, employe.getPoste());
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la sauvegarde de l'employé: " + e.getMessage());
        }
    }

    public List<Employe> chargerEmployes(Stock stock) {
        List<Employe> employes = new ArrayList<>();
        String requete = "SELECT * FROM employes";
        try {
            Statement stmt = connexion.createStatement();
            ResultSet rs = stmt.executeQuery(requete);

            while (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String poste = rs.getString("poste");

                if (poste.equals("Serveur")) {
                    employes.add(new Serveur(nom, prenom));
                } else if (poste.equals("Cuisinier")) {
                    employes.add(new Cuisinier(nom, prenom, stock));
                } else if (poste.equals("Gérant")) {
                    employes.add(new Gerant(nom, prenom, stock));
                }
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des employés: " + e.getMessage());
        }
        return employes;
    }

        public void sauvegarderCommande (Commande commande){
            if (connexion == null) {
                System.out.println("Pas de connexion à la base de données.");
                return;
            }

            String requete = "INSERT INTO commandes (id, statut) VALUES (?, ?)";

            try (PreparedStatement pstmt = connexion.prepareStatement(requete)) {
                pstmt.setInt(1, commande.getId());
                pstmt.setString(2, commande.getEtat());

                pstmt.executeUpdate();
                System.out.println("Commande sauvegardée avec succès !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de la sauvegarde de la commande : " + e.getMessage());
            }
        }

    public List<Commande> chargerCommandes() {
        List<Commande> commandes = new ArrayList<>();

        if (connexion == null) {
            System.out.println("Pas de connexion à la base.");
            return commandes;
        }

        String requete = "SELECT * FROM commandes";

        try (Statement stmt = connexion.createStatement(); ResultSet rs = stmt.executeQuery(requete)) {
            while (rs.next()) {
                Commande c = new Commande();
                c.setId(rs.getInt("id"));
                c.setEtat(rs.getString("statut"));
                commandes.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du chargement des commandes : " + e.getMessage());
            e.printStackTrace();
        }

        return commandes;
    }

}