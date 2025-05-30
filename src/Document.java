import java.util.*;

class Document {
    private String fileName;
    private String content;

    public Document(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }

    public boolean containsKeywords(List<String> keywords) {
        String lowerContent = content.toLowerCase();
        for (String keyword : keywords) {
            if (lowerContent.contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}