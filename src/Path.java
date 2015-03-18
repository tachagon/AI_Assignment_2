import java.util.ArrayList;
import java.util.StringJoiner;

public class Path {
    public ArrayList<City> path;    // used for keep path of City of an object
    public double fitness;          // used for keep fitness value of an object
    public double distance;         // used for keep total distance of path
    
    public Path() {
        this.path = new ArrayList<City>();
        this.fitness = 0.0;
        this.distance = -1.0;
    }
    
    public void createChromosome(ArrayList<City> cities) {
        ArrayList<City> chromosome = new ArrayList<City>();
        while (true) {
            int numCity = (int) (Math.random() * cities.size());
            City newCity = cities.get(numCity);
            if (chromosome.indexOf(newCity) == -1)
                chromosome.add(newCity);
            if(chromosome.size() == cities.size())
                break;
        }
        this.path = chromosome;
    }
    
    // This Function is used for calculate Finess value of a Path object
    public void calFitness(double Cmax){
        this.fitness = Cmax - this.distance;
    }
    
    // This function is used for calculate total distance of path
    public void calDistance(){
        // Calculate distance from city to city
        double x = 0 ;
        for(int i = 0 ; i < this.path.size() ; i++){ //number city
            if (i < this.path.size()-1){
                x = x + this.path.get(i).distanceTo(this.path.get(i+1));}//calculate distance to another city
            else{
                x = x + this.path.get(i).distanceTo(this.path.get(0));}//calculate distance back to first city
            
        }
        this.distance =x;
    }
    
    public String toString(){
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for(City c:this.path){
          sj.add(c.toString());
        }
        return sj.toString();
    }
    
    public static void main(String[] args){
        City x1 = new City(1, 0.0, 0.0);
        City x2 = new City(2, 0.0, 10.0);
        City x3 = new City(3, 5.0, 10.0);
        City x4 = new City(4, 5.0, 20.0);
        City x5 = new City(5, 10.0, 20.0);
        City x6 = new City(6, 10.0, 0.0);
        
        Path p12 = new Path();
        ArrayList<City> li1 = new ArrayList<City>();
        li1.add(x1);
        li1.add(x2);
        li1.add(x3);
        li1.add(x4);
        li1.add(x5);
        li1.add(x6);
        
        System.out.println(x1.distanceTo(x2));
        System.out.println(x2.distanceTo(x3));
        System.out.println(x3.distanceTo(x4));
        System.out.println(x4.distanceTo(x5));
        System.out.println(x5.distanceTo(x6));
        System.out.println(x6.distanceTo(x1));
        
        p12.path = li1;
        p12.calDistance();
        System.out.println(p12.distance);
        
    }
}
