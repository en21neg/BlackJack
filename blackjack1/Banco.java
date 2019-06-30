public class Banco {
    private Carta[] cards = new Carta[10];
    private int numero = 0;
    private int value = 0;
    private boolean over = false;

    /**
     * Costruttore per l'oggetto banco, a cui viene assegnata la carta s iniziale
     * 
     * @param s : carta iniziale
     */
    Banco(Carta s) {
        numero = 1;
        cards[0] = s;
        value = cards[0].getValue();
        over = false;
    }

    /**
     * Metodo per l'aggiunta di una carta al banco
     * 
     * @param s : carta da aggiungere
     */
    public void addCard(Carta s) {
        cards[numero] = s;
        numero++;
        value = value + s.getValue();
        if (value > 21)
            over = true;

        return;
    }

    /**
     * Metodo get per il valore del banco
     * 
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Metodo toString per l'oggetto banco
     */
    public String toString() {
        String s = "Banco : \n\t";
        for (int i = 0; i < numero; i++)
            s = s + cards[i].toString();
        s = s + "\n";
        return s;
    }

    /**
     * Metodo di valutazione se il banco ha sballato o meno
     * 
     * true = sballato, false = in gioco
     * 
     * @return over 
     */
    public boolean isOver() {
        return over;
    }
}