import java.util.concurrent.locks.*;

import javax.lang.model.util.ElementScanner14;

import java.util.concurrent.TimeUnit;
import java.lang.Runnable;



// the host will fill up drinks in the bowl
class Host implements Runnable {
    private Bowl bowl;
    public Host(Bowl bowl) {
        this.bowl = bowl;
    }
    public void run() {
        while (true) {
            bowl.fill();
            System.out.println("Host is filling up the bowl");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}






// implement a bowl that can be filled and drunk from

class Bowl {
    private int capacity;
    private int amount;
    private Lock lock;
    private Condition notFull;
    private Condition notEmpty;
    public Bowl(int capacity) {
        this.capacity = capacity;
        this.amount = 0;
        this.lock = new ReentrantLock();
        this.notFull = lock.newCondition();
        this.notEmpty = lock.newCondition();
    }
    public void fill() {
        lock.lock();
        try {
            while (amount == capacity) {
                notFull.await();
            }
            amount = capacity;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    public void drink() {
        lock.lock();
        try {
            while (amount == 0) {
                notEmpty.await();
            }
            amount = 0;
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // Check #drinks left (in the bowl)
    public int getDrinks() {
        return amount;
    }

    // Notify the host when there is 0 drinks in bowl
    public boolean isEmpty() {
        return amount == 0;
    }

    // Make guest wait while filling the bowl till it is full
    public boolean isFull() {
        return amount == capacity;
    }

}

// implement guest class that drinks from bowl if getDrinks > 0
class Guest implements Runnable {
    private String name;
    private Bowl bowl;
    private Lock lock;
    private Condition condition;

    public Guest(String name,  Bowl bowl, Lock lock, Condition condition) {
        this.name = name;
        this.bowl = bowl;
        this.lock = lock;
        this.condition = condition;
    }

    public void run() {
        while (getDrinks > 0) {
            lock.lock();
            try {
                while (bowl.getDrinks() == 0) {
                    condition.await();
                }
                bowl.drink();
                getDrinks--;
                System.out.println(name + " drinks from bowl. " + getDrinks + " drinks left.");
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
// implement a monitor to test the whole program
class Monitor implements Runnable {
    private Bowl bowl;
    public Monitor(Bowl bowl) {
        this.bowl = bowl;
    }
    public void run() {
        while (true) {
            if (bowl.isEmpty()) {
                System.out.println("Bowl is empty");
            } else if (bowl.isFull()) {
                System.out.println("Bowl is full");
            } else {
                System.out.println("Bowl has " + bowl.getDrinks() + " drinks left");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// main class
public class exercise1 {
    public static void main(String[] args) {
        Bowl bowl = new Bowl(10);
        Guest guest = new Guest("Guest", bowl);
        Host host = new Host(bowl);
        Monitor monitor = new Monitor(bowl);
        Thread guestThread = new Thread(guest);
        Thread hostThread = new Thread(host);
        Thread monitorThread = new Thread(monitor);
        guestThread.start();
        hostThread.start();
        monitorThread.start();
    }
}









