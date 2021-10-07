package com.basejava.webapp.drafts;

public class TestDeadlock {
    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();

    private static int shared_1 = 0;
    private static int shared_2 = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t_1 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                synchronized (LOCK_1) {
                    synchronized (LOCK_2) {
                        shared_1++;
                        shared_2--;
                    }
                }
            }
        });
        Thread t_2 = new Thread(() -> {
            for (int i = 0; i < 1_000_000; i++) {
                synchronized (LOCK_2) {
                    synchronized (LOCK_1) {
                        shared_2++;
                        shared_1--;
                    }
                }
            }
        });

        t_1.start();
        t_2.start();

        t_1.join();
        t_2.join();

        System.out.println("done; shared_1=" + shared_1 + "; shared_2=" + shared_2);
    }

    /*
    Last message (done blah blah) never appears :)

    "Thread-1" #12 prio=5 os_prio=0 tid=0x000001e9bc400800 nid=0x5914 waiting for monitor entry [0x00000025da8fe000]
       java.lang.Thread.State: BLOCKED (on object monitor)
            at com.basejava.webapp.drafts.TestDeadlock.lambda$main$1(TestDeadlock.java:25)
            - waiting to lock <0x000000076ac159d8> (a java.lang.Object)
            - locked <0x000000076ac159e8> (a java.lang.Object)
            at com.basejava.webapp.drafts.TestDeadlock$$Lambda$2/1747585824.run(Unknown Source)
            at java.lang.Thread.run(Thread.java:748)
       Locked ownable synchronizers:
            - None

    "Thread-0" #11 prio=5 os_prio=0 tid=0x000001e9bc3fd800 nid=0x591c waiting for monitor entry [0x00000025da7fe000]
       java.lang.Thread.State: BLOCKED (on object monitor)
            at com.basejava.webapp.drafts.TestDeadlock.lambda$main$0(TestDeadlock.java:15)
            - waiting to lock <0x000000076ac159e8> (a java.lang.Object)
            - locked <0x000000076ac159d8> (a java.lang.Object)
            at com.basejava.webapp.drafts.TestDeadlock$$Lambda$1/1096979270.run(Unknown Source)
            at java.lang.Thread.run(Thread.java:748)
       Locked ownable synchronizers:
            - None
    */
}
