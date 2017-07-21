package net.masterthought.dlanguage.codeinsight.dcd.completions;

public class TextCompletion implements Completion {

    private final String completionType;
    private final String completionText;

    public TextCompletion(final String completionType, final String completionText) {
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
