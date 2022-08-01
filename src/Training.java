import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.stream.IntStream;

public class Training {
    private final Hashtable<String, DataClass> classTable = new Hashtable<>();
    private final int indexOfClass;
    private final DataClass counter;
    private final Statistic statistic;

    public Training(ArrayList<String[]> data, double percentForTraining) {
        /* ArrayList<String[]> data - структура даних що містить в собі String[]
         * String[] - один рядок з файлу .csv. Наприклад:
         * data[0] це рядок: high,low,4,more,med,med,acc
         */
        final int numberForTraining = (int) (percentForTraining * 0.01 * data.size());
        // numberForTraining - кількість екземплярів обрана для тренування
        indexOfClass = data.get(0).length - 1;
        /*indexOfClass - це індекс класу.
         * Тобто у рядку "high,low,4,more,med,med,acc" під індексом 6 буде значення "acc"
         */
        counter = new DataClass("counter for statistic", indexOfClass);
        /* counter - клас потрібний нам для статистики.
         * Він запам'ятовує кількість усіх входжень кожного значення кожного атрибута.
         * Тобто якщо клас "acc" в атрибуті "Ціна покупки" має значення high 392 рази,
         * а клас "vgood" в атрибуті "Ціна покупки" має значення high лише 1 раз.
         * Тоді клас counter матиме в атрибуті "Ціна покупки" має
         * значення high 393 рази - бо це сума всіх випадків появи значення "high" для атрибута "Ціна покупки"
         * І при тестуванні ймовірність, що "high" належить до класу "acc" буде 392/393, а для
         * класу "vgood" лише 1/393
         * */
        IntStream.range(0, numberForTraining).forEach(i -> count(data.get(i)));
        statistic = new Statistic(classTable, counter, numberForTraining);
        addBlackBoxes();
    }

    public Statistic getStatistic() {
        return statistic;
    }

    private void addBlackBoxes() {
        Set<String> classNames = classTable.keySet();
        classNames.forEach(className -> {
            DataClass dataClass = classTable.get(className);
            ArrayList<Hashtable<String, Integer>> attributes = counter.getAttributes();
            for (int i = 0; i < attributes.size(); i++) {
                Hashtable<String, Integer> attribute = attributes.get(i);
                Set<String> instancesOfAttribute = attribute.keySet();
                int indexOfAttribute = i;
                instancesOfAttribute
                        .forEach(nameOfAttribute -> dataClass.increase(indexOfAttribute, nameOfAttribute));
            }
        });
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
