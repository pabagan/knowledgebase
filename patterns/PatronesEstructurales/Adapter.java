public class Adapter {
   static void Jobs (Extra job) {
       if (job instanceof Clean) 
           ((Clean)job).makeClean();
       if (job instanceof Extra)
           ((Extra)job).takeCare();
   }
   public static void main(String[] args) {
       Extra e = new Facility();
       Jobs(e);
       Clean c1 = new Office();
       Clean c2 = new Laboratory();
       c1.makeClean();
       c2.makeClean();
       e.makeClean();
   }
}

interface Clean {
    public void makeClean();
}

class Office implements Clean{
    public void makeClean() {
        System.out.println("Limpiar la oficina");
    }
}

class Laboratory implements Clean{
    public void makeClean() {
        System.out.println("Limpiar el laboratorio");
    }
}

interface Extra extends Clean{
    public void takeCare();
}

class Facility implements Extra{
    public void makeClean() {
        System.out.println("Limpiar las instalaciones");
    }
    public void takeCare() {
        System.out.println("Hemos tenido cuidado");
    }
}