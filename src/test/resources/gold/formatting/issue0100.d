unittest
{
	loop: while (i < tokens.length) switch (tokens[i].type)
		{
		case tok!"(":
			parenDepth++;
			i++;
			break;
		case tok!"{":
			braceDepth++;
			i++;
			break;
		}
}
