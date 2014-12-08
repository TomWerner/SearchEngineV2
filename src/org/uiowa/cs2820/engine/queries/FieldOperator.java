package org.uiowa.cs2820.engine.queries;

import org.uiowa.cs2820.engine.Field;

public interface FieldOperator
{

    public boolean isSatisfiedBy(Field field, Field testField);

}
