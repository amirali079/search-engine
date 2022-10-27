import java.util.*;

public class QueryManager {

    private static QueryManager single_instance = null;
    private Map<String, LinkedHashSet<String>> invertedIndex;

    private QueryManager() {
    }

    public static QueryManager getInstance() {
        if (single_instance == null)
            single_instance = new QueryManager();
        return single_instance;
    }

    public void setInvertedIndex(Map<String, LinkedHashSet<String>> invertedIndex) {
        this.invertedIndex = invertedIndex;
    }

    public Set<String> search(String query) {

        String[] commands = query.split(" ");

        ArrayList<Set<String>> and = new ArrayList<Set<String>>();
        Set<String> or = new HashSet<String>();
        Set<String> not = new HashSet<String>();
        Set<String> result = new HashSet<String>();

        for (String word : commands) {
            if (word.charAt(0) == '+')
                or.addAll(invertedIndex.get(word.substring(1)));
            else if (word.charAt(0) == '-')
                not.addAll(invertedIndex.get(word.substring(1)));
            else {
                and.add(new HashSet<String>(invertedIndex.get(word)));
            }
        }

        result = intersection(and.get(0), and.get(1));
        for (int i = 2; i < and.size(); i++)
            result = intersection(result, and.get(i));

        result = intersection(result, or);

        for (String wordNot : not)
            result.removeIf(word -> word.equals(wordNot));

        return result;
    }


    private Set<String> intersection(Set<String> first, Set<String> second) {
        Set<String> result = new HashSet<String>();
        for (String element1 : first)
            for (String element2 : second)
                if (element1.equals(element2))
                    result.add(element1);


        return result;
    }
}
