import java.util.*;

public class Partita {
    private Mazzo mazzo;
    private int Ngiocatori;
    private Giocatore[] giocatori;

    Scanner key = new Scanner(System.in);

    protected final static String helperCount = "\n1. Hi-Lo :"
            + "\n\tSistema di conteggio base per le carte, per principianti"
            + "\n\t\t2-5 -> +1\t6-9 -> 0\t10,J-K, A -> -1" + "\n2. Omega 2 :"
            + "\n\tSistema molto complesso e preciso per contare le carte, riservato ai piu' esperti"
            + "\n\t\t2,3,7 -> +1\t4,5,6 -> +2\t9 -> -1\tJ-K,10 -> -2\t8,A -> 0" + "\n3. Red 7 :"
            + "\n\tSistema relativamente semplice, basato anche sul colore delle carte. Non e' bilanciato, ovvero il mazzo termina con conteggio +2"
            + "\n\t\t2-6, red 7 -> +1\t10,J-K,A -> -1\t8,9, black 7 -> 0" + "\n4. Zen Count :"
            + "\n\tUno dei piu' forti sistemi di conteggio in assoluto, con le carte divise in 5 livelli, e' forse il metodo piu' preciso ed efficace per contare le carte"
            + "\n\t\t2,3,7 -> +1\t4,5,6 -> +2\tA -> -1\t10,J-K -> -2\t8,9 -> 0\n\n";

    
    /**
     * Costruttore per partita che genera i giocatori, in numero inserito in input
     * 
     * @param n numero di giocatori
     * @param k numero di mazzi da utilizzare
     */
    Partita(int n, int k) {
        Ngiocatori = n;
        giocatori = new Giocatore[Ngiocatori];

        mazzo = new Mazzo(k);
        mazzo.mix();

        for (int i = 0; i < n; i++) {
            System.out.print("\nGiocatore numero" + (i + 1) + " : \n");
            giocatori[i] = new Giocatore(askName(), askCount(), askBudget());
        }
    }

    /**
     * Funzione che simula il gioco di una mano di BlackJack, Dando le carte ai
     * giocatori e poi al banco e facendo poi giocare i giocatori
     */
    public void go() {
        if (mazzo.getNcarte() / 2 < mazzo.getPointer())
            mazzo.mix();

        long[] a = this.punta();

        int i = 0;
        while (i < Ngiocatori) {
            if (giocatori[i].getGioco()) {
                giocatori[i].setMani(new Mano(), 0);
                giocatori[i].setN_mani(1);
                hit(i, 0);
                hit(i, 0);
            }
            i++;
        }

        this.punta(a);

        System.out.println(this.toString());

        Banco banco = new Banco(mazzo.getCard());
        System.out.println(banco.toString());

        System.out.println("-------------------------------------------------------------------------------");
        evaluateBJ();
        System.out.println("-------------------------------------------------------------------------------");


        i = 0;
        while (i < Ngiocatori) {
            if (giocatori[i].getGioco() && giocatori[i].getMani(0).getValue() >= 0) {
                play(i);
                System.out.println("-------------------------------------------------------------------------------");
            }
            i++;
        }
        System.out.println("-------------------------------------------------------------------------------");

        while (banco.getValue() < 17)
            banco.addCard(mazzo.getCard());

        System.out.println(banco.toString());
   
        // gestire vittoria
        if (banco.isOver()) {
                 vincita(0);
        } else      {   
            vincita(banco.getValue());
        }     
        System.out.println("\n-------------------------------------------------------------------------------\n");

        for      (int j = 0; j < Ngiocatori;   j++ ) {
              if (giocatori[j].getGioco()){
                System.out.println(giocatori[j].getNome() + " : " + giocatori[j].getPortafoglio());
             } 
        }   

        for (int j = 0; j < Ngiocatori; j++){
            if (giocatori[j].getGioco() && giocatori[j].getPortafoglio() <= 0)
                giocatori[j].setGioco(false);
        }
        
        System.out.println("\n-------------------------------------------------------------------------------\n");


        for(int j = 0; j < Ngiocatori; j++)
        {
            if (giocatori[j].getGioco()) {
                System.out.print(giocatori[j].getNome());
                int w = -1;
                do {
                    System.out.println("\tVuoi giocare ancora   ? \t(1. Si    0. No)");
                    if(key.hasNextInt()) 
                        w = key.nextInt();
                    else
                        System.out.println("\n\tErrore in input, inserire solo 0 e 1\n");
                    if (w == 0)
                        giocatori[j].setGioco(false);
                } while (w != 0 && w != 1);
            }
        }

        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------------------------");

    }

