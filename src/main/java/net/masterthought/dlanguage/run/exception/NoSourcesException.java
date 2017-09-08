package net.masterthought.dlanguage.run.exception;

public class NoSourcesException extends Exception {
    private final String sourcesRoot;

    public NoSourcesException(final String sourcesRoot) {
        super();
        this.sourcesRoot = sourcesRoot;
    }

    public String getSourcesRoot() {
        return sourcesRoot;
    }
}
