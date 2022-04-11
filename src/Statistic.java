import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Statistic {
    private final Hashtable<String, DataClass> classTable;
    private final DataClass counter;
    private final int numberForTraining;
    private final Hashtable<String, Double> priorProbability = new Hashtable<>();

    public Statistic(Hashtable<String, DataClass> classTable, DataClass counter, int numberForTraining) {
        this.classTable = classTable;
        this.counter = counter;
        this.numberForTraining = numberForTraining;
        calculatePriorProbability();
    }

    private void calculatePriorProbability() {
        Set<String> classNames = classTable.keySet();
        for(String className : classNames){
            priorProbability.put(className, calculatePriorProbabilityOf(className));
        }
    }

    private double calculatePriorProbabilityOf(String className) {
        double counterOfClass = classTable.get(className).getCounterOfClass();
        return counterOfClass / numberForTraining;
    }

    public double getPriorProbabilityOf(String className) {
        return priorProbability.get(className);
    }

    public double getProbabilityOf(String className, int indexOfAttribute, String valueOfAttribute) {
        double counterOfAttributeForClass = classTable.get(className).getCounterOf(indexOfAttribute, valueOfAttribute);
        double counterOfInstancesOfThisValueOfAttribute = counter.getCounterOf(indexOfAttribute, valueOfAttribute);
        return counterOfAttributeForClass / counterOfInstancesOfThisValueOfAttribute;
    }

    public void printStatistic(){
        Set<String> classNames = classTable.keySet();
        System.out.println();
        for(String className : classNames){
            DataClass dataClass = classTable.get(className);
            String probability = String.format("%.3f", (getPriorProbabilityOf(className) * 100));
            System.out.println("Class name: " + className + " has " + probability
            + "% priorProbability. Class has " + dataClass.getCounterOfClass() + " instances");
            ArrayList<Hashtable<String, Integer>> attributes = dataClass.getAttributes();
            for (int i = 0; i < attributes.size(); i++) {
                System.out.println("Attribute index is " + i);
                Hashtable<String, Integer> attribute =  attributes.get(i);
                Set<String> instancesOfAttribute = attribute.keySet();
                for(String nameOfAttribute : instancesOfAttribute){
                    int number = dataClass.getCounterOf(i, nameOfAttribute);
                    System.out.print(nameOfAttribute + " = " + number + "; ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    public Hashtable<String, DataClass> getClassTable() {
        return classTable;
    }

    public int getNumberForTraining() {
        return numberForTraining;
    }
}
