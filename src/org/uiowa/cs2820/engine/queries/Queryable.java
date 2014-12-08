package org.uiowa.cs2820.engine.queries;

import org.uiowa.cs2820.engine.Field;

public interface Queryable
{
    public boolean isSatisfiedBy(Field testField);
}
