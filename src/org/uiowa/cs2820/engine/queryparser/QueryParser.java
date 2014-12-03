package org.uiowa.cs2820.engine.queryparser;

import java.util.ArrayList;

import org.uiowa.cs2820.engine.Field;
import org.uiowa.cs2820.engine.queries.FieldEquals;
import org.uiowa.cs2820.engine.queries.FieldOperator;
import org.uiowa.cs2820.engine.queries.OperatorFactory;
import org.uiowa.cs2820.engine.queries.Query;
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
                FieldOperator fieldOp = new FieldEquals();
                String fieldName;
                String fieldValue;

                if (current.getType() == Token.FIELD_OPERATOR) // Not the
                                                               // default
                {
                    fieldOp = OperatorFactory.getFieldOperator(current.getString());
                    index++;
                    current = tokens.get(index);
                }

                if (current.getType() != Token.FIELD_START)
                    throw createParseError("Expected field start", index, tokens);
                else
                {
                    index++;
                    current = tokens.get(index);
                }

                if (current.getType() != Token.TERM) // Expecting field
                    throw createParseError("Expected a term", index, tokens);
                else
                {
                    fieldName = current.getString();
                    index++;
                    current = tokens.get(index);
                }

                if (current.getType() != Token.TERM) // Expecting field
                    throw createParseError("Expected a term", index, tokens);
                else
                {
                    fieldValue = current.getString();
                    index++;
                    current = tokens.get(index);
                }

                if (current.getType() != Token.FIELD_END)
                    throw createParseError("Expected field end", index, tokens);

                if (index != tokens.size() - 1)
                    throw createParseError("Unexpected token after field end", index, tokens);

                // We made it!
                Query query = new Query(new Field(fieldName, fieldValue), fieldOp);
                queries.add(query);
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            throw createParseError("Index out of bounds", index, tokens);
        }

        return queries.get(0);
    }

    private ParsingException createParseError(String string, int index, ArrayList<Token> tokens)
    {
        String message = string;
        message += "\nTokens: " + tokens;
        message += "\nError at: " + index;
        return new ParsingException(message);
    }

}
