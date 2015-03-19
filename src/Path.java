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
    
    // function for create chromosome from all city
    public void createChromosome(ArrayList<City> cities) {
        ArrayList<City> chromosome = new ArrayList<City>(); // store new path for travel all city or call new chromosome
        while (true) {
            int numCity = (int) (Math.random() * cities.size());    // random name city for add in new path
            City newCity = cities.get(numCity); // store new city from random city
            if (chromosome.indexOf(newCity) == -1)  // check new city have in new path
                chromosome.add(newCity);    // if not have new city then add new city to new path
            if(chromosome.size() == cities.size())  // check number of new city in new path equal number of all city
                break;  // if number of new city in new path equal number of all city then end 
        }
        this.path = chromosome; // store new path
    }
    
    // This Function is used for calculate Finess value of a Path object
    public void calFitness(double Cmax){
        // calculate Finess from path have longest distance in current generation - distance of current path
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
