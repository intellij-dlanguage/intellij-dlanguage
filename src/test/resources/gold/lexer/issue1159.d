module gold.lexer.issue1159;

enum E
{
	add, delete;
}

E delete()
{
	return E.delete;
}

void test2()
{
	assert(delete() == E.delete);
}
