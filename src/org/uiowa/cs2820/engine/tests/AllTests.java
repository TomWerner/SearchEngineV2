package org.uiowa.cs2820.engine.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AVLIteratorTest.class, BinaryTreeIteratorTest.class, FieldFileNodeTest.class, FieldTest.class, IntegratedFileDatabaseTests.class,
        IntegratedFileDatabaseTestsWithRAFile.class, IntegrationTests.class, LinkedListIteratorTest.class, RAFileTest.class,
        ValueFileNodeTest.class, org.uiowa.cs2820.engine.databases.tests.AllTests.class, org.uiowa.cs2820.engine.queryparser.tests.AllTests.class})
public class AllTests
{

}
