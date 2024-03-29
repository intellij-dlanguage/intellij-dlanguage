import std.stdio;

void main(){
  string a = q"/*/";
  a = q"FOO
FOO";
  a = q"_test_";
  a = q"test";
  a = q"/test/test/";
  a = q"(test)";
  a = q"]test]";
}
