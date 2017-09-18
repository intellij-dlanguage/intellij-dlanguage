import std.stdio;

void main()
{
    auto i = 3;
    auto twice  = function (int x) => x * 2;
    auto square = delegate (int x) => x * x;

    auto n = 5;
    auto mul_n = (int x) => x * n;

    writeln(twice(i));   // prints 6
    writeln(square(i));  // prints 9
    writeln(mul_n(i));   // prints 15
}
