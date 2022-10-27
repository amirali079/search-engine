import java.io.FileNotFoundException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Scanner;

public class SearchEngine {

    private static SearchEngine single_instance = null;
    private Map<String, LinkedHashSet<String>> invertedIndex;
    private final String filePath= "EnglishData";

    private SearchEngine() {
    }

    public static SearchEngine getInstance() {
        if (single_instance == null)
            single_instance = new SearchEngine();
        return single_instance;
    }

    public void start() throws FileNotFoundException {

        QueryManager.getInstance().setInvertedIndex(Indexer.getInstance().getInvertedIndex(FileReader.getInstance().readFile(filePath)));

        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.println("input query/ 0 for exit");
            String query = input.nextLine();
            if (query.equals("0"))break;

            System.out.println(QueryManager.getInstance().search(query));
        }

    }
}
