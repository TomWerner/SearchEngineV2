package org.uiowa.cs2820.engine.queries;

import org.uiowa.cs2820.engine.Field;

public interface FieldOperator
{

    public Boolean compare(Field field, Field testField);

}
