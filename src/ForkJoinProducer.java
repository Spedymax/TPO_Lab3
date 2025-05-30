import java.util.concurrent.*;

class ForkJoinProducer implements Runnable {
    private final BlockingQueue<String> queue;
    private final int itemCount;

    public ForkJoinProducer(BlockingQueue<String> queue, int itemCount) {
        this.queue = queue;
        this.itemCount = itemCount;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < itemCount; i++) {
                String item = "Обєкт_" + i;
                queue.put(item);
                if (i % 100 == 0) {
                    Thread.sleep(1);
                }
            }
            queue.put("DONE");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}