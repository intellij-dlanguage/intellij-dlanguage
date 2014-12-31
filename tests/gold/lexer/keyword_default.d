import std.stdio;

void main(){
  switch (x)
  {
      case 3:
          goto case;
      case 4:
          goto default;
      case 5:
          goto case 4;
      default:
          x = 4;
          break;
  }
}


