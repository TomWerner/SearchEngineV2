package org.uiowa.cs2820.engine.databases;

import java.util.ArrayList;
import java.util.Iterator;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;

public class HashmapFieldDatabase extends FieldDatabase
{

    public HashmapFieldDatabase(ChunkedAccess fileHandle)
    {
        super(fileHandle);
    }

    @Override
    public void add(FieldFileNode node)
    {
        int hash = node.hashCode();
        int position = getFileHandle().getNumberOfChunks();
        int oldNumChunks = position;
        position = Math.abs(hash % position);
        if (node.equals(getFileHandle().get(position)))
        {
            return;
        }
        else if (getFileHandle().get(position) == null)
        {
            getFileHandle().set(node.convert(), position);
        }
        else
        {
            int result = getFileHandle().nextAvailableChunk(position);
            if (getFileHandle().getNumberOfChunks() > oldNumChunks)
            {
                ArrayList<FieldFileNode> nodes = new ArrayList<FieldFileNode>();
                for (int i = 0; i < oldNumChunks; i++)
                {
                    FieldFileNode tempNode = (FieldFileNode) getFileHandle().get(i);
                    if (tempNode != null)
                    {
                        nodes.add(tempNode);
                        getFileHandle().free(i);
                    }
                }
                for (FieldFileNode toAdd : nodes)
                {
                    add(toAdd);
                }
                add(node);
            }
            else
            {
                getFileHandle().set(node.convert(), result);
            }
        }
    }

    @Override
    public int getIdentifierPosition(Field field)
    {
        int hash = field.hashCode();
        int position = getFileHandle().getNumberOfChunks();
        position = Math.abs(hash % position);

        FieldFileNode currentNode = (FieldFileNode) getFileHandle().get(position);

        while (currentNode != null && !currentNode.getField().equals(field) && position < getFileHandle().getNumberOfChunks())
        {
            position++;
            currentNode = (FieldFileNode) getFileHandle().get(position);
        }

        if (currentNode == null || position >= getFileHandle().getNumberOfChunks())
        {
            return -1;
        }
        return currentNode.getHeadOfLinkedListPosition();
    }

    @Override
    public void setIdentifierPosition(Field field, int headOfLinkedListPosition)
    {
        int hash = field.hashCode();
        int position = getFileHandle().getNumberOfChunks();
        position = Math.abs(hash % position);

        FieldFileNode currentNode = (FieldFileNode) getFileHandle().get(position);

        while (currentNode != null && !currentNode.getField().equals(field) && position < getFileHandle().getNumberOfChunks())
        {
            position++;
            currentNode = (FieldFileNode) getFileHandle().get(position);
        }

        if (currentNode == null || position >= getFileHandle().getNumberOfChunks())
        {
            return;
        }

        currentNode.setHeadOfLinkedListPosition(headOfLinkedListPosition);
        getFileHandle().set(currentNode.convert(), position);

    }

    @Override
    public void removeElement(int lastReturned)
    {
        return;
    }

    @Override
    public Iterator<Field> iterator()
    {
        return null;
    }
    
    

}
