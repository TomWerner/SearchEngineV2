package org.uiowa.cs2820.engine.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.utilities.ByteConverter;

public class FieldTest
{

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThrowsForTooLongOnValue()
    {
        new Field("Iowa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorThrowsForTooLongOnName()
    {
        new Field("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "Iowa");
    }

    @Test
    public void testComparisonMatchingFieldName()
    {
        Field field1 = new Field("name", "a");
        Field field2 = new Field("name", "b");

        assertTrue(field1.compareTo(field1) == 0);
        assertTrue(field1.compareTo(field2) == -1);
        assertTrue(field2.compareTo(field1) == 1);
    } 

    @Test
    public void testComparisonDifferentFieldName()
    {
        Field field1 = new Field("fieldA", "value");
        Field field2 = new Field("fieldB", "value");

        assertTrue(field1.compareTo(field1) == 0);
        assertTrue(field1.compareTo(field2) == -1);
        assertTrue(field2.compareTo(field1) == 1);
    }

    @Test
    public void testToAndFromByteArray()
    {
        Field field1 = new Field("value", "name");
        byte[] byteRepr = field1.convert();
        Field revertedField = (Field) (ByteConverter.revert(byteRepr));
        assertEquals(field1, revertedField);
    }
}
