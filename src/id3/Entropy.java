package id3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.Main;
import entity.Entity;
import util.MathEq;

public class Entropy
{
	private HashMap<String, HashMap<String, Integer>> mapppedSet = null;

	public HashMap<String, HashMap<String, Integer>> getMapppedSet()
	{
		if (mapppedSet == null)
			mapppedSet = new HashMap<>();
		return mapppedSet;
	}

	public void setMapppedSet(HashMap<String, HashMap<String, Integer>> mapppedSet)
	{
		this.mapppedSet = mapppedSet;
	}

	public double entropy(List<Entity> allEntities, int predictor)
	{
		// Convert set into hashed map
		HashMap<String, HashMap<String, Integer>> occurances = mappedSetInit(predictor, allEntities);

		//Calculate entropy for atribute
		return calculateEntropy(occurances, allEntities.size());

		// Entropy = P1*E1 + P2*E2
	}

	public HashMap<String, HashMap<String, Integer>> mappedSetInit(int predictor, List<Entity> allEntities)
	{
		HashMap<String, HashMap<String, Integer>> occurances = getMapppedSet();
		for (int i = 0; i < allEntities.size(); i++)
		{
			String key = allEntities.get(i).getParameters()[predictor];

			// Check and init
			if (!occurances.containsKey(key))
				occurances.put(key, new HashMap<>());

			// Insert value
			insertClassification(occurances.get(key), allEntities.get(i).getClassification());

		}
		return occurances;
	}

	public double calculateEntropy(HashMap<String, HashMap<String, Integer>> occurances, int count)
	{
		// How many times each different key occured eg 2/10
		java.util.Set<String> keys = occurances.keySet();
		double result = 0;
		for (String key : keys)
		{
			// P
			HashMap<String, Integer> _classifications = occurances.get(key);

			int sum = 0;
			for (String _key : _classifications.keySet())
			{
				sum += _classifications.get(_key);
			}

			// Debugging purpose
			if (Main.isDebbuging)
				dbgPrntEntropyEquation(sum, (double) count, calculateE(_classifications));

			result += (sum / (double) count) * calculateE(_classifications);

		}
		return result;
	}

	private void dbgPrntEntropyEquation(int sum, double count, double d)
	{
		System.out.printf("(%d / %f) * %f%n", sum, (double) count, d);

	}

	public double calculateE(HashMap<String, Integer> _classifications)
	{
		double result = 0;
		List<Integer> values = new ArrayList<>();
		int sum = 0;
		for (String key : _classifications.keySet())
		{
			values.add(_classifications.get(key));
			sum += _classifications.get(key);
		}
		for (int val : values)
		{
			double _val = val / (double) sum;
			result -= (_val) * (MathEq.logAny(_val, 2.0));
		}

		return result;
	}

	public void insertClassification(HashMap<String, Integer> classifications, String key)
	{
		int val = 1;
		if (classifications.containsKey(key))
		{
			val += classifications.get(key);
		}
		classifications.put(key, val);
	}

}
