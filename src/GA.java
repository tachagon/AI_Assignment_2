
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
        ArrayList<Path> keep = new ArrayList<>();
        ArrayList<Double> allFitness = new ArrayList<>();
        double cmax = this.calCmax();  // generate Cmax for calulate fitness.
        // add all fitness to allFitness value.
        for(Path p:this.population){  // access each path from population.
            p.calFitness(cmax);    // calulate fitness.
            F += p.fitness; 
            allFitness.add(p.fitness);
        } 
        double start = new Random().nextInt((int)F);
        double P = F/N;
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
        int quadrant = -1;
        for(int i=0; i<allFitness.size(); i++){  
            if (quadrant == -1) {
                quadrant = (int)(start/(F/4)); 
            }
            int start_search, end_search;   
            switch (quadrant){ 
                case 0:{ 
                    start_search = 0;
                    end_search = population.size()/4;
                    for(int j=start_search; j<=end_search; j++){
                        if ( (start < wheel.get(j).get(1)) && (start > wheel.get(j).get(0)) ){
                            keep.add(this.population.get( wheel.get(j).get(2).intValue()));
                            start=(start+(P))%F;
                            quadrant = -1;
                            break;
                        }
                        else if (j==end_search){
                            i -= 1;
                            if ( start < wheel.get(j).get(1) ){
                                quadrant = 3;
                            }
                            else{
                                quadrant = 1;
                            }
                        }
                    }
                    break;
                }
                case 1:{ 
                    start_search = population.size()/4;
                    end_search = population.size()*2/4;
                    for(int j=start_search; j<=end_search; j++){ 
                        if ( (start < wheel.get(j).get(1)) && (start > wheel.get(j).get(0)) ){
                            keep.add(this.population.get( wheel.get(j).get(2).intValue()));
                            start=(start+(P))%F;
                            quadrant = -1;
                            break;
                        }
                        else if (j==end_search){
                            i -= 1;
                            if ( start < wheel.get(j).get(1) ){
                                quadrant = 0;
                            }
                            else{
                                quadrant = 2;
                            }
                        }
                    }
                    break;
                }
                case 2:{ 
                    start_search = population.size()*2/4;
                    end_search = population.size()*3/4;
                    for(int j=start_search; j<=end_search; j++){ 
                        if ( (start < wheel.get(j).get(1)) && (start > wheel.get(j).get(0)) ){
                            keep.add(this.population.get( wheel.get(j).get(2).intValue())); 
                            start=(start+(P))%F;
                            quadrant = -1;
                            break;
                        }
                        else if (j==end_search){
                            i -= 1;
                            if ( start < wheel.get(j).get(1) ){
                                quadrant = 1;
                            }
                            else{
                                quadrant = 3;
                            }
                        }                        
                    }
                    break;
                }
                case 3:{  
                    start_search = population.size()*3/4;
                    end_search = population.size()-1;
                    for(int j=start_search; j<=end_search; j++){ 
                        if ( (start < wheel.get(j).get(1)) && (start > wheel.get(j).get(0)) ){
                            keep.add(this.population.get( wheel.get(j).get(2).intValue())); 
                            start=(start+(P))%F;
                            quadrant = -1;
                            break;
                        }
                        else if (j==end_search){
                            i -= 1;
                            if ( start < wheel.get(j).get(1) ){
                                quadrant = 2;
                            }
                            else{
                                quadrant = 1;
                            }
                        }                        
                    }
                   break;
                }
            }
            //start=(start+(P))%F;
        }
        this.newPopulation = keep;
        println("Fitness size : "+ allFitness.size());
    }
    
    // This function for crossover form PMX  for new population in next generation
        // 1. use this.newPopulation for crossover
        // 2. random Probability value for crossover
        // 3. random 2 position for PMX crossover
    public void crossover() {
        ArrayList<Path> newGen = new ArrayList<Path>(); // store new population from crossover
        City initCity =  new City(-100, -100, -100);    // default city
        int popCross = 0;   // store probability value for crossover
        // int point1, point2 store point 1, 2 for PMX crossover
        // int index for tell position of city in path
        int point1, point2, index;
        int time = 0;   // store times for crossover
        int maxTime = newPopulation.size() / 2; // maximum times for crossover = half of all population
        while(time < maxTime) { // operate in while loop when times for crossover < maximum times
            Path P1 = new Path(); // store Parent 1 for crossover
            Path P2 = new Path(); // store Parent 2 for crossover
            Path C1 = new Path(); // store Child 1 from crossover
            Path C2 = new Path(); // store Child 2 from crossover
            point1 = 0; // reset point 1
            point2 = 0; // reset point 2
            index = 0;  // reset index
            println("Crossover " + ++time); // display times for crossover
            P1 = newPopulation.get((int) (Math.random() * newPopulation.size()));   // random parent 1 from population
            P2 = newPopulation.get((int) (Math.random() * newPopulation.size()));   // random parent 2 from population
            println("P1 : " + P1.toString());   // display parent 1
            println("P2 : " + P2.toString());   // display parent 2
            popCross = (int) (Math.random() * 100); // random probability value    range of probability value 0 - 99
            println("Pop = " + popCross);   // display probability value
            if(popCross > 79) { // not crossover
                newGen.add(P1); // add parent 1 in new generation
                newGen.add(P2); // add parent 2 in new generation
                println("C1 : " + P1);  // display parent 1 that add in new generation
                println("C2 : " + P2);  // display parent 2 that add in new generation
            } else { // crossover
                while(point1 == point2) { // random point for PMX crossover
                    point1 = (int) (Math.random() * P1.path.size());    // random point 1 for PMX crossover
                    point2 = (int) (Math.random() * P1.path.size());    // random point 2 for PMX crossover
                }
                if(point1 > point2) { // point 1 right, point 2 left. want point 1 left, point 2 right
                    int point = point1; // store point 1
                    point1 = point2;    // swap value of point 1 with point 2
                    point2 = point;
                }
                println("Point1 = " + point1);  // display point 1 for PMX crossover
                println("Point2 = " + point2);  // display point 2 for PMX crossover
                for(int i = 0; i < P1.path.size(); i++) {   // add default city in child 1, 2
                    C1.path.add(initCity);  // add default city in child 1
                    C2.path.add(initCity);  // add default city in child 2
                }
                for(int i = point1; i <= point2; i++) { // swap city in parent 1 and parent 2 in range of point 1 with point 2
                    C1.path.remove(i);  // remove city at i of child 1
                    C1.path.add(i, P2.path.get(i)); // add city at i of child 1
                    C2.path.remove(i);  // remove city at i of child 2
                    C2.path.add(i, P1.path.get(i)); // add city at i of child 2
                }
                while(C1.path.indexOf(initCity) != -1) { // operate in while loop when have default city
                    index = C1.path.indexOf(initCity);  // find index of default city
                    for(int i = 0; i < cities.size(); i++) {    // find new city
                        if(C1.path.indexOf(cities.get(i)) == -1) {  // No have new city in child 1
                            C1.path.remove(index);  // remove default city at index
                            C1.path.add(index, cities.get(i));  // add new city at index 
                        }
                    }
                }
                while(C2.path.indexOf(initCity) != -1) { // operate in while loop when have default city
                    index = C2.path.indexOf(initCity);  // find index of default city
                    for(int i = 0; i < cities.size(); i++) {    // find new city
                        if(C2.path.indexOf(cities.get(i)) == -1) {  // No have new city in child 2
                            C2.path.remove(index);  // remove default city at index
                            C2.path.add(index, cities.get(i));  // add new city at index 
                        }
                    }
                }
                newGen.add(C1); // add child 1 in new generation
                newGen.add(C2); // add child 2 in new generation
                println("C1 : " + C1);  // display child 1 that add in new generation
                println("C2 : " + C2);  // display child 2 that add in new generation
            }
        }
        newPopulation = newGen; // store new generation from crossover in newPopulation
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
        double keep = 0.0;                                  // create object for keep path for check 
        for(int i = 0 ; i < this.population.size() ; i++){  // check every path in population
            this.population.get(i).calDistance();           // calculate distance
            println("Distance "+i+" "+this.population.get(i).distance);
            if(keep == 0.0){                                // check keep is empty or not
                keep = this.population.get(i).distance ;    // get path into keep
                this.goodPath = this.population.get(i);     // get pop to goodpath
            }
            else{
                println("keep dis "+i+" "+keep);            
                if(this.population.get(i).distance < keep){ // check path for check is more than new path 
                    keep = this.population.get(i).distance ;// get path into keep
                    this.goodPath = this.population.get(i); // get pop to goodpath
                }
            }
        }
        this.goodPath.calDistance();                        // calculate distance of goodpath
        this.bestPath.calDistance();                        // calculate distance of bestpath
        if(this.bestPath.distance <= 0){                    // checkbestpath is empty
            this.bestPath = this.goodPath;                  // get good path into bestpath
        }
        if(this.bestPath.distance > this.goodPath.distance){// check bestpath more than goodpath or not
            this.bestPath = this.goodPath;                  // get good path into bestpath
        }
        // for Test check goodpath bestpath ... 
        this.goodPath.calDistance();                                
        this.bestPath.calDistance();                                
        println("BestPaht is "+this.bestPath.distance);             
        println("GoodPath is "+this.goodPath.distance);            
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
        int numCity = 8, numPath = 6; 
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
            test2.cities.add(new City(i, i, i*2));
        test2.initPopulation();
        println("Test2.1 : "+test2.population.get(0));
        println("Test2.2 : "+test2.population.get(1));
        println("Test2.3 : "+test2.population.get(2));
        println("Test2.4 : "+test2.population.get(3));
        test2.newPopulation.add(test2.population.get(0));
        test2.newPopulation.add(test2.population.get(1));
        test2.newPopulation.add(test2.population.get(2));
        test2.newPopulation.add(test2.population.get(3));
        test2.mutation();
        for(Path p:test2.newPopulation){
            println (p) ;
        }
        /// Boss \\\
        GA test3 = new GA();
        int position[][] = {{1,2}, {30,5}, {24,46}, {34, 56}, {84,24}, {53, 24}, {36, 43}, { 23, 41}, {63,13}, {63,13} ,{32, 43}, {39,90}};
        for(int i = 0; i < position.length; i++)
            test3.cities.add(new City(i, position[i][0], position[i][1]));
        test3.initPopulation();
        println("++++++ Test3 : Boss selection ++++++" );
        test3.select();
        println("==Old population==");
        for(Path p:test3.population){
            println(p);
        }
        println("==New population==");
        for(Path v:test3.newPopulation){
            println(v);
        }
        println("Number of new population: "+test3.newPopulation.size());
    }
    
}
















