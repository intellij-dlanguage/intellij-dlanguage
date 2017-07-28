void *pc;

unittest
{
asm
{
    call L1          ;
  L1:                ;
    pop  EBX         ;
    mov  pc[EBP],EBX ; // pc now points to code at L1
}

asm
{
    db 5,6,0x83;   // insert bytes 0x05, 0x06, and 0x83 into code
    ds 0x1234;     // insert bytes 0x34, 0x12
    di 0x1234;     // insert bytes 0x34, 0x12, 0x00, 0x00
    dl 0x1234;     // insert bytes 0x34, 0x12, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    df 1.234;      // insert float 1.234
    dd 1.234;      // insert double 1.234
    de 1.234;      // insert real 1.234
    db "abc";      // insert bytes 0x61, 0x62, and 0x63
    ds "abc";      // insert bytes 0x61, 0x00, 0x62, 0x00, 0x63, 0x00
}

asm
{
    rep   ;
    movsb ;
}

asm
{
    rep  ;
    nop  ;
}
}
struct Foo { int a,b,c; }
int bar(Foo *f)
{
    asm
    {
        mov EBX,f                   ;
        mov EAX,Foo.b.offsetof[EBX] ;
    }
}
void main()
{
    Foo f = Foo(0, 2, 0);
    assert(bar(&f) == 2);
}

struct Foo   // or class
{
    int a,b,c;
    int bar()
    {
        asm
        {
            //mov EBX, this   ; //todo check if this is valid
            mov EAX, b[EBX] ;
        }
    }
}
void main()
{
    Foo f = Foo(0, 2, 0);
    assert(f.bar() == 2);
}

int foo(int x)
{
    asm
    {
        mov EAX,x[EBP] ; // loads value of parameter x into EAX
        mov EAX,x      ; // does the same thing
    }
}

