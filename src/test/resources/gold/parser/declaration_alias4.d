alias Fun = int(string p);
int fun(string){return 0;}
static assert(is(typeof(fun) == Fun));

alias MemberFun1 = int() const;
alias MemberFun2 = const int();
// leading attributes apply to the func, not the return type
static assert(is(MemberFun1 == MemberFun2));