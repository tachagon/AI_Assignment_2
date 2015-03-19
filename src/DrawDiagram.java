
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class DrawDiagram extends JPanel implements ActionListener{
    Timer tm = new Timer(0, this);
    public int goodPathPop = 0;
    public Path bestPath = new Path();
    public ArrayList<City> cities = new ArrayList<City>();      // for keep all City Objects
    public ArrayList<Path> population = new ArrayList<Path>();  // for keep population of current generation
    
    @Override
    public void paintComponent(Graphics g){         // draw diagram
        this.drawCity(g);
    }
    
    private void drawPath(Graphics g){
        for(int i=0; i<this.population.size(); i++){
            Path p = this.population.get(i);    // get each Path from population
            if(this.goodPathPop != i){          // if path is not good
                g.setColor(new Color(181, 181, 181));   // set color for draw line
                for(int j=0; j<p.path.size(); j++){     // each City in path
                    
                }
            }
        }
    }
    
    private void drawCity(Graphics g){
        double Xmax=0, Ymax=0;
        int r = 10;
        for(City c:this.cities){
            if(c.x > Xmax) Xmax = c.x;  // find maximum x value
            if(c.y > Ymax) Ymax = c.y;  // find maximum y value
        }
        for(City c:this.cities){
            g.setColor(new Color(82, 139, 139));    // set color for outter circle
            int x=0, y=0;
            x = (int)(c.x*490/Xmax);    // calculate x position for draw circle
            y = (int)(c.y*490/Ymax);    // calculate y position fot draw circle
            g.fillOval(x, y, r, r);     // draw outter circle
            g.setColor(new Color(121, 205, 205));   // set new color fot inner circle
            g.fillOval(x, y, r-2, r-2); // draw inner circle
        }
    }
    
    public void startAnimate(){ // is used for start animate
        tm.start();
    }
    
    public void stopAnimate(){  // is used fot stop animate
        tm.stop();
    }
    
    public void actionPerformed(ActionEvent e){ // update something for animate
        repaint();
    }
    
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setBackground(Color.yellow);
        
        DrawDiagram panel1 = new DrawDiagram();
        panel1.setBackground(Color.ORANGE);
        panel1.setPreferredSize(new Dimension(500, 500));
        
        DrawDiagram panel2 = new DrawDiagram();
        panel2.setBackground(Color.ORANGE);
        panel2.setPreferredSize(new Dimension(200, 200));
        panel2.setOpaque(false);
        panel1.add(panel2);
        panel1.setOpaque(false);
        frame.add(panel1);
        frame.pack();
        frame.setVisible(true);
                
    }
}