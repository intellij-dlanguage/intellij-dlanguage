package io.github.intellij.dlanguage.run.exception;

public class NoValidDlangSdkFound extends Exception {

    public NoValidDlangSdkFound() {
        super("No valid D compiler found");
    }

    public NoValidDlangSdkFound(final String message) {
        super(message);
    }
}
