module test;
import std.stdio;
import std.math;

void main(string[] args){
    writeln("something");
}


class A{
    this(){
    }
}

template temp(type){
    type a;
}

mixin temp!int;
