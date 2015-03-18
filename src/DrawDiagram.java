
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
    int x=0, velX = 2;
    public ArrayList<City> cities = new ArrayList<City>();
    
    @Override
    public void paintComponent(Graphics g){     // draw diagram
        for(int i=0; i<this.cities.size(); i++){
            for(int j=0; j<this.cities.size(); j++){
                g.setColor(new Color(181, 181, 181));
                if(i != j){
                    City c1 = this.cities.get(i);
                    City c2 = this.cities.get(j);
                    g.drawLine((int)c1.x, (int)c1.y, (int)c2.x, (int)c2.y);
                }
            }
        }
        
    }
    
    public void setAnimate(){   // is used for start animate
        tm.start();
    }
    
    public void actionPerformed(ActionEvent e){ // update something for animate
        if(x<0 || x>500){
            velX = -velX;
        }
        x+=velX;
        repaint();
        System.out.println("test");
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