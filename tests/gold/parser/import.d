import std.stdio;
import foo, bar;
public import B;
static import std.stdio;
import io = std.stdio;
import std.stdio : writeln, foo = write;
import io = std.stdio : foo = writeln;

void main()
{
    import std.stdio;
    writeln("bar");
}