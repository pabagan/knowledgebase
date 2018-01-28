
interface Display {

   public void load(String fileName);

   public void formatConsistency();

}

//archivo de texto
class CSVFile implements Display{

    public void load(String textfile) {
        System.out.println("cargar desde un archivo de texto");
    }
    public void formatConsistency() {
        System.out.println("formato del archivo de texto ha cambiado");
    } 
}

//archivo de XML
class XMLFile implements Display {

    public void load(String xmlfile) {
        System.out.println("cargar desde un archivo de XML");
    }
    public void formatConsistency() {
        System.out.println("formato del archivo de XML ha cambiado");
    } 
}

//archivo binario
class DBFile implements Display {

    public  void load(String dbfile) {
        System.out.println("cargar bbdd desde un archivo");
    }
    public void formatConsistency() {
        System.out.println("formato del archivo de bbdd ha cambiado");
    } 
}

public class Factory {
    public static void main(String[] args) {
        Display display = null;
        
        if (args[0].equals("1"))
           display = new CSVFile();
        else if (args[0].equals("2"))
           display = new XMLFile();
        else if (args[0].equals("3"))
           display = new DBFile();
        else
           System.exit(1);

        display.load("");
        display.formatConsistency();
   }    
}