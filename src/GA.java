
import java.util.ArrayList;
import java.util.Random;

public class GA {
    public ArrayList<City> cities;          // for keep all City Objects
    public ArrayList<Path> population;      // for keep population of current generation
    public ArrayList<Path> newPopulation;   // for keep new population in next generation
    public Path bestPath;                   // fot keep the best Path
    public Path goodPath;                   // for keep good path in current generation
    
    public GA(){
        this.cities = new ArrayList<City>();
        this.population = new ArrayList<Path>();
        this.newPopulation = new ArrayList<Path>();
        this.bestPath = new Path();
        this.goodPath = new Path();
    }
    
    // This function used for create initial population
    public void initPopulation() {
        for(int i = 0; i < this.cities.size(); i++) {
            Path temp = new Path();             // create temporarily Path Object
            temp.createChromosome(this.cities); // create a Chromosome as Path
            temp.calDistance();                 // calculate ditance of each path
            this.population.add(temp);          // add Path into a population
        }
        this.findBestPath();                    // find the best Path
    }
    
    // This function used for selection new population in next generation
    public void select(){
        //------WRITE--CODE--BELOW-------
        /*/
           ---value description---
        F       is sum of fitness each object in population.
        N       is number of population.
        P       is F/N that distance between point.
        keep    is new population from selection.
        start   is start point in roulette wheel.
        allFitness is used to save all fitness for each population.
        /*/
        // used this.population AND use fitness
        
        double F=0;
        // cal sum of fitness
        int N = this.population.size();
        double P = F/N;
        double start = new Random().nextInt((int)F);
        ArrayList<Path> keep = new ArrayList<>();
        ArrayList<Double> allFitness = new ArrayList<>();
        double cmax = this.calCmax();  // generate Cmax for calulate fitness.
        // add all fitness to allFitness value.
        for(Path p:this.population){  // access each path from population.
            p.calFitness(cmax);    // calulate fitness.
            F += p.fitness; 
            allFitness.add(p.fitness);
        }
        
        // make wheel
        /*/
              ---value description---
            wheel    is roulette wheel that has each channal's width from fitness.
            brink    is used to be base of wheel each fitness.
            dataWheel consist { start, end, index }
                    start - end = channal's width from fitness.
                    index is population's index.
                Sumation of all fitness from allFitness is total area of roulette wheel.
        Each population has own fitness. The first population has range in wheel between
        0 to its fitness. The secound population has range in wheel between 
        0+population's fitenss from previous population to secound population's fitness
        + population's fitenss from previous population. Assume that each chanal in wheel
        equal between population's fitenss from previous+base and base +
        current population's fitenss then roulette wheel will complete.
        /*/
        ArrayList<ArrayList<Double>> wheel = new ArrayList<>();
        ArrayList<Double> dataWheel = new ArrayList<>();
        double brink=0;
        for(int i=0; i<allFitness.size(); i++){
            dataWheel.add(brink);
            dataWheel.add(allFitness.get(i) + brink);
            dataWheel.add((double)i);
            wheel.add(dataWheel); // add data to wheel
            brink = allFitness.get(i)+brink+ 0.01;
            dataWheel = new ArrayList<>(); // clear data in dataWheel
        }
        
        /*/
            Deviding to be quarant for reduce chanal to search new population.
        step1 : We get start point from random then we calulate that whice quadrant
                does start point is in.
        step2 : Search population in quarant in step1 and select population.
        stop3 : keep new population to this.newpopulation.
        /*/
        for(int i=0; i<allFitness.size(); i++){
            int quadrant = (int)(start/(F/4));
            int start_search, end_search;   
            switch (quadrant){ 
                case 0:{ 
                    start_search = 0;
                    end_search = population.size()/4;
                    for(int j=start_search; j<=end_search; j++){ 
                        if ( (start < wheel.get(j).get(1)) && (start > wheel.get(j).get(0)) )
                            keep.add(this.population.get( wheel.get(j).get(2).intValue()));

                    }
                    break;
                }
                case 1:{ 
                    start_search = population.size()/4;
                    end_search = population.size()*2/4;
                    for(int j=start_search; j<=end_search; j++){ 
                        if ( (start < wheel.get(j).get(1)) && (start > wheel.get(j).get(0)) )
                            keep.add(this.population.get( wheel.get(j).get(2).intValue()));
                    }
                    break;
                }
                case 2:{ 
                    start_search = population.size()*2/4;
                    end_search = population.size()*3/4;
                    for(int j=start_search; j<=end_search; j++){ 
                        if ( (start < wheel.get(j).get(1)) && (start > wheel.get(j).get(0)) )
                            keep.add(this.population.get( wheel.get(j).get(2).intValue()));
                    }
                    break;
                }
                case 3:{  
                    start_search = population.size()*3/4;
                    end_search = population.size()-1;
                    for(int j=start_search; j<=end_search; j++){ 
                        if ( (start < wheel.get(j).get(1)) && (start > wheel.get(j).get(0)) )
                            keep.add(this.population.get( wheel.get(j).get(2).intValue()));
                    }
                    break;
                }
            }
            start=(start+(P))%F;
        }
        this.newPopulation = keep;
    }
    
