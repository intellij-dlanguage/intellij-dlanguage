int gethardware()
{
    asm
    {
        mov EAX, dword ptr 0x1234;
    }
}