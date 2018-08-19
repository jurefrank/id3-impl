package id3;

import entity.Set;
import util.MathEq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import core.Main;
import entity.Entity;

public class Id3
{

	public double entropy(Set set, int predictor)
	{
		// 1. Get classification count for every atribute
		// 2. Calculate entropy

		List<Entity> allEntities = set.getAllParameterEntries();
		int lines = allEntities.size();

		HashMap<String, HashMap<String, Integer>> occurances = new HashMap<>();
		for (int i = 0; i < lines; i++)
		{
			String key = allEntities.get(i).getParameters()[predictor];

			// Check and init
			if (!occurances.containsKey(key))
				occurances.put(key, new HashMap<>());

			// Insert value
			insertClassification(occurances.get(key), allEntities.get(i).getClassification());

		}

		// Fully scanned and counted.
		// --
		return calculateEntropy(occurances, lines);

		// Entropy = P1*E1 + P2*E2
	}

	private double calculateEntropy(HashMap<String, HashMap<String, Integer>> occurances, int count)
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

	private double calculateE(HashMap<String, Integer> _classifications)
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

	private void insertClassification(HashMap<String, Integer> classifications, String key)
	{
		int val = 1;
		if (classifications.containsKey(key))
		{
			val += classifications.get(key);
		}
		classifications.put(key, val);
	}

}
