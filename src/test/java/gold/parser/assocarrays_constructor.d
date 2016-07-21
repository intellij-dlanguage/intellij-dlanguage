string[int] aa;
string s;
s = aa[1];          // throws RangeError in runtime
aa[1] = "hello";    // handled for setting AA entry
s = aa[1];          // succeeds to lookup
assert(s == "hello");

struct S
{
    int val;
    void opAssign(S rhs) { this.val = rhs.val * 2; }
}
S[int] aa;
aa[1] = S(10);  // first setting initializes the entry aa[1]
assert(aa[1].val == 10);
aa[1] = S(10);  // second setting invokes normal assignment, and
                // operator-overloading rewrites it to member opAssign function.
assert(aa[1].val == 20);

struct S
{
    int val;
    void opAssign(int v) { this.val = v * 2; }
}
S[int] aa;
aa[1] = 10;     // is rewritten to: aa[1].opAssign(10), and
                // throws RangeError before opAssign is called

struct S
{
    int val;
    this(int v) { this.val = v; }
    void opAssign(int v) { this.val = v * 2; }
}
S s = 1;    // OK, rewritten to: S s = S(1);
  s = 1;    // OK, rewritten to: s.opAssign(1);

S[int] aa;
aa[1] = 10; // first setting is rewritten to: aa[1] = S(10);
assert(aa[1].val == 10);
aa[1] = 10; // second setting is rewritten to: aa[1].opAssign(10);
assert(aa[1].val == 20);

import std.bigint;
BigInt[string] aa;
aa["a"] = 10;   // construct BigInt(10) and move it in AA
aa["a"] = 20;   // call aa["a"].opAssign(20)