    /**
     * @return the mazzo
     */
    public Mazzo getMazzo() {
        return mazzo;
    }

    /**
     * Metodo per giocare le proprie mani del giocatore i
     * 
     * @param i numero del giocatore
     */
        private void play(int i) {
    
        int a = 2;

            if (giocatori[i].getMani(0).getCards(0).getValue() == giocatori[i].getMani(0).getCards(1).getValue()
                                && giocatori[i].getMani(0).getPuntata() <= giocatori[i].getPortafoglio()) {
              System.out.println(giocatori[i].toString());
            do {
                System.out.println("Vuoi dividere la tua mano?");
                System.out.println("1.Si    2.No    3. help");
                        a = key.nextInt();
                if (a == 3)
                    System.out.println(" \nSplit:"
                            + "\n\tQuando un giocatore riceve due carte dello stesso valore puo' effettuare uno split, ovvero:"
                            + "\n\t\t1. Dividere le due carte e creare cosi' due mani"
                            + "\n\t\t2. Aggiungere una carta su entrambe"
                            + "\n\t\t3. Puntare su entrambe la stessa puntata presente in origine"
                            + "\n\t\t4. Proseguire la partita come se si avessero due mani indipendenti\n\n");
                System.out.println("-------------------------------------------------------------------------------");

            } while (a < 1 || a > 2);
        }

        if (a == 1) {
            split(i);
            int j = 0;
            while (j < 2) {
                System.out.println("MANO " + (j + 1) + "\n\n");
                do {
                    a = action(i, j);
                    if (a == 1) {
                        hit(i, j);
                        System.out.print(giocatori[i].getMani(j).toString());
                        System.out.println("\tValore mano :" + giocatori[i].getMani(j).getValue());
                    } else if (a == 2) {
                        bis(i, j);
                        System.out.print(giocatori[i].getMani(j).toString());
                        System.out.println("\tValore mano :" + giocatori[i].getMani(j).getValue());
                    } else if (a == 3) {
                        giocatori[i].getMani(j).setModificable(false);
                        System.out.print(giocatori[i].getMani(j).toString());
                        System.out.println("\tValore mano :" + giocatori[i].getMani(j).getValue());
                    }
                    System.out.println("\n-------------------------------------------------------------------------------\n");

                } while (giocatori[i].getMani(j).isModificable());
                System.out.println("\nTurno finito\n");
                System.out.println("\n-------------------------------------------------------------------------------\n");

                j++;
            }

        } else {
            do{    
                a = action(i);

                if (a == 1) {
                    hit(i, 0);
                    System.out.print(giocatori[i].getMani(0).toString());
                    System.out.print("\tValore mano :" + giocatori[i].getMani(0).getValue());
                } else if (a == 2) {
                    bis(i, 0);
                    System.out.print(giocatori[i].getMani(0).toString());
                    System.out.println("\tValore mano :" + giocatori[i].getMani(0).getValue());
                } else if (a == 3) {
                    giocatori[i].getMani(0).setModificable(false);
                    System.out.print(giocatori[i].getMani(0).toString());
                    System.out.println("\tValore mano :" + giocatori[i].getMani(0).getValue());
                }
                System.out.println("-------------------------------------------------------------------------------");

            } while (giocatori[i].getMani(0).isModificable());

            System.out.println("Turno finito");
            System.out.println("-------------------------------------------------------------------------------");

        }

        return;
    }

