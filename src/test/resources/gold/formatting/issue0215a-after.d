module example;

bool aTemplatedFunction(One)(One alpha) if (isNumeric!One) {
}

unittest {
    {
        bool anotherTemplatedFunction(One, Two, Three)(One alpha, Two bravo,
                Three charlie, double delta)
                if (isNumeric!One && isNumeric!Two && isNumeric!Three && echo
                    && foxtrot && golf && hotel && india && juliet) {

        }
    }
}
