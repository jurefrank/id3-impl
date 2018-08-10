package id3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

public class Id3
{

	public double entropy(Queue<String[]> parameters)
	{
		// get all count
		HashMap<String, Integer> probability = new HashMap<String, Integer>();
		Iterator<String[]> iterator = parameters.iterator();
		int count = 0;
		while (iterator.hasNext())
		{
			String[] param = iterator.next();
			if (probability.containsKey(param[param.length - 1]))
				probability.put(param[param.length - 1], probability.get(param[param.length - 1]) + 1);
			else
				probability.put(param[param.length - 1], 1);
			count++;
		}
		
		//calculate probabilites
		for(int i = 0; i < probability.size(); i++) {
			//probability./
		}
		

		// first look at last becasue this is classification

		return 0;
	}

}
