char[] a = "\xE2\x89\xA0".dup;  // \u2260 encoded as 3 UTF-8 bytes

foreach (dchar c; a)
{
    writefln("a[] = %x", c); // prints 'a[] = 2260'
}

dchar[] b = "\u2260"d.dup;

foreach (char c; b)
{
    writef("%x, ", c);  // prints 'e2, 89, a0, '
}