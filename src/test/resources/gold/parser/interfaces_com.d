import core.sys.windows.windows;
import core.stdc.windows.com;

interface IText
{
    void write();
}

abstract class Printer : IText
{
    void print() { }
}

class C : Printer, IUnknown
{
    // Implements the IText $(D write) class method.
    extern(D) void write() { }

    // Overrides the Printer $(D print) class method.
    extern(D) override void print() { }

    // Overrides the Object base class $(D toString) method.
    extern(D) override string toString() { return "Class C"; }

    // Methods of class implementing the IUnknown interface have
    // the extern(System) calling convention by default.
    HRESULT QueryInterface(const(IID)*, void**);
    uint AddRef();
    uint Release();
}
