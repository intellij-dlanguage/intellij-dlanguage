int function(char c) fp; // declare pointer to a function

void test()
{
    static int foo(char c) { return 6; }

    fp = &foo;
}


int function(char c) fp;

void test()
{
    fp = function int(char c) { return 6;} ;
}

int abc(int delegate(int i));

void test()
{
    int b = 3;
    int foo(int c) { return 6 + b; }

    abc(&foo);
}

int abc(int delegate(int i));

void test()
{
    int b = 3;

    abc( delegate int(int c) { return 6 + b; } );
}