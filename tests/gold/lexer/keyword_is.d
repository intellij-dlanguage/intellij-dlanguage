import std.stdio;

void main(){
  shared int x;
  assert(is(typeof(cast(const)x) == const int));
}



