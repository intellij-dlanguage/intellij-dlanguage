import std.stdio;

void main(){
  string a = q{*};
  string b = q{this is the voice of}; // "this is the voice of"
  string c = q{/*}*/ };               // "/*}*/ "
  string d = q{ world(q{control}); } ;// " world(q{control}); "
  string e = q{ __TIME__ };           // " __TIME__ " i.e. it is not replaced with the time
  string f = q{ foo(xxx) };           // " foo(xxx) "
  string g = q{foo$(LPAREN)};         // "foo$(LPAREN)"
  string h = q{{foo}"}"};             // "{foo}"}""
  string i = q{".-,.-`{-.c-u"};       // "",-,.-`{-,c-u""
  string k = q{test}w;
}
