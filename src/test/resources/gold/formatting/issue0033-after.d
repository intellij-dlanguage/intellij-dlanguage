static IntRange fromType(Type type, bool isUnsigned) {
    if (type.toBasetype().ty == Tdchar)
        upper.value = 0x10FFFFUL;
    else if (!isUnsigned) {
        lower.value = ~(mask >> 1);
        lower.value = ~(mask >> 1);
        lower.negative = true;
        upper.value = (mask >> 1);
    }
    uinteger_t minHalfChunk = imin.value & ~halfChunkMask;
    uinteger_t maxHalfChunk = imax.value & ~halfChunkMask;
    return IntRange(lower, upper);
}
