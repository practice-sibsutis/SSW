package com.sibsutis.study.SynchronizeExamples;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample
{
    private static CyclicBarrier FerryBarrier;
    private static final int FerryBoat_size = 3;

    // Переправляющий автомобили паром
    public static class FerryBoat implements Runnable
    {
        @Override
        public void run() {
            try {
                // Задержка на переправе
                System.out.println(
                        "\nЗагрузка автомобилей");
                Thread.sleep(500);
                System.out.println(
                        "Паром переправил автомобили\n");
            } catch (InterruptedException e) {}
        }
    }

    // Класс автомобиля
    public static class Car implements Runnable
    {
        private int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            try {
                System.out.printf(
                        "К переправе подъехал автомобиль %d\n",
                        carNumber);
                // Вызов метода await при подходе к
                // барьеру; поток блокируется в ожидании
                // прихода остальных потоков
                FerryBarrier.await();
                System.out.printf(
                        "Автомобиль %d продолжил движение\n",
                        carNumber);
            } catch (Exception e) {}
        }
    }

    public static void main(String[] args)
            throws InterruptedException {
        FerryBarrier = new CyclicBarrier(FerryBoat_size,
                new FerryBoat());
        for (int i = 1; i < 10; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(400);
        }
    }
}
