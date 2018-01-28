import java.util.*;

class MementoDemo {
   private String state;

   public MementoDemo(String stateToSave) { state = stateToSave; }
   public String getSavedState() { return state; }
}

class Originator {
   private String state;
   
   public void set(String state) { 
       System.out.println("Emisor: Fijando estado a "+state);
       this.state = state; 
   }

   public MementoDemo saveToMemento() { 
       System.out.println("Emisor: Almacenando en Memento.");
       return new MementoDemo(state); 
   }
   public void restoreFromMemento(MementoDemo m) {
       state = m.getSavedState(); 
       System.out.println("Emisor: Estado despœes de recuperaci—n del Memento: "+state);
   }
}   

class Caretaker {
   private ArrayList<MementoDemo> savedStates = new ArrayList<MementoDemo>();

   public void addMemento(MementoDemo m) { savedStates.add(m); }
   public MementoDemo getMemento(int index) { return savedStates.get(index); }
}   

public class Memento {
   public static void main(String[] args) {
       Caretaker caretaker = new Caretaker();

       Originator originator = new Originator();
       originator.set("Estado1");
       originator.set("Estado2");
       caretaker.addMemento( originator.saveToMemento() );
       originator.set("Estado3");
       caretaker.addMemento( originator.saveToMemento() );
       originator.set("Estado4");

       originator.restoreFromMemento( caretaker.getMemento(1) );
   }
}