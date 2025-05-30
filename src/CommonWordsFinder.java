import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;

public class CommonWordsFinder {

    public static void main(String[] args) throws IOException {
        createTestFiles();

        String[] documents = {
                "document1.txt",
                "document2.txt",
                "document3.txt"
        };

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        CommonWordsTask task = new CommonWordsTask(documents, 0, documents.length);
        Set<String> result = forkJoinPool.invoke(task);

        System.out.println("Спільні слова:");
        result.forEach(System.out::println);
    }

    public static void createTestFiles() throws IOException {
        String[] contents = {
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi volutpat metus non massa mollis venenatis. Morbi malesuada, massa eget tristique posuere, eros arcu blandit odio, eu hendrerit velit nibh vitae justo. Mauris placerat interdum leo sit amet iaculis. Etiam lectus nulla, dignissim ut consequat non, ornare et massa. Donec sollicitudin elementum lacus sed porttitor. Praesent elementum risus nec scelerisque pretium. Cras vel scelerisque orci, id tempor lectus. Phasellus feugiat ante eget dolor consequat, non ornare purus tempus. Curabitur pharetra, velit a facilisis efficitur, lectus lacus bibendum arcu, id sodales justo ligula gravida enim. Nulla ac libero vitae nunc faucibus dapibus.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam consectetur aliquam neque, vitae tristique justo aliquam quis. Donec id blandit felis, sit amet porttitor dolor. Nunc finibus augue et diam volutpat tempus. Donec nec facilisis odio. Ut hendrerit varius ullamcorper. Vestibulum vel lacus purus. Fusce sapien urna, aliquam vitae viverra ac, placerat eu velit. Integer quis metus vitae magna elementum pulvinar nec vitae erat. Suspendisse consequat ligula nec est consequat fringilla. Maecenas purus erat, faucibus a bibendum cursus, congue sit amet felis. Aliquam tristique elementum lectus, sed vehicula ante pellentesque et. Pellentesque eu dapibus ipsum. Proin vestibulum est bibendum tortor.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam nunc sapien, bibendum eget ante non, aliquam euismod nisi. Mauris ipsum eros, venenatis id turpis ut, dignissim convallis enim. Quisque pretium nec lacus sed malesuada. Interdum et malesuada fames ac ante ipsum primis in faucibus. Sed blandit, felis sit amet vehicula pellentesque, ex risus efficitur nunc, sit amet fermentum eros arcu ac risus. Nulla non faucibus magna. In non libero luctus, blandit neque a, luctus ante. Sed efficitur ipsum a lorem laoreet ullamcorper. Praesent interdum urna velit, et placerat ante consequat eget. Vivamus suscipit nisi nec ipsum vehicula tincidunt. Donec mollis."
        };

        for (int i = 0; i < contents.length; i++) {
            Files.write(Paths.get("document" + (i + 1) + ".txt"), contents[i].getBytes());
        }
    }
}