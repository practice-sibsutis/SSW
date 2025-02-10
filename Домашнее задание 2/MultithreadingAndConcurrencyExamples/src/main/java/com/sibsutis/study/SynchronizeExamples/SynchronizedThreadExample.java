package com.sibsutis.study.SynchronizeExamples;

class CommonObject
{
    int counter = 0;
}

class CounterThread implements Runnable
{
    CommonObject res;
    CounterThread(CommonObject res)
    {
        this.res = res;
    }
    @Override
    public void run()
    {
//      synchronized(res) {
        res.counter = 1;
        for (int i = 1; i < 5; i++){
            System.out.printf("'%s' - %d\n",
                    Thread.currentThread().getName(),
                    res.counter);
            res.counter++;
            try {
                Thread.sleep(100);
            }
            catch(InterruptedException e){}
        }
//      }
    }
}
public class SynchronizedThreadExample
{
    public static void main(String[] args) {
        CommonObject commonObject= new CommonObject();
        for (int i = 1; i < 6; i++) {
            Thread t;
            t = new Thread(new CounterThread(commonObject));
            t.setName("Поток " + i);
            t.start();
        }
    }
}
