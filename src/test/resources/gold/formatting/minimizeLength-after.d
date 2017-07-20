unittest {
    validMoves!(typeof(open))(open, tokens[0 .. tokensEnd], depths[0 .. tokensEnd],
            current.breaks, config, currentLineLength, indentLevel);
}

/+
// good
unittest
{
	validMoves!(typeof(open))(open, tokens[0 .. tokensEnd], depths[0 .. tokensEnd],
			current.breaks, config, currentLineLength, indentLevel);
}
// bad
unittest
{
	validMoves!(typeof(open))(open, tokens[0 .. tokensEnd],
			depths[0 .. tokensEnd], current.breaks, config, currentLineLength, indentLevel);
}
+/
