struct ClassFlags
 {
     alias Type = uint;
     enum Enum : int
     {
        isCOMclass = 0x1,
        noPointers = 0x2,
        hasOffTi = 0x4,
        hasCtor = 0x8,
        hasGetMembers = 0x10,
        hasTypeInfo = 0x20,
        isAbstract = 0x40,
        isCPPclass = 0x80,
        hasDtor = 0x100,

     }

     alias isCOMclass = Enum.isCOMclass;
    alias noPointers = Enum.noPointers;
    alias hasOffTi = Enum.hasOffTi;
    alias hasCtor = Enum.hasCtor;
    alias hasGetMembers = Enum.hasGetMembers;
    alias hasTypeInfo = Enum.hasTypeInfo;
    alias isAbstract = Enum.isAbstract;
    alias isCPPclass = Enum.isCPPclass;
    alias hasDtor = Enum.hasDtor;


 }
