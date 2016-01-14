public static void runTests(TestSelection[] testSelections, TestListener[] testListeners)
in
{
    assert(all!"a !is null"(testListeners));
}
body
{
    bool tryRun(string phase, void delegate() action)
    {
        try
        {
            action();
            return true;
        }
        catch (AssertException exception)
        {
            foreach (testListener; testListeners)
                testListener.addFailure(phase, exception);
            return false;
        }
        catch (Throwable throwable)
        {
            foreach (testListener; testListeners)
                testListener.addError(phase, throwable);
            return false;
        }
    }

    foreach (testSelection; testSelections) with (testSelection)
    {
        foreach (testListener; testListeners)
            testListener.enterClass(testClass.name);

        Object testObject = null;
        bool classSetUp = true;  // not yet failed

        // run each @Test of the class
        foreach (test; tests)
        {
            bool success = true;
            bool ignore = cast(bool)(test in testClass.ignores);

            foreach (testListener; testListeners)
                testListener.enterTest(test);
            scope (exit)
                foreach (testListener; testListeners)
                    testListener.exitTest(success);

            // create test object on demand
            if (!ignore && testObject is null)
            {
                if (classSetUp)
                {
                    classSetUp = tryRun("this",
                            { testObject = testClass.create(); });
                }
                if (classSetUp)
                {
                    classSetUp = tryRun("@BeforeClass",
                            { testClass.beforeClass(testObject); });
                }
            }

            if (ignore || !classSetUp)
            {
                string reason = testClass.ignores.get(test, Ignore.init).reason;

                foreach (testListener; testListeners)
                    testListener.skip(reason);
                success = false;
                continue;
            }

            success = tryRun("@Before",
                    { testClass.before(testObject); });

            if (success)
            {
                success = tryRun("@Test",
                        { testClass.test(testObject, test); });
                // run @After even if @Test failed
                success = tryRun("@After",
                        { testClass.after(testObject); })
                        && success;
            }
        }

        if (testObject !is null && classSetUp)
        {
            tryRun("@AfterClass",
                    { testClass.afterClass(testObject); });
        }
    }

    foreach (testListener; testListeners)
        testListener.exit();
}