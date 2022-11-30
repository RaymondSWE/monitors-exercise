package Messages;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.lang.Runnable;
import java.util.concurrent.locks.Lock;

public class messages {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<String>();
        int min;
        int max;
        int mean;
        int a;

        // process 1 will create new events
        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String event = UUID.randomUUID().toString();
                    synchronized (queue) {
                        queue.add(event);
                        queue.notify();
                    }
                }
            }
        });

        // process 2 will also create events
        Thread producer2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String event = UUID.randomUUID().toString();
                    synchronized (queue) {
                        queue.add(event);
                        queue.notify();
                    }
                }
            }
        });

        // Process 2 will pair two different events
        Thread PairingEvents = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (queue) {
                        while (queue.size() < 2) {
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        String event1 = queue.poll();
                        String event2 = queue.poll();
                        System.out.println("Paired: " + event1 + ", " + event2);
                    }
                }
            }
        });

        // Process 3 collects event pairs until it has 10 pairs.
        Thread collector = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<String>();
                while (true) {
                    synchronized (queue) {
                        while (queue.size() < 2) {
                            try {
                                queue.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        String event1 = queue.poll();
                        String event2 = queue.poll();
                        list.add(event1);
                        list.add(event2);
                        if (list.size() == 10) {
                            System.out.println("Collected: " + list);
                            list.clear();
                        }
                    }
                }
            }
        });
        
        //  The following output rule. let a = Min + (Max-Min)*3/4    if Mean>=a then output.
        Thread output = new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<String>();
                // let a = min + (max-min)*3/4
                a = min + (max - min) * 3 / 4;
                // if mean>=a then output
                if (mean >= a) {
                    System.out.println("Output: " + list);
                    list.clear();
                }
 
            }
        });




    

    }
}