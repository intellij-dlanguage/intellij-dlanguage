int abc(int delegate(int i));
int def(int function(int s));

void test()
{
    int b = 3;

    abc( (int c) { return 6 + b; } );  // inferred to delegate
    def( (int c) { return c * 2; } );  // inferred to function
  //def( (int c) { return c * b; } );  // error!
    // Because the FunctionLiteralBody accesses b, then the function literal type
    // is inferred to delegate. But def cannot receive delegate.
}

void foo(int function(int) fp);

void test()
{
    int function(int) fp = (n) { return n * 2; };
    // The type of parameter n is inferred to int.

    foo((n) { return n * 2; });
    // The type of parameter n is inferred to int.
}

double test()
{
    double d = 7.6;
    float f = 2.3;

    void loop(int k, int j, void delegate() statement)
    {
        for (int i = k; i < j; i++)
        {
            statement();
        }
    }

    loop(5, 100, { d += 1; });
    loop(3, 10,  { f += 3; });

    return d + f;
}