package org.uiowa.cs2820.engine.queries.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.queries.*;

public class FieldOperatorTest {

Field A, B;
FieldOperator op;
	@Test
	public void FieldEqualsReturnsTrue()
	{
		op = new FieldEquals();
		A = new Field("part","screw");
		B = new Field("part","screw");
		assertTrue(op.compare(A, B));
	}
	@Test
	public void FieldEqualsReturnsFalse()
	{
		op = new FieldEquals();
		A = new Field("part","skrew");
		B = new Field("part","screw");
		assertFalse(op.compare(A, B));
	}
	@Test
	public void FieldGreaterThanReturnsTrue()
	{
		op = new FieldGreaterThan();
		A = new Field("part","screw");
		B = new Field("part","hammer");
		assertTrue(op.compare(A, B));
	}
	@Test
	public void FieldGreaterThanReturnsFalse()
	{
		op = new FieldGreaterThan();
		A = new Field("part","lights");
		B = new Field("part","muffler");
		assertFalse(op.compare(A, B));
	}
	@Test
	public void FieldLessThanReturnsTrue()
	{
		op = new FieldLessThan();
		A = new Field("part","ruler");
		B = new Field("part","saw");
		assertTrue(op.compare(A, B));
	}
	@Test
	public void FieldLessThanReturnsFalse()
	{
		op = new FieldLessThan();
		A = new Field("food","jello");
		B = new Field("food","carrot");
		assertFalse(op.compare(A, B));
	}
	@Test
	public void FieldIncludesReturnsTrue()
	{
		op = new FieldIncludes();
		A = new Field("book","Treasure Island");
		B = new Field("book","sure");
		assertTrue(op.compare(A, B));
	}
	@Test
	public void FieldIncludesReturnsFalse()
	{
		op = new FieldIncludes();
		A = new Field("book","To Kill a Mockingbird");
		B = new Field("book","Island");
		assertFalse(op.compare(A, B));
	}
	@Test
	public void FieldPrefixReturnsTrue()
	{
		op = new FieldPrefix();
		A = new Field("book","Treasure Island");
		B = new Field("book","Treasure");
		assertTrue(op.compare(A, B));
	}
	@Test
	public void FieldPrefixReturnsFalse()
	{
		op = new FieldPrefix();
		A = new Field("book","Game of Thrones");
		B = new Field("book","of");
		assertFalse(op.compare(A, B));
	}
	@Test
	public void FieldPostfixReturnsTrue()
	{
		op = new FieldPostfix();
		A = new Field("book","Game of Thrones");
		B = new Field("book","Thrones");
		assertTrue(op.compare(A, B));
	}
	@Test
	public void FieldPostfixReturnsFalse()
	{
		op = new FieldPostfix();
		A = new Field("book","The Hobbit");
		B = new Field("book","The");
		assertFalse(op.compare(A, B));
	}
	
}
