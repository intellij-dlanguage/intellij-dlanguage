module gold.highlighting.strings_escapes;

string a = "\&para;\U0001F603";
string b = "\101";
string c = "\xBBB";
string d = "test\nfoo\tbar";
string e = `\&para;\U0001F603test\nfoo\tbar\xBBB\101`;
string e = r"\&para;\U0001F603test\nfoo\tbar\xBBB\101";
string e = q"*\&para;\U0001F603test\nfoo\tbar\xBBB\101*";
