SignExtendedNumber opSub(const SignExtendedNumber a) const {
    if (a.isMinimum())
        return negative ? SignExtendedNumber(value, false) : max();
    else
        return this + (-a);
}
