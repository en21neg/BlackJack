public class Carta {
    private int n;
    private int value;
    private int numero;
    private byte seme;

    /**
     * Generatore della carta:
     * 
     * inserito un numero intero tra 0 e 51, genera una carta nel seguente modo: n è
     * il numero identificatore num è il numero della carta (ciò che c'è scritto)
     * value è il valore della carta nel gioco seme è il seme della carta (0 = picche, 
     * 1 = cuori, 2 = fiori, 3 = quadri)
     * 
     */
    Carta(int num) {
        n = num;
        numero = n % 13 + 1;

        if (numero == 1)
            value = 11;
        else if (numero >= 10)
            value = 10;
        else
            value = numero;

        if (n / 13 == 0)
            seme = 0;
        else if (n / 13 == 1)
            seme = 1;
        else if (n / 13 == 2)
            seme = 2;
        else
            seme = 3;
    }

    Carta() {
        n = 0;
        numero = 0;
        value = 0;
        seme = 0;
    }

    /**
     * @return the n
     */
    public int getN() {
        return n;
    }

    /**
     * @return the seme
     */
    public byte getSeme() {
        return seme;
    }

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Metodo toString per l'oggetto carta
     */
    public String toString() {
        String s = "[ ";
        if (numero == 1)
            s = s + "A ";
        else if (numero <= 10)
            s = s + Integer.toString(numero) + " ";
        else if (numero == 11)
            s = s + "J ";
        else if (numero == 12)
            s = s + "Q ";
        else
            s = s + "K ";

        if (seme == 0)
            s = s + "\u2660"; // picche
        else if (seme == 1)
            s = s + "\u2661"; // cuori
        else if (seme == 2)
            s = s + "\u2663"; // fiori
        else if (seme == 3)
            s = s + "\u2662"; // quadri

        s = s + " ] ";
        return s;
    }

}