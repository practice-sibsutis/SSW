package com.sibsutis.study.SynchronizeExamples;

import java.util.concurrent.Exchanger;

public class ExchangerExample
{
    // Обменник почтовыми письмами
    private static Exchanger<Letter> EXCHANGER;

    static String msg1;
    static String msg2;
    static String msg3;
    static String msg4;
    static String msg5;

    static {
        msg1 = "Почтальон %s получил письма : %s, %s\n";
        msg2 = "Почтальон %s выехал из %s в %s\n";
        msg3 = "Почтальон %s приехал в пункт Д\n";
        msg4 = "Почтальон %s получил письма для %s\n";
        msg5 = "Почтальон %s привез в %s : %s, %s\n";
    }

    public static class Postman implements Runnable
    {
        private String   id;
        private String   departure;
        private String   destination;
        private Letter[] letters;

        public Postman(String id, String departure,
                       String destination,
                       Letter[] letters) {
            this.id          = id;
            this.departure   = departure;
            this.destination = destination;
            this.letters     = letters;
        }

        @Override
        public void run() {
            try {
                System.out.printf(msg1, id, letters[0],
                        letters[1]);
                System.out.printf(msg2, id, departure,
                        destination);
                Thread.sleep((long) Math.random() * 5000
                        + 5000);
                System.out.printf(msg3, id);
                // Самоблокировка потока для
                // обмена письмами
                letters[1] = EXCHANGER.exchange(letters[1]);
                // Обмен письмами
                System.out.printf(msg4, id, destination);
                Thread.sleep(1000 + (long)
                        Math.random() * 5000);
                System.out.printf(msg5, id, destination,
                        letters[0],
                        letters[1]);
            } catch (InterruptedException e) {}
        }
    }
    public static class Letter
    {
        private String address;
        public Letter(final String address) {
            this.address = address;
        }
        public String toString()
        {
            return address;
        }
    }
    public static void main(String[] args)
            throws InterruptedException
    {
        EXCHANGER = new Exchanger<Letter>();
        // Формирование отправлений
        Letter[] posts1 = new Letter[2];
        Letter[] posts2 = new Letter[2];

        posts1[0] = new Letter("п.В - Петров"           );
        posts1[1] = new Letter("п.Г - Киса Воробьянинов");
        posts2[0] = new Letter("п.Г - Остап Бендер"     );
        posts2[1] = new Letter("п.В - Иванов"           );
        // Отправление почтальонов
        new Thread(new Postman("a","А","В",posts1)).start();
        Thread.sleep(100);
        new Thread(new Postman("б","Б","Г",posts2)).start();
    }
}
