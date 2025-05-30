import java.security.SecureRandom;
import java.util.*;

class TestDataGenerator {
    public String makeText(int wordCount) {
        String[] words = {"програма", "алгоритм", "тест", "швидкість", "код",
                "студент", "університет", "завдання", "проект", "результат"};
        Random r = new SecureRandom();
        StringBuilder text = new StringBuilder();

        for (int i = 0; i < wordCount; i++) {
            text.append(words[r.nextInt(words.length)]);
            if (i < wordCount - 1) text.append(" ");
        }
        return text.toString();
    }
}
