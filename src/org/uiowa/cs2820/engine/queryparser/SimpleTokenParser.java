package org.uiowa.cs2820.engine.queryparser;

import java.util.ArrayList;
import java.util.EmptyStackException;
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

public class SimpleTokenParser
{
    public Queryable parseTokens(ArrayList<Token> tokens) throws ParsingException
    {
        try
        {
        Stack<Object> stack = createQueryOperatorStack(tokens);
        stack = setupQueryOperatorStack(stack);
        return parseQueryOperatorStack(stack);
        }
        catch (EmptyStackException | ClassCastException e)
        {
            throw new ParsingException(e);
        }
    }

    protected Stack<Object> setupQueryOperatorStack(Stack<Object> stack)
    {
        // Reverse the stack for the order
        Stack<Object> reverse = new Stack<Object>();
        while (!stack.isEmpty())
            reverse.push(stack.pop());
        return reverse;
    }

    protected Stack<Object> createQueryOperatorStack(ArrayList<Token> tokens)
    {
        Stack<Object> stack = new Stack<Object>();

        int index = 0;
        while (index < tokens.size())
        {
            Token token = tokens.get(index);

            if (token.getType() == Token.FIELD_END)
            {
                Stack<Object> substack = new Stack<Object>();

                while (!(stack.peek() instanceof Token && ((Token) stack.peek()).getType() == Token.FIELD_START))
                    substack.push(stack.pop());

                if (!stack.isEmpty() && stack.peek() instanceof Token && ((Token) stack.peek()).getType() == Token.FIELD_START)
                    stack.pop();

                if (!stack.isEmpty() && stack.peek() instanceof Token && ((Token) stack.peek()).getType() == Token.FIELD_OPERATOR)
                    substack.push(stack.pop());

                Query query = buildQuery(substack);
                stack.push(query);
            }
            else if (token.getType() == Token.FIELD_START || token.getType() == Token.FIELD_OPERATOR)
            {
                if (stack.peek() instanceof Queryable)
                {
                    stack.push(new QueryOr());
                    stack.push(token);
                }
                else
                    stack.push(token);
            }
            else if (token.getType() == Token.QUERY_OPERATOR)
                stack.push(OperatorFactory.getQueryOperator(token.getString()));
            else if (token.getType() != Token.QUERY_END && token.getType() != Token.QUERY_START && token.getType() != Token.FIELD_END)
                stack.push(token);

            index++;
        }
        return stack;
    }

    protected Queryable parseQueryOperatorStack(Stack<Object> stack)
    {
        // Now we have all our queries
        if (stack.size() == 1)
            return (Queryable) stack.pop();
        else
        {
            Queryable q1 = (Queryable) stack.pop();
            QueryOperator qOp = new QueryOr();
            if (stack.peek() instanceof QueryOperator)
                qOp = (QueryOperator) stack.pop();
            Queryable q2 = (Queryable) stack.pop();

            DoubleQuery query = new DoubleQuery(q1, q2, qOp);

            while (!stack.isEmpty())
            {
                qOp = new QueryOr();
                if (stack.peek() instanceof QueryOperator)
                    qOp = (QueryOperator) stack.pop();
                q2 = (Query) stack.pop();
                query = new DoubleQuery(query, q2, qOp);
            }

            return query;
        }
    }

    private Query buildQuery(Stack<Object> substack)
    {
        FieldOperator fieldOp = new FieldEquals();
        if (((Token) substack.peek()).getType() == Token.FIELD_OPERATOR)
            fieldOp = OperatorFactory.getFieldOperator(((Token) substack.pop()).getString());

        return new Query(new Field(((Token) substack.pop()).getString(), ((Token) substack.pop()).getString()), fieldOp);
    }
}
