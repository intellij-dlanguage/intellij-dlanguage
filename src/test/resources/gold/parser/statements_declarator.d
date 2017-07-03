unittest
{
if (auto m = std.regexp.search("abcdef", "b(c)d"))
{
    writefln("[%s]", m.pre);      // prints [a]
    writefln("[%s]", m.post);     // prints [ef]
    writefln("[%s]", m.match(0)); // prints [bcd]
    writefln("[%s]", m.match(1)); // prints [c]
    writefln("[%s]", m.match(2)); // prints []
}
else
{
    writeln(m.post);    // error, m undefined
}
writeln(m.pre);         // error, m undefined
}
