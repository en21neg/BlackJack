public class Mano {
    private int numero;
    private static final int NCARTEMANO = 10;
    private Carta[] cards = new Carta[NCARTEMANO];
    private long puntata;
    private boolean modificable;
    private int value = 0;

    /**
     * Costruttore Mano, all'inizio la mano risulta vuota
     */
    Mano() {
        numero = 0;
        puntata = 0;
        modificable = true;
    }

    /**
     * Metodo get per valore della mano
     * 
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Metodo valutazione possibilita' di modificare la mano
     * 
     * @return the modificable
     */
    public boolean isModificable() {
        return modificable;
    }

    /**
     * Metodo set per il valore booleano di modificabile o meno
     * 
     * @param modificable the modificable to set
     */
    public void setModificable(boolean modificable) {
        this.modificable = modificable;
    }

    /**
     * Metodo toString
     * 
     * @return stringa con la mano e le relative carte
     */
    public String toString() {
        String s = "";
        for (int i = 0; i < numero; i++)
            s = s + cards[i].toString();
        return s;
    }

    /**
     * Metodo get per puntata
     * 
     * @param puntata
     */
    public void setPuntata(long puntata) {
        this.puntata = puntata;
    }

    /**
     * Metodo set per numero di carte
     * 
     * @param n 
     */
    public void setNumero(int numero) {
        this.numero = numero;
        value = 0;
        for (int i = 0; i < numero; i++) {
            value = value + cards[i].getValue();
        }

        if (value < 21)
            this.modificable = true;
    }

    /**
     * Metodo get per l'array delle carte
     * 
     * @return the cards (come array di carte)
     */
    public Carta[] getCards() {
        return cards;
    }

    /**
     * Metodo get per il numero di carte
     * 
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Metodo get per la puntata della mano
     * 
     * @return the puntata
     */
    public long getPuntata() {
        return puntata;
    }

    /**
     * Metodo get per la carta numero i della mano
     * 
     * @param i
     * @return Carta in posizione i della mano
     */
    public Carta getCards(int i) {
        return cards[i];
    }

    /**
     * Metodo per aggiungere una carta alla mano
     * 
     * @param s : Carta da aggiungere
     */
    public void addCard(Carta s) {
        if (modificable) {
            cards[numero] = s;
            numero++;
            value = value + s.getValue();
            if (value >= 21)
                modificable = false;
        }

        return;
    }

    /**
     * Metodo set per valore della mano
     * 
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }

}