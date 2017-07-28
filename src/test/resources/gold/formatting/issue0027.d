SignExtendedNumber opMul(const SignExtendedNumber a) const
{
	//  like 0x10 * 0x10 == 0x100 == 0.

	/* Special handling for zeros:
    */
}

unittest
{
	if (Port.isNan(r1) || Port.isNan(r2)) // if unordered
	{
	}

	while (cur && cur.ty == Tsarray)
		// sizes of dimensions
	{
		TypeSArray sa = cast(TypeSArray) cur;
		mangleNumber(sa.dim ? sa.dim.toInteger() : 0);
		cur = cur.nextOf();
	}

	if (fd)
	{
		/* Use storage_class2 instead of storage_class otherwise when we do .di generation
		* we'll wind up with 'const const' rather than 'const'.
		*/
		/* Don't think we need to worry about mutually exclusive storage classes here
		*/
		fd.storage_class2 |= stc;
	}

}
SignExtendedNumber opMul(const SignExtendedNumber a) const
{
    /* Special handling for zeros:
    */

    //  like 0x10 * 0x10 == 0x100 == 0.
}

 // Because int64_t and friends may be any integral type of the
 // correct size, we have to explicitly ask for the correct
 // integer type to get the correct mangling with ddmd

 // Be careful not to care about sign when using dinteger_t
 // use this instead of integer_t to
 // avoid conflicts with system #include's
