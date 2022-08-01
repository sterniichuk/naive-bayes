import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    private ArrayList<String[]> data;
    private FileManager fileManager;

    public static void main(String[] args) {
        Program program = new Program();
        program.start();
    }

    public void start() {
        fileManager = new FileManager();
        data = fileManager.getData();
        Training training = new Training(data, selectPercentForTraining());
        Statistic statistic = training.getStatistic();
        statistic.printStatistic();
        Testing testing = new Testing(statistic, fileManager.getData());
        testing.printResult();
    }

    private double selectPercentForTraining() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("File " + fileManager.getFileName() + " was read\n" +
                "Data set has " + data.size() + " instances");
        System.out.print("How many percent for Training do you want select\n" +
                "Enter: ");
        return scanner.nextDouble();
    }
}
