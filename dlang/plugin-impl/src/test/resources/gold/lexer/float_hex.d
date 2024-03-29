import std.stdio;

void main(){
  float a = 0x1f;
  float b = 0x1.FFFFFFFFFFFFFp1023; // double.max
  float c = 0x1p-52;                // double.epsilon
  float d = 1.175494351e-38F;       // float.min
}
