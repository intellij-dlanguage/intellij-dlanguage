write("1");
{
    write("2");
    scope(exit) write("3");
    scope(exit) write("4");
    write("5");
}
writeln();


{
    scope(exit) write("1");
    scope(success) write("2");
    scope(exit) write("3");
    scope(success) write("4");
}
writeln();



struct Foo
{
    this(string s) { write(s); }
    ~this() { write("1"); }
}

try
{
    scope(exit) write("2");
    scope(success) write("3");
    Foo f = Foo("0");
    scope(failure) write("4");
    throw new Exception("msg");
    scope(exit) write("5");
    scope(success) write("6");
    scope(failure) write("7");
}
catch (Exception e)
{
}
writeln();