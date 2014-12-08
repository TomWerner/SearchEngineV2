package org.uiowa.cs2820.engine.databases;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;


public abstract class FieldDatabase implements Iterable<FieldFileNode>
{
    protected ChunkedAccess fileHandle;

    /**
     * Create a new FieldDatabase with a given ChunkedAccess file. It stores
     * fields using a binary tree.
     * 
     * @param fileHandle
     *            The file to store fields in
     */
    public FieldDatabase(ChunkedAccess fileHandle)
    {
        this.fileHandle = fileHandle;
    }

    /**
     * Add a node to the database
     * 
     * @param node
     *            the node to add
     */
    public abstract void add(FieldFileNode node);

    /**
     * Get the position of the head of the linked list of identifiers for the
     * given field
     * 
     * @param field
     *            The field to search for
     * @return the corresponding identifier linked list head position
     */
    public abstract int getIdentifierPosition(Field field);

    /**
     * Set the position of the corresponding identifier's linked list head
     * position for a given field
     * 
     * @param field
     *            The field to change
     * @param headOfLinkedListPosition
     *            The new position of the identifier linked list
     */
    public abstract void setIdentifierPosition(Field field, int headOfLinkedListPosition);
    
    public FieldFileNode getElementAt(int index)
    {
        return (FieldFileNode) getFileHandle().get(index); 
    }
    
    protected ChunkedAccess getFileHandle()
    {
        return fileHandle;
    }

    public abstract void removeElement(int index); 
}