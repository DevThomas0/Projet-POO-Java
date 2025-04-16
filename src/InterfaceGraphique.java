import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class InterfaceGraphique extends JFrame {
    private JComboBox<String> comboPlats;
    private JTextArea zoneCommande;
    private JButton boutonAjouter;
    private JButton boutonValider;

    private Commande commande;
    private Menu menu;
    private Stock stock;
    private ConnexionBD bd;

    public InterfaceGraphique(Menu menu, Stock stock, ConnexionBD bd) {
        this.menu = menu;
        this.stock = stock;
        this.bd = bd;
        this.commande = new Commande();

        setTitle("Créer une commande");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        comboPlats = new JComboBox<>();
        for (Plat plat : menu.getPlats()) {
            comboPlats.addItem(plat.getNom());
        }

        boutonAjouter = new JButton("Ajouter Plat");
        boutonValider = new JButton("Valider Commande");
        zoneCommande = new JTextArea();
        zoneCommande.setEditable(false);

        JPanel panelHaut = new JPanel();
        panelHaut.add(comboPlats);
        panelHaut.add(boutonAjouter);
        panelHaut.add(boutonValider);

        add(panelHaut, BorderLayout.NORTH);
        add(new JScrollPane(zoneCommande), BorderLayout.CENTER);

        boutonAjouter.addActionListener(e -> ajouterPlat());
        boutonValider.addActionListener(e -> validerCommande());

        setVisible(true);
    }

    private void ajouterPlat() {
        String nomPlat = (String) comboPlats.getSelectedItem();
        Plat plat = menu.rechercherPlat(nomPlat);
        if (stock.verifierDisponibilite(plat)) {
            commande.ajoutePlat(plat);
            stock.miseAJourStock(plat);
            zoneCommande.append(plat.getNom() + " ajouté.\n");
        } else {
            JOptionPane.showMessageDialog(this, "Ingrédients insuffisants pour ce plat.");
        }
    }

    private void validerCommande() {
        commande.setId((int)(Math.random() * 10000));
        commande.setEtat("En attente");

        if (bd.connecter()) {
            bd.sauvegarderCommande(commande);
            bd.deconnecter();
            JOptionPane.showMessageDialog(this, "Commande enregistrée !");
            zoneCommande.setText("");
            commande = new Commande();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base.");
        }
    }
}
