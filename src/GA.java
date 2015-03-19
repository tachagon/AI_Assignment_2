
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
        /*====VVVVVVVVVVVVVVVVVVVVVVV====
        *==========VVVVVVVVVVVV==========
        *=============VVVVVV=============
        *===============VV===============
        */
        // used this.population AND use fitness
        
        double F=0;
        // cal sum of fitness
        int N = this.population.size();
        double P = F/N;
        double start = new Random().nextInt((int)F);
        ArrayList<Path> keep = new ArrayList<>();
        ArrayList<Double> allFitness = new ArrayList<>();
        // add all fitness to allFitness value.
        for(Path p:this.population){
            p.calFitness(start);
        }
        
        // make wheel
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
        
        for(int i=0; i<allFitness.size(); i++){
            int quadrant = (int)(start/(F/4));
            int start_search, end_search; System.out.println(quadrant);     double in;
            switch (quadrant){ 
                case 0:{ 
                    start_search = 0;
                    end_search = population.size()/4;
                    for(int j=start_search; j<=end_search; j++){ System.out.println("0");
                        if ( (start < wheel.get(j).get(1)) && (start > wheel.get(j).get(0)) )
                            keep.add(this.population.get( wheel.get(j).get(2).intValue()));

                    }
                    break;
                }
                case 1:{ 
                    start_search = population.size()/4;
                    end_search = population.size()*2/4;
                    for(int j=start_search; j<=end_search; j++){ System.out.println("1");
                        if ( (start < wheel.get(j).get(1)) && (start > wheel.get(j).get(0)) )
                            keep.add(this.population.get( wheel.get(j).get(2).intValue()));
                    }
                    break;
                }
                case 2:{ 
                    start_search = population.size()*2/4;
                    end_search = population.size()*3/4;
                    for(int j=start_search; j<=end_search; j++){ System.out.println("2");
                        if ( (start < wheel.get(j).get(1)) && (start > wheel.get(j).get(0)) )
                            keep.add(this.population.get( wheel.get(j).get(2).intValue()));
                    }
                    break;
                }
                case 3:{  
                    start_search = population.size()*3/4;
                    end_search = population.size()-1;
                    for(int j=start_search; j<=end_search; j++){ System.out.println("3");
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
        ArrayList<City> change1 = new ArrayList<City>();
        ArrayList<City> change2 = new ArrayList<City>();
        Path P1 = new Path();
        Path P2 = new Path();
        Path C1 = new Path();
        Path C2 = new Path();
        int popCross = 0;
        int point1 = 0, point2 = 0;
        while(true) {
            P1 = newPopulation.get((int) (Math.random() * newPopulation.size()));
            P2 = newPopulation.get((int) (Math.random() * newPopulation.size()));
            popCross = (int) (Math.random() * 11); // 0 - 10
            if(popCross > 11) { // not Crossover ///////////////////////////////////////////////
                newGen.add(P1);
                newGen.add(P2);
            } else { // Crossover
                while(point1 == point2) {
                    point1 = (int) (Math.random() * P1.path.size());
                    point2 = (int) (Math.random() * P1.path.size());
                }
                if(point1 > point2) { // point 1 right, point 2 left. want point 1 left, point 2 right
                    int point = point1;
                    point1 = point2;
                    point2 = point;
                }
                for(int i = point1; i < point2+1; i++) {
                    change1.add(P1.path.get(i));
                    change2.add(P2.path.get(i));
                }
                for(int i = 0; i < P1.path.size(); i++) {
                    
                }
                newGen.add(C1);
                newGen.add(C2);
            }
            if(newGen.size() == newPopulation.size())
                break;
        }
        newPopulation = newGen;
        //------WRITE--CODE--BELOW-------
        /*====VVVVVVVVVVVVVVVVVVVVVVV====
        *==========VVVVVVVVVVVV==========
        *=============VVVVVV=============
        *===============VV===============
        */
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
        Path path1 = new Path();
        Path path2 = new Path();
        ArrayList<City> newCity1 = new ArrayList<City>();
        GA test = new GA();
        for(int i = 0; i < 8; i++) {
            newCity1.add(new City(i, 12, 12));
        }
        path1.createChromosome(newCity1);
        path2.createChromosome(newCity1);
        test.newPopulation.add(path1);
        test.newPopulation.add(path2);
        println("P1 : " + test.newPopulation.get(0));
        println("P2 : " + test.newPopulation.get(1));
        test.crossover();
        println("Crossover");
        println("C1 : " + test.newPopulation.get(0));
        println("C2 : " + test.newPopulation.get(1));
        
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
















