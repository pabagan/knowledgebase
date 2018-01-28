
public class Singleton {
	    public static boolean haveOne = false;
	    public Singleton() throws Exception{
	        if (!haveOne) {
	           doSomething();
	           haveOne = true;
	        }else {
	          throw new Exception("No se puede generar una segunda instancia");
	        }
	    }
	    public static Singleton getSingleton() throws Exception{
	        return new Singleton();
	    }
	    void doSomething() {}
	    //...
	    public static void main(String [] args) {
	        try {
	        	Singleton s = new Singleton(); //Permitido
	        }catch(Exception e) {
	            System.out.println("primero: " +e.getMessage());
	        }
	        try {
	        	Singleton s2 = Singleton.getSingleton(); //No permitido
	        }catch(Exception e) {
	            System.out.println("segundo: " +e.getMessage());
	        }
	    }
	}
