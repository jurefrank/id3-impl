package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import entity.Entity;
import entity.Set;

public class Parser
{
	private Set allEntries = null;
	private File fileToParse;

	public Parser(Set allEntries, File fileToParse)
	{
		this.allEntries = allEntries;
		this.fileToParse = fileToParse;
	}

	// isParsed
	public boolean parse(String parseFormat)
	{
		if (parseFormat == null || parseFormat.length() < 1)
			throw new IllegalArgumentException("Parse format is not given.");

		try (BufferedReader reader = new BufferedReader(new FileReader(fileToParse)))
		{
			String line = "";
			boolean firstLine = true;
			while ((line = reader.readLine()) != null)
			{
				String[] splitLine = line.split(parseFormat);

				if (firstLine)
				{
					allEntries.setAttributeNames(splitLine);
					firstLine = !firstLine;
				} else
				{
					allEntries.addParameters(new Entity(trimArray(splitLine), splitLine[splitLine.length - 1]));
				}

			}

		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		} catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private String[] trimArray(String[] splitLine)
	{
		String[] ret = new String[splitLine.length - 1];
		for (int i = 0; i < ret.length; i++)
			ret[i] = splitLine[i];
		return ret;
	}
}
