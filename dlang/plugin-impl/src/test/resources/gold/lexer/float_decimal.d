import std.stdio;

void main(){
  float a = 1.2;
  float b = 1.;
  float c = 4._a;//should not be all one float literal
  float d = 2.645_751;
  float e = 6.022140857E+23;
  float f = 6_022.140857E+20;
  float g = 6_022_.140_857E+20_;
  float h = 1_000_000.12;
}
