void main(string[] args)
{
	if (prevLocation != size_t.max)
	{
		addErrorMessage(line, column, KEY, "Expression %s is true: already checked on line %d.".format(expressions[prevLocation].formatted, expressions[prevLocation].line));
	}
}
