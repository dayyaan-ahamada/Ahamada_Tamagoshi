package doc_private.tamagoshi.graphic;

import doc_private.tamagoshi.tamagoshis.Tamagoshi;
import doc_private.tamagoshi.jeu.Game;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

public class TamaJPanel extends JFrame implements ActionListener {
    private final AbstractButton btnManger;
    private final AbstractButton btnJouer;
    private final AbstractButton btnSuivant;
    private final Tamagoshi tamagoshi;
    private final JLabel etatTamagoshi;
    private Game game;
    static boolean alreadyEat = false;
    static boolean alreadyPlay = false;

    public TamaJPanel(Tamagoshi t, Point point) {
        tamagoshi = t;

        etatTamagoshi = new JLabel("");

        btnManger = new JButton("Manger");
        btnJouer = new JButton("Jouer");
        btnSuivant = new JButton("Suivant");
        btnSuivant.setEnabled(false);

        btnManger.addActionListener(this);
        btnJouer.addActionListener(this);
        btnSuivant.addActionListener(this);

        createWindow(point);
    }

    private void createWindow(Point point) {
        JComponent panInformations = new JPanel(new FlowLayout());
        panInformations.add(etatTamagoshi);

        JComponent panButtons = new JPanel(new FlowLayout());
        panButtons.add(btnManger);
        panButtons.add(btnJouer);
        panButtons.add(btnSuivant);

        GridBagConstraints constraints = createConstraints();
        setLayout(new FlowLayout());
        setTitle(tamagoshi.getName());
        setSize(320, 150);
        setLocation(point);

        add(panInformations, constraints);
        add(panButtons, constraints);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    private GridBagConstraints createConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets.bottom = 10;
        constraints.insets.right = 15;

        constraints.gridy = 0;
        constraints.gridwidth = GridBagConstraints.VERTICAL;
        return constraints;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Manger":
                game.eatTamagoshi(tamagoshi);
                alreadyEat = true;
                game.enableNextBtn(true, alreadyPlay);
                break;
            case "Jouer":
                game.playTamagoshi(tamagoshi);
                alreadyPlay = true;
                game.enableNextBtn(alreadyEat, true);
                break;
            case "Suivant":
                game.newCycle();
                alreadyEat = false;
                alreadyPlay = false;
                break;
        }
    }

    public String getEtatTamagoshi() {
        return etatTamagoshi.getText();
    }

    public void setEtatTamagoshi(String e) {
        etatTamagoshi.setText(e);
    }

    public void setGame(Game g) {
        game = g;
    }

    public void setEnabledBtnJouer(boolean enable) {
        btnJouer.setEnabled(enable);
    }

    public void setEnabledBtnManger(boolean enable) {
        btnManger.setEnabled(enable);
    }

    public void setEnabledBtnSuivant(boolean enable) {
        btnSuivant.setEnabled(enable);
    }
}