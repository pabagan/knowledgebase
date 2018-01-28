
public class AbstractFactory {
	public static void main(String[] args) {
        DataManager dm = new DataManager();
        DataInfo[] di = null;
        String dbFileName = "db.db";
        // ejemplo local
   		dm.setConnection(true);
        LocalMode lm = (LocalMode)dm.getLocalConnection(); 
        di = lm.loadDB(dbFileName); 
        System.out.println("\n");
        
		// ejemplo remoto
        RemoteMode rm = (RemoteMode)dm.getRemoteConnection();
        rm.connect2WWW("www.javacamp.org/db/");
        di = rm.loadDB(dbFileName); 
    }
}

	
	class DataInfo {}
	interface Local {
	  DataInfo[] loadDB(String filename);
	}

	interface Remote extends Local{
	  void connect2WWW(String url);
	}

	class LocalMode implements Local {
	  public DataInfo[] loadDB(String name) {
	    System.out.print("Cargar datos desde una base de datos local");
	    return null;
	  }
	}

	class RemoteMode implements Remote {
	  public void connect2WWW(String url) {
	    System.out.println("Conectar a un sitio remoto");
	  }
	  public DataInfo[] loadDB(String name) {
	     System.out.println("Cargar datos desde una base de datos remota");
	     return null;
	  }
	}

	interface ConnectionFactory {
	  Local getLocalConnection();
	  Remote getRemoteConnection();
	}

	class DataManager implements ConnectionFactory {
	    boolean local = false;
	    DataInfo[] data;
	    //...
	    public Local getLocalConnection() {
	        return new LocalMode();
	    }
	    public Remote getRemoteConnection() {
	        return new RemoteMode();
	    }
	    public  void loadData() {
	         if(local){
	            Local conn = getLocalConnection();
	            data = conn.loadDB("db.db");
	         }else {
	            Remote conn = getRemoteConnection();
	            conn.connect2WWW("www.some.where.com");
	            data = conn.loadDB("db.db");
	         }
	         
	     }
	    
	    public void setConnection(boolean b) {
	       local = b;
	    }
	}

	    