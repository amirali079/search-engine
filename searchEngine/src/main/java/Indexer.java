import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.regex.Pattern;


public class Indexer {


    private static Indexer single_instance = null;
    private Map<String, LinkedHashSet<String>> invertedIndex = new HashMap<String, LinkedHashSet<String>>();

    private Indexer() {
    }

    public static Indexer getInstance() {
        if (single_instance == null)
            single_instance = new Indexer();
        return single_instance;
    }

    public Map<String, LinkedHashSet<String>> getInvertedIndex(Map<String, String> docs) {

        for (String doc : docs.keySet()) {


            String[] words = wordCreator(docs.get(doc));

            for (String word : words) {
                if (invertedIndex.containsKey(word))
                    invertedIndex.get(word).add(doc);
                else {
                    LinkedHashSet<String> docIds = new LinkedHashSet<String>();
                    docIds.add(doc);
                    invertedIndex.put(word, docIds);
                }
            }
        }
        return invertedIndex;
    }


    private String[] wordCreator(String line) {
        String prunedLine = Pattern.compile("[^a-z A-Z]").matcher(line).replaceAll(" ");
        return prunedLine.split(" ");
    }

}
