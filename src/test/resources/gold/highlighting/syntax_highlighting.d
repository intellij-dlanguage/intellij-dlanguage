module syntax_highlighting.cool.nice_yeah;

import std.stdio;

class Apple
{

    string var1;
    int var2;

    public string[] myArray()
    {
        return [var1, var2];
    }

    public bool myBool(string name, int age)
    {
        if (age > 20)
        {
            return true;
        }
        else
        {
            writeln(name);
            return false;
        }

    }

}

struct Pear
{
    int age;
    string name;
}

void main()
{
    Apple apple = new Apple();
    Pear pear = Pear(1, "pear");
    apple.myArray();
    string age = 21;
    apple.myBool("name", age);
    pear.name;
}
