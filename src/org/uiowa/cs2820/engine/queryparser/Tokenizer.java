package org.uiowa.cs2820.engine.queryparser;

import java.util.ArrayList;
import java.util.HashSet;

public class Tokenizer
{
    private static final char QUOTE = '\"';
    private static final char START_FIELD = '{';
    private static final char END_FIELD = '}';
    private static final char START_QUERY = '[';
    private static final char END_QUERY = ']';
    
    private static HashSet<Character> whitespace = new HashSet<Character>();
    {{
        whitespace.add(' ');
        whitespace.add('.');
        whitespace.add(',');
    }}

    public Tokenizer()
    {

    }

    public ArrayList<Token> tokenize(String string)
    {
        ArrayList<Token> tokens = new ArrayList<Token>();
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
            else if (!whitespace.contains(current))
            {
                currentToken.append(current);
            }
        }

        return tokens;
    }

}
