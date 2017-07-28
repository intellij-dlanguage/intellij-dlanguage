unittest {
    if (a) {
        while (sBraceDepth == 0 && indents.topIsTemp()
                && ((indents.top != tok!"if" && indents.top != tok!"version")
                    || !peekIs(tok!"else"))) {
            a();
        }
    }

    if (parenDepth == 0 && (peekIs(tok!"is") || peekIs(tok!"in")
            || peekIs(tok!"out") || peekIs(tok!"body")))
        writeToken();

    {
        {
            while (sBraceDepth == 0 && indents.topIsTemp()
                    && ((indents.top != tok!"if"
                        && indents.top != tok!"version") || !peekIs(tok!"else"))) {
                indents.pop();
            }
        }
    }

    {
        while (sBraceDepth == 0 && indents.topIsTemp()
                && ((indents.top != tok!"if" && indents.top != tok!"version")
                    || !peekIs(tok!"else"))) {
            indents.pop();
        }
    }
}
