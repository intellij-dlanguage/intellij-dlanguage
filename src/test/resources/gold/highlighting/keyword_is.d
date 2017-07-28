import std.stdio;

void main(){
  shared int x;
  assert(is(typeof(cast(const)x) == const int));
}



int test(int[string] input){
    if("" is null){
        return !isSomeTemplateThingThatStartsWithIn!("");
    }
    return isSomeTemplateThingThatStartsWithIn!("");
}
