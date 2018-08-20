package core;

import java.io.File;

import entity.Set;
import id3.Entropy;
import id3.Id3;
import id3.InformationGain;
import parser.Parser;

public class Main
{
	public static boolean isDebbuging = false;
	public static boolean isCalculatingInformationGain = true;

	public static void main(String[] args) throws Exception
	{
		File file = new File("C:\\Users\\Jure\\Desktop\\processMe");
		Set set = new Set();
		Parser parser = new Parser(set, file);
		if (!parser.parse(";"))
			throw new Exception();
		new Id3().startId3(set.getAllParameterEntries());
		// if (isCalculatingInformationGain)
		// System.out.println(new
		// InformationGain().informationGain(set.getAllParameterEntries(), 0));
		// else
		// System.out.println(new
		// Entropy().entropy(set.getAllParameterEntries(), 0));
		System.out.println("Finished");

	}

}
