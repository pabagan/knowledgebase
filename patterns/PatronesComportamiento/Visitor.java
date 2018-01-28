interface Element {
   public void accept( VisitorDemo v );
}

class This implements Element {
   public void   accept( VisitorDemo v ) {
     v.visit( this );
   } 
   public String thiss() {
     return "Esto";
   }
}

class That implements Element {
   public void   accept( VisitorDemo v ) {
     v.visit( this );
   }
   public String that() {
     return "Eso";
   }
}

class TheOther implements Element {
   public void   accept( VisitorDemo v ) {
     v.visit( this );
   }
   public String theOther() {
     return "Aquello"; 
   }
}

interface VisitorDemo {
   public void visit( This e );
   public void visit( That e );
   public void visit( TheOther e );
}

class UpVisitor implements VisitorDemo {                   
   public void visit( This e ) {
      System.out.println( "haz algo a " + e.thiss() );
   }
   public void visit( That e ) {
      System.out.println( "haz algo a " + e.that() );
   }
   public void visit( TheOther e ) {
      System.out.println( "haz algo a " + e.theOther() );
   }
}

class DownVisitor implements VisitorDemo {
   public void visit( This e ) {
      System.out.println( "haz otra cosa a " + e.thiss() );
   }
   public void visit( That e ) {
      System.out.println( "haz otra cosa a " + e.that() );
   }
   public void visit( TheOther e ) {
      System.out.println( "haz otra cosa a " + e.theOther() );
   }
}

public class Visitor {
   public static Element[] list = { new This(), new That(), new TheOther() };

   public static void main( String[] args ) {
      UpVisitor    up   = new UpVisitor();
      DownVisitor  down = new DownVisitor();
      for (int i=0; i < list.length; i++) {
         list[i].accept( up );
      }
      for (int i=0; i < list.length; i++) {
         list[i].accept( down );
      }
   }
}