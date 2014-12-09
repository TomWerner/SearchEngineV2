package org.uiowa.cs2820.engine.queryparser;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * This tokenizer allows for parenthesizing input
 * 
 * @author Tom
 * 
 */
public class ComplexTokenizer implements Tokenizer
{
    private char QUOTE = '\"';
    private char START_FIELD = '{';
    private char END_FIELD = '}';
    private char START_QUERY = '[';
    private char END_QUERY = ']';
    private char START_PAREN = '(';
    private char END_PAREN = ')';

    private HashSet<Character> whitespace = new HashSet<Character>();
    {
        {
            whitespace.add(' ');
            whitespace.add('.');
            whitespace.add(',');
        }
    }

    public ComplexTokenizer()
    {
    }

    @Override
    public boolean canHandleParenthesis()
    {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.uiowa.cs2820.engine.queryparser.Tokenizer#tokenize(java.lang.String)
     */
    @Override
    public ArrayList<Token> tokenize(String string)
    {
        ArrayList<Token> tokens = new ArrayList<Token>();
        tokens.add(new Token("" + START_PAREN, Token.PAREN_START)); // The
                                                                    // entire
                                                                    // thing
                                                                    // should be
                                                                    // enclosed
                                                                    // in
                                                                    // parenthesis
        StringBuffer currentToken = new StringBuffer();

        for (int i = 0; i < string.length(); i++)
        {
            char current = string.charAt(i);
            if (current == QUOTE) // Now inside term
            {
                StringBuffer term = new StringBuffer();
                int k = i + 1;
                while (k < string.length() && string.charAt(k) != QUOTE)
                {
                    term.append(string.charAt(k));
                    k++;
                }
                i = k;
                tokens.add(new Token(term.toString(), Token.TERM));
            }
            else if (current == START_FIELD)
            {
                if (currentToken.length() != 0)
                {
                    tokens.add(new Token(currentToken.toString(), Token.FIELD_OPERATOR));
                    currentToken = new StringBuffer();
                }
                tokens.add(new Token("" + current, Token.FIELD_START));
            }
            else if (current == END_FIELD)
            {
                tokens.add(new Token("" + current, Token.FIELD_END));
                currentToken = new StringBuffer();
            }
            else if (current == START_QUERY)
            {
                if (currentToken.length() != 0)
                {
                    tokens.add(new Token(currentToken.toString(), Token.QUERY_OPERATOR));
                    currentToken = new StringBuffer();
                }
                tokens.add(new Token("" + current, Token.QUERY_START));
            }
            else if (current == END_QUERY)
            {
                tokens.add(new Token("" + current, Token.QUERY_END));
                currentToken = new StringBuffer();
            }
            else if (current == START_PAREN)
            {
                if (currentToken.length() != 0)
                {
                    tokens.add(new Token(currentToken.toString(), Token.QUERY_OPERATOR));
                    currentToken = new StringBuffer();
                }
                tokens.add(new Token("" + current, Token.PAREN_START));
            }
            else if (current == END_PAREN)
            {
                tokens.add(new Token("" + current, Token.PAREN_END));
                currentToken = new StringBuffer();
            }
            else if (!whitespace.contains(current))
            {
                currentToken.append(current);
            }
        }
        tokens.add(new Token("" + END_PAREN, Token.PAREN_END)); // The entire
                                                                // thing should
                                                                // be enclosed
                                                                // in
                                                                // parenthesis

        return tokens;
    }

}
