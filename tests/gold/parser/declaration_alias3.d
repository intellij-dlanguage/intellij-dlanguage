version (Win32)
 {
     alias myfoo = win32.foo;
 }
 version (linux)
 {
     alias myfoo = linux.bar;
 }

 alias strlen = string.strlen;

 alias abc = foo.bar; // is it a type or a symbol?

 struct S { static int i; }
 S s;

 alias a = s.i; // illegal, s.i is an expression
 alias b = S.i; // ok
 b = 4;         // sets S.i to 4

 