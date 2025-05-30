
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

class CommonWordsTask extends RecursiveTask<Set<String>> {
    private static final int THRESHOLD = 1;
    private final String[] documents;
    private final int start;
    private final int end;

    public CommonWordsTask(String[] documents, int start, int end) {
        this.documents = documents;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Set<String> compute() {
        if (end - start <= THRESHOLD) {
            return readAndProcessDocument();
        }

        int middle = (start + end) / 2;

        CommonWordsTask leftTask = new CommonWordsTask(documents, start, middle);
        CommonWordsTask rightTask = new CommonWordsTask(documents, middle, end);

        leftTask.fork();

        Set<String> rightResult = rightTask.compute();
        Set<String> leftResult = leftTask.join();

        return findIntersection(leftResult, rightResult);
    }

    private Set<String> readAndProcessDocument() {
        if (start >= end) {
            return new HashSet<>();
        }

        Set<String> result = null;

        for (int i = start; i < end; i++) {
            Set<String> words = readWordsFromDocument(documents[i]);

            if (result == null) {
                result = new HashSet<>(words);
            } else {
                result.retainAll(words);
            }
        }

        return result != null ? result : new HashSet<>();
    }

    private Set<String> readWordsFromDocument(String filename) {
        try {
            Path path = Paths.get(filename);
            String content = Files.readString(path);

            return Arrays.stream(content.toLowerCase().split("\\W+"))
                    .filter(word -> !word.isEmpty() && word.length() > 2)
                    .collect(Collectors.toSet());

        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + filename);
            return new HashSet<>();
        }
    }

    private Set<String> findIntersection(Set<String> set1, Set<String> set2) {
        if (set1.isEmpty()) return set2;
        if (set2.isEmpty()) return set1;

        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return intersection;
    }
}