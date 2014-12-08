package org.uiowa.cs2820.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import org.uiowa.cs2820.engine.databases.AVLFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;
import org.uiowa.cs2820.engine.databases.ValueFileNode;
import org.uiowa.cs2820.engine.fileoperations.RAFile;
import org.uiowa.cs2820.engine.queries.FieldIdentifierPair;
import org.uiowa.cs2820.engine.queries.Queryable;

/**
 * This class combines the FieldDatabase and IdentiferDatabase to implement the
 * functionality specified in the Database interface.
 * 
 * @author Tom
 * 
 */
public class IntegratedFileDatabase implements Database
{
    private FieldDatabase fieldDB;
    private IdentifierDatabase identDB;

    public IntegratedFileDatabase(FieldDatabase fieldDB, IdentifierDatabase identDB)
    {
        this.fieldDB = fieldDB;
        this.identDB = identDB;
    }

    /**
     * A default constructor that uses the RAFile so that the users don't NEED
     * to have any knowledge of the underlying database structure
     */
    public IntegratedFileDatabase()
    {
        this(new AVLFieldDatabase(new RAFile(new File("data/fieldDatabase.dat"), 16, FieldFileNode.MAX_SIZE)), 
                new IdentifierDatabase(new RAFile(new File("data/identifierDatabase.dat"), 16, ValueFileNode.MAX_SIZE)));
    }

    @Override
    public ArrayList<String> fetch(Field field)
    {
        int position = fieldDB.getIdentifierPosition(field);

        // If position is -1 it didn't find the field
        if (position == -1)
            return null;

        return identDB.getAllIdentifiers(position);
    }

    @Override
    public void delete(Field field, String identifier)
    {
        int position = fieldDB.getIdentifierPosition(field);

        // If position is -1 we didn't find the field
        if (position == -1)
            return;

        int location = identDB.removeIdentifier(position, identifier);

        // if location isn't NULL_ADDRESS it means we have a new
        // position for the identifer linked list head node and need to adjust
        // it
        // accordingly
        if (location != ValueFileNode.NULL_ADDRESS)
            fieldDB.setIdentifierPosition(field, location);
    }

    @Override
    public void store(Field field, String identifier)
    {
        // Check to see if its in the database already
        int linkedListHeadPosition = fieldDB.getIdentifierPosition(field);

        // If it isn't in the database
        if (linkedListHeadPosition == -1)
        {
            linkedListHeadPosition = identDB.addIdentifier(identifier);
            fieldDB.add(new FieldFileNode(field, linkedListHeadPosition));
        }
        else
        {
            linkedListHeadPosition = identDB.addIdentifier(linkedListHeadPosition, identifier);
            fieldDB.setIdentifierPosition(field, linkedListHeadPosition);
        }
    }
    
    public ArrayList<FieldIdentifierPair> matchQuery(Queryable queryable)
    {
        ArrayList<FieldIdentifierPair> result = new ArrayList<FieldIdentifierPair>();
        
        Iterator<FieldFileNode> iter = fieldDB.iterator();
        while (iter.hasNext())
        {
            FieldFileNode node = iter.next();
            if (queryable.isSatisfiedBy(node.getField()))
                for (String identifier : identDB.getAllIdentifiers(node.getHeadOfLinkedListPosition()))
                    result.add(new FieldIdentifierPair(node.getField(), identifier));
        }
        
        return result;
    }
    
    public String toString()
    {
        return fieldDB.toString() + "\n\n" + identDB.toString();
    }

}
