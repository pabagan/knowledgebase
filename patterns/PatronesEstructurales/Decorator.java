import java.util.Random;
class Number {
   public void print() {
       System.out.println(new Random().nextInt());
   }
}

class DecoratorPattern {
    public DecoratorPattern() {
        System.out.print("Nœmero aleatorio: ");//add a description to the number printed
        new Number().print();
    }
}

class SubNumber extends Number{
    public SubNumber() {
       super();
       System.out.print("Numbero aleatorio: ");
       print();
    }
}

public class Decorator {
    public static void main(String[] args) {
        new DecoratorPattern();
        new SubNumber();
    }
}