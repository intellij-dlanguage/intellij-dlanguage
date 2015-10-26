import std.stdio;
import std.typetuple; // for TypeTuple

void main()
{
    alias TL = TypeTuple!(int, long, double);

    foreach (T; TL)
    {
        writeln(typeid(T));
    }
}