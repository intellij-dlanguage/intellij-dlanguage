void func(X thisObj);

X obj;
obj.func();
// If 'obj' does not have regular member 'func',
// it's automatically rewritten to 'func(obj)'

stdin.byLine(KeepTerminator.yes)
    .map!(a => a.idup)
    .array
    .sort
    .copy(stdout.lockingTextWriter());

@property prop(X thisObj);
@property prop(X thisObj, int value);

X obj;
obj.prop;      // Rewrites to: prop(obj);
obj.prop = 1;  // Rewrites to: prop(obj, 1);

module a;
void foo(X);
alias boo = foo;
void main()
{
    void bar(X);
    import b : baz;  // void baz(X);

    X obj;
    obj.foo();    // OK, calls a.foo;
    //obj.bar();  // NG, UFCS does not see nested functions
    obj.baz();    // OK, calls b.baz, because it is declared at the
                  // top level scope of module b

    import b : boo = baz;
    obj.boo();    // OK, calls aliased b.baz instead of a.boo (== a.foo),
                  // because the declared alias name 'boo' in local scope
                  // overrides module scope name
}
class C
{
    void mfoo(X);
    static void sbar(X);
    import b : ibaz = baz;  // void baz(X);
    void test()
    {
        X obj;
        //obj.mfoo();  // NG, UFCS does not see member functions
        //obj.sbar();  // NG, UFCS does not see static member functions
        obj.ibaz();    // OK, ibaz is an alias of baz which declared at
                       //     the top level scope of module b
    }
}

int front(int[] arr) { return arr[0]; }

void main()
{
    int[] a = [1,2,3];
    auto x = a.front();   // call .front by UFCS

    auto front = x;       // front is now a variable
    auto y = a.front();   // Error, front is not a function
}

class C
{
    int[] arr;
    int front()
    {
        return arr.front(); // Error, C.front is not callable
                            // using argument types (int[])
    }
}