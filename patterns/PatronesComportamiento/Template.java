abstract class CheckBackground {
  
    public abstract void checkBank();
    public abstract void checkCredit();
    public abstract void checkLoan();
    public abstract void checkStock();
    public abstract void checkIncome();

  //work as template method
    public void check() {
        checkBank();
        checkCredit();
        checkLoan();
        checkStock();
        checkIncome(); 
    }
}

class LoanApp extends CheckBackground {
    private String name;
   
    public LoanApp(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void checkBank() {
        //ck acct, balance
        System.out.println("comprobar banco...");
    }

    public void checkCredit() {
        //ck score from 3 companies
        System.out.println("comprobar crédito...");
    }

    public void checkLoan() {
        //ck other loan info
        System.out.println("comprobar otros prestamos...");
    }

    public void checkStock() {
        //ck how many stock values
        System.out.println("comprobar valores en bolsa...");
    }

    public void checkIncome() {
        //ck how much a family make
        System.out.println("comprobar retribución familiar...");
    }
   
    //other methods
}

class Template {
    public static void main(String[] args) {
        
        LoanApp mortgageClient = new LoanApp("Judy:");
        System.out.println("\nComprobar cliente " + mortgageClient.getName()+ " solicitud para hipotéca. ");
        mortgageClient.check();
        
        LoanApp equityloanClient = new LoanApp("Mark:");
        System.out.println("\nComprobar cliente " + equityloanClient.getName()+ " solicitud para un préstamos. ");
        equityloanClient.check();
    }
}