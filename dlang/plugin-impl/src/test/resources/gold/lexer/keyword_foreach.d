import std.stdio;

void main()
{
    foreach (char c; "ab")
    {
        writefln("'%s'", c);
    }
    foreach (wchar w; "xy")
    {
        writefln("'%s'", w);
    }
}
