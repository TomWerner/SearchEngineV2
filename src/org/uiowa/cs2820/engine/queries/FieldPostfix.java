package org.uiowa.cs2820.engine.queries;

import org.uiowa.cs2820.engine.Field;

public class FieldPostfix implements FieldOperator 
{

	public String toString()
	{
		return "ends with";
	}
	public Boolean compare(Field A, Field B) 
	{
		if (A.getFieldName().equals(B.getFieldName()))
		{
			int postfixLength = A.getFieldValue().toString().length();
    		if ( A.toString().substring(postfixLength).equals(B.toString()))
    		{
    			return true;
    		}
		}
		return false;
	}

}
