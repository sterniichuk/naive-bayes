import java.util.ArrayList;
import java.util.Hashtable;

public class DataClass {
    private final String name;
    //name of class.
    private final ArrayList<Hashtable<String, Integer>> attributes;
    /*Array of attributes like color, age, gender
    Hashtable contains:
    String - instances of attribute like green for color, 18 for age, male for gender
    Integer - counter of all attributes for current class
    Example of attributes data structure
    _____________________________________________________________________________________
    | Index |    |                  Attributes                                          |
    |   0   | -> | Hashtable -> [Red, 10], [Green, 14], [Yellow, 100]                   |
    |   1   | -> | Hashtable -> [Big, 50], [Small, 78]                                  |
    |   2   | -> | Hashtable -> [Circle, 8], [Triangle, 78], [Rectangle, 45], [Oval, 1] |
    |   3   | -> | Hashtable -> [Male, 7], [Female, 5]                                  |
    |_______|____|______________________________________________________________________|
         */
    private int counterOfClass = 0;
    private final int numberOfAttributes;

    public DataClass(String name, int numberOfAttributes) {
        this.name = name;
        this.numberOfAttributes = numberOfAttributes;
        attributes = new ArrayList<>(numberOfAttributes);
        for (int i = 0; i < numberOfAttributes; i++) {
            attributes.add(new Hashtable<>());
        }
    }

    public void count(String[] instance) {
        for (int i = 0; i < numberOfAttributes; i++) {
            increase(i, instance[i]);
        }
        counterOfClass++;
    }

    public void increase(int indexOfAttribute, String nameOfAttribute) {
        Hashtable<String, Integer> hashtable = attributes.get(indexOfAttribute);
        int counter = 0;
        if (hashtable.containsKey(nameOfAttribute)) {
            counter = hashtable.get(nameOfAttribute);
        }
        hashtable.put(nameOfAttribute, (counter + 1));
    }

    public int getCounterOfClass() {
        return counterOfClass;
    }

    public int getCounterOf(int indexOfAttribute, String attributeName) {
        return attributes.get(indexOfAttribute).get(attributeName);
    }

    public ArrayList<Hashtable<String, Integer>> getAttributes() {
        return attributes;
    }
}
