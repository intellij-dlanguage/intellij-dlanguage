module foo;

string getName(string name){
  return name;
}

void main(){
  string name1 = <caret>getName("Kingsley");
}