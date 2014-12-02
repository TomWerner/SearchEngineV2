package org.uiowa.cs2820.engine.utilities;

import java.util.Arrays;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.databases.ValueFileNode;

public class ByteConverter
{
    
    /*
     * These byte arrays identify the contents of a chunk.
     * To check if a chunk is occupied we check to see
     * the value of the first two bytes of the chunk.
     * 
     * If the first two bytes are [0, 1], [1, 1], or [1, 0]
     * then we know the chunk is occupied. If the first two
     * bytes are [0, 0] then we treat the entire chunk as empty.
     * 
     * This helps with quickly determining if a chunk is empty or not.
     */
    public static final byte[] BINARY_FILE_NODE = { 0, 1 };
    public static final byte[] VALUE_FILE_NODE = { 1, 1 };
    public static final byte[] FIELD = { 1, 0 };
    
    
    public static final int EXISTS_POSITION = 0;
    public static final int EXISTS_SIZE = 2;
    
    
    public static Object revert(byte[] byteRepr)
    {
        byte[] objType = new byte[EXISTS_SIZE];
        System.arraycopy(byteRepr, EXISTS_POSITION, objType, 0, EXISTS_SIZE);
        
        if (Arrays.equals(objType, BINARY_FILE_NODE))
            return FieldFileNode.revert(byteRepr);
        else if (Arrays.equals(objType, VALUE_FILE_NODE))
            return ValueFileNode.revert(byteRepr);
        else if (Arrays.equals(objType, FIELD))
            return Field.revert(byteRepr);
        else //The first two bytes are [0, 0]
            return null;
    }
}
