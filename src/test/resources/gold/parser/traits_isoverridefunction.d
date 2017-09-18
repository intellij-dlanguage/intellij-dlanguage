import std.stdio;

void main()
{
    int i;
    writeln(__traits(isArithmetic, int));
    writeln(__traits(isArithmetic, i, i+1, int));
    writeln(__traits(isArithmetic));
    writeln(__traits(isArithmetic, int*));
}
