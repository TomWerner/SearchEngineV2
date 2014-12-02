package org.uiowa.cs2820.engine.databases;

import java.nio.ByteBuffer;

import org.uiowa.cs2820.engine.utilities.ByteConverter;

public class ValueFileNode
{
    /*
     * Constants used to conversion to a byte array
     */
    public static final int MAX_SIZE = 256;
    public static final String NULL_VALUE = null;
    private static final int INTEGER_SIZE = Integer.SIZE / Byte.SIZE;
    private static final int ADDRESS_POSITION = ByteConverter.EXISTS_POSITION + ByteConverter.EXISTS_SIZE;
    private static final int NEXT_NODE_POSITION = ADDRESS_POSITION + INTEGER_SIZE;
    private static final int IDENTIFIER_SIZE_POSITION = NEXT_NODE_POSITION + INTEGER_SIZE;
    private static final int IDENTIFIER_POSITION = IDENTIFIER_SIZE_POSITION + INTEGER_SIZE;
    private static final int MAX_IDENTIFIER_SIZE = MAX_SIZE - INTEGER_SIZE * 3 - ByteConverter.EXISTS_SIZE;
    
    /**
     * Null address - this node doesn't point anywhere
     */
    public static final int NULL_ADDRESS = -1;

    /**
     * The position of the next ValueFileNode
     */
    private int nextNode;
    
    /**
     * The string identifier of this node
     */
    private String identifier;
    
    /**
     * The chunk position of this node in the database
     */
    private int address;

    /**
     * Create a new ValueFileNode with a given identifier
     * @param identifier the identifer for the node
     */
    public ValueFileNode(String identifier)
    {
        nextNode = NULL_ADDRESS;
        setIdentifier(identifier);
    }

    /**
     * Create a new ValueFileNode with a given identifier that points to a given position
     * @param identifier The idntifier for the node
     * @param nextNode The location of the next node in the linked list
     */
    public ValueFileNode(String identifier, int nextNode)
    {
        this.nextNode = nextNode;
        setIdentifier(identifier);
    }

    public int getNextNode()
    {
        return nextNode;
    }

    public void setNextNode(int nextNode)
    {
        this.nextNode = nextNode;
    }

    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
        convert();
    }

    public int getAddress()
    {
        return address;
    }
    
    public void setAddress(int address)
    {
        this.address = address;
    }

    public String toString()
    {
        return identifier + "@ chunk " + address + ". Points to " + nextNode;
    }

    /**
     * Convert this object to a byte array
     */
    public byte[] convert()
    {
        byte[] result = new byte[MAX_SIZE];

        for (int i = 0; i < ByteConverter.EXISTS_SIZE; i++)
            result[i + ByteConverter.EXISTS_POSITION] = ByteConverter.VALUE_FILE_NODE[i];
        
        byte[] addrSection = ByteBuffer.allocate(INTEGER_SIZE).putInt(address).array();
        byte[] nextNodeSection = ByteBuffer.allocate(INTEGER_SIZE).putInt(nextNode).array();
        byte[] identSection = new byte[0];
        if (identifier != null)
             identSection = identifier.getBytes();
        byte[] identSectionLength = ByteBuffer.allocate(INTEGER_SIZE).putInt(identSection.length).array(); 
        
        if (identSection.length > MAX_IDENTIFIER_SIZE)
            throw new IllegalArgumentException("Identifier is too long");
        
        System.arraycopy(addrSection, 0, result, ADDRESS_POSITION, addrSection.length);
        System.arraycopy(nextNodeSection, 0, result, NEXT_NODE_POSITION, nextNodeSection.length);
        System.arraycopy(identSectionLength, 0, result, IDENTIFIER_SIZE_POSITION, identSectionLength.length);
        System.arraycopy(identSection, 0, result, IDENTIFIER_POSITION, identSection.length);
        
        return result;
    }

    /**
     * Convert a given byte array to a new ValueFileNode
     * @param byteArray The byte array to revert
     * @return The new ValueFileNode
     */
    public static Object revert(byte[] byteArray)
    {
        if (byteArray.length != MAX_SIZE)
            throw new IllegalArgumentException("Byte array is not the correct size");
        
        byte[] addressSection = new byte[INTEGER_SIZE];
        System.arraycopy(byteArray, ADDRESS_POSITION, addressSection, 0, INTEGER_SIZE);
        ByteBuffer wrapped = ByteBuffer.wrap(addressSection);
        int address = wrapped.getInt();
        
        byte[] nextNodeSection = new byte[INTEGER_SIZE];
        System.arraycopy(byteArray, NEXT_NODE_POSITION, nextNodeSection, 0, INTEGER_SIZE);
        wrapped = ByteBuffer.wrap(nextNodeSection);
        int nextNode = wrapped.getInt();
        
        byte[] identSectionLength = new byte[INTEGER_SIZE];
        System.arraycopy(byteArray, IDENTIFIER_SIZE_POSITION, identSectionLength, 0, INTEGER_SIZE);
        wrapped = ByteBuffer.wrap(identSectionLength);
        int identLength = wrapped.getInt();
        
        byte[] identSection = new byte[identLength];
        System.arraycopy(byteArray, IDENTIFIER_POSITION, identSection, 0, identSection.length);
        String ident;
        if (identLength == 0)
            ident = null;
        else
            ident = new String(identSection);
        
        ValueFileNode node = new ValueFileNode(ident, nextNode);
        node.setAddress(address);
        
        return node;
    }
    
    public boolean equals(Object other)
    {
        if (other instanceof ValueFileNode)
        {
            ValueFileNode o = (ValueFileNode)other;
            return identifier.equals(o.identifier) && address == o.address && nextNode == o.nextNode;
        }
        return false;
    }
    
    
}
