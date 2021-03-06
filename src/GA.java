
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
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
        for(int i = 0; i < 100; i++) {
            Path temp = new Path();             // create temporarily Path Object
            temp.createChromosome(this.cities); // create a Chromosome as Path
            temp.calDistance();                 // calculate ditance of each path
            this.population.add(temp);          // add Path into a population
        }
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
        int N = this.population.size();
        if(N == 0 || N == 1 || N ==2 || N==3){
            return;}
        // cal sum of fitness
        ArrayList<Path> keep = new ArrayList<Path>();
        ArrayList<Double> allFitness = new ArrayList<Double>();
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
        if (keep.size()%2 != 0){
            for(int i=0; i<allFitness.size(); i++){
                if(allFitness.get(i) == Collections.min(allFitness)){
                    keep.remove(i);
                }
            }
        }
        this.newPopulation = keep;
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
            time++;
            P1 = newPopulation.get((int) (Math.random() * newPopulation.size()));   // random parent 1 from population
            P2 = newPopulation.get((int) (Math.random() * newPopulation.size()));   // random parent 2 from population
            popCross = (int) (Math.random() * 100); // random probability value    range of probability value 0 - 99
            if(popCross > 79) { // not crossover
                newGen.add(P1); // add parent 1 in new generation
                newGen.add(P2); // add parent 2 in new generation
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
        for (int i = 0 ; i < newPopulation.size() ; i++){
            Ran = Math.random();                        // Random math for ค่าโอกาศเกิด                 
            if (Ran <= 0.1){                            
                onePath = newPopulation.get(i);    // Add newPopulation into onePath
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
                newPopulation.set(i, onePath);     // Add path back to population
            }
        }
    }
    
    // This function used for update population in next generation
    public void updatePop(){
        if(this.newPopulation.size() > 0){
            this.population = new ArrayList<Path>();
            for(Path p:this.newPopulation){
                this.population.add(p);
            }
            this.newPopulation = new ArrayList<Path>();
        }
    }
    
    // This function used for calculate average distance of population
    public double avgDistance(){
        if(this.population.size() > 0){
            double out = 0;
            for(Path p:this.population){
                p.calDistance();    // calculate distance of each path
                out += p.distance;  // sum all distance
            }
            return out/this.population.size();  // calculate average of distance
        }else{
            return 0.0;
        }
    }
    
    private double calCmax(){
        return Integer.MAX_VALUE;    // return Cmax value
    }
    
    // This function used for create all City object from datas that read from a TSP file
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
    
    // This function used for find a path that is minimum distance in current population
    public Path getBestPath(){
        if(this.population.size() > 0){
            Path out = this.population.get(0);
            for(Path p:this.population){
                p.calDistance();            // calculate distance of path
                out = out.minDistance(p);   // find path is minimum distance
            }
            return out;
        }else{
            return null;
        }
    }
    
}
















