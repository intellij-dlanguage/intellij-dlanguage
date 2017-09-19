scoped import on enum with enum members not resolved



```d
shared static this()
{
    CACHELIMIT = core.cpuid.datacache[0].size*1024/2;
}
```
