package org.uiowa.cs2820.engine.queries.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;
import org.uiowa.cs2820.engine.queries.FieldEquals;
import org.uiowa.cs2820.engine.queries.FieldFuzzySearch;
import org.uiowa.cs2820.engine.queries.FieldGreaterThan;
import org.uiowa.cs2820.engine.queries.FieldIncludes;
import org.uiowa.cs2820.engine.queries.FieldLessThan;
import org.uiowa.cs2820.engine.queries.FieldOperator;
import org.uiowa.cs2820.engine.queries.FieldPostfix;
import org.uiowa.cs2820.engine.queries.FieldPrefix;
import org.uiowa.cs2820.engine.queries.Query;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenParser;
import org.uiowa.cs2820.engine.queryparser.ComplexTokenizer;
import org.uiowa.cs2820.engine.queryparser.ParsingException;
import org.uiowa.cs2820.engine.queryparser.QueryParser;

public class QueryParserFieldOperatorIntegrationTest 
{
	@Test
	public void testOperatorParsing() throws ParsingException
	{
		HashMap<String, FieldOperator> mapping = new HashMap<String, FieldOperator>();
		mapping.put("equals", 		new FieldEquals());
		mapping.put("equal", 		new FieldEquals());
		mapping.put("greaterthan",  new FieldGreaterThan());
		mapping.put(">", 			new FieldGreaterThan());
		mapping.put("lesserthan",   new FieldLessThan());
		mapping.put("<", 			new FieldLessThan());
		mapping.put("includes", 	new FieldIncludes());
		mapping.put("startswith",   new FieldPrefix());
		mapping.put("prefix",       new FieldPrefix());
		mapping.put("endswith",     new FieldPostfix());
		mapping.put("postfix",      new FieldPostfix());
		
		QueryParser parser = new QueryParser(new ComplexTokenizer(), new ComplexTokenParser(), true);
		for (String key : mapping.keySet())
		{
			String queryString = String.format("%s (\"Term1\", \"Term2\")",key);
			Query query = (Query) parser.parseQuery(queryString);
			assertTrue(mapping.get(key).getClass().equals(query.getOperator().getClass()));
		}
	}
	
	@Test
	public void parsingFuzzyOperator() throws ParsingException
	{
		QueryParser parser = new QueryParser(new ComplexTokenizer(), new ComplexTokenParser(), true);
		String queryString = "~3(\"Term\", \"Value\")";
		Query query = (Query) parser.parseQuery(queryString);
		FieldFuzzySearch operator = (FieldFuzzySearch) query.getOperator();
		assertEquals(3, operator.getFuzz());
	}
}
