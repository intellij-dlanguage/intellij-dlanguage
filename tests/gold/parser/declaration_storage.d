struct S
{
    int x;
    int method() const
    {
	//x++;    // Error: this method is const and cannot modify this.x
        return x; // OK: we can still read this.x
    }
}