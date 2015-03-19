
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

class DrawDiagram extends JPanel implements ActionListener{
    Timer tm = new Timer(0, this);
    public Path goodPath = new Path();
    public Path bestPath = new Path();
    public ArrayList<City> cities = new ArrayList<City>();      // for keep all City Objects
    public ArrayList<Path> population = new ArrayList<Path>();  // for keep population of current generation
    private double Xmax=0, Ymax=0;
    private int r = 10;
    
    @Override
    public void paintComponent(Graphics g){         // draw diagram
        this.drawPath(g);
        this.drawCity(g);
    }
    
    private void drawPath(Graphics g){
        Graphics2D g2d = (Graphics2D) g.create();
        for(City c:this.cities){
            if(c.x > Xmax) Xmax = c.x;  // find maximum x value
            if(c.y > Ymax) Ymax = c.y;  // find maximum y value
        }/*
        for(int i=0; i<this.population.size(); i++){    // each Path in population
            Path p = this.population.get(i);            // get each Path from population
            if(this.population.indexOf(this.goodPath) != i){    // if path is not good
                g.setColor(new Color(181, 181, 181));   // set color for draw line
                for(int j=0; j<p.path.size(); j++){     // each City in path
                    City c = p.path.get(j);     // get current City
                    City cn;                    // get next City
                    if(j < p.path.size()-1){cn = p.path.get(j+1);}  // if City is not last City
                    else{cn = p.path.get(0);}   // City is last City
                    // draw line of path is not good
                    g.drawLine((int)(c.x*490/Xmax+(r/2)), (int)(c.y*490/Ymax+(r/2)), (int)(cn.x*490/Xmax+(r/2)), (int)(cn.y*490/Ymax+(r/2)));
                }
            }
        }*/
        g2d.setColor(new Color(255, 215, 0));
        for(int i=0; i<this.goodPath.path.size(); i++){ // if Path is good in this generation
            City c = this.goodPath.path.get(i);         // get current City
            City cn;                                    // get next City
            if(i < this.goodPath.path.size()-1){cn = this.goodPath.path.get(i+1);}  // if City is not last City
            else{cn = this.goodPath.path.get(0);}       // City is last City
            // draw line of path is good
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine((int)(c.x*490/Xmax+(r/2)), (int)(c.y*490/Ymax+(r/2)), (int)(cn.x*490/Xmax+(r/2)), (int)(cn.y*490/Ymax+(r/2)));
        }/*
        g2d.setColor(new Color(50, 205, 50));
        for(int i=0; i<this.bestPath.path.size(); i++){ // if Path is the Best
            City c = this.bestPath.path.get(i);         // get current City
            City cn;                                    // get next City
            if(i < this.bestPath.path.size()-1){cn = this.bestPath.path.get(i+1);}  // if City is not last City
            else{cn = this.bestPath.path.get(0);}       // City is last City
            // draw line of path is the best
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawLine((int)(c.x*490/Xmax+(r/2)), (int)(c.y*490/Ymax+(r/2)), (int)(cn.x*490/Xmax+(r/2)), (int)(cn.y*490/Ymax+(r/2)));
        }*/
    }
    
    private void drawCity(Graphics g){
        for(City c:this.cities){
            g.setColor(new Color(205, 92, 92));    // set color for outter circle
            int x=0, y=0;
            x = (int)(c.x*490/Xmax);    // calculate x position for draw circle
            y = (int)(c.y*490/Ymax);    // calculate y position fot draw circle
            g.fillOval(x, y, r, r);     // draw outter circle
            g.setColor(new Color(255, 106, 106));   // set new color fot inner circle
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