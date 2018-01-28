
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Proxy extends JFrame {
  public Proxy() {
    super("Presentar imagen en el proxy");
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    JPanel p = new JPanel();
    getContentPane().add(p);
    p.setLayout(new BorderLayout());
    //Hay que incluir el camino a la imagen
    ImageProxy image = new ImageProxy("/Users/Tim/Downloads/dog.jpg", 278, 300);
    p.add("Center", image);
    p.add("North", new Label("    "));
    p.add("West", new Label("  "));
    setSize(370, 350);
    setVisible(true);
  }

  
  static public void main(String[] argv) {
    new Proxy();
  }
}
//==================================

class ImageProxy extends JPanel implements Runnable {
  int height, width;

  MediaTracker tracker;

  Image img;

  JFrame frame;

  Thread imageCheck; 

  public ImageProxy(String filename, int w, int h) {
    height = h;
    width = w;

    tracker = new MediaTracker(this);
    img = Toolkit.getDefaultToolkit().getImage(filename);
    tracker.addImage(img, 0); 

    imageCheck = new Thread(this);
    imageCheck.start();

    try {
      tracker.waitForID(0, 1);
    } catch (InterruptedException e) {
    }
  }

  
  public void paint(Graphics g) {
    if (tracker.checkID(0)) {
      height = img.getHeight(frame);
      width = img.getWidth(frame);

      g.setColor(Color.lightGray); 
      g.fillRect(0, 0, width, height);
      g.drawImage(img, 0, 0, this); 
    } else {
      g.setColor(Color.black);
      g.drawRect(1, 1, width - 2, height - 2);
    }
  }

  
  public Dimension getPreferredSize() {
    return new Dimension(width, height);
  }

  public void run() {
    try {
      Thread.sleep(1000);
      while (!tracker.checkID(0))
        Thread.sleep(1000);
    } catch (Exception e) {
    }
    repaint();
  }
}