import std.file;         // D file I/O
import std.stdio;
import std.ascii;

void main (string[] args)
{
    ulong totalWords, totalLines, totalChars;
    ulong[string] dictionary;

    writeln("   lines   words   bytes file");
    foreach (arg; args[1 .. $]) // for each argument except the first one
    {
        ulong wordCount, lineCount, charCount;

        foreach(line; File(arg).byLine())
        {
            bool inWord;
            size_t wordStart;

            void tryFinishWord(size_t wordEnd)
            {
                if (inWord)
                {
                    auto word = line[wordStart .. wordEnd];
                    ++dictionary[word.idup];   // increment count for word
                    inWord = false;
                }
            }

            foreach (i, char c; line)
            {
                if (std.ascii.isDigit(c))
                {
                    // c is a digit (0..9)
                }
                else if (std.ascii.isAlpha(c))
                {
                    // c is an ASCII letter (A..Z, a..z)
                    if (!inWord)
                    {
                        wordStart = i;
                        inWord = true;
                        ++wordCount;
                    }
                }
                else
                    tryFinishWord(i);
                ++charCount;
            }
            tryFinishWord(line.length);
            ++lineCount;
        }

        writefln("%8s%8s%8s %s", lineCount, wordCount, charCount, arg);
        totalWords += wordCount;
        totalLines += lineCount;
        totalChars += charCount;
    }

    if (args.length > 2)
    {
        writefln("-------------------------------------\n%8s%8s%8s total",
                 totalLines, totalWords, totalChars);
    }

    writeln("-------------------------------------");
    foreach (word; dictionary.keys.sort)
    {
        writefln("%3s %s", dictionary[word], word);
    }
}
