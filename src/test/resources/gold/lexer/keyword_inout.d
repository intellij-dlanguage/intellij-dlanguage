import std.stdio;

void main(){
  inout(int)[] foo(inout(int)[] a, int x, int y) { return a[x .. y]; }
}



