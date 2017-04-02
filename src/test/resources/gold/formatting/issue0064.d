unittest
{
	return true; //
Lnomatch:
	//printf("nomatch\n");
	return false; // nomatch;
}

unittest
{
	if (x)
		return true;
}

unittest
{
	return true; // match
Lnomatch: //printf("nomatch\n");
	return false; // nomatch;
}
