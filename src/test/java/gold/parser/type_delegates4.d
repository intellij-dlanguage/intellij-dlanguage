class C
{
    int a;
    int foo(int i) { return i + a; }
}

// mfp is the member function pointer
auto mfp = function(C self, int i) { return self.foo(i); };
auto c = new C();  // create an instance of C
mfp(c, 1);  // and call c.foo(1)