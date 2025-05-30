import java.util.*;

class SequentialAnalyzer {
    public List<Integer> analyze(String text) {
        String[] words = text.split(" ");
        List<Integer> lengths = new ArrayList<>();

        for (String word : words) {
            String clean = word.replaceAll("[^a-zA-Zа-яА-Я]", "");
            if (!clean.isEmpty()) {
                lengths.add(clean.length());
            }
        }
        return lengths;
    }
}