    /**
     * Metodo per il raddoppio della puntata su una mano
     * 
     * @param i numero del giocatore
     * @param k numero della mano
     */
    private void bis(int i, int k) {
        long puntata = giocatori[i].getMani(k).getPuntata();
        giocatori[i].getMani(k).setPuntata(puntata * 2);
        giocatori[i].setPortafoglio(giocatori[i].getPortafoglio() - puntata);
        hit(i, k);
        giocatori[i].getMani(k).setModificable(false);

        return;
    }

    /**
     * Metodo per far scegliere al giocatore i la mossa da compiere
     * 
     * @param i numero del giocatore
     * @return int : codice per mossa
     */
    private int action(int i) {
        int a;
        if (giocatori[i].getMani(0).getPuntata() <= giocatori[i].getPortafoglio()) {
            do {
                System.out.print(giocatori[i].toString());
                System.out.println("\tValore mano :" + giocatori[i].getMani(0).getValue());
                System.out.println("\n\nCosa vuoi fare?");
                System.out.println("1.Hit       2.Double      3.Stand        4. help");

                System.out.println(
                        "\n\t(il conteggio delle carte e'" + mazzo.getConteggio(giocatori[i].getConteggio()) + ")\n");

                a = key.nextInt();

                if (a == 4)
                    System.out.println("\nHit:" + "\n\tChiama una carta sulla tua mano" + "\nDouble:"
                            + "\n\tRaddoppi la puntata sulla tua mano e chiedi una sola carta" + "\nStand:"
                            + "\n\tNon chiami piu' carte\n\n");
            } while (a <= 0 || a > 3);
        } else {
            do {
                System.out.print(giocatori[i].toString());
                System.out.println("\tValore mano :" + giocatori[i].getMani(0).getValue());
                System.out.println("\n\nCosa vuoi fare?");
                System.out.println("1.Hit       2.Stand        3. help");

                System.out.println(
                        "\n\t(il conteggio delle carte e'" + mazzo.getConteggio(giocatori[i].getConteggio()) + ")\n");

                a = key.nextInt();

                if (a == 3)
                    System.out.println("\nHit:" + "\n\tChiama una carta sulla tua mano" + "\nStand:"
                            + "\n\tNon chiami piu' carte\n\n");
            } while (a <= 0 || a > 3);

            if (a == 2)
                a++;
        }

        return a;
    }

    /**
     * Metodo per far scegliere al giocatore i la mossa da effettuare sulla mano j
     * 
     * @param i numero del giocatore
     * @param j numero della sua mano
     * @return int : codice per mossa
     */
    private int action(int i, int j) {
        int a;
        if (giocatori[i].getMani(j).getPuntata() <= giocatori[i].getPortafoglio()) {
            do {
                System.out.println(giocatori[i].getMani(j).toString());
                System.out.println("\nCosa vuoi fare?");
                System.out.println("1.Hit       2.Double      3.Stand        4. help");

                System.out.println(
                        "\n\t(il conteggio delle carte e'" + mazzo.getConteggio(giocatori[i].getConteggio()) + ")\n");

                a = key.nextInt();

                if (a == 4)
                    System.out.println("\nHit:" + "\n\tChiama una carta sulla tua mano" + "\nDouble:"
                            + "\n\tRaddoppi la puntata sulla tua mano e chiedi una sola carta" + "\nStand:"
                            + "\n\tNon chiami piu' carte\n\n");
            } while (a <= 0 || a > 3);
        } else {
            do {
                System.out.println(giocatori[i].getMani(j).toString());
                System.out.println("Valore mano :" + giocatori[i].getMani(j).getValue());
                System.out.println("\n\nCosa vuoi fare?");
                System.out.println("1.Hit       2.Stand        3. help");

                System.out.println(
                        "\n\t(il conteggio delle carte e'" + mazzo.getConteggio(giocatori[i].getConteggio()) + ")\n");

                a = key.nextInt();

                if (a == 3)
                    System.out.println("\nHit:" + "\n\tChiama una carta sulla tua mano" + "\nStand:"
                            + "\n\tNon chiami piu' carte\n\n");
            } while (a <= 0 || a > 3);

            if (a == 2)
                a++;
        }

        return a;
    }

