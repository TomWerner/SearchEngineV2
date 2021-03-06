package org.uiowa.cs2820.engine.queries;



import org.uiowa.cs2820.engine.Field;

public class FieldFuzzySearch implements FieldOperator
{
	private int fuzz = 0;
	public void setFuzz(int fuzzLevel)
	{
		if (fuzzLevel < 0)
			fuzzLevel = 0;
		fuzz = fuzzLevel;
	}
	public int getFuzz()
	{
		return fuzz;
	}
	public String toString()
	{
		return("~"+fuzz);
	}
	public Boolean compare(Field A, Field B)
	{
		if (A.getFieldName().equalsIgnoreCase(B.getFieldName()))
		{
			String text = A.getFieldValue().toString();
			String pattern = B.getFieldValue().toString();
			
			int textLen = text.length();
			int patternLen = pattern.length();
			// Calculate Levenshtein distance
			int[][] LevMatrix = new int[textLen][patternLen];
			for(int i = 1;i<textLen;i++)
			{
				LevMatrix[i][0] = i;
			}
			for(int j = 1;j<patternLen;j++)
			{
				LevMatrix[0][j] = j;
			}
			for(int j = 1;j<patternLen;j++)
			{
				for(int i = 1; i<textLen;i++)
				{
					if (text.charAt(i) == (pattern.charAt(j)))
					{
						LevMatrix[i][j] = LevMatrix[i-1][j-1];
					}
					else
					{
						int del = 1 + LevMatrix[i-1][j];
						int ins = 1 + LevMatrix[i][j-1];
						int sub = 1 + LevMatrix[i-1][j-1];
						int min = Math.min(Math.min(del,ins), sub);
						LevMatrix[i][j] = min;
					}
				}
			}
			if (LevMatrix[textLen-1][patternLen-1] <= fuzz)
			{
				return true;
			}
		}
		return false;
	}
}
