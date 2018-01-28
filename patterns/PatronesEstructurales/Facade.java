class PointCarte {
  private double x, y;
  public PointCarte( double xx, double yy ) {
    x = xx;
    y = yy;
  }
  public void  move( int dx, int dy ) {
    x += dx;
    y += dy;
  }
  public String toString() {
    return "(" + x + "," + y + ")";
  }
  public double getX() {
    return x;
  }
  public double getY() {
    return y;
  } 
}

class PointPolar {
  private double radius, angle;
  public PointPolar( double r, double a ) {
    radius = r;
    angle = a;
  }
  public void  rotate( int ang ) {
    angle += ang % 360;
  }
  public String toString() {
    return "[" + radius + "@" + angle + "]";
  }
}

class Point {
  private PointCarte pc;
  
  public Point( double xx, double yy ) {
    pc = new PointCarte( xx,yy );
  }
  public String toString() {
    return pc.toString();
  }
  
  public void move( int dx, int dy ) {
    pc.move( dx,dy );
  }
  public void rotate( int angle, Point o ) {
    double x = pc.getX() - o.pc.getX();
    double y = pc.getY() - o.pc.getY();
    PointPolar pp = new PointPolar( Math.sqrt(x*x+y*y),
                          Math.atan2(y,x)*180/Math.PI );
    pp.rotate( angle );
    System.out.println( "  PointPolar es " + pp );
    String str = pp.toString();  int i = str.indexOf( '@' );
    double r = Double.parseDouble( str.substring(1,i) );
    double a = Double.parseDouble( str.substring(i+1,str.length()-1) );
    pc = new PointCarte(r*Math.cos(a*Math.PI/180) + o.pc.getX(),
                  r*Math.sin(a*Math.PI/180) + o.pc.getY() );
  }
}

class NewLine {
  private Point o, e;
  public NewLine( Point ori, Point end ) {
    o = ori;
    e = end;
  }
  public void  move( int dx, int dy ) {
    o.move( dx, dy );
    e.move( dx, dy );
  }
  public void  rotate( int angle ) {
    e.rotate( angle, o );
  }
  public String toString() {
    return "principio es " + o + ", final is " + e;
  }
}

public class Facade {
  public static void main( String[] args ) {
    NewLine line1 = new NewLine( new Point(2,4), new Point(5,7) );
    line1.move(-2,-4);
    System.out.println( "después de mover:  " + line1 );
    line1.rotate(45);
    System.out.println( "después de girar: " + line1 );
    NewLine line2 = new NewLine( new Point(2,1), new Point(2.866,1.5) );
    line2.rotate(30);
    System.out.println( "30 grados a 60 grados: " + line2 );
  }
}