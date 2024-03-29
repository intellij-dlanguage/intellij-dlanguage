unittest
{
import std.conv;
int i = 0;
(i = 2) = ++i * i++ + i;
assert(i == 13); // left to right evaluation of side effects
assert(text(++i, ++i) == "1415"); // left to right evaluation of arguments
}
