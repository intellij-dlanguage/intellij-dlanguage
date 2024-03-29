unittest
{
	switch (cast(uint) sz)
	{
	case 3:
		if (!global.params.is64bit)
			goto Lmemory;
	case 4:
		t1 = Type.tint32;
		break;
	case 5:
		if (!global.params.is64bit)
			goto Lmemory;
	default:
		break;
	}
}
