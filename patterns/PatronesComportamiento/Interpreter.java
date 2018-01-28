import java.util.*;

class Context {

    private String input;
    private int output;

    public Context(String input)
    {
      this.input = input;
    }

    public String getInput()
    {
      return input;
    }
    
    public void setInput(String input)
    {
      this.input = input; 
    }    

    public int getOutput()
    {
      return output;
    }

    public void setOutput(int output)
    {
      this.output = output;
    }    
	
}

abstract class Expression {

    public void interpret(Context context)
    {
      if (context.getInput().length() == 0) 
        return;

      if (context.getInput().startsWith(nine()))
      {
        context.setOutput(context.getOutput() + (9 * multiplier()));
        context.setInput(context.getInput().substring(2));
      }
      else if (context.getInput().startsWith(four()))
      {
        context.setOutput(context.getOutput() + (4 * multiplier()));
        context.setInput(context.getInput().substring(2));
      }
      else if (context.getInput().startsWith(five()))
      {
        context.setOutput(context.getOutput() + (5 * multiplier()));
        context.setInput( context.getInput().substring(1));
      }

      while (context.getInput().startsWith(one()))
      {
        context.setOutput(context.getOutput() + (1 * multiplier()));
        context.setInput(context.getInput().substring(1));
      }
    }

    public abstract String one();
    public abstract String four();
    public abstract String five();
    public abstract String nine();
    public abstract int multiplier();
	
}

class ThousandExpression  extends Expression{

    public String one() { return "M"; }
    public String four(){ return " "; }
    public String five(){ return " "; }
    public String nine(){ return " "; }
    public int multiplier() { return 1000; }
}

class HundredExpression extends Expression{
    public  String one() { return "C"; }
    public  String four(){ return "CD"; }
    public  String five(){ return "D"; }
    public  String nine(){ return "CM"; }
    public  int multiplier() { return 100; }
}

class TenExpression  extends Expression{
    public String one() { return "X"; }
    public String four(){ return "XL"; }
    public String five(){ return "L"; }
    public String nine(){ return "XC"; }
    public int multiplier() { return 10; }
}

class OneExpression  extends Expression{
    public String one() { return "I"; }
    public String four(){ return "IV"; }
    public String five(){ return "V"; }
    public String nine(){ return "IX"; }
    public int multiplier() { return 1; }
}

public class Interpreter {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	      String roman = "MCMXXVIII";
	      Context context = new Context(roman);

	      // Build the 'parse tree' 
	      ArrayList<Expression> tree = new ArrayList<Expression>();
	      tree.add(new ThousandExpression());
	      tree.add(new HundredExpression());
	      tree.add(new TenExpression());
	      tree.add(new OneExpression());

	      // Interpret 
	      for (Iterator it = tree.iterator(); it.hasNext();)
	      {
	    	  Expression exp = (Expression)it.next();
	    	  exp.interpret(context);
	      }

	      System.out.println(roman + " = " + Integer.toString(context.getOutput()));
	}
}