    // This function used for crossover for new population in next generation
    public void crossover(){
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
        *==========VVVVVVVVVVVV==========
        *=============VVVVVV=============
        *===============VV===============
        */
        // 1. use this.newPopulation fot mutation
        // 2. random Probability value for mutation
        // 3. random 2 position fot Inversion mutation
        ArrayList<City> keep = new ArrayList<City>();   // create Object for save city from Path
        Path onePath = new Path();                      // create Object for save population from newPopulation
        int point1 = 0, point2 = 0;                     // create Object for pointer point where will flip
        double Ran = 0;                                 // create Object for save MATH.RANDOM()
        for (int i = 0 ; i < this.newPopulation.size() ; i++){
            Ran = Math.random();                        // Random math for ค่าโอกาศเกิด
            println ("prop "+Ran);                      
            if (Ran <= 0.1){                            
                onePath = this.newPopulation.get(i);    // Add newPopulation into onePath
                while(point1 == point2) {               // Check same pointer (at first 0 = 0)
                    point1 = (int) (Math.random() * onePath.path.size()); // random pointer for point1
                    point2 = (int) (Math.random() * onePath.path.size()); // random pointer for point2
                }
                if ( point1 > point2 ){                 // Check point where more than
                    int point = point1;                 // set point1 to point
                    point1 = point2;                    // set point2 to point1
                    point2 = point;                     // set point  to point2
                }
                keep = onePath.path;                    // add city from path to keep
                int mutain =  (int)(((point2-point1+1)/2));       // cal how many round to swap
                for( int j = point1 ; j < point1+mutain ; j++ ){  // loop and plus front pointer        
                    City swap = keep.get(j);            // Add front city into swap
                    keep.set(j, keep.get(point2));      // Add back  city into front city
                    keep.set(point2, swap);             // Add swap       into back city
                    point2 = point2 - 1 ;               // minus back pointer 
                }
                onePath.path = keep;                    // Add city back to path
                this.newPopulation.set(i, onePath);     // Add path back to population
            }
            
            
        }
        
        
        // this.newPopulation = SomeThing
    }
    
    // This function used for update population in next generation
    public void updatePop(){
        this.population.clear();
        this.population = this.newPopulation;
    }
    
    public void findBestPath(){
        
    }
    
    private double calCmax(){
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
        
        /// KUNG \\\
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
        
        //// BOOK \\\
        GA test2 = new GA();
        for(int i = 0; i < 8; i++)
            test2.cities.add(new City(i, 12, 12));
        test2.initPopulation();
        println("Test2.1 : "+test2.population.get(0));
        println("Test2.2 : "+test2.population.get(1));
        test2.newPopulation.add(test2.population.get(0));
        test2.newPopulation.add(test2.population.get(1));
        test2.mutation();
        for(Path p:test2.newPopulation){
            println (p) ;
        }
    }
    
}
















