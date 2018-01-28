interface FortuneCookies {
    public void print();
}
class Five implements FortuneCookies {
    public void print() {
        System.out.println("Vas a ganar mucho dinero");
    }
}
class Two implements FortuneCookies {
    public void print() {
        System.out.println("Nunca es tarde empezar");
    }
}
class Null implements FortuneCookies {
    public void print() {
        System.out.println("No has conseguido nada");    
    }
}
class Dice {
   public int throwIt() {
       return (int)(Math.random()*6)+1;
   }
}

public class Strategy {  
    static void goodFortune() {
        int luckyNum = new Dice().throwIt();
        FortuneCookies fc;
        switch (luckyNum) {
            case 2: fc = new Two();
                    break;
            case 5: fc = new Five();
                    break;
            //more
            default: fc = new Null();
        }
        fc.print();
    }
    public static void main(String[] args) {
        goodFortune();
    }
}