package org.uiowa.cs2820.engine.queryparser;

import java.util.ArrayList;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.queries.FieldEquals;
import org.uiowa.cs2820.engine.queries.FieldOperator;
import org.uiowa.cs2820.engine.queries.DoubleQuery;
import org.uiowa.cs2820.engine.queries.OperatorFactory;
import org.uiowa.cs2820.engine.queries.Query;
import org.uiowa.cs2820.engine.queries.QueryOperator;
import org.uiowa.cs2820.engine.queries.QueryOr;
import org.uiowa.cs2820.engine.queries.Queryable;

public class SimpleBadTokenParser implements TokenParser
{
    public SimpleBadTokenParser()
    {
    }
    
    @Override
    public boolean canHandleParenthesis()
    {
        return false;
    }



    /* (non-Javadoc)
     * @see org.uiowa.cs2820.engine.queryparser.TokenParser#parseTokens(java.util.ArrayList)
     */
    @Override
    public Queryable parseTokens(ArrayList<Token> tokens) throws ParsingException
    {
        ArrayList<Queryable> queries = new ArrayList<Queryable>();
        
        for (Token token : tokens)
            if (token.getType() == Token.PAREN_START || token.getType() == Token.PAREN_END)
                throw new UnsupportedOperationException("SimpleTokenParser doesn't handle parenthesized expressions");

        int index = 0;
        try
        {
            if (tokens.get(index).getType() != Token.QUERY_START) // Only one query
            {
                Query query = parseSingleQuery(0, tokens.size() - 1, tokens);
                queries.add(query);
            }
            else
            {
                // Now we find the first query
                // Since index is at the QUERY_START, index + 1 is start
                expectedTokenFound(Token.QUERY_START, index, tokens);
                int endPosition = findNextInstance(Token.QUERY_END, tokens, index + 1);
                Query query = parseSingleQuery(index + 1, endPosition - 1, tokens);
                index = endPosition;
                queries.add(query);

                // There are still tokens left
                while (index < tokens.size() - 1)
                {
                    // Advance to the next token
                    index++;

                    // Check to see if there is a specified query
                    QueryOperator queryOp = new QueryOr();
                    if (tokens.get(index).getType() == Token.QUERY_OPERATOR)
                    {
                        queryOp = OperatorFactory.getQueryOperator(tokens.get(index).getString());
                        index++;
                    }

                    // If we're not on a query, something went wrong
                    expectedTokenFound(Token.QUERY_START, index, tokens);
                    endPosition = findNextInstance(Token.QUERY_END, tokens, index + 1);
                    query = parseSingleQuery(index + 1, endPosition - 1, tokens);
                    index = endPosition;
                    queries.set(0, new DoubleQuery(queries.get(0), query, queryOp));
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
            return null; //Something went wrong, fix this
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

    protected boolean expectedTokenFound(int tokenType, int index, ArrayList<Token> tokens) throws ParsingException 
    {
        if (tokens.get(index).getType() != tokenType)
            throw createParseError("Expected " + Token.getTypeName(tokenType), index, tokens);
        return true;
    }

    protected ParsingException createParseError(String string, int index, ArrayList<Token> tokens)
    {
        String message = string;
        message += "\nTokens: " + tokens;
        message += "\nError at: " + index;
        return new ParsingException(message);
    }
    
    protected int findNextInstance(int tokenType, ArrayList<Token> tokens, int startIndex)
    {
        // Find the end
        while (tokens.get(startIndex).getType() != tokenType)
            startIndex++;
        return startIndex;
    }
}
