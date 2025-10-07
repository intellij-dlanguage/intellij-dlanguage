
package enum maxAlignment(U...) =
{
    size_t result = U[0].alignof;
    static foreach (T; U[1 .. $])
    if (result < T.alignof)
    result = T.alignof;
    return result;
}();