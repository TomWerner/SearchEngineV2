package org.uiowa.cs2820.engine.queryparser;

import java.util.ArrayList;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.queries.FieldEquals;
import org.uiowa.cs2820.engine.queries.FieldOperator;
import org.uiowa.cs2820.engine.queries.MockMultipleQuery;
import org.uiowa.cs2820.engine.queries.OperatorFactory;
import org.uiowa.cs2820.engine.queries.Query;
import org.uiowa.cs2820.engine.queries.QueryOperator;
import org.uiowa.cs2820.engine.queries.QueryOr;
import org.uiowa.cs2820.engine.queries.Queryable;

public class QueryParser
{

    private Tokenizer tokenizer;

    public QueryParser()
    {
        tokenizer = new Tokenizer();
    }

    public Queryable parseExpression(String expression) throws ParsingException
    {
        ArrayList<Token> tokens = tokenizer.tokenize(expression);
        ArrayList<Query> queries = new ArrayList<Query>();

        int index = 0;
        Token current = tokens.get(index);
        try
        {
            if (current.getType() != Token.QUERY_START) // Only one query
            {
                Query query = parseSingleQuery(0, tokens.size() - 1, tokens);
                queries.add(query);
            }
            else
            {
                // Now we find the first query
                // Since index is at the QUERY_START, index + 1 is start
                int start = index + 1;
                while (current.getType() != Token.QUERY_END)
                {
                    index++;
                    current = tokens.get(index);
                }
                int stop = index - 1; // index is at QUERY_END
                Query query = parseSingleQuery(start, stop, tokens);
                queries.add(query);

                // There are still tokens left
                while (index < tokens.size() - 1)
                {
                    // Advance to the next token
                    index++;
                    current = tokens.get(index);

                    // Check to see if there is a specified query
                    QueryOperator queryOp = new QueryOr(); // TODO: Do something
                                                           // with this
                    if (current.getType() == Token.QUERY_OPERATOR)
                    {
                        queryOp = OperatorFactory.getQueryOperator(current.getString());
                        index++;
                        current = tokens.get(index);
                    }

                    // If we're not on a query, something went wrong
                    expectedTokenFound(Token.QUERY_START, index, tokens);

                    // Start is one past the query start
                    start = index + 1;

                    // Find the end
                    while (current.getType() != Token.QUERY_END)
                    {
                        index++;
                        current = tokens.get(index);
                    }
                    stop = index - 1; // index is at QUERY_END
                    query = parseSingleQuery(start, stop, tokens);
                    queries.add(query);
                }
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
            throw createParseError("Index out of bounds", index, tokens);
        }

        // TODO: Fix this
        if (queries.size() == 1)
            return queries.get(0);
        else
            return new MockMultipleQuery(queries);
    }

    /**
     * 
     * @param start
     *            First index of the query, excluding the QUERY_START token
     * @param stop
     *            Last index of the query, excluding the QUERY_STOP token
     * @param tokens
     *            Full list of tokens
     * @return The query created from the tokens
     * 
     *         Example: Tokens = [ "[", "equals", "(", "name", "value", ")", "]"
     *         ] The function call would be parseSingleQuery(1, 5, tokens)
     * @throws ParsingException
     */
    protected Query parseSingleQuery(int start, int stop, ArrayList<Token> tokens) throws ParsingException
    {
        int index = start;
        try
        {
            FieldOperator fieldOp = new FieldEquals();
            String fieldName = null;
            String fieldValue = null;

            // If an operator is specified
            if (tokens.get(index).getType() == Token.FIELD_OPERATOR)
            {
                fieldOp = OperatorFactory.getFieldOperator(tokens.get(index).getString());
                index++;
            }

            // If a field doesn't follow something went wrong
            if (expectedTokenFound(Token.FIELD_START, index, tokens))
                index++;

            // If a term isn't in the field something went wrong
            if (expectedTokenFound(Token.TERM, index, tokens))
            {
                fieldName = tokens.get(index).getString();
                index++;
            }

            // If a term isn't in the field something went wrong
            if (expectedTokenFound(Token.TERM, index, tokens))
            {
                fieldValue = tokens.get(index).getString();
                index++;
            }

            // If the field doesn't end something went wrong
            expectedTokenFound(Token.FIELD_END, index, tokens);

            // If there are still tokens left something went wrong
            if (index != stop)
                throw createParseError("Unexpected token after field end", index, tokens);

            // We made it!
            Query query = new Query(new Field(fieldName, fieldValue), fieldOp);
            return query;
        }
        catch (IndexOutOfBoundsException e)
        {
            throw createParseError("Index out of bounds", index, tokens);
        }
    }

    private boolean expectedTokenFound(int tokenType, int index, ArrayList<Token> tokens) throws ParsingException 
    {
    	if (tokens.get(index).getType() != tokenType)
            throw createParseError("Expected " + Token.getTypeName(tokenType), index, tokens);
        return true;
	}

	private ParsingException createParseError(String string, int index, ArrayList<Token> tokens)
    {
        String message = string;
        message += "\nTokens: " + tokens;
        message += "\nError at: " + index;
        return new ParsingException(message);
    }

}
