package net.masterthought.dlanguage.run.exception;

public class NoSourcesException extends Exception {
    private final String sourcesRoot;

    public NoSourcesException(String sourcesRoot) {
        super();
        this.sourcesRoot = sourcesRoot;
    }

    public String getSourcesRoot() {
        return sourcesRoot;
    }
}
