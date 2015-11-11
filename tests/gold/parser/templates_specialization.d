template TFoo(T)        {  } // #1
template TFoo(T : T[])  {  } // #2
template TFoo(T : char) {  } // #3
template TFoo(T, U, V)  {  } // #4

alias foo1 = TFoo!(int);            // instantiates #1
alias foo2 = TFoo!(double[]);       // instantiates #2 with T being double
alias foo3 = TFoo!(char);           // instantiates #3
alias fooe = TFoo!(char, int);      // error, number of arguments mismatch
alias foo4 = TFoo!(char, int, int); // instantiates #4