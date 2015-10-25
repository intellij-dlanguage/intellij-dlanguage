extern (C):
    int foo(); // call foo() with C conventions

extern (D):

extern (Windows):
    void *VirtualAlloc(
        void *lpAddress,
        uint dwSize,
        uint flAllocationType,
        uint flProtect
    );