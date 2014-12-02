package org.uiowa.cs2820.engine.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class Utilities
{
    public static byte[] convert(Object O)
    {
        // private method converts objects into byte array
        ByteArrayOutputStream M = new ByteArrayOutputStream();
        ObjectOutput N = null;
        try
        {
            N = new ObjectOutputStream(M);
            N.writeObject(O);
        }
        catch (IOException e)
        {
            return null; // wrong, but should not happen
        }
        return M.toByteArray();
    }

    public static Object revert(byte[] seq)
    {
        Object O = null; // default value
        try
        {
            ByteArrayInputStream M = new ByteArrayInputStream(seq);
            ObjectInputStream N = new ObjectInputStream(M);
            O = N.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.err.println("Error reverting byte array");
            System.err.println(Arrays.toString(e.getStackTrace()));
        };
        return O;
    }
}
