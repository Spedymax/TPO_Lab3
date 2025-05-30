import java.util.*;
import java.util.concurrent.*;

class DocumentSearchTask extends RecursiveTask<List<Document>> {
    private List<Document> documents;
    private List<String> keywords;
    private int start;
    private int end;
    private static final int THRESHOLD = 10;

    public DocumentSearchTask(List<Document> documents, List<String> keywords, int start, int end) {
        this.documents = documents;
        this.keywords = keywords;
        this.start = start;
        this.end = end;
    }

    @Override
    protected List<Document> compute() {
        if (end - start <= THRESHOLD) {
            List<Document> result = new ArrayList<>();
            for (int i = start; i < end; i++) {
                if (documents.get(i).containsKeywords(keywords)) {
                    result.add(documents.get(i));
                }
            }
            return result;
        } else {
            int middle = start + (end - start) / 2;
            DocumentSearchTask leftTask = new DocumentSearchTask(documents, keywords, start, middle);
            DocumentSearchTask rightTask = new DocumentSearchTask(documents, keywords, middle, end);

            leftTask.fork();
            List<Document> rightResult = rightTask.compute();
            List<Document> leftResult = leftTask.join();

            List<Document> combinedResult = new ArrayList<>();
            combinedResult.addAll(leftResult);
            combinedResult.addAll(rightResult);

            return combinedResult;
        }
    }
}