unittest {
    if (a)
        if (b)
            doSomething();
    doSomethingElse();
}

void indent() {
    import std.range : repeat, take;

    if (config.useTabs)
        foreach (i; 0 .. indentLevel + tempIndent) {
            currentLineLength += config.tabSize;
            output.put("\t");
        } else
        foreach (i; 0 .. indentLevel + tempIndent)
            foreach (j; 0 .. config.indentSize) {
                output.put(" ");
                currentLineLength++;
            }
}
