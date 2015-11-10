template TFoo(T) { alias t = T*; }
...
TFoo!(int).t x; // declare x to be of type int*

TFoo!int.t x;   // same as TFoo!(int).t x;

template TFoo(T) { alias t = T*; }
alias abc = TFoo!(int);
abc.t x;        // declare x to be of type int*

template TFoo(T) { T f; }
alias a = TFoo!(int);
alias b = TFoo!(int);
...
a.f = 3;
assert(b.f == 3);  // a and b refer to the same instance of TFoo

struct TFoo(int x) { }

// 3 and 2+1 are both 3 of type int
static assert(is(TFoo!(3) == TFoo!(2 + 1)));

// 3u is implicitly converted to 3 to match int parameter,
// and refers exactly same instance with TFoo!(3).
static assert(is(TFoo!(3) == TFoo!(3u)));

template TCopy(T)
{
    void copy(out T to, T from)
    {
        to = from;
    }
}

int i;
TCopy!(int).copy(i, 3);

