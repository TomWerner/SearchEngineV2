package org.uiowa.cs2820.engine.queryparser;

import java.util.Stack;

import org.uiowa.cs2820.engine.queries.Queryable;

public class ComplexStackTokenParser extends SimpleStackTokenParser
{
    @Override
    protected Queryable parseQueryOperatorStack(Stack<Object> stack)
    {
        Stack<Object> parseStack = new Stack<Object>();
        // Stick the first parenthesis on the stack
        parseStack.push(stack.pop());

        Queryable query = null;

        while (!stack.isEmpty())
        {
            while (!stack.isEmpty() && !(stack.peek() instanceof Token && ((Token) stack.peek()).getType() == Token.PAREN_END))
                parseStack.push(stack.pop());

            Stack<Object> subStack = new Stack<Object>();
            while (!(parseStack.peek() instanceof Token && ((Token) parseStack.peek()).getType() == Token.PAREN_START))
                subStack.add(parseStack.pop());
            parseStack.pop(); // remove the start parenthesis

            // Use the simple parser to parse this without parenthesis
            query = super.parseQueryOperatorStack(subStack);

            parseStack.push(query);
            stack.pop();
        }

        return query;
    }

    @Override
    protected Stack<Object> setupQueryOperatorStack(Stack<Object> stack)
    {
        // We need to push parenthesis around the whole expression
        Stack<Object> tempStack = new Stack<Object>();
        tempStack.push(new Token(")", Token.PAREN_END));
        while (!stack.isEmpty())
            tempStack.push(stack.pop());
        tempStack.push(new Token("(", Token.PAREN_START));
        return tempStack;
    }
}
