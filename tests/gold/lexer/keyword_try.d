import std.stdio;

int main()
{
    try
    {
        try
        {
            throw new Exception("first");
        }
        finally
        {
            writeln("finally");
            throw new Exception("second");
        }
    }
    catch (Exception e)
    {
        writeln("catch %s", e.msg);
    }
    writeln("done");
    return 0;
}
