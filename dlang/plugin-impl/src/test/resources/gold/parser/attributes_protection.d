//module A;
//private class Foo {}

module B;
public class Foo {}

import A;
import B;

Foo f1; // error, could be either A.Foo or B.Foo
B.Foo f2; // ok
