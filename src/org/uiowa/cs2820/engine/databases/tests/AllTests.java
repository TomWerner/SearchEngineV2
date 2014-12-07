package org.uiowa.cs2820.engine.databases.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AVLFieldDatabaseTest.class, BinaryTreeFieldDatabaseTest.class, HashmapFieldDatabaseTest.class, IdentifierDatabaseTest.class })
public class AllTests
{

}
