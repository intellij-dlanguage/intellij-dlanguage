int[string] aa;   // Associative array of ints that are
                  // indexed by string keys.
                  // The KeyType is string.
aa["hello"] = 3;  // set value associated with key "hello" to 3
int value = aa["hello"];  // lookup value from a key
assert(value == 3);