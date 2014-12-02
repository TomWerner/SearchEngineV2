package org.uiowa.cs2820.engine;

import java.nio.ByteBuffer;

import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.utilities.ByteConverter;
import org.uiowa.cs2820.engine.utilities.Utilities;

@SuppressWarnings( { "rawtypes" })
public class Field implements Comparable<Field>
{
    private static final int INTEGER_SIZE = Integer.SIZE / Byte.SIZE;
    private static final int MAX_NAME_SIZE = FieldFileNode.MAX_FIELD_SIZE / 4 - INTEGER_SIZE - ByteConverter.EXISTS_SIZE;
    private static final int MAX_VALUE_SIZE = FieldFileNode.MAX_FIELD_SIZE / 2 * 3 - INTEGER_SIZE;
    private static final int NAME_LENGTH_POSITION = ByteConverter.EXISTS_POSITION + ByteConverter.EXISTS_SIZE;
    private static final int NAME_POSITION = NAME_LENGTH_POSITION + INTEGER_SIZE;
    private static final int VALUE_LENGTH_POSITION = NAME_POSITION + MAX_NAME_SIZE;
    private static final int VALUE_POSITION = VALUE_LENGTH_POSITION + INTEGER_SIZE;
    
    private String fieldName;
    private Comparable value;

    // constructor for Fields with String
    public Field(String fieldName, Comparable value) throws IllegalArgumentException
    {
        this.fieldName = fieldName;
        this.value = value;
        convert();
        return;
    }

    public String getFieldName()
    {
        return fieldName;
    }
 
    public Comparable getFieldValue()
    {
        return value;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public int compareTo(Field o)
    {
        if (fieldName.equals(o.fieldName))
        {
            return ((Comparable)value).compareTo(o.value);
        }
        return fieldName.compareTo(o.fieldName);
    }
    
    public boolean equals(Object other)
    {
        if (other instanceof Field)
            return ((Field)other).toString().equals(toString());
        return false;
    }
    
    public String toString()
    {
        return "(" + fieldName + " : " + value + ")";
    }

    public byte[] convert()
    {
        byte[] result = new byte[FieldFileNode.MAX_FIELD_SIZE];
        
        for (int i = 0; i < ByteConverter.EXISTS_SIZE; i++)
            result[i + ByteConverter.EXISTS_POSITION] = ByteConverter.FIELD[i];
        
        byte[] nameSection = fieldName.getBytes();
        byte[] nameSectionLength = ByteBuffer.allocate(INTEGER_SIZE).putInt(nameSection.length).array();
        byte[] valueSection = Utilities.convert(value);
        byte[] valueSectionLength = ByteBuffer.allocate(INTEGER_SIZE).putInt(valueSection.length).array();
        
        if (valueSection.length > MAX_VALUE_SIZE)
            throw new IllegalArgumentException("Value is too large");
        if (nameSection.length > MAX_NAME_SIZE)
            throw new IllegalArgumentException("Name is too large");
        
        System.arraycopy(nameSectionLength, 0, result, NAME_LENGTH_POSITION, nameSectionLength.length);
        System.arraycopy(nameSection, 0, result, NAME_POSITION, nameSection.length);
        System.arraycopy(valueSectionLength, 0, result, VALUE_LENGTH_POSITION, valueSectionLength.length);
        System.arraycopy(valueSection, 0, result, VALUE_POSITION, valueSection.length);
        return result;
    }

    public static Object revert(byte[] byteArray)
    {
        byte[] nameSectionLength = new byte[INTEGER_SIZE];
        System.arraycopy(byteArray, NAME_LENGTH_POSITION, nameSectionLength, 0, INTEGER_SIZE);
        ByteBuffer wrapped = ByteBuffer.wrap(nameSectionLength);
        int nameLength = wrapped.getInt();
        
        byte[] nameSection = new byte[nameLength];
        System.arraycopy(byteArray, NAME_POSITION, nameSection, 0, nameSection.length);
        String fieldName = new String(nameSection);
        
        
        
        byte[] valueSectionLength = new byte[INTEGER_SIZE];
        System.arraycopy(byteArray, VALUE_LENGTH_POSITION, valueSectionLength, 0, INTEGER_SIZE);
        wrapped = ByteBuffer.wrap(valueSectionLength);
        int valueLength = wrapped.getInt();
        
        byte[] valueSection = new byte[valueLength];
        System.arraycopy(byteArray, VALUE_POSITION, valueSection, 0, valueSection.length);
        Comparable comp = (Comparable) Utilities.revert(valueSection);
        
        return new Field(fieldName, comp);
    }
    
    public int hashCode()
    {
    	return toString().hashCode();
    }
    
    
}
