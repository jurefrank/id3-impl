package id3;

import java.util.HashMap;
import java.util.List;

import entity.Entity;

public class InformationGain
{
	private Entropy entropy;

	public double informationGain(List<Entity> allEntities, int predictor)
	{
		entropy = new Entropy();
		double entropyOfAtribute = entropy.entropy(allEntities, predictor);
		HashMap<String, Integer> allClassifications = allClassifications(allEntities);
		double entropyOfClassification = entropy.calculateE(allClassifications);
		return entropyOfClassification - entropyOfAtribute;
	}

	private HashMap<String, Integer> allClassifications(List<Entity> allEntities)
	{
		HashMap<String, Integer> ret = new HashMap<>();
		for (Entity entity : allEntities)
		{
			String classification = entity.getClassification();
			int value = 1;
			if (ret.containsKey(classification))
				value += ret.get(classification);
			ret.put(classification, value);
		}
		return ret;
	}

	public Entropy getEntropy()
	{
		return entropy;
	}
}
