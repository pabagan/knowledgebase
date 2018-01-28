import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

interface Subject {
 public void addObserver(ObserverDemo o);
 public void removeObserver(ObserverDemo o);
 public String getState();
 public void setState(String state);
}

interface ObserverDemo {
 public void update(Subject o);
}

class ObserverImpl implements ObserverDemo {
 private String state = "";

 public void update(Subject o) {
   state = o.getState();
   System.out.println("Actualizaci—n recibida del Subjeto, cambio en estado a : " + state);
 }
}

class SubjectImpl implements Subject {
 private List observers = new ArrayList();

 private String state = "";

 public String getState() {
   return state;
 }

 public void setState(String state) {
   this.state = state;
   notifyObservers();
 }

 public void addObserver(ObserverDemo o) {
   observers.add(o);
 }

 public void removeObserver(ObserverDemo o) {
   observers.remove(o);
 }

 public void notifyObservers() {
   Iterator i = observers.iterator();
   while (i.hasNext()) {
     ObserverDemo o = (ObserverDemo) i.next();
     o.update(this);
   }
 }
}

public class Observer {

 public static void main(String[] args) {
   ObserverDemo o = new ObserverImpl();
   Subject s = new SubjectImpl();
   s.addObserver(o);
   s.setState("Estado nuevo");

 }
}