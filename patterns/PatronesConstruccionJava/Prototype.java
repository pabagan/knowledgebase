
interface Shape {
	public void draw();
} 
class Line implements Shape {
	public void draw() {
		System.out.println("l’nea");
	}
}
class Square implements Shape {
	public void draw() {
		System.out.println("cuadrado");
	}
}
class Circle implements Shape {
	public void draw() {
		System.out.println("c’rculo");
	}
}

public class Prototype {
	public static void main(String[] args) {
		Shape s1 = new Line();
		Shape s2 = new Square();
		Shape s3 = new Circle();
		paint(s1);
		paint(s2);
		paint(s3);
	}
	static void paint(Shape s) {
		s.draw();
	}
}