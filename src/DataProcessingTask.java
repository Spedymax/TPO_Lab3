import java.util.concurrent.*;
import java.util.List;

class DataProcessingTask extends RecursiveTask<Integer> {
    private final List<String> data;
    private final int start;
    private final int end;
    private static final int THRESHOLD = 100;

    public DataProcessingTask(List<String> data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            int processed = 0;
            for (int i = start; i < end; i++) {
                if (i < data.size()) {
                    String item = data.get(i);
                    int length = item.length();
                    processed++;


                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            return processed;
        } else {

            int mid = (start + end) / 2;
            DataProcessingTask leftTask = new DataProcessingTask(data, start, mid);
            DataProcessingTask rightTask = new DataProcessingTask(data, mid, end);

            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();

            return leftResult + rightResult;
        }
    }
}