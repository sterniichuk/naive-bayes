import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Training {
    private final Hashtable<String, DataClass> classTable = new Hashtable<>();
    private final int indexOfClass;
    private final DataClass counter;
    private final Statistic statistic;

    public Training(ArrayList<String[]> data, double percentForTraining) {
        final int numberForTraining = (int) (percentForTraining * 0.01 * data.size());
        indexOfClass = data.get(0).length - 1;
        counter = new DataClass("counter for statistic", indexOfClass);
        for (int i = 0; i < numberForTraining; ++i) {
            count(data.get(i));
        }
        statistic = new Statistic(classTable, counter, numberForTraining);
        addBlackBoxes();
    }

    public Statistic getStatistic() {
        return statistic;
    }

    private void addBlackBoxes() {
        Set<String> classNames = classTable.keySet();
        for (String className : classNames) {
            DataClass dataClass = classTable.get(className);
            ArrayList<Hashtable<String, Integer>> attributes = counter.getAttributes();
            for (int i = 0; i < attributes.size(); i++) {
                Hashtable<String, Integer> attribute = attributes.get(i);
                Set<String> instancesOfAttribute = attribute.keySet();
                for (String nameOfAttribute : instancesOfAttribute) {
                    dataClass.increase(i, nameOfAttribute);
                }
            }
        }
    }

    private void count(String[] instance) {
        String className = instance[indexOfClass];
        DataClass dataClass = getClass(className);
        dataClass.count(instance);
        counter.count(instance);
    }

    private DataClass getClass(String className) {
        if (classTable.containsKey(className)) {
            return classTable.get(className);
        }
        DataClass newClass = new DataClass(className, indexOfClass);
        classTable.put(className, newClass);
        return newClass;
    }

}
