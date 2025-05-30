import java.util.*;
import java.util.concurrent.*;

public class DocumentSearchEngine {
    private List<Document> documents;
    private ForkJoinPool forkJoinPool;

    public DocumentSearchEngine() {
        this.documents = new ArrayList<>();
        this.forkJoinPool = new ForkJoinPool();
        initializeDocuments();
    }

    private void initializeDocuments() {
        documents.add(new Document("java_basics.txt", "Java програмування об'єктно-орієнтоване мова розробка додатків"));
        documents.add(new Document("web_development.txt", "HTML CSS JavaScript веб розробка фронтенд бекенд сервер"));
        documents.add(new Document("database_intro.txt", "MySQL PostgreSQL база даних SQL запити таблиці індекси"));
        documents.add(new Document("python_guide.txt", "Python програмування алгоритми структури даних машинне навчання"));
        documents.add(new Document("network_basics.txt", "TCP IP протоколи мережа інтернет клієнт сервер"));
        documents.add(new Document("mobile_dev.txt", "Android iOS мобільна розробка додатки смартфон планшет"));
        documents.add(new Document("security.txt", "кібербезпека шифрування хакінг захист інформації"));
        documents.add(new Document("ai_intro.txt", "штучний інтелект машинне навчання нейронні мережі"));
        documents.add(new Document("cloud_computing.txt", "хмарні технології AWS Azure серверна інфраструктура"));
        documents.add(new Document("data_science.txt", "аналіз даних статистика візуалізація великі дані"));
        documents.add(new Document("software_engineering.txt", "розробка програмного забезпечення тестування архітектура"));
        documents.add(new Document("devops.txt", "DevOps автоматизація розгортання CI CD Jenkins Docker"));
        documents.add(new Document("blockchain.txt", "блокчейн криптовалюта біткоїн розподілені системи"));
        documents.add(new Document("ui_ux.txt", "дизайн інтерфейсів користувацький досвід UX UI"));
        documents.add(new Document("algorithms.txt", "алгоритми сортування пошук складність обчислень"));
        documents.add(new Document("os_concepts.txt", "операційні системи Linux Windows процеси пам'ять"));
        documents.add(new Document("git_tutorial.txt", "Git версійний контроль GitHub репозиторій комміти"));
        documents.add(new Document("react_guide.txt", "React JavaScript бібліотека компоненти віртуальний DOM"));
        documents.add(new Document("spring_boot.txt", "Spring Boot Java фреймворк REST API мікросервіси"));
        documents.add(new Document("docker_intro.txt", "Docker контейнери віртуалізація розгортання додатків"));
    }

    public List<Document> searchDocuments(List<String> keywords) {
        if (documents.isEmpty()) {
            return new ArrayList<>();
        }

        DocumentSearchTask task = new DocumentSearchTask(documents, keywords, 0, documents.size());
        return forkJoinPool.invoke(task);
    }

    public void printSearchResults(List<Document> results) {
        if (results.isEmpty()) {
            System.out.println("Документи не знайдені");
        } else {
            System.out.println("Знайдені документи:");
            for (Document doc : results) {
                System.out.println("- " + doc.getFileName());
            }
        }
    }

    public static void main(String[] args) {
        DocumentSearchEngine engine = new DocumentSearchEngine();

        List<String> keywords1 = Arrays.asList("Java", "програмування");
        System.out.println("Пошук за ключовими словами: " + keywords1);
        List<Document> results1 = engine.searchDocuments(keywords1);
        engine.printSearchResults(results1);

        System.out.println();

        List<String> keywords2 = Arrays.asList("веб", "розробка");
        System.out.println("Пошук за ключовими словами: " + keywords2);
        List<Document> results2 = engine.searchDocuments(keywords2);
        engine.printSearchResults(results2);

        System.out.println();

        List<String> keywords3 = Arrays.asList("база даних", "SQL");
        System.out.println("Пошук за ключовими словами: " + keywords3);
        List<Document> results3 = engine.searchDocuments(keywords3);
        engine.printSearchResults(results3);

        System.out.println();

        List<String> keywords4 = Arrays.asList("машинне навчання", "штучний інтелект");
        System.out.println("Пошук за ключовими словами: " + keywords4);
        List<Document> results4 = engine.searchDocuments(keywords4);
        engine.printSearchResults(results4);
    }
}