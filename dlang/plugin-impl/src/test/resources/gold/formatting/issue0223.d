unittest
{
	if (info > 0)
		throw new ExceptionWithLongName(
		std.string.format(
		"During the LU factorization, it was found that the " ~ "%sth diagonal value is exactly zero.",
			info), file, line);
}
