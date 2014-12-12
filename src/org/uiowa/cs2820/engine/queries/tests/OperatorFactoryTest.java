package org.uiowa.cs2820.engine.queries.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.uiowa.cs2820.engine.queries.*;

public class OperatorFactoryTest {

	
	@Test
	public void getFieldOperatorReturnsFieldEquals() 
	{
		String operator = "Equals";
		FieldOperator opClass = OperatorFactory.getFieldOperator(operator);
		assertTrue(opClass instanceof FieldEquals);
	}
	@Test
	public void getFieldOperatorReturnsFieldGreaterThan() 
	{
		String operator = ">";
		FieldOperator opClass = OperatorFactory.getFieldOperator(operator);
		assertTrue(opClass instanceof FieldGreaterThan);
	}
	@Test
	public void getFieldOperatorReturnsFieldLessThan() 
	{
		String operator = "<";
		FieldOperator opClass = OperatorFactory.getFieldOperator(operator);
		assertTrue(opClass instanceof FieldLessThan);
	}
	@Test
	public void getFieldOperatorReturnsFieldIncludes() 
	{
		String operator = "includes";
		FieldOperator opClass = OperatorFactory.getFieldOperator(operator);
		assertTrue(opClass instanceof FieldIncludes);
	}
	@Test
	public void getFieldOperatorReturnsFieldPrefix() 
	{
		String operator = "starts with";
		FieldOperator opClass = OperatorFactory.getFieldOperator(operator);
		assertTrue(opClass instanceof FieldPrefix);
	}
	@Test
	public void getFieldOperatorReturnsFieldPostfix() 
	{
		String operator = "ends with";
		FieldOperator opClass = OperatorFactory.getFieldOperator(operator);
		assertTrue(opClass instanceof FieldPostfix);
	}
	@Test
	public void getFieldOperatorReturnsFieldFuzzySearch() 
	{
		String operator = "~1";
		FieldOperator opClass = OperatorFactory.getFieldOperator(operator);
		assertTrue(opClass instanceof FieldFuzzySearch);
	}
	@Test
	public void getFieldOperatorFieldFuzzySearchParsedCorrectly() 
	{
		String operator = "~3";
		FieldFuzzySearch opClass = (FieldFuzzySearch)OperatorFactory.getFieldOperator(operator);
		assertTrue(opClass.getFuzz() == 3);
	}
	@Test
	public void getFieldOperatorReturnsNull() 
	{
		String operator = "AnOperatorThatDoesNotExist";
		FieldOperator opClass = OperatorFactory.getFieldOperator(operator);
		assertTrue(opClass == null);
	}

}
