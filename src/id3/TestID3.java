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
		if (hasCompleted)
		{
			int sum = 0;
			for (int[] rows : confusionMatrix)
			{
				for (int val : rows)
				{
					sum += val;
				}
			}
			int correct = 0;
			for (int i = 0; i < confusionMatrix.length; i++)
				correct += confusionMatrix[i][i];

			return correct / (double) sum;
		} else
			return -1;
	}

	// sensitivity
	public double recall()
	{
		if (!hasCompleted)
			return -1;

		double sumSens = 0;
		int count = 0;
		for (int i = 0; i < confusionMatrix.length; i++)
		{
			int sum = 0;
			for (int val : confusionMatrix[i])
			{
				sum += val;
			}
			int correct = confusionMatrix[i][i];
			double sensitivity = correct / (double) sum;
			sumSens += sensitivity;
			count++;
		}
		return sumSens / (double) count;

	}

	// xoo
	// xoo
	// xoo
	public double precision()
	{
		if (!hasCompleted)
			return -1;

		double sumSens = 0;
		int count = 0;
		for (int i = 0; i < confusionMatrix.length; i++)
		{
			int sum = 0;
			for (int j = 0; j < confusionMatrix.length; j++)
			{
				sum += confusionMatrix[j][i];
			}
			int correct = confusionMatrix[i][i];
			double sensitivity = correct / (double) sum;
			sumSens += sensitivity;
			count++;
		}
		return sumSens / (double) count;
	}

	public double FScore()
	{
		if (!hasCompleted)
			return -1;

		return (2 * precision() * recall()) / (double) (precision() + recall());
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
