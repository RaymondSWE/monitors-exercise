
import java.util.concurrent.locks.*;
import java.util.concurrent.TimeUnit;
import java.lang.Runnable;


// Implement reader and writer classes
class Reader implements Runnable {
    private Host host;
    private int id;
    public Reader(Host host, int id) {
        this.host = host;
        this.id = id;
    }
    public void run() {
        try {
            host.read(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Writer implements Runnable {
    private Host host;
    private int id;
    public Writer(Host host, int id) {
        this.host = host;
        this.id = id;
    }
    public void run() {
        try {
            host.write(id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Rather than having controll class have 2 lock objects one for read and one for write
// we can use a single lock object and use the condition object to control the threads
// that are waiting to read or write.

class Host {
    private int readers = 0;
    private int writers = 0;
    private int writeRequests = 0;
    private Lock lock = new ReentrantLock();
    private Condition okToRead = lock.newCondition();
    private Condition okToWrite = lock.newCondition();

    public void read(int id) throws InterruptedException {
        lock.lock();
        try {
            while (writers > 0 || writeRequests > 0) {
                okToRead.await();
            }
            readers++;
            System.out.println("Reader " + id + " is reading");
            Thread.sleep(1000);
            System.out.println("Reader " + id + " is done reading");
            readers--;
            if (readers == 0) {
                okToWrite.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void write(int id) throws InterruptedException {
        lock.lock();
        try {
            writeRequests++;
            while (readers > 0 || writers > 0) {
                okToWrite.await();
            }
            writeRequests--;
            writers++;
            System.out.println("Writer " + id + " is writing");
            Thread.sleep(1000);
            System.out.println("Writer " + id + " is done writing");
            writers--;
            if (writeRequests > 0) {
                okToWrite.signal();
            } else {
                okToRead.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}


// Check the conditions before interacting with them so that they act more like a complete monitor system as seen in the lectures.
// Not completed yet, some issues.
public class exercise1 {
    public static void main(String[] args) {
        Host host = new Host();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                threads[i] = new Thread(new Reader(host, i));
            } else {
                threads[i] = new Thread(new Writer(host, i));
            }
        }
        for (int i = 0; i < 10; i++) {
            threads[i].start();
        }
    }
}



