package org.uiowa.cs2820.engine.queryparser.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ComplexTokenParserTest.class, ComplexTokenizerTest.class, SimpleQueryParserTest.class,
        SimpleTokenParserTest.class, SimpleTokenizerTest.class, SimpleBadTokenParserTest.class })
public class AllTests
{

}
