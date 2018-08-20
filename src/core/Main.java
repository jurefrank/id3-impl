package core;

import java.io.File;

import entity.Set;
import id3.Id3;
import parser.Parser;

public class Main
{
	public static boolean isDebbuging = false;
	public static boolean isCalculatingInformationGain = true;

	public static void main(String[] args) throws Exception
	{
		File file = new File("processMe");
		Set set = new Set();
		Parser parser = new Parser(set, file);
		if (!parser.parse(";"))
			throw new Exception();
		Id3 id3 = new Id3(set.getAttributeNames());
		id3.startId3(set.getAllParameterEntries());
		System.out.println(id3.getDecisionTree());
		// if (isCalculatingInformationGain)
		// System.out.println(new
		// InformationGain().informationGain(set.getAllParameterEntries(), 0));
		// else
		// System.out.println(new
		// Entropy().entropy(set.getAllParameterEntries(), 0));
		System.out.println("Finished");

	}

}
