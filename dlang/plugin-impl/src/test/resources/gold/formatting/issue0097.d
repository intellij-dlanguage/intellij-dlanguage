unittest
{
	switch (x)
	{
	case 0:
		version (none)
		{
			// Comment
	case '\n':
			break;
		}
	}
}

unittest
{
	switch (x)
	{
	case 0:
		{
	Label: while (1)
			{
			}
			break;
		}
	Label2:
		doStuff();
	}
}

unittest
{
	switch (a)
	{
	case a:
		doStuff();
		doOtherStuff();
		break;
	default:
		break;
	}
}
