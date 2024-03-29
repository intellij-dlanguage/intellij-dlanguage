unittest
{
int a;
int b = 1;
t c;
t d = cast(t)3;

int.init; // is 0
a.init;   // is 0
b.init;   // is 0
t.init;   // is 2
c.init;   // is 2
d.init;   // is 2

struct Foo
{
    int a;
    int b = 7;
}

Foo.init.a;  // is 0
Foo.init.b;  // is 7
}
