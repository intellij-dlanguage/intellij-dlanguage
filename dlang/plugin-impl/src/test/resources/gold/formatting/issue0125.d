void main(string[] args)
{
    // dfmt off
    getopt(args,
        "optionOne", &optionOne,
        "optionTwo", &optionTwo,
        "optionThree", &optionThree);
    // dfmt on

    getopt(args, "optionOne", &optionOne, "optionTwo", &optionTwo, "optionThree", &optionThree);
}
