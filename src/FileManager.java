import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FileManager {
    private final String separator = File.separator;
    private final String path = "src" + separator + "data";
    private final ArrayList<String[]> data = new ArrayList<>();
    private String fileName;

    public FileManager() {
        try {
            fileName = getFile();
            BufferedReader bf = new BufferedReader(new FileReader( path + separator + fileName));
            String line;
            while ((line = bf.readLine()) != null) {
                data.add((line.split(",")));
            }
            Collections.shuffle(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFile() {
        File nf = new File(path);
        File[] files = nf.listFiles();
        if(files == null || files.length == 0){
            System.out.println("Folder \"data\" is empty");
            return null;
        }
        else if (files.length > 1) {
            return selectFile(files);
        }
        return files[0].getName();
    }

    private String selectFile(File[] files) {
        System.out.println("Select file");
        int i;
        for (i = 0; i < files.length; ++i) {
            System.out.println(i + ". " + files[i].getName());
        }
        System.out.print("Enter: ");
        Scanner scanner = new Scanner(System.in);
        i = scanner.nextInt();
        return files[i].getName();
    }

    public ArrayList<String[]> getData() {
        return this.data;
    }

    public String getFileName() {
        return fileName;
    }
}
