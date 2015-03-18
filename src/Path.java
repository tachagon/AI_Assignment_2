import java.util.ArrayList;
import java.util.StringJoiner;

public class Path {
    public ArrayList<City> path;    // used for keep path of City of an object
    public double fitness;          // used for keep fitness value of an object
    public double distance;         // used for keep total distance of path
    
    public Path(){
        this.path = new ArrayList<City>();
        this.fitness = 0.0;
        this.distance = -1.0;
        this.calDistance();     // calculate Distance
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
    private void calDistance(){
        // Write Code below
        
        
        
        this.distance = 0.0;
    }
    
    public String toString(){
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for(City c:this.path){
          sj.add(c.toString());
        }
        return sj.toString();
    }
}
