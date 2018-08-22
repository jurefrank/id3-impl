package id3;

import java.util.ArrayList;
import java.util.List;

import decisiontree.Node;
import entity.Entity;

public class TestID3
{
	private int[][] confusionMatrix;
	private List<String> classes;
	private boolean hasCompleted = false;

	public void testID3(Node decisionTree, List<Entity> testEntities, String[] attributes)
	{
		hasCompleted = false;
		int classificatons = getCount(testEntities);
		confusionMatrix = new int[classificatons][classificatons];
		for (int i = 0; i < confusionMatrix.length; i++)
		{
			for (int j = 0; j < confusionMatrix[i].length; j++)
				confusionMatrix[i][j] = 0;
		}

		for (Entity ent : testEntities)
		{
			recursive(decisionTree, ent, attributes);

		}
		hasCompleted = true;
	}

	public double accuracy()
	{
		double accuracy = 0;
		int allClasses = allClasses();
		for (int i = 0; i < confusionMatrix.length; i++)
		{
			accuracy += (allOfClass(i) / (double) allClasses) * accuracyOfClass(i);
		}
		return accuracy;
	}

	// RECALL GLEJ DOL
	// PRECISION GLEJ VODORAVNO
	public double accuracyOfClass(int classLine)
	{
		int tp = 0;
		int tn = 0;
		int fp = 0;
		int fn = 0;

		tp = confusionMatrix[classLine][classLine];

		for (int i = 0; i < confusionMatrix.length; i++)
		{
			if (i == classLine)
				continue;
			for (int j = 0; j < confusionMatrix.length; j++)
			{
				if (j == classLine)
					continue;
				tn += confusionMatrix[i][j];
			}
		}

		for (int i = 0; i < confusionMatrix[classLine].length; i++)
		{
			if (i == classLine)
				continue;
			fn += confusionMatrix[classLine][i];
		}

		for (int i = 0; i < confusionMatrix.length; i++)
		{
			if (i == classLine)
				continue;
			fp += confusionMatrix[i][classLine];
		}

		return (tp + tn) / (double) (tp + tn + fp + fn);

	}

	// sensitivity
	public double recall()
	{
		if (!hasCompleted)
			return -1;

		double recall = 0;
		int allClasses = allClasses();
		for (int i = 0; i < confusionMatrix.length; i++)
		{
			recall += (allOfClass(i) / (double) allClasses) * recallOfClass(i);
		}

		return recall;

	}

	public double recallOfClass(int classLine)
	{
		int[] line = confusionMatrix[classLine];
		int correct = 0;
		int divide = 0;
		for (int i = 0; i < line.length; i++)
			divide += line[i];
		correct = line[classLine];

		return correct / (double) divide;
	}

	public double precision()
	{
		if (!hasCompleted)
			return -1;

		double precision = 0;
		int allClasses = allClasses();
		for (int i = 0; i < confusionMatrix.length; i++)
		{
			precision += (allOfClass(i) / (double) allClasses) * precisionOfClass(i);
		}
		return precision;
	}

	public double precisionOfClass(int classLine)
	{
		int correct = 0;
		int divide = 0;
		for (int i = 0; i < confusionMatrix.length; i++)
			divide += confusionMatrix[i][classLine];
		correct = confusionMatrix[classLine][classLine];

		return correct / (double) divide;
	}

	public double FScore()
	{
		if (!hasCompleted)
			return -1;

		List<Double> precisions = new ArrayList<>();
		List<Double> recalls = new ArrayList<>();
		List<Double> fscores = new ArrayList<>();

		// Fill precisions and recalls
		fillArrays(precisions, recalls, fscores);

		double fscore = 0;
		int allClasses = allClasses();
		for (int i = 0; i < fscores.size(); i++)
		{
			double fscoreI = fscores.get(i);
			if (Double.isNaN(fscoreI))
				fscoreI = 0;
			fscore += (allOfClass(i) / (double) allClasses) * fscoreI;
		}
		return fscore;
		// return (2 * precision() * recall()) / (double) (precision() + recall());
	}

	private void fillArrays(List<Double> precisions, List<Double> recalls, List<Double> fscores)
	{
		for (int i = 0; i < confusionMatrix.length; i++)
		{
			precisions.add(precisionOfClass(i));
			recalls.add(recallOfClass(i));
			fscores.add((2 * precisions.get(i) * recalls.get(i)) / (precisions.get(i) + recalls.get(i)));
		}
	}

	private int allOfClass(int classLine)
	{
		int result = 0;
		for (int value : confusionMatrix[classLine])
			result += value;
		return result;
	}

	private int allClasses()
	{
		int result = 0;
		for (int[] values : confusionMatrix)
			for (int value : values)
				result += value;
		return result;
	}

	private int getCount(List<Entity> testEntities)
	{
		List<String> classifications = new ArrayList<>();
		for (Entity ent : testEntities)
		{
			String value = ent.getClassification();
			if (classifications.contains(value))
				continue;
			else
				classifications.add(value);
		}
		classes = classifications;
		return classifications.size();
	}

	private void recursive(Node node, Entity entity, String[] attributes)
	{
		if (node == null)
			return;
		if (node.getChildren() == null)
		{
			String modelClass = node.getValue();
			int modelLocation = classes.indexOf(modelClass);
			String actualClass = entity.getClassification();
			int actualLocation = classes.indexOf(actualClass);
			confusionMatrix[actualLocation][modelLocation]++;
			return;
		}

		String attribute = node.getValue();
		int attributePredictor = findValueAttribute(attributes, attribute);
		List<Node> children = node.getChildren();
		for (Node _node : children)
		{
			if (_node.getValue().equals(entity.getParameters()[attributePredictor]))
			{
				node = _node;
				break;
			}
		}
		List<Node> attributeNode = node.getChildren();
		if (attributeNode.size() == 1 && attributeNode.get(0).isAttributeName())
			recursive(attributeNode.get(0), entity, attributes);
		// Children should be null so we get classification
		else if (attributeNode.size() == 1)
			recursive(attributeNode.get(0), entity, attributes);

	}

	private int findValueAttribute(String[] values, String value)
	{
		for (int i = 0; i < values.length; i++)
		{
			if (values[i].equals(value))
				return i;
		}
		return -1;
	}

	public int[][] getConfusionMatrix()
	{
		return confusionMatrix;
	}

	public List<String> getClasses()
	{
		return classes;
	}
}
