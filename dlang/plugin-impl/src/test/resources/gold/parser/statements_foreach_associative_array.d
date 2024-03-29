unittest
{
double[string] a; // index type is string, value type is double

foreach (string s, double d; a)
{
    writefln("a['%s'] = %g", s, d);
}
}
