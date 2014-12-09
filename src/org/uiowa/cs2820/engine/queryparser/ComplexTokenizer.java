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

    private HashSet<String> queryOperators = new HashSet<String>();
    {
        {
            queryOperators.add("AND");
            queryOperators.add("OR");
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
            else if (current == START_PAREN)
            {
                if (getNextNonWhitespaceCharacter(string, i) == QUOTE)
                {
                    if (currentToken.length() != 0)
                        tokens.add(new Token(currentToken.toString(), Token.FIELD_OPERATOR));
                    tokens.add(new Token("" + current, Token.FIELD_START));
                }
                else
                {
                    if (currentToken.length() != 0)
                        tokens.add(new Token(currentToken.toString(), Token.QUERY_OPERATOR));
                    tokens.add(new Token("" + current, Token.PAREN_START));
                }
                currentToken = new StringBuffer();
            }
            else if (current == END_PAREN)
            {
                if (getPreviousNonWhitespaceCharacter(string, i) == QUOTE)
                    tokens.add(new Token("" + current, Token.FIELD_END));
                else
                    tokens.add(new Token("" + current, Token.PAREN_END));
                currentToken = new StringBuffer();
            }
            else if (!whitespace.contains(current))
            {
                currentToken.append(Character.toUpperCase(current));
                if (queryOperators.contains(currentToken.toString()))
                {
                    tokens.add(new Token(currentToken.toString(), Token.QUERY_OPERATOR));
                    currentToken = new StringBuffer();
                }
            }
        }
        tokens.add(new Token("" + END_PAREN, Token.PAREN_END)); // The entire
                                                                // thing should
                                                                // be enclosed
                                                                // in
                                                                // parenthesis
        return tokens;
    }
    
    private char getPreviousNonWhitespaceCharacter(String string, int currentPosition)
    {
        for (int i = currentPosition - 1; i >= 0; i--)
            if (!whitespace.contains(string.charAt(i)))
                return string.charAt(i);
        return 0;
    }

    private char getNextNonWhitespaceCharacter(String string, int currentPosition)
    {
        for (int i = currentPosition + 1; i < string.length(); i++)
            if (!whitespace.contains(string.charAt(i)))
                return string.charAt(i);
        return 0;
    }
    

}
