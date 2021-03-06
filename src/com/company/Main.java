package com.company;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    static Object lock = new Object();

    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        System.out.println("How many steps i need to make, bro? C:");
        int steps = scan.nextInt();

        Thread left = new Thread(new Runnable() {
            public void run() {
                int k = steps % 2 == 0 ? steps / 2 : steps / 2 + 1;
                for (int i = 0; i < k; i++) {
                    synchronized (lock) {
                        if (i == 0) System.out.print("Pravoi");
                        else System.out.print(" Pravoi");
                        try {
                            lock.notify();
                            if(i == k - 1) break;
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Thread right = new Thread(new Runnable() {
            public void run() {

                for (int i = 0; i < steps/2; i++) {
                    synchronized (lock) {
                        System.out.print(" Levoi");
                        try {
                            lock.notify();
                            if(i == steps/2 - 1) break;
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        try {
            left.start();
            right.start();
            left.join();
            right.join();
            return;
        } catch (Exception e) {

        }
    }
}