    /**
     * Metodo per chiedere una carta dal mazzo in una determinata mano di un
     * determinato giocatore
     * 
     * @param i numero del giocatore
     * @param j numero della mano
     */
    private void hit(int i, int j) {
        giocatori[i].getMani(j).addCard(mazzo.getCard()); // aggiungi una carta
    }

    /**
     * Metodo per effettuare lo split della mano del giocatore
     * 
     * @param i numero del giocatore
     */
    private void split(int i) {
        giocatori[i].getMani()[1] = new Mano();
        giocatori[i].setN_mani(2);

        giocatori[i].getMani(0).setNumero(1);

        giocatori[i].getMani(1).addCard(giocatori[i].getMani(0).getCards(1));

        giocatori[i].getMani(0).addCard(mazzo.getCard());
        giocatori[i].getMani(1).addCard(mazzo.getCard());

        giocatori[i].getMani(1).setPuntata(giocatori[i].getMani(0).getPuntata());
        giocatori[i].setPortafoglio(giocatori[i].getPortafoglio() - giocatori[i].getMani(0).getPuntata());

        return;
    }

    /**
     * Metodo che analizza la possibilita'/volonta' dei giocaotori di proseguire la
     * partita e restituisce dunque lo stato della partita
     * 
     * @return t : stato della partita, true = finita, false = in corso
     */
    public boolean isEnd() {
        boolean t = true;
        for (int i = 0; i < Ngiocatori; i++) {
            if (giocatori[i].getGioco())
                t = false;
        }
        return  t;
    }
    
    /**
     * Metodo toString per la partita
     */
    public String toString() {
        String    s = "SITUAZIONE GIOCATORI   :  \n";
        for (int i = 0; i < Ngiocatori; i++)
            if (giocatori[i].getGioco())
                s = s + giocatori[i].toString();

        return s;
    }

    /**
     * Compara il valore n con il valore delle mani, se maggiore eroga vincita come
     * puntata * 2 (poiché all'atto della puntata i soldi sono stati sottratti), se minore non fa nulla, 
     * questo per tutti i giocatori
     * 
     * @param n valore da confrontare
     */
    private void vincita(int n) {
        for (int i = 0; i < Ngiocatori; i++) {
            for (int j = 0; j < giocatori[i].getN_mani(); j++) {
                long m = giocatori[i].getMani(j).getPuntata();
                   if (giocatori[i].getMani(j).getValue() > n && giocatori[i].getMani(j).getValue() <= 21)
                    giocatori[i].pay(m * 2);
                   else if (giocatori[i].getMani(j).getValue() == n && giocatori[i].getMani(j).getValue() <= 21)
                    giocatori[i].pay(m);
            } 
        }
        return; 
    }

