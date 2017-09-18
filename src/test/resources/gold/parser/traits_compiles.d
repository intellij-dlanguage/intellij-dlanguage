import std.stdio;

struct S
{
    static int s1;
    int s2;
}

int foo();
int bar();

void main()
{
    writeln(__traits(compiles));                      // false
    writeln(__traits(compiles, foo));                 // true
    writeln(__traits(compiles, foo + 1));             // true
    writeln(__traits(compiles, &foo + 1));            // false
    writeln(__traits(compiles, typeof(1)));           // true
    writeln(__traits(compiles, S.s1));                // true
    writeln(__traits(compiles, S.s3));                // false
    writeln(__traits(compiles, 1,2,3,int,long,std));  // true
    writeln(__traits(compiles, 3[1]));                // false
    writeln(__traits(compiles, 1,2,3,int,long,3[1])); // false
}
