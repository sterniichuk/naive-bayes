import java.util.*;

public class Testing {
    private final Statistic statistic;
    private final ArrayList<String[]> data;
    private final int indexOfClass;
    private int counterOfCorrectClassification = 0;
    private final Set<String> classNames;
    private final Hashtable<String, Integer> correctClassifiedClass = new Hashtable<>();
    private final Hashtable<String, Integer> allClassifiedClass = new Hashtable<>();

    public Testing(Statistic statistic, ArrayList<String[]> data) {
        this.statistic = statistic;
        final int numberForLearning = statistic.getNumberForTraining();
        this.data = data;
        this.indexOfClass = data.get(0).length - 1;
        classNames = statistic.getClassTable().keySet();
        for (int i = numberForLearning; i < data.size(); i++) {
            test(data.get(i));
        }
    }

    private void test(String[] instance) {
        TreeMap<Double, String> results = new TreeMap<>((new MyComparator()));
        for (String className : classNames) {
            double probability = classProbability(className, instance);
            results.put(probability, className);
        }
        String possibleClass = results.get(results.firstKey());
        String realClassName = instance[indexOfClass];
        boolean isCorrectClassification = realClassName.equals(possibleClass);
        setResultOfClassClassification(realClassName, isCorrectClassification);
    }

    private void setResultOfClassClassification(String className, boolean isCorrectlyClassified) {
        Integer number;
        if (isCorrectlyClassified) {
            counterOfCorrectClassification++;
            number = correctClassifiedClass.get(className);
            if (number == null) {
                number = 0;
            }
            correctClassifiedClass.put(className, number + 1);
        }
        number = allClassifiedClass.get(className);
        if (number == null) {
            number = 0;
        }
        allClassifiedClass.put(className, number + 1);
    }

    private double getPercentOfCorrectClassClassification(String className) {
        Integer correct = correctClassifiedClass.get(className);
        if(correct == null){
            return 0;
        }
        double all = allClassifiedClass.get(className);
        return (correct / all) * 100;
    }

    private double classProbability(String className, String[] instance) {
        double probability = statistic.getPriorProbabilityOf(className);
        for (int i = 0; i < indexOfClass; i++) {
            String attribute = instance[i];
            double attributeProbability = statistic.getProbabilityOf(className, i, attribute);
            probability *= attributeProbability;
        }
        return probability;
    }

    private static class MyComparator implements Comparator<Double> {
        @Override
        public int compare(Double o1, Double o2) {
            if (o1 > o2) {
                return -1;
            } else if (o1 < o2) {
                return 1;
            }
            return 0;
        }
    }

    public double getPercentOfCorrectClassification() {
        double correct = counterOfCorrectClassification;
        double all = data.size();
        return (correct / all) * 100;
    }

    public String results() {
        String percent = String.format("%.3f", (getPercentOfCorrectClassification()));
        String result = "Naive Bayes algorithm has correctly classified " + percent + "% of test data set\n";
        for(String className : classNames){
            percent = String.format("%.3f", getPercentOfCorrectClassClassification(className));
            String str = className + " is correctly classified " + percent + "%\n";
            result = result.concat(str);
        }
        return result;
    }

    public void printResult() {
        System.out.println(results());
    }

}

