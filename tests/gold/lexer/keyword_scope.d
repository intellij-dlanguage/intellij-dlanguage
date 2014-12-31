write("1");
{
    write("2");
    scope(exit) write("3");
    scope(exit) write("4");
    write("5");
}
writeln();
