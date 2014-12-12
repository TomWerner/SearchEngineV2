package org.uiowa.cs2820.engine;

import java.util.List;
import java.util.ArrayList;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.uiowa.cs2820.engine.queries.Queryable;
import org.uiowa.cs2820.engine.queryparser.QueryParser;


@Path("/searchengine")
@Produces("text/html")
public class Handler {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("search")
	public String performSearch(
			@DefaultValue("") @QueryParam("query") String query
			) {
		QueryParser parser = new QueryParser(new ComplexTokenizer(), new ComplexTokenParser(), true);
		Queryable parsedQuery = parser.parseQuery(query);

	}

}
