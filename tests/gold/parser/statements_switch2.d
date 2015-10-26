int number;
string message;
switch (number)
{
    default:    // valid: ends with 'throw'
        throw new Exception("unknown number");

    case 3:     // valid: ends with 'break' (break out of the 'switch' only)
        message ~= "three ";
        break;

    case 4:     // valid: ends with 'continue' (continue the enclosing loop)
        message ~= "four ";
        continue;

    case 5:     // valid: ends with 'goto' (explicit fall-through to next case.)
        message ~= "five ";
        goto case;

    case 6:     // ERROR: implicit fall-through
        message ~= "six ";

    case 1:     // valid: the body is empty
    case 2:     // valid: this is the last case in the switch statement.
        message = "one or two";
}