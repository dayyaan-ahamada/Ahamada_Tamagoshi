package doc_private.tamagoshi.jeu;

import doc_private.tamagoshi.tamagoshis.Tamagoshi;
import doc_private.tamagoshi.tamagoshis.GrosJoueur;
import doc_private.tamagoshi.tamagoshis.GrosMangeur;
import doc_private.tamagoshi.graphic.TamaJPanel;
import doc_private.tamagoshi.util.Utilisateur;

import javax.swing.*;
import java.awt.geom.Dimension2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.*;

public class Game extends JFrame {
    private final List<Tamagoshi> initialTamagoshis;
    private final List<Tamagoshi> onRunTamagoshis;
    private final List<String> tamagoshiPossibleName;
    private final JTextArea mainPanel;
    private int cycle;

    public Game() {
        initialTamagoshis = new ArrayList<>();
        onRunTamagoshis = new ArrayList<>();
        tamagoshiPossibleName = new ArrayList<>();
        tamagoshiPossibleName.add("Paul");
        tamagoshiPossibleName.add("Bob");
        tamagoshiPossibleName.add("Joseph");
        tamagoshiPossibleName.add("Clara");
        tamagoshiPossibleName.add("Lili");
        tamagoshiPossibleName.add("Nome");
        tamagoshiPossibleName.add("Baptiste");
        tamagoshiPossibleName.add("Julius");
        tamagoshiPossibleName.add("Ciela");
        tamagoshiPossibleName.add("Horion");
        mainPanel = new JTextArea();
        cycle = 1;
        initialisation();
    }

    private void initialisation() {
        affectRandomClassToTamagoshis(getNbTamagoshis());
        onRunTamagoshis.addAll(initialTamagoshis);
        setSize(300, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new JScrollPane(mainPanel));
    }

    private int getNbTamagoshis() {
        int nbTamagoshis = 0;
        while (nbTamagoshis < 3 || nbTamagoshis > 8) try {
            nbTamagoshis = Integer.parseInt(JOptionPane.showInputDialog(
                    "Entrez le nombre de tamagoshis:", "5"));
        } catch (NumberFormatException var11) {
            JOptionPane.showMessageDialog(null,
                    "Entrez un nombre compris entre 3 et 8", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
        return nbTamagoshis;
    }

    private void affectRandomClassToTamagoshis(int nbTamagoshis) {
        String nameTamagoshi;
        for (int i = 0; i < nbTamagoshis; i++) {
            nameTamagoshi = tamagoshiPossibleName.get(Utilisateur.randomizer(0, tamagoshiPossibleName.size() - 1));
            if (Utilisateur.randomizer(0, 1) == 0)
                initialTamagoshis.add(GrosMangeur.create(nameTamagoshi));
            else
                initialTamagoshis.add(GrosJoueur.create(nameTamagoshi));
        }
    }

    public void newGame() {
        print("------------Cycle n°0-------------");
        setVisible(true);
        createTamaPanels();
    }

    private void createTamaPanels() {
        Point point = new Point();
        TamaJPanel tamaJPanel;
        Dimension2D screenDimension = new Dimension(
                Toolkit.getDefaultToolkit().getScreenSize().width,
                Toolkit.getDefaultToolkit().getScreenSize().height);
        for (Tamagoshi tamagoshi : onRunTamagoshis) {
            tamaJPanel = new TamaJPanel(tamagoshi, point);
            tamaJPanel.setGame(this);
            tamagoshi.setTamaJPanel(tamaJPanel);
            if (xIsNotOverflowing(point, screenDimension, tamaJPanel))
                point.x += tamaJPanel.getSize().width + 20;
            else {
                point.y += screenDimension.getHeight() - tamaJPanel.getSize().height - 50;
                point.x = 0;
            }
            tamagoshi.parle();
            print(tamaJPanel.getEtatTamagoshi());
        }
    }

    private boolean xIsNotOverflowing(Point point, Dimension2D screenDimension, TamaJPanel tamaJPanel) {
        return point.x <= screenDimension.getWidth() - tamaJPanel.getWidth() * 2;
    }

    public void newCycle() {
        Tamagoshi tamagoshi;
        TamaJPanel tamaJPanel;
        print("------------------Cycle n°" + cycle + "------------------");
        Iterator<Tamagoshi> tamagoshiIterator = onRunTamagoshis.iterator();
        while (tamagoshiIterator.hasNext()) {
            tamagoshi = tamagoshiIterator.next();
            tamaJPanel = tamagoshi.getTamaJPanel();

            if (isTamagoshiAlive(tamagoshi)) {
                tamagoshi.parle();
                print(tamaJPanel.getEtatTamagoshi());
                enableEatAndPlayButtons(tamaJPanel);
            } else {
                print(tamaJPanel.getEtatTamagoshi());
                tamagoshiIterator.remove();
                disableAllBtns(tamaJPanel);
            }
        }
        cycle++;
        if (onRunTamagoshis.isEmpty())
            result();
    }

    private void enableEatAndPlayButtons(TamaJPanel tamaJPanel) {
        tamaJPanel.setEnabledBtnSuivant(false);
        tamaJPanel.setEnabledBtnManger(true);
        tamaJPanel.setEnabledBtnJouer(true);
    }

    private void disableAllBtns(TamaJPanel tamaJPanel) {
        tamaJPanel.setEnabledBtnJouer(false);
        tamaJPanel.setEnabledBtnManger(false);
        tamaJPanel.setEnabledBtnSuivant(false);
    }

    private boolean isTamagoshiAlive(Tamagoshi tamagoshi) {
        return tamagoshi.consommeEnergie() && tamagoshi.consommeFun() && !tamagoshi.incrementAge();
    }

    public void eatTamagoshi(Tamagoshi tamagoshi) {
        tamagoshi.mange();
        print(tamagoshi.getTamaJPanel().getEtatTamagoshi());
        for (Tamagoshi t : onRunTamagoshis)
            t.getTamaJPanel().setEnabledBtnManger(false);
    }

    public void playTamagoshi(Tamagoshi tamagoshi) {
        tamagoshi.joue();
        print(tamagoshi.getTamaJPanel().getEtatTamagoshi());
        for (Tamagoshi t : onRunTamagoshis)
            t.getTamaJPanel().setEnabledBtnJouer(false);
    }

    public void enableNextBtn(boolean alreadyEat, boolean alreadyPlay) {
        if (alreadyEat && alreadyPlay)
            for (Tamagoshi t : onRunTamagoshis)
                t.getTamaJPanel().setEnabledBtnSuivant(true);
    }

    private int score() {
        int score = 0;
        for (Tamagoshi t : initialTamagoshis)
            score += t.getAge();
        return score * 100 / (Tamagoshi.getLifeTime() * initialTamagoshis.size());
    }

    private void result() {
        print("\n-------------Fin------------");
        for (Tamagoshi t : initialTamagoshis)
            if (t.getAge() == Tamagoshi.getLifeTime())
                print(" Le " + t.getClass().getSimpleName() + " " + t.getName() + " est en vie !");
            else
                print(" Le " + t.getClass().getSimpleName() + " " + t.getName() + " est mort..");
        print("Difficulté : " + initialTamagoshis.size() + "\nscore : " + score() + "%");
    }

    private void print(String informations) {
        mainPanel.append(informations + "\n");
        mainPanel.setCaretPosition(mainPanel.getDocument().getLength());
    }
}
