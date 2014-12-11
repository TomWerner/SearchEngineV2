package org.uiowa.cs2820.engine.queries;
import org.uiowa.cs2820.engine.Field;
public class FieldLessThan implements FieldOperator
{
	public String toString()
	{
		return "less than";
	}
    public Boolean compare(Field A, Field B)
    {
		if( A.compareTo(B) < 0 )
		{
			return true;
		}
		return false;
    }
}
