package org.uiowa.cs2820.engine.databases;

import java.util.ArrayList;
import java.util.Iterator;

import org.uiowa.cs2820.engine.fileoperations.ChunkedAccess;

/**
 * This database holds all of the identifiers that have been added to our
 * database. It uses a ChunkedAccess file to store the identifiers, represented
 * below. A ChunkedAccess file allows a user to: get(chunkPosition) - get the
 * object at a specific chunk set(objectByteRepr, chunkPosition) - set a
 * specific chunk to an object's byte representation free(chunkPosition) -
 * delete the data at a specific chunk nextAvailableChunk() - get the next open
 * chunk These are the methods that this class will use to store identifiers.
 * 
 * Identifiers are stored using ValueFileNode's, a class which holdes 3 things:
 * address - the location the node has within the database nextNode - the
 * location of the next node in a linked list identifier - the identifier of the
 * node
 * 
 * When a new node is added using addIdentifier(identifier) the database creates
 * a new node with: address = the next available chunk of the ChunkedAccess file
 * nextNode = NULL_ADDRESS (it doesn't point to anything) identifier = the
 * identifier specified. The function then returns address to be used as a
 * pointer to the linked list.
 * 
 * When a new node is added using addIdentifier(linkedListHeadPosition,
 * identifier) The database loads the node at the given head position. It then
 * creates a new node with: address = next available chunk of ChunkedAccess
 * nextNode = address of the previous head identifier = the identifier specified
 * 
 * ---- Example ----
 * 
 * location = add("file") location is now 0
 * +----------+----------+----------+----------+----------+ | addr = 0 | | | | |
 * | next = -1| | | | | | id = file| | | | |
 * +----------+----------+----------+----------+----------+
 * 
 * location = add(location, "file2") location is now 1
 * +----------+----------+----------+----------+----------+ | addr = 0 | addr =
 * 1 | | | | | next = -1| next = 0 | | | | | id = file| id =file2| | | |
 * +----------+----------+----------+----------+----------+
 * 
 * 
 * 
 * The function getAllIdentifiers(location) starts at the head of the list and
 * iterates through it until it hits the end, NULL_ADDRESS.
 * 
 * The function removeIdentifier(linkedListHeadPosition, identifier) does
 * standard linked list removal of an identifier.
 * 
 */
public class IdentifierDatabase
{
    private ChunkedAccess fileHandle;

    /**
     * Create a new IdentifierDatabase, a structure to store and manipulate
     * identifiers, stored as linked lists.
     * 
     * @param fileHandle
     *            the ChunkedAccess file to store identifier data
     */
    public IdentifierDatabase(ChunkedAccess fileHandle)
    {
        this.fileHandle = fileHandle;
    }

    /**
     * Add a new identifier to a given linked list
     * 
     * @param linkedListHeadPosition
     *            head of the linked list
     * @param identifier
     *            identifier to add
     * @return the new linkedListHeadPosition
     */
    public int addIdentifier(int linkedListHeadPosition, String identifier)
    {
        ValueFileNode node = new ValueFileNode(identifier);
        ValueFileNode existingNode = (ValueFileNode) fileHandle.get(linkedListHeadPosition);
        // If it already exists, make the new node point to the existing one
        // Then find the next available spot to stick our new node and change
        // the address to that, then add normally.
        if (existingNode != null)
        {
            if (node.getNextNode() != ValueFileNode.NULL_ADDRESS)
                throw new IllegalAccessError();
            node.setNextNode(existingNode.getAddress());
            linkedListHeadPosition = fileHandle.nextAvailableChunk();
        }

        node.setAddress(linkedListHeadPosition);
        fileHandle.set(node.convert(), linkedListHeadPosition);

        return linkedListHeadPosition;
    }

    /**
     * Add new identifier without an existing linked list
     * 
     * @param identifier
     *            the identifier to add
     * @return the head of the new linked list
     */
    public int addIdentifier(String identifier)
    {
        int addrOfIdentifier = fileHandle.nextAvailableChunk();
        ValueFileNode node = new ValueFileNode(identifier);
        node.setAddress(addrOfIdentifier);
        fileHandle.set(node.convert(), addrOfIdentifier);

        return addrOfIdentifier;
    }

    /**
     * Remove a given identifier from a given linked list of identifiers
     * 
     * @param linkedListHeadPosition
     *            position of the linked list head
     * @param identifier
     *            identifier to remove
     * @return -1 if the head did not change, otherwise the new head of the
     *         linked list
     */
    public int removeIdentifier(int linkedListHeadPosition, String identifier)
    {
        ValueFileNode start = (ValueFileNode) fileHandle.get(linkedListHeadPosition);
        if (start == null)
            return ValueFileNode.NULL_ADDRESS;
        if (start.getIdentifier().equals(identifier))
        {
            int next = start.getNextNode();
            fileHandle.free(linkedListHeadPosition);
            return next;
        }
        else
        {
            Iterator<String> iterator = iterator(linkedListHeadPosition);
            while (iterator.hasNext())
            {
                String ident = iterator.next();
                if (ident.equals(identifier))
                {
                    iterator.remove();
                    break;
                }
            }

            return ValueFileNode.NULL_ADDRESS;
        }
    }

    /**
     * Get all of the identifier strings in a linked list of identifiers
     * 
     * @param linkedListPositionHead
     *            The head of the linked list
     * @return an ArrayList of the identifier strings
     */
    public ArrayList<String> getAllIdentifiers(int linkedListPositionHead)
    {
        ArrayList<String> result = new ArrayList<String>();

        Iterator<String> iter = iterator(linkedListPositionHead);

        while (iter.hasNext())
            result.add(iter.next());

        return result;
    }

    public ValueFileNode getElementAt(int index)
    {
        return (ValueFileNode) fileHandle.get(index);
    }

    public Iterator<String> iterator(int headIndex)
    {
        return new LinkedListIterator(this, headIndex);
    }

    public String toString()
    {
        return fileHandle.toString();
    }

    public void removeElement(int index)
    {
        ValueFileNode node = (ValueFileNode) fileHandle.get(index);

        // We have two cases:
        // If node has a "next node" then we just copy the value of
        // next node into this node and adjust the next pointer
        // accordingly
        // If the node doesn't have a next node then its at the end
        // of the list. What we can do is null out the value and
        // adjust the code for the iterator to ignore this value.
        // Because we only add from the head this works.
        if (node.getNextNode() != ValueFileNode.NULL_ADDRESS)
        {
            // Case 1
            ValueFileNode next = (ValueFileNode) fileHandle.get(node.getNextNode());
            node.setIdentifier(next.getIdentifier());
            node.setNextNode(next.getNextNode());
            fileHandle.free(next.getAddress());
        }
        else
        {
            // Case 2
            node.setIdentifier(ValueFileNode.NULL_VALUE);
        }
        fileHandle.set(node.convert(), node.getAddress());
    }
}
