package org.uiowa.cs2820.engine;

import java.io.File;
import java.util.ArrayList;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.uiowa.cs2820.engine.databases.AVLFieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldDatabase;
import org.uiowa.cs2820.engine.databases.FieldFileNode;
import org.uiowa.cs2820.engine.databases.IdentifierDatabase;
import org.uiowa.cs2820.engine.databases.ValueFileNode;
import org.uiowa.cs2820.engine.fileoperations.RAFile;
import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenParser;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenizer;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.QueryParser;

@Path("/searchengine")
@Produces("text/html")
public class Handler
{
    @GET
    @Path("search")
    public String performSearch(@DefaultValue("") @QueryParam("query") String query) throws ParsingException
    {
        QueryParser parser = new QueryParser(new ComplexTokenizer(), new ComplexTokenParser(), true);
        Queryable parsedQuery = parser.parseQuery(query);
        Database database = getDatabase();
        ArrayList<String> result = database.matchQuery(parsedQuery);
        return this.toHTML(result);
    }
    
    private String toHTML(ArrayList<String> idents) {
    	String result = "<div><ul>";
    	for(String e: idents) {
    		result = result + "<li>" + e + "</li>"; 
    	}
    	result = result + "</ul></div>";
    	return result;
    }

    private Database getDatabase()
    {
        FieldDatabase fieldDB = new AVLFieldDatabase(new RAFile(new File("data/musicFieldDatabase.dat"), 128, FieldFileNode.MAX_SIZE));
        IdentifierDatabase identDB = new IdentifierDatabase(new RAFile(new File("data/musicIdentifierDatabase.dat"), 128, ValueFileNode.MAX_SIZE));
        return new IntegratedFileDatabase(fieldDB, identDB);
    }

}
