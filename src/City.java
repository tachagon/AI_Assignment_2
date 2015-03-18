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
    
    public double distanceTo(City destination){
        return (double)Math.sqrt(Math.pow(this.x - destination.x, 2) + Math.pow(this.y - destination.y, 2));
    }
    
    public static void main(String[] args){
        /*ArrayList<Integer> num = new ArrayList<Integer>();
        num.add(0);
        num.add(5);
        num.add(10);
        println(num);
        
        for(int item:num){
            println(item+10);
        }*/
        City c1 = new City(1, 1.0, 2.0);
        City c2 = new City(2, 1.0, 4.0);
        println(c1.distanceTo(c2));
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
