class Employee {
   String name;
   double salary;
   Employee(String n, double s){
       name = n;
       salary = s;
   }
   String getName() {
      return name;
   }
   double getSalary() {
      return salary;
   }
   public String toString() {
       return "Empleado " + name;
   }
}
class Manager {
   Manager mgr;
   Employee[] ely;
   String dept;
   Manager(Manager mgr,Employee[] e, String d ) {
       this(e, d);
       this.mgr = mgr;
   }
   
   Manager(Employee[] e, String d) {
       ely = e;
       dept =d;
   }
   String getDept() {
       return dept;
   }
   Manager getManager() {
       return mgr;
   }
   Employee[] getEmployee() {
       return ely;
   }
   public String toString() {
       return " Director de " + dept;
   }
}

public class Composite {
   public static void main(String[] args) {
       Employee[] e1 = {new Employee("Aaron", 50),
                        new Employee("Betty", 60)};
       Manager m1 = new Manager(e1, "Contabilidad");
       
       Employee[] e2 = {new Employee("Cathy", 70),
                        new Employee("Dan", 80),
                        new Employee("Eliz", 90)};
       Manager m2 = new Manager(m1, e2, "Producci—n");
       
       System.out.println(m2);
       Employee[] emp = m2.getEmployee();
       if (emp != null)
             for (int k = 0; k < emp.length; k++)
                  System.out.println(" "+emp[k]+" Nomina: $"+ emp[k].getSalary());       
       Manager m = m2.getManager();
       System.out.println(" " + m);
       if (m!= null) {
          Employee[] emps = m.getEmployee();
          if (emps != null)
             for (int k = 0; k < emps.length; k++)
                   System.out.println("    " + emps[k]+" Nomina: $"+ emps[k].getSalary());
        
       }   
   }
}
