package org.uiowa.cs2820.engine.queries;
import org.uiowa.cs2820.engine.Field;
public interface FieldOperator
{
	public String toString();
	public Boolean compare(Field A, Field B);
}
