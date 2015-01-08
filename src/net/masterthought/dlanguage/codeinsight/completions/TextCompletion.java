package net.masterthought.dlanguage.codeinsight.completions;

public class TextCompletion implements Completion {

    private String completionType;
    private String completionText;

    public TextCompletion(String completionType, String completionText) {
        this.completionType = completionType;
        this.completionText = completionText;
    }

    @Override
    public String completionType() {
        return completionType;
    }

    @Override
    public String completionText() {
        return completionText;
    }
}
