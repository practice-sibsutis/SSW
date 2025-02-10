package com.sibsutis.study;

class MyThread implements Runnable
{
    Thread thread;
    MyThread() {
        thread = new Thread(this, "�������������� �����");
        System.out.println("������ �������������� ����� " +
                thread);
        thread.start();
    }
    @Override
    public void run() {
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println(
                        "\t�������������� �����: " + i);
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            System.out.println(
                    "\t�������������� ����� �������");
        }
        System.out.println(
                "\t�������������� ����� ��������");
    }
}
public class RunnableExample
{
    public static void main(String[] args)
    {
        new MyThread();
        try {
            for (int i = 5; i > 0; i--) {
                System.out.println("������� �����: " + i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("������� ����� �������");
        }
        System.out.println("������� ����� ��������");
    }
}
