import java.util.*;
import java.util.concurrent.*;

public class TextAnalyzer {
    private ForkJoinPool forkJoinPool;

    public TextAnalyzer() {
        forkJoinPool = new ForkJoinPool();
    }

    public Statistics analyzeText(String text) {

        String[] words = text.toLowerCase().split("\\s+");

        WordLengthTask task = new WordLengthTask(words, 0, words.length);

        List<Integer> wordLengths = forkJoinPool.invoke(task);

        return new Statistics(wordLengths);
    }

    public void shutdown() {
        forkJoinPool.shutdown();
    }

    public static void main(String[] args) {
        TextAnalyzer analyzer = new TextAnalyzer();

        String sampleText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis tempus scelerisque purus, ac sollicitudin lorem fringilla vel. Pellentesque molestie sed ipsum eget hendrerit. Morbi metus quam, cursus sed risus ac, vehicula scelerisque lectus. Phasellus semper eu lorem vel bibendum. Sed cursus dui ut felis euismod, vel pulvinar massa efficitur. Sed feugiat maximus tortor, quis blandit nisl congue et. Maecenas libero sem, placerat quis molestie ut, scelerisque in ipsum. Pellentesque nec nisi suscipit, ullamcorper ante quis, fringilla nulla. In malesuada vulputate mi, eu aliquet eros sollicitudin at. Vestibulum tempus turpis nec magna ultrices, ac pulvinar urna consequat. Aliquam sed ex sagittis, porttitor mauris sed, scelerisque nibh. Duis facilisis dictum lacus a volutpat. Praesent non dolor vulputate, sollicitudin turpis in, aliquet turpis. Sed in arcu ut enim volutpat auctor. Nunc feugiat ex ut egestas interdum. Sed eu rhoncus odio.\n" +
                "Mauris dapibus justo ut est hendrerit aliquam. Vestibulum in nisl in magna efficitur aliquam. Quisque tempus fringilla enim at posuere. Sed pellentesque efficitur libero, vel tempus dui pellentesque id. Cras nunc est, ornare ut blandit a, dignissim in ligula. Praesent quis purus a tellus commodo imperdiet a in nunc. Pellentesque pharetra, magna at pulvinar finibus, ligula erat tempus mauris, eget venenatis mauris risus in eros. Sed quam tortor, tincidunt a est vitae, vulputate elementum lectus. Morbi dictum fermentum eros sit amet scelerisque. Nullam interdum nisl eu pharetra lacinia. Cras vestibulum sagittis dolor, ac semper nunc iaculis et. Suspendisse semper purus mauris, in semper ex ultrices et. Curabitur ac sapien mauris. Maecenas quis elit mauris. Nullam fermentum at ligula nec vehicula. Integer accumsan quis sem nec gravida.\n" +
                "Curabitur commodo odio id eros eleifend iaculis. Curabitur et metus felis. Suspendisse non mi imperdiet, suscipit lorem ac, vehicula metus. Interdum et malesuada fames ac ante ipsum primis in faucibus. Duis aliquet lacus nibh, dignissim pharetra purus tempus id. Curabitur blandit augue id mi ultrices rhoncus. In non velit porta, rhoncus felis sed, lacinia magna. Sed et interdum ligula, vel posuere ligula. Morbi lacinia non tortor quis rutrum. Curabitur non sem lectus. Suspendisse vel justo eleifend, tincidunt ex non, iaculis dolor. Sed sed elementum nulla, sed ultricies.";

        System.out.println("Аналізується текст:");
        System.out.println(sampleText);
        System.out.println("\n" + "=".repeat(50) + "\n");

        Statistics stats = analyzer.analyzeText(sampleText);
        System.out.println(stats);

        analyzer.shutdown();
    }
}
