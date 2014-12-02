package org.uiowa.cs2820.engine.fileoperations;

import org.uiowa.cs2820.engine.utilities.ByteConverter;

public class MockChunkRandomAccessFile extends ChunkedAccess
{
    private byte[] mockFile;
    
    public MockChunkRandomAccessFile(int initialNumChunks, int chunkSize)
    {
        super(initialNumChunks, chunkSize);
        mockFile = new byte[numberOfChunks * chunkSize];
    }
    
    @Override
    protected void getChunk(byte[] result, int chunkPosition)
    {
        System.arraycopy(mockFile, chunkPosition * chunkSize, result, 0, chunkSize);
    }

    @Override
    protected void setChunk(byte[] objectByteRepr, int chunkPosition)
    {
        System.arraycopy(objectByteRepr, 0, mockFile, chunkPosition * chunkSize, objectByteRepr.length);
    }

    protected void setCapacity(int length)
    {
        byte[] newArray = new byte[length];
        System.arraycopy(mockFile, 0, newArray, 0, mockFile.length);
        mockFile = newArray;
    }

    @Override
    public void free(int chunkPosition)
    {
        byte[] zeros = new byte[ByteConverter.EXISTS_SIZE];
        System.arraycopy(zeros, 0, mockFile, chunkPosition * chunkSize, zeros.length);
    }
    
    public String toString()
    {
        String result = "";
        for (int i = 0; i * chunkSize < mockFile.length; i++)
        {
            result += i + " : " + get(i) + "\n";
        }
        return result;
    }
}
