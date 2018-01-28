class MediatorDemo {
  private boolean slotFull = false;
  private int number;
  public synchronized void storeMessage( int num ) {
    while (slotFull == true) {
      try {
        wait();
      }
      catch (InterruptedException e ) { }
    }
    slotFull = true;
    number = num;
    notifyAll();
  }
  public synchronized int retrieveMessage() {
    while (slotFull == false)
      try {
        wait();
      }
      catch (InterruptedException e ) { }
    slotFull = false;
    notifyAll();
    return number;
  }
}

class Producer extends Thread {
  private MediatorDemo med;
  private int    id;
  private static int num = 1;
  public Producer( MediatorDemo m ) {
    med = m;
    id = num++;
  }
  public void run() {
    int num;
    while (true) {
      med.storeMessage( num = (int)(Math.random()*100) );
      System.out.print( "p" + id + "-" + num + "  " );
    }
  }
}

class Consumer extends Thread {
  private MediatorDemo med;
  private int    id;
  private static int num = 1;
  public Consumer( MediatorDemo m ) {
    med = m;
    id = num++;
  }
  public void run() {
    while (true) {
      System.out.print("c" + id + "-" + med.retrieveMessage() + "  ");
    }
  }
}

public class Mediator {
  public static void main( String[] args ) {
    MediatorDemo mb = new MediatorDemo();
    new Producer( mb ).start();
    new Producer( mb ).start();
    new Consumer( mb ).start();
    new Consumer( mb ).start();
    new Consumer( mb ).start();
    new Consumer( mb ).start();
  }
}