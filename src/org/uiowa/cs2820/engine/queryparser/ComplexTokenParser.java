package org.uiowa.cs2820.engine.queryparser;

import java.util.ArrayList;
import java.util.Stack;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.queries.DoubleQuery;
import org.uiowa.cs2820.engine.queries.FieldEquals;
import org.uiowa.cs2820.engine.queries.FieldOperator;
import org.uiowa.cs2820.engine.queries.OperatorFactory;
import org.uiowa.cs2820.engine.queries.Query;
import org.uiowa.cs2820.engine.queries.QueryOperator;
import org.uiowa.cs2820.engine.queries.QueryOr;
import org.uiowa.cs2820.engine.queries.Queryable;

public class ComplexTokenParser implements TokenParser
{
    public ComplexTokenParser()
    {
    }
    
    /* (non-Javadoc)
     * @see org.uiowa.cs2820.engine.queryparser.TokenParser#parseTokens(java.util.ArrayList)
     */
    @Override
    public Queryable parseTokens(ArrayList<Token> tokens) throws ParsingException
    {
        ArrayList<Object> queries = new ArrayList<Object>();

        int index = 0;
        try
        {
            // Always starts with a parenthesis
            while (tokenFound(Token.PAREN_START, index, tokens))
            {
                queries.add(tokens.get(index));
                index++;
            }
            
            if (tokens.get(index).getType() != Token.QUERY_START) // Only one query
            {
                Query query = parseSingleQuery(1, tokens.size() - 2, tokens);
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

                // There are still tokens left, -2 b/c we can ignore the last paren
                while (index < tokens.size() - 2)
                {
                    // Advance to the next token
                    index++;


                    while (tokenFound(Token.PAREN_END, index, tokens))
                    {
                        queries.add(tokens.get(index));
                        index++;
                    }
                    while (tokenFound(Token.PAREN_START, index, tokens))
                    {
                        queries.add(tokens.get(index));
                        index++;
                    }

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
                    queries.add(queryOp);
                    queries.add(query);
                }
            }
            
            // Always ends with a parenthesis
            if (expectedTokenFound(Token.PAREN_END, tokens.size() - 1, tokens))
            {
                queries.add(tokens.get(tokens.size() - 1));
                index++;
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            e.printStackTrace();
            throw createParseError("Index out of bounds", index, tokens);
        }

        return parseQueryList(queries);
    }
    
    protected Queryable parseQueryList(ArrayList<Object> queries)
    {
        // The way we turn our parenthesized objects in a a query
        // For example the list: (, a, or, (, b, or, c, ), ),
        // We push things onto a stack until we hit a right paren.
        // Then we pop them until we hit the left. That becomes a 
        // new list which is then converted to a double query and stuck back on the stack
        // So we hit the first right, our new list is (, b, or, c, ),
        // which becomes DoubleQuery(b, c, or)
        // Our stack is then (, a, or, DoubleQuery(b, c, or), ),
        // Which when we hit the right paren, becomes our list so we have
        // DoubleQuery(a, DoubleQuery(b, c, or), or), which is correct
        // In the case where we have more than two queries in our list, like
        // (, a, or, b, or, c, ),
        // while the list has more than one query we grab the first two and
        // replace them with a double query and so on
        Stack<Object> stack = new Stack<Object>();
        
        stack.push(queries.get(0)); //Stick the first paren on the stack
        int index = 1;
        
        // Loop until we hit a right paren
        System.out.println("Original: " + queries);
        while (index < queries.size() && !(queries.get(index) instanceof Token && ((Token)queries.get(index)).getType() == Token.PAREN_END))
        {
            stack.push(queries.get(index));
            index++;
        }
        Stack<Object> subStack = new Stack<Object>();
        System.out.println("Stack: " + stack);
        while (!(stack.peek() instanceof Token && ((Token)stack.peek()).getType() == Token.PAREN_START))
            subStack.add(stack.pop());
        stack.pop(); //remove the paren start
        System.out.println("List: " + subStack);
        
        // We now know our list is structured like < a, op, b, op, c, op, ...>
        if (subStack.size() == 1)
            return (Queryable) subStack.get(0); // Single Query
        else if (subStack.size() % 2 != 1)
            throw new IllegalStateException();

        Query q1 = (Query) subStack.pop();
        QueryOperator qOp = (QueryOperator) subStack.pop();
        Query q2 = (Query) subStack.pop();
        DoubleQuery query = new DoubleQuery(q1, q2, qOp);
        
        while (!subStack.isEmpty())
        {
            qOp = (QueryOperator) subStack.pop();
            q2 = (Query) subStack.pop();
            query = new DoubleQuery(query, q2, qOp);
        }
        
        return query;
        
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
    
    protected boolean tokenFound(int tokenType, int index, ArrayList<Token> tokens) throws ParsingException 
    {
        return tokens.get(index).getType() == tokenType;
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
