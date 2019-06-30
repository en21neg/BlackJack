import java.util.Random;

public class Mazzo {
    private int numero;
    private Carta[] cards;
    private static final int NCARTE = 52;
    private int pointer = 0;
    private int countHL = 0;
    private int countO2 = 0;
    private int countR7 = 0;
    private int countZen = 0;

    Mazzo() {
        this(2);
    }

    Mazzo(int n) {
        numero = n;
        cards = new Carta[numero * NCARTE];
        for (int i = 0; i < numero * NCARTE; i++) {
            cards[i] = new Carta(i % NCARTE);
        }
        pointer = 0;
    }

    public void mix() {
        Carta s = new Carta();
        int a;
        Random rand = new Random();

        for (int i = 0; i < numero * NCARTE; i++) {
            a = rand.nextInt(numero * NCARTE);

            s = cards[a];
            cards[a] = cards[i];
            cards[i] = s;
        }

        for (int i = numero * NCARTE - 1; i >= 0 ; i--) {
            a = rand.nextInt(numero * NCARTE);

            s = cards[a];
            cards[a] = cards[i];
            cards[i] = s;
        }

        pointer = 0;
        countHL = 0;
        countO2 = 0;
        countR7 = 0;
        countZen = 0;
    }

    /**
     * Questo metodo corrisponde all'estrazione di una carta dal mazzo del banco
     * 
     * @return the card[i]
     */
    public Carta getCard() {
        Carta c = cards[pointer];

        countZen(c);
        countR7(c);
        countO2(c);
        countHL(c);

        pointer++;

        return c;
    }

    /**
     * @return the ncarte
     */
    public int getNcarte() {
        return NCARTE * numero;
    }

    /**
     * @return the numero (numero di mazzi)
     */
    public int getNumero() {
        return numero;
    }

    /**
     * 
     * @return pointer
     */
    public int getPointer() {
        return this.pointer;
    }

    /**
     * Metodo di progressione del conteggio delle carte secondo il metodo Hi-Lo
     * 
     * @param c Carta da valutare
     */
    private void countHL(Carta c) {
        if (c.getNumero() <= 6 && c.getNumero() >= 2)
            countHL++;
        else if (c.getValue() >= 10)
            countHL--;
    }

    /**
     * Metodo di progressione del conteggio delle carte secondo il metodo Omega 2
     * 
     * @param c Carta da valutare
     */
    private void countO2(Carta c) {
        if (c.getNumero() == 2 || c.getNumero() == 3 || c.getNumero() == 7)
            countO2++;
        else if (c.getNumero() == 4 || c.getNumero() == 5 || c.getNumero() == 6)
            countO2 = countO2 + 2;
        else if (c.getNumero() == 9)
            countO2--;
        else if (c.getValue() == 10)
            countO2 = countO2 - 2;
    }

    /**
     * Metodo di progressione del conteggio delle carte secondo il metodo Red - 7
     * 
     * @param c Carta da valutare
     */
    private void countR7(Carta c) {
        if ((c.getNumero() >= 2 && c.getNumero() <= 6)
                || (c.getNumero() == 7 && (c.getSeme() == 1 || c.getSeme() == 3)))
            countR7++;
        else if (c.getValue() >= 10)
            countR7--;
    }

    /**
     * Metodo di progressione del conteggio delle carte secondo il metodo Zen
     * 
     * @param c Carta da valutare
     */
    private void countZen(Carta c) {
        if (c.getNumero() == 2 || c.getNumero() == 3 || c.getNumero() == 7)
            countZen++;
        else if (c.getNumero() == 4 || c.getNumero() == 5 || c.getNumero() == 6)
            countZen = countZen + 2;
        else if (c.getNumero() == 1)
            countZen--;
        else if (c.getValue() == 10)
            countZen = countZen - 2;
    }    

    /**
     * Metodo per restituire il conteggio del mazzo.
     * Se in input riceve 1 2 3 4 restituisce il conteggio corrispondente, zero
     * altrimenti.
     * 1 = Hi-Lo, 2 = Omega 2, 3 = Red-7, 4 = Zen
     * 
     * @param i tipologia di conteggio utilizzata
     * @return conteggio corrispondente
     */
    public int getConteggio(byte i) {
        if (i == 1)
            return countHL;
        if (i == 2)
            return countO2;
        if (i == 3)
            return countR7;
        if (i == 4)
            return countZen;
        else
            return 0;
    }
}