import java.util.*;

public class BlackJack {
    static final String help = "\nRegole base del BlackJack  : "
                                + "\nI giocatori giocano individualmente contro il banco, e lo scopo del gioco e' superare il punteggio del banco senza superare il valore di 21."
                                + "\nI valori delle carte sono per l'asso 11 o 1, per le figure 10, per le altre figure il loro numero"
                                + "\nRegole di gioco" 
                                + "\nCome prima cosa i giocatori effettuano la propria puntata, poi il banco assegna ad ogni giocatore due carte e a se' stesso una sola."
                                + "\nSe un giocatore ottiene come prime due carte un asso e una figura (o un 10) ed effettua 21 ha diritto all'immediato pagamento della sua puntata a 1.5"
                                + "\nPoi il banco chiede ad ogni giocatore come vuole giocare, il giocatore puo' chiedere una carta (hit), raddoppiare la puntata impegnandosi a chiedere"
                                + "\nuna sola carta (double down) oppure "
                                + "\n";
    
    public static void main(String args[]) {
        Scanner key = new Scanner(System.in);
        int n = -1, k = -1;
        



        System.out.println("Benvenuti signori: ");

        System.out.println("\nVolete conoscere le regole  ?      \n\t(inserire 1 per spiegazioni, qualunque altro tasto altrimenti)    :");
        if(key.hasNextInt()){
            if(key.nextInt() == 1)
                System.out.println(help);
        }else
            key.next();

        do {
            System.out.println("In quanti volete giocare  ?");
            
            if(key.hasNextInt())
                n = key.nextInt();
            else
                key.next();
            
            if(n <= 0)
                System.out.print("\n\tErrore nell'input, si e' pregati di riprovare\n\n");

            } while (n <= 0);

        do {
            System.out.print("Con quanti mazzi di carte volete giocare ? (le regole standard prevedono 2 mazzi)\n\t:");
            if(key.hasNextInt())
                k = key.nextInt();
            else
                key.next();
            
            if(k <= 0)
                System.out.print("\n\tErrore nell'input, si e' pregati di riprovare\n\n");
            
        } while (k <= 0);

        Partita game = new Partita(n, k);

        while (!game.isEnd())
            game.go();

        System.out.println("\n\nSignori per riscuotere le vostre fish dirigetevi alla cassa");
        key.close();
        return;
    }

    
}