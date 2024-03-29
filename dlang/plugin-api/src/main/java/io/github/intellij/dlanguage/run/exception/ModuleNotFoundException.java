package io.github.intellij.dlanguage.run.exception;

public class ModuleNotFoundException extends Exception {

    public ModuleNotFoundException() {
        this("Module was null");
    }

    public ModuleNotFoundException(final String message) {
        super(message);
    }
}
