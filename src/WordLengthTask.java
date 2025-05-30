import java.util.*;
import java.util.concurrent.*;

class WordLengthTask extends RecursiveTask<List<Integer>> {
    private String[] words;
    private int start;
    private int end;
    private static final int THRESHOLD = 50;

    public WordLengthTask(String[] words, int start, int end) {
        this.words = words;
        this.start = start;
        this.end = end;
    }

    protected List<Integer> compute() {
        if (end - start <= THRESHOLD) {
            List<Integer> lengths = new ArrayList<>();
            for (int i = start; i < end; i++) {
                String cleanWord = words[i].replaceAll("[^a-zA-Zа-яА-ЯіІїЇєЄ]", "");
                if (!cleanWord.isEmpty()) {
                    lengths.add(cleanWord.length());
                }
            }
            return lengths;
        }

        int middle = start + (end - start) / 2;

        WordLengthTask leftTask = new WordLengthTask(words, start, middle);
        WordLengthTask rightTask = new WordLengthTask(words, middle, end);

        leftTask.fork();

        List<Integer> rightResult = rightTask.compute();

        List<Integer> leftResult = leftTask.join();

        List<Integer> result = new ArrayList<>(leftResult);
        result.addAll(rightResult);

        return result;
    }
}