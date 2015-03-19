
import java.util.ArrayList;

public class GA {
    public ArrayList<City> cities;          // for keep all City Objects
    public ArrayList<Path> population;      // for keep population of current generation
    public ArrayList<Path> newPopulation;   // for keep new population in next generation
    
    public GA(){
        this.cities = new ArrayList<City>();
        this.population = new ArrayList<Path>();
        this.newPopulation = new ArrayList<Path>();
    }
    
    // This function used for create initial population
    public void initPopulation() {
        for(int i = 0; i < this.cities.size(); i++) {
            Path temp = new Path();             // create temporarily Path Object
            temp.createChromosome(this.cities); // create a Chromosome as Path
            temp.calDistance();                 // calculate ditance of each path
            this.population.add(temp);          // add Path into a population
        }
    }
    
    // This function used for selection new population in next generation
    public void select(){
        //------WRITE--CODE--BELOW-------
        /*====VVVVVVVVVVVVVVVVVVVVVVV====
        *=======VVVVVVVVVVVVVVVVVV=======
        *==========VVVVVVVVVVVV==========
        *=============VVVVVV=============
        *===============VV===============
        */
        // used this.population AND use fitness
        
        
        
        
        
        
        // this.newPopulation = SomeThing
    }
    
    // This function used for crossover for new population in next generation
    public void crossover() {
        ArrayList<Path> newGen = new ArrayList<Path>();
        City initCity =  new City(-100, -100, -100);
        int popCross = 0;
        int point1, point2, index;
        int time = 0; // for print
        int maxTime = newPopulation.size() / 2;
        while(time < maxTime) {
            Path P1 = new Path(); // Parent Path
            Path P2 = new Path(); // Parent Path
            Path C1 = new Path(); // Child Path
            Path C2 = new Path(); // Child Path
            point1 = 0; 
            point2 = 0; 
            index = 0;
            println("Crossover " + ++time);
            P1 = newPopulation.get((int) (Math.random() * newPopulation.size()));
            P2 = newPopulation.get((int) (Math.random() * newPopulation.size()));
            println("P1 : " + P1.toString());
            println("P2 : " + P2.toString());
            popCross = (int) (Math.random() * 100); // 0 - 99
            println("Pop = " + popCross);
            if(popCross > 79) { // not Crossover ///////////////////////////////////////////////
                newGen.add(P1);
                newGen.add(P2);
                println("C1 : " + P1);
                println("C2 : " + P2);
            } else { // Crossover
                while(point1 == point2) { // random point for Crossover
                    point1 = (int) (Math.random() * P1.path.size());
                    point2 = (int) (Math.random() * P1.path.size());
                }
                if(point1 > point2) { // point 1 right, point 2 left. want point 1 left, point 2 right
                    int point = point1;
                    point1 = point2;
                    point2 = point;
                }
                println("Point1 = " + point1);
                println("Point2 = " + point2);
                for(int i = 0; i < P1.path.size(); i++) {
                    C1.path.add(initCity);
                    C2.path.add(initCity);
                }
                for(int i = point1; i <= point2; i++) {
                    C1.path.remove(i);
                    C1.path.add(i, P2.path.get(i));
                    C2.path.remove(i);
                    C2.path.add(i, P1.path.get(i));
                }
                while(C1.path.indexOf(initCity) != -1) { // true when have init city
                    index = C1.path.indexOf(initCity);
                    for(int i = 0; i < cities.size(); i++) {
                        if(C1.path.indexOf(cities.get(i)) == -1) { // No have city
                            C1.path.remove(index);
                            C1.path.add(index, cities.get(i));
                        }
                    }
                }
                while(C2.path.indexOf(initCity) != -1) { // true when have init city
                    index = C2.path.indexOf(initCity);
                    for(int i = 0; i < cities.size(); i++) {
                        if(C2.path.indexOf(cities.get(i)) == -1) { // No have city
                            C2.path.remove(index);
                            C2.path.add(index, cities.get(i));
                        }
                    }
                }
                newGen.add(C1);
                newGen.add(C2);
                println("C1 : " + C1);
                println("C2 : " + C2);
            }
        }
        newPopulation = newGen;
        
        // 1. use this.newPopulation for crossover
        // 2. random Probability value for crossover
        // 3. random 2 position for PMX crossover
        // Note: Not Forget create new Path Object in this.newPopulation
        // this.newPopulation = SomeThing
    }
    
    // This function used for mutation for new population in next generation
    public void mutation(){
        //------WRITE--CODE--BELOW-------
        /*====VVVVVVVVVVVVVVVVVVVVVVV====
        *=======VVVVVVVVVVVVVVVVVV=======
        *==========VVVVVVVVVVVV==========
        *=============VVVVVV=============
        *===============VV===============
        */
        // 1. use this.newPopulation fot mutation
        // 2. random Probability value for mutation
        // 3. random 2 position fot Inversion mutation
        
        
        
        
        // this.newPopulation = SomeThing
    }
    
    // This function used for update population in next generation
    public void updatePop(){
        this.population.clear();
        this.population = this.newPopulation;
    }
    
    public double calCmax(){
        double Cmax=0;                  // create variable for output of function
        for(Path p:this.population){    // each path in population
            p.calDistance();            // calculate distance of each path
            if(p.distance > Cmax) Cmax = p.distance;    // update Cmax value
        }
        return Cmax;    // return Cmax value
    }
    
    public void createCity(ArrayList<ArrayList> datas){
        for(int i=0; i<datas.size(); i++){
            double x,y;
            x = (double)datas.get(i).get(0);    // get x from datas file
            y = (double)datas.get(i).get(1);    // get y from datas file
            this.cities.add(new City(i, x, y)); // add each City into cities ArrayList
        }
    }
    
    public static void println(Object obj){
        System.out.println(obj.toString());
    }
    
    public static void print(Object obj){
        System.out.print(obj.toString());
    }
    
    public static void main(String[] args) {
        /*GA test = new GA();
        for(int i = 0; i < 8; i++)
            test.cities.add(new City(i, 12, 12));
        test.initPopulation();
        for(Path P:test.population) {
            println(P);
        } */
        Path travel;
        GA test = new GA();
        int numCity = 8, numPath = 20; 
        for(int i = 0; i < numCity; i++) {
            City newCity = new City(i, 12, 12);
            test.cities.add(newCity);
        }
        for(int i = 0; i < numPath; i++) {
            travel = new Path();
            travel.createChromosome(test.cities);
            test.newPopulation.add(travel);
        }
        
        println("New Population");
        for(int i = 0; i < test.newPopulation.size(); i++) {
            println("Population " + i + " : " + test.newPopulation.get(i).toString());
        }
        println("Crossover");
        test.crossover();
        println("New Population Next Generation");
        for(int i = 0; i < test.newPopulation.size(); i++) {
            println("Population " + i + " : " + test.newPopulation.get(i).toString());
        }
    }
    
}















