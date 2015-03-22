import java.util.ArrayList;

public class City {
    public double x;    // Store position x of city
    public double y;    // Store position y of city
    public int name;    // Store name of city
    
    public City(int name, double x, double y){
        this.x = x;
        this.y = y;
        this.name = name;
    }
    
    // This function used to calculate distance between 2 City
    public double distanceTo(City destination){
        return (double)Math.sqrt(Math.pow(this.x - destination.x, 2) + Math.pow(this.y - destination.y, 2));
    }
    
    public static void print(Object obj){
        System.out.print(obj.toString());
    }
    
    public static void println(Object obj){
        System.out.println(obj.toString());
    }
    
    public String toString(){
        return ""+this.name;
    }
}
