
int i = <error descr="Hexadecimal numbers must contain at least one hexadecimal digit">0x</error>;
int i = <error descr="Binary numbers must contain at least one binary digit">0b</error>;
int i = <error descr="Value too large">999999999999999999999999999999999999999999999</error>;

// valid values
int i = 0xFF;
int i = 0;
int i = 0b1;

