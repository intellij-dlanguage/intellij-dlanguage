static assert(!is(T : int));

unittest
{
	foo(!is(T : int));
}

enum a(T) = !is(T : int);
