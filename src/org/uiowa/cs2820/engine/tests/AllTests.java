package org.uiowa.cs2820.engine.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.uiowa.cs2820.engine.databases.tests.AVLFieldDatabaseTest;
import org.uiowa.cs2820.engine.databases.tests.BinaryTreeFieldDatabaseTest;
import org.uiowa.cs2820.engine.databases.tests.HashmapFieldDatabaseTest;
import org.uiowa.cs2820.engine.databases.tests.IdentifierDatabaseTest;

@RunWith(Suite.class)
@SuiteClasses({ AVLFieldDatabaseTest.class, AVLIteratorTest.class, BinaryTreeIteratorTest.class, BinaryTreeFieldDatabaseTest.class, FieldFileNodeTest.class,
        FieldTest.class, HashmapFieldDatabaseTest.class, IntegratedFileDatabaseTests.class, IntegratedFileDatabaseTestsWithRAFile.class,
        IdentifierDatabaseTest.class, IntegrationTests.class, IdentifierDatabaseTest.class, LinkedListIteratorTest.class, RAFileTest.class,
        ValueFileNodeTest.class })
public class AllTests
{

}
