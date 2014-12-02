package org.uiowa.cs2820.engine.fileoperations;

import org.uiowa.cs2820.engine.utilities.ByteConverter;

/**
 * This class defines how this program expects to interact with a file.
 * It assumes the ability to change the size of the file, and read and write
 * byte arrays to it.
 * 
 * It structures the file into "chunks" which can be of any size, and any number.
 * The number of chunks must always be a power of two, to match the behavior of RandomAccessFile.
 * 
 * Its main public methods are:
 *      get(chunkPosition)                  - get the object at a specific chunk
 *      set(objectByteRepr, chunkPosition)  - set a specific chunk to an object's byte representation
 *      free(chunkPosition)                 - delete the data at a specific chunk
 *      nextAvailableChunk()                - get the next open chunk
 *      doubleCapacity()                    - double the capacity of the file
 * 
 * The files subclasses need to implement are:
 *      getChunk(byte[] result, int chunkPosition)      - puts the byte content at chunkPosition into result
 *      setChunk(byte[] objectByteRepr, chunkPosition)  - put the contents of objectByteRepr into the file at chunkPosition
 *      free(chunkPosition)                             - delete the contents of the file at chunkPosition
 *      internalDoubleCapacity()                        - double the contents of of the file, but without modifying number of chunks
 *                                                        doubleCapacity() calls this and also double numberOfChunks
 */
public abstract class ChunkedAccess
{
    protected int chunkSize;
    protected int numberOfChunks;

    public ChunkedAccess(int initialNumChunks, int chunkSize)
    {
        this.chunkSize = chunkSize;
        numberOfChunks = 1;
        while (numberOfChunks < initialNumChunks)
            numberOfChunks <<= 1;
    }

    
    /*
     * ------------------------------------------------------------------------
     *                          Methods to implement
     * ------------------------------------------------------------------------
     */
    
    /**
     * Put the contents of the specified chunk into result
     * @param result the byte array to put the chunk into
     * @param chunkPosition the chunk to load data from
     */
    protected abstract void getChunk(byte[] result, int chunkPosition);
    
    /**
     * Put the contents of the given byte array into the specified chunk
     * @param objectByteRepr The byte array to to put into the chunk
     * @param chunkPosition The chunk to put the data in
     */
    protected abstract void setChunk(byte[] objectByteRepr, int chunkPosition);
    
    /**
     * Delete the contents of the given chunk
     * @param chunkPosition The chunk to delete data from
     */
    public abstract void free(int chunkPosition);
    
    /**
     * Double the size of the current file and transfer the old content to the doubled file if necessary.
     * This method DOES NOT change the value of number of chunks
     */
    protected abstract void setCapacity(int lengthOfFile);
    
    
    /*
     * ------------------------------------------------------------------------
     *                          Existing methods
     * ------------------------------------------------------------------------
     */
    
    /**
     * Get the object at a specified chunk
     * @param chunkPosition The chunk to get an object from
     * @return The object at chunkPositioon
     */
    public Object get(int chunkPosition)
    {
        byte[] result = new byte[chunkSize];
        if (isChunkPositionOutOfBounds(chunkPosition))
            return null;
        getChunk(result, chunkPosition);
        return ByteConverter.revert(result);
    }

    /**
     * Sets a given chunk to the byte array given. This will increase the size capacity
     * of the file until it chunkPosition is valid.
     * @param objectByteRepr The object's byte array to store
     * @param chunkPosition The chunk position to store the byte array at
     */
    public void set(byte[] objectByteRepr, int chunkPosition)
    {
        if (objectByteRepr.length > chunkSize)
            throw new IllegalArgumentException("Byte array is larger than chunk size");

        while (isChunkPositionOutOfBounds(chunkPosition))
        {
            doubleCapacity();
        }

        setChunk(objectByteRepr, chunkPosition);
    }

    /**
     * Find the first open chunk in the file
     * @return the chunk position of the first open chunk
     */
    public int nextAvailableChunk()
    {
        for (int i = 0; i < numberOfChunks; i++)
            if (get(i) == null)
                return i;
        int result = numberOfChunks;
        doubleCapacity();
        return result;
    }

    public int nextAvailableChunk(int start)
    {
        for (int i = start; i < numberOfChunks; i++)
            if (get(i) == null)
                return i;
        int result = numberOfChunks;
        doubleCapacity();
        return result;
    }

    /**
     * Double the capacity of the file.
     */
    public void doubleCapacity()
    {
        numberOfChunks *= 2;
        setCapacity(numberOfChunks * chunkSize);
    }
    
    public int getNumberOfChunks()
    {
        return numberOfChunks;
    }
    
    /**
     * Check the validity of a given chunk position. If its negative throw an ArrayIndexOutOfBoundsException
     * @param chunkPosition the chunk position to check
     * @return
     */
    protected boolean isChunkPositionOutOfBounds(int chunkPosition)
    {
        return chunkPosition >= numberOfChunks || chunkPosition < 0;
    }
}
