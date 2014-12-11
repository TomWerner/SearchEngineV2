package org.uiowa.cs2820.engine.queries;
import org.uiowa.cs2820.engine.Field;
public class FieldIncludes implements FieldOperator
{
	public String toString()
	{
		return "includes";
	}
    public Boolean compare(Field A, Field B)
    {
    	if ( A.getFieldName().equals(B.getFieldName()) )
    	{
    		if ( A.getFieldValue().toString().contains(B.getFieldValue().toString()) )
    		{
    			return true;
    		}
    	}
    	return false;
    }
}
