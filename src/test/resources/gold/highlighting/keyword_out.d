long square_root(long x)
in
{
    assert(x >= 0);
}
out (result)
{
    assert((result * result) <= x && (result+1) * (result+1) > x);
}
do
{
    return cast(long)std.math.sqrt(cast(real)x);
}
