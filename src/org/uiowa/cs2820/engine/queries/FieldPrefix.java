package org.uiowa.cs2820.engine.queries;

import org.uiowa.cs2820.engine.Field;

public class FieldPrefix implements FieldOperator 
{
	public String toString()
	{
		return "starts with";
	}
    public Boolean compare(Field A, Field B)
    {
    	return A.equals(B);
    }
}