    /**
     * Metodo di richiesta puntate per i giocatori
     * 
     * @return long[] puntate un vettore che contiene le puntate dei giocatori, dove
     *         gli indici corrispondono a quelli dei giocatori
     */
    protected long[] punta() {
        long[] puntate = new long[Ngiocatori];
        for (int i = 0; i < Ngiocatori; i++) {
            long n;
            if (giocatori[i].getGioco()) {
                do {
                    System.out.println(giocatori[i].getNome() + "  : \n" + "\tQuanto vuoi puntare   : ");
                    System.out.println("\t\t(il tuo budget e' " + giocatori[i].getPortafoglio() + " e il conteggio e' "
                            + this.mazzo.getConteggio(giocatori[i].getConteggio()) + ")");
                    System.out.print("\t  : ");

                    if(key.hasNextLong())
                        n = key.nextLong();
                    else{
                        key.next();
                        System.out.print("\n\tErrore nell'input, si e' pregati di riprovare\n\n");
                        n = -1;
                    }

                } while (n > giocatori[i].getPortafoglio() || n <= 0);
                giocatori[i].setPortafoglio(giocatori[i].getPortafoglio() - n);
                puntate[i] = n;
            }  
        }
        return puntate;
    }

    /**
     * Metodo che effettua l'assegnamento delle puntate registrate nell'array
     * puntate alla variabile d'istanza puntata della prima mano del giocatore
     * 
     * Ad indice corrisponde giocatore
     * 
     * @param long[] puntate : array dove sono registrate le puntate dei singoli giocatori
     */
    protected void punta(long[] puntate) {
        for (int i = 0; i < Ngiocatori; i++)
            giocatori[i].getMani(0).setPuntata(puntate[i]);
        return;
    }

    protected void evaluateBJ(){
        for(int i = 0; i < Ngiocatori; i++){
            if(giocatori[i].getGioco()){
                if(giocatori[i].getMani(0).getValue() == 21){
                    System.out.println("Complimenti " + giocatori[i].getNome() + " hai fatto BlackJack !!");
                    giocatori[i].pay(giocatori[i].getMani(0).getPuntata()*5/2);
                    giocatori[i].getMani(0).setModificable(false);
                    giocatori[i].getMani(0).setValue(-1);
                }
            }
        }
    } 

    /**
     * Metodo di richiesta per il nome del giocatore
     * 
     * @return name : String con nome del giocatore
     */
    protected String askName(){
        Scanner in = new Scanner(System.in);
        String name;
        System.out.print("Qual e\' il suo nome   ?\n\t");
        name = in.next();
        return name;
    }

    /**
     * Metodo di richiesta tipologia conteggio del giocatore
     * 
     * @return byte con tipologia conteggio
     */
    protected byte askCount(){
        byte conteggio = 0;
        do {
            System.out.println("Come preferisci contare le carte?");
            System.out.println("1.Hi-Lo        2.0mega 2        3.Red 7        4.Zen Count       5. help");

            if(key.hasNextByte()){
                conteggio = key.nextByte();
            }else{
                key.next();
            }

            if(conteggio <=0 ||  conteggio > 5)
                System.out.print("\n\tErrore nell'input, si e' pregati di riprovare\n\n");
            if (conteggio == 5)
                System.out.println(helperCount);
        } while (conteggio <= 0 || conteggio > 4);
        return conteggio;
    }

    /**
     * Metodo di richiesta budget del giocatore
     * 
     * @return long con budget utilizzato
     */
    protected long askBudget(){
        long budget = -1;
        byte zz = -1;
        do {
            System.out.print("Accetta di giocare con il budget standard (1000 $) o no?\t(1 = Si\', 0 = No)\n\t");
            if(key.hasNextByte()){
                zz = key.nextByte();
            }else{
                key.next();
            }

            if(zz != 0 && zz != 1)
                System.out.print("\n\tErrore nell'input, inserire solo 1 e 0");

            System.out.println();
        } while (zz != 0 && zz != 1);

        if (zz == 1)
            budget = 1000;
        else {
            do {
                System.out.print("A quanto ammonta la somma che vuole utilizzare stasera ? \t");
                
                if(key.hasNextLong()){
                    budget = key.nextLong();
                }else{
                    key.next();
                }
    
                if(budget <= 0)
                    System.out.print("\n\tErrore nell'input, si e' pregati di riprovare");
            } while (budget <= 0);
        }

        return budget;
    }
}