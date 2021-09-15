package cmpt213.assignment1.tasktracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Main class which handles the saving and loading from JSON using the gson class.
 */
public class Main {
    public static void main(String[] args) {
        Menu test = new Menu();
//      referenced from this video https://www.youtube.com/watch?v=BbI8FdQOKNs for deserializing
        try {
            Path filename = Path.of("out.json");
            String temp = Files.readString(filename);
            Gson gson = new Gson();
            test = gson.fromJson(temp, Menu.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        test.menuPrint();
        // referenced from one of my my old assignments
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            Writer writer = new FileWriter("out.json", false);
            gson.toJson(test, writer);
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
