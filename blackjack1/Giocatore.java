import java.util.*;

public class Giocatore {
    private int n_mani;
    private long portafoglio;
    private Mano[] mani = new Mano[2];
    private String nome;
    private byte conteggio;
    private boolean gioco;

    Scanner key = new Scanner(System.in);

    /**
     * Metodo costruttore dell'oggetto giocatore
     * 
     * @param name il nome del giocatore da inserire
     * @param count il tipo di conteggio utilizzato
     * @param budget quanti soldi ha inizialmente a disposizione il giocatore
     */
    Giocatore(String name, byte count, long budget) {
        this.n_mani = 1;
        this.nome = name;
        this.conteggio = count;
        this.portafoglio = budget;
        this.gioco = true;
        this.mani[0] = new Mano();
        this.mani[1] = new Mano();
    }

    /**
     * @return nome del giocatore
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the portafoglio
     */
    public long getPortafoglio() {
        return portafoglio;
    }

    /**
     * @param portafoglio the portafoglio to set
     */
    public void setPortafoglio(long portafoglio) {
        this.portafoglio = portafoglio;
    }

    /**
     * @return the mani
     */
    public Mano[] getMani() {
        return mani;
    }

    /**
     * @return the mani
     */
    public Mano getMani(int i) {
        return mani[i];
    }

    /**
     * Inserisce la mano m nella posizione i
     * 
     * @param m Mano
     * @param i numero della mano da inserire
     */
    public void setMani(Mano m, int i) {
        mani[i] = m;
    }

    /**
     * Metodo get per il numero delle mani
     * 
     * @return the n_mani
     */
    public int getN_mani() {
        return n_mani;
    }

    /**
     * Metodo toString per l'oggetto giocatore
     */
    public String toString() {
        String s = this.nome + " : \n";
        for (int i = 0; i < n_mani; i++) {
            s = s + "Mano " + Integer.toString(i + 1) + ":\n\t";
            s = s + mani[i].toString() + "\n";
            s = s + "\tPuntata  : " + Long.toString(mani[i].getPuntata()) + "\n";
        }

        return s;
    }

    /**
     * Metodo get per lo stato di gioco
     * 
     * true = gioca ancora false = non gioca piu'
     * 
     * @return the gioco
     */
    public boolean getGioco() {
        return gioco;
    }

    /**
     * Metodo set per lo stato di gioco
     * 
     * @param gioco the gioco to set
     */
    public void setGioco(boolean gioco) {
        this.gioco = gioco;
        return;
    }

    /**
     * @param n_mani the n_mani to set
     */
    public void setN_mani(int n_mani) {
        this.n_mani = n_mani;
    }

    /**
     * Metodo get per il tipo di conteggio utilizzato (per dettagli vedi classe
     * Mazzo)
     * 
     * @return the conteggio
     */
    public byte getConteggio() {
        return conteggio;
    }

    /**
     * Metodo set per il nome del giocatore
     * 
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Metodo di pagamento di una somma n
     * 
     * @param n
     */
    public void pay(long n) {
        this.portafoglio = this.portafoglio + n;
    }
}