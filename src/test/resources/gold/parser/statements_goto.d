unittest
{
 if (foo)
        goto L1;
    x = 3;
L1:
    x++;



switch (x)
{
    case 3:
        goto case;
    case 4:
        goto default;
    case 5:
        goto case 4;
    default:
        x = 4;
        break;
}
}
