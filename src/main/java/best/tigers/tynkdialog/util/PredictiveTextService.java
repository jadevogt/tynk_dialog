package best.tigers.tynkdialog.util;

import java.util.*;

public class PredictiveTextService {
    private static PredictiveTextService predictiveTextService = null;
    private final HashMap<String, HashMap<String, Integer>> knownTerms = new HashMap<>();
    public static PredictiveTextService getInstance() {
        if (predictiveTextService == null) {
            predictiveTextService = new PredictiveTextService();
        }
        return predictiveTextService;
    }

    private PredictiveTextService() {}

    public void incrementTerm(String collection, String term) {
        if (term == null || term.replace(" ", "").equals("")) {
            return;
        }
        if (!knownTerms.containsKey(collection)) {
            knownTerms.put(collection, new HashMap<>());
        }
        if (!knownTerms.get(collection).containsKey(term)) {
            knownTerms.get(collection).put(term, 1);
            Log.info("PredictiveTextService: adding %s to known terms in collection %s".formatted(term, collection));
        } else {
            var currentValue = knownTerms.get(collection).get(term);
            knownTerms.get(collection).put(term, currentValue+1);
        }
    }

    public void decrementTerm(String collection, String term) {
        if (!knownTerms.containsKey(collection)) {
            knownTerms.put(collection, new HashMap<>());
        }
        if (knownTerms.get(collection).containsKey(term)) {
            var currentValue = knownTerms.get(collection).get(term);
            knownTerms.get(collection).put(term, currentValue-1);
        }
    }

    public List<Map.Entry<String, Integer>> getCollectionTerms(String collection) {
        if (!knownTerms.containsKey(collection)) {
            knownTerms.put(collection, new HashMap<>());
            return new LinkedHashMap<String, Integer>().entrySet().stream().toList();
        }
        var sorted = new LinkedHashMap<String, Integer>();
        knownTerms.get(collection)
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x->sorted.put(x.getKey(), x.getValue()));
        return sorted.entrySet().stream().toList();
    }
}
