
// issue #76
immutable trst = ["some", "any",]; // error
enum SE
{
one,
two, // error
}

//the following are not asssociated with any specific issue:

void foo(){
        switch(gh){
            case 0: .. case 9:
                //do stuff
                i++;
                break;
        }

}


private template foo()
{
    static if (bar == bar2) enum bar3 = foo();
    else {
           enum mid = (low + high) / 2;
           //do some stuff
           int i = 0;
    }
}

enum var1;
enum var1 = 6;
enum var;

void bar(){
    switch(foo){
        default:
    }
    foreach(string k, ref const Json v; value){}
}

@name!CustomPolicy("PrintSize")

unittest{
    enum int foo = 0;
}

struct Bar{
    int i;
    char c;
    string s;
}

Bar bar = {9,'c',"hgh",};

private void foo(in Type[] argument...) const{}

const(T) opt(T)(const(T) def = const(T).init)
