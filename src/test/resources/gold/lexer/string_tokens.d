import std.stdio;

void main(){
  string a = q{*};
  string b = q{this is the voice of}; // "this is the voice of"
  string c = q{/*}*/ };               // "/*}*/ "
  string d = q{ world(q{control}); } ;// " world(q{control}); "
  string e = q{ __TIME__ };           // " __TIME__ " i.e. it is not replaced with the time
}
