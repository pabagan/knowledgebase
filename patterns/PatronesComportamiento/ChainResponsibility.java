import java.io.*;
abstract class PurchasePower {

    protected final double base = 500;
    protected PurchasePower successor;

    public void setSuccessor(PurchasePower successor){
        this.successor = successor;
    }

    abstract public void processRequest(PurchaseRequest request);
}

class ManagerPPower extends PurchasePower {
    private final double ALLOWABLE = 10 * base;

    public void processRequest(PurchaseRequest request ) {
        if( request.getAmount() < ALLOWABLE )
            System.out.println("El Directivo puede aprobar la cantidad de $"+ request.getAmount());
        else
           if( successor != null)
               successor.processRequest(request);
  }
}

class DirectorPPower extends PurchasePower {
    private final double ALLOWABLE = 20 * base;

    public void processRequest(PurchaseRequest request ) {
        if( request.getAmount() < ALLOWABLE )
            System.out.println("El Director puede aprobar la cantidad de $"+ request.getAmount());
        else
           if( successor != null)
               successor.processRequest(request);
  }
}

class VicePresidentPPower extends PurchasePower {
    private final double ALLOWABLE = 40 * base;

    public void processRequest(PurchaseRequest request) {
        if( request.getAmount() < ALLOWABLE )
            System.out.println("El Vice-presidente puede aprobar la cantidad de  $" + request.getAmount());
        else
        if( successor != null )
            successor.processRequest(request);
  }
}

class PresidentPPower extends PurchasePower {
    private final double ALLOWABLE = 60 * base;
   
    public void processRequest(PurchaseRequest request){
        if( request.getAmount() < ALLOWABLE )
            System.out.println("El presidente puede aprobar la cantidad de $" + request.getAmount());
        else
            System.out.println("ÁTu solicitud para gastar $" + request.getAmount() + " require una reuni—n del equipo de direcci—n!");
    }
}

class PurchaseRequest {
    private int number;
    private double amount;
    private String purpose;

    public PurchaseRequest(int number, double amount, String purpose){
        this.number = number;
        this.amount = amount;
        this.purpose = purpose;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amt){
        amount = amt;
    }
    
    public String getPurpose() {
        return purpose;
    }
    public void setPurpose(String reason) {
        purpose = reason;
    }

    public int getNumber(){
        return number;
    }
    public void setNumber(int num) {
        number = num;
     }   
}

public class ChainResponsibility {
    public static void main(String[] args){
        ManagerPPower manager = new ManagerPPower();
        DirectorPPower director = new DirectorPPower();
        VicePresidentPPower vp = new VicePresidentPPower();
        PresidentPPower president = new PresidentPPower();
        manager.setSuccessor(director);
        director.setSuccessor(vp);
        vp.setSuccessor(president);
        
        //enter ctrl+c to kill.
        try{
            while (true) {
                System.out.println("ÀCuanto dinero quieres gastar?");
                System.out.print(">");
                double d = Double.parseDouble(new BufferedReader(new InputStreamReader(System.in)).readLine());
                manager.processRequest(new PurchaseRequest(0, d, "General"));
           }
        }catch(Exception e){
            System.exit(1);
        }  
  }
}
