package org.uiowa.cs2820.engine.fileoperations;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RAFile extends ChunkedAccess
{
	protected File FILE;
	
	public RAFile( File fileName, int initialNumChunks, int chunkSize) 
	{
		// gives me chunkSize and numberOfChunks (power of 2)
		super(initialNumChunks, chunkSize);
		this.FILE = fileName;
		setCapacity(numberOfChunks * chunkSize);
	}

	@Override
	protected void getChunk(byte[] result, int chunkPosition) 
	{
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(FILE, "r");
			file.seek(chunkPosition * chunkSize);
			file.read(result);
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	protected void setChunk(byte[] objectByteRepr, int chunkPosition) {
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(FILE, "rw");
			file.seek(chunkPosition * chunkSize);
			file.write(objectByteRepr);
		    file.close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void free(int chunkPosition)
	{
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(FILE, "rws"); // "rws" synchronizes underlying storage device
			file.seek(chunkPosition * chunkSize);
			file.write(new byte[2]); 
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	@Override
	protected void setCapacity(int length) 
	{
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(FILE, "rw");
			file.setLength(length); 
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
