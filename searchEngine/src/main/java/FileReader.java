import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class FileReader {
    private static FileReader single_instance = null;

    private FileReader() {
    }

    public static FileReader getInstance() {
        if (single_instance == null)
            single_instance = new FileReader();
        return single_instance;
    }

    public Map<String, String> readFile(String path) throws FileNotFoundException {
        Map<String, String> docs = new HashMap<>();
        final File folder = new File(path);
        Scanner reader;
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles()))
            if (!fileEntry.isDirectory()) {
                reader = new Scanner(fileEntry);
                docs.put(fileEntry.getName(), reader.nextLine());
                reader.close();
            }

        return docs;
    }
}
