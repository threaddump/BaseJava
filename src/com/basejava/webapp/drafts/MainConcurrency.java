package com.basejava.webapp.drafts;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static int counter;

    public static void main(String[] args) throws InterruptedException {
        /*
        System.out.println(Thread.currentThread().getName());
        new Thread() {
            @Override
            public void run() {
                System.out.println(getName());
            }
        }.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();

        Thread t = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        t.start();
        System.out.println(t.getState());
        */

        /*
        // single thread
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 100; j++) {
                counter++;
            }
        }
        System.out.println(counter);
         */

        /*
        // multiple threads, synchronized method, not waiting for all threads to terminate
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
            }).start();
        }
        System.out.println(counter);
        */

        /*
        // multiple threads, synchronized method, sleeping (BAD)
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc();
                }
            }).start();
        }
        Thread.sleep(500);
        System.out.println(counter);
         */

        /*
        // multiple threads, synchronized method, sleeping (BAD)
        Object lock = new Object(); // private static final is better
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    inc(lock);
                }
            }).start();
        }
        Thread.sleep(500); // Thread.join() is a better approach
        System.out.println(counter);
         */

        MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(10000);
        for (int i = 0; i < 10000; i++) {
            Thread t = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
            });
            t.start();
            threads.add(t);
        }

        // CountDownLatch is another possible solution instead of join()
        threads.forEach(t -> {
            try {
                t.join(); // if we join thread that has already finished - we just proceed to the next thread
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);
    }

    /*
    // almost equivalent to synchronized(MainConcurrency.class);
    // for non-static method its synchronized(this)
    private static synchronized void inc() {
        counter++;
    }
     */

    /*
    private static  void inc() {
        double a = Math.sin(13.);
        Object lock = new Object(); // WRONG
        synchronized (lock) {
            counter++;
        }
    }
    */

    /*
    private static  void inc(Object lock) {
        double a = Math.sin(13.);
        synchronized (lock) {
            counter++;
        }
    }
     */

    /*
    private void inc() {
        double a = Math.sin(13.);
        try {
            wait();// IllegalMonitorStateException. can't call outside synchronized block
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            counter++;
        }
    }
    */

    /*
    private void inc() {
        double a = Math.sin(13.);
        try {
            synchronized (this) {
                counter++;
                wait();
                // ... something that we wait for, e.g. readiness of some resource
                // wait transfers control to other threads
                // all other threads will wait outside synchronized block if we don't release the lock
                // wait() releases the lock so that other threads may enter synchronized block

                // notify()/notifyAll() wake up threads that wait(), they acquire lock and continue execution
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
     */

    /*
    private void inc() {
        synchronized (this) {
            counter++;
        }
    }
     */

    private synchronized void inc() {
        counter++;
    }
}
