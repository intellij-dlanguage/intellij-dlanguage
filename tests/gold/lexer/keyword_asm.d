void *pc;
asm
{
    call L1          ;
  L1:                ;
    pop  EBX         ;
    mov  pc[EBP],EBX ; // pc now points to code at L1
}
