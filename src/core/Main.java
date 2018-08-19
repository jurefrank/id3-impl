package core;

import java.io.File;

import entity.Set;
import id3.Id3;
import parser.Parser;

public class Main
{
	public static boolean isDebbuging = false;

	public static void main(String[] args) throws Exception
	{
		File file = new File("C:\\Users\\Jure\\Desktop\\processMe");
		Set set = new Set();
		Parser parser = new Parser(set, file);
		if (!parser.parse(";"))
			throw new Exception();
		System.out.println(new Id3().entropy(set, 0));
		System.out.println("Finished");

	}

}
