package org.uiowa.cs2820.engine.queryparser;

import java.util.ArrayList;

public interface Tokenizer
{

    public abstract ArrayList<Token> tokenize(String string);

    public abstract boolean canHandleParenthesis();

}