package org.uiowa.cs2820.engine.databases;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListIterator implements Iterator<String>
{
    private IdentifierDatabase database;
    private int nextPosition;
    private int lastReturned = -1;

    public LinkedListIterator(IdentifierDatabase database, int headPosition)
    {
        this.database = database;
        this.nextPosition = headPosition;
    }

    @Override
    public boolean hasNext()
    {
        ValueFileNode next = (ValueFileNode) database.getElementAt(nextPosition);
        return nextPosition != ValueFileNode.NULL_ADDRESS && next != null && next.getIdentifier() != ValueFileNode.NULL_VALUE;
    }

    @Override
    public String next()
    {
        if (!hasNext())
            throw new NoSuchElementException();
        ValueFileNode next = (ValueFileNode) database.getElementAt(nextPosition);
        if (next == null)
            throw new NoSuchElementException();

        nextPosition = next.getNextNode();
        lastReturned = next.getAddress();
        
        return next.getIdentifier();
    }

    @Override
    public void remove()
    {
        if (lastReturned < 0)
            throw new IllegalStateException();
        
        database.removeElement(lastReturned);
        nextPosition = lastReturned;
        lastReturned = -1;

    }

}
