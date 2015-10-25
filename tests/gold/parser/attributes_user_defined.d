@(3) int a;
@("string", 7) int b;

enum Foo;
@Foo int c;

struct Bar
{
    int x;
}

@Bar(3) int d;


@(1)
{
    @(2) int a;         // has UDA's (1, 2)
    @("string") int b;  // has UDA's (1, "string")
}