package doc_private.tamagoshi.tamagoshis;

import doc_private.tamagoshi.util.Utilisateur;
import doc_private.tamagoshi.graphic.TamaJPanel;

public abstract class Tamagoshi {
    private final String name;
    private int age;
    private final int maxEnergy;
    protected int energy;
    private final int maxFun;
    protected int fun;
    private static final int lifeTime = 10;
    private TamaJPanel tamaJPanel;

    public Tamagoshi(String n) {
        name = n;
        age = 0;
        maxEnergy = Utilisateur.randomizer(5, 9);
        energy = Utilisateur.randomizer(3, 7);
        maxFun = Utilisateur.randomizer(5, 9);
        fun = Utilisateur.randomizer(3, 7);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static int getLifeTime() {
        return lifeTime;
    }

    @Override
    public String toString() {
        return name + " a " + age + " ans\nIl a " + energy + " d'énergies actuellement\n" +
                "Son enérgie maximum est " + maxEnergy;
    }

    public void parle() {
        String str = "";
        if (energy <= 4 && fun <= 4)
            str += "J'ai faim et en plus je m'ennuie!";
        else if (energy <= 4)
            str += "Qu'est-ce que j'ai faim ! ";
        else if (fun <= 4)
            str += "Qu'est-ce que je m'ennuie !";

        if (str.isEmpty())
            print("Ça roule !");
        else
            print(str);
    }

    public boolean incrementAge() {
        age++;
        return age == lifeTime;
    }

    public void mange() {
        if (energy < maxEnergy) {
            energy += Utilisateur.randomizer(1, 3);
            print("Grrrr !");
        } else
            print("Pas trop la dalle là");
    }

    public void joue() {
        if (fun < maxFun) {
            fun += Utilisateur.randomizer(1, 3);
            print("Drôle ça");
        } else
            print("Là en en fait je suis occupé tu vois");
    }

    public boolean consommeEnergie() {
        energy--;
        if (isDead()) {
            print("Je m'éteins...argh");
            return false;
        } else
            return true;
    }

    public boolean consommeFun() {
        fun--;
        if (isDead()) {
            print("envie de mourir...bye");
            return false;
        } else
            return true;
    }

    private void print(String str) {
        tamaJPanel.setEtatTamagoshi(name + " : " + str);
    }

    public boolean isDead() {
        return energy <= 0;
    }

    public void setTamaJPanel(TamaJPanel tp) {
        tamaJPanel = tp;
    }

    public TamaJPanel getTamaJPanel() {
        return tamaJPanel;
    }

}
