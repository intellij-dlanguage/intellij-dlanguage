struct S { int a; int b; int c; int d = 7;}
static S x = { a:1, b:2};            // c is set to 0, d to 7
static S z = { c:4, b:5, a:2 , d:5}; // z.a = 2, z.b = 5, z.c = 4, z.d = 5

static S q = { 1, 2 }; // q.a = 1, q.b = 2, q.c = 0, q.d = 7

static S q = S( 1, 2+3 ); // q.a = 1, q.b = 5, q.c = 0, q.d = 7

void test(int i)
{
    S q = { 1, i }; // q.a = 1, q.b = i, q.c = 0, q.d = 7
}

