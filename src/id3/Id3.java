package id3;

import java.util.ArrayList;
import java.util.List;

import decisiontree.Node;
import entity.Entity;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Id3
{
	// Default should be set to true
	private boolean useInformationGain;
	private Node decisionTree;
	private String[] attributes;

	public Id3()
	{
		this(true, null, null);
	}

	public Id3(String[] attributes)
	{
		this(true, null, attributes);
	}

	public Id3(boolean useInformationGain, String[] attributes)
	{
		this(useInformationGain, null, attributes);
	}

	public Id3(boolean useInformationGain)
	{
		this(useInformationGain, null);
	}

	public Id3(boolean useInformationGain, Node decisionTree, String[] attributes)
	{
		this.setUseInformationGain(useInformationGain);
		this.setDecisionTree(decisionTree);
		this.attributes = attributes;
	}

	public boolean isUseInformationGain()
	{
		return useInformationGain;
	}

	public void setUseInformationGain(boolean useInformationGain)
	{
		this.useInformationGain = useInformationGain;
	}

	public Node getDecisionTree()
	{
		return decisionTree;
	}

	public void setDecisionTree(Node decisionTree)
	{
		this.decisionTree = decisionTree;
	}

	// ID3 Implementation

	public void startId3(List<Entity> allEntities) throws Exception
	{
		// Root node
		decisionTree = new Node();
		decisionTree.setParent(null);

		// First check 2 rules
		// If all entries have the same classification
		String classification = null;
		boolean isSame = true;
		for (Entity ent : allEntities)
		{
			if (classification == null)
				classification = ent.getClassification();

			else if (!classification.equals(ent.getClassification()))
			{
				isSame = false;
				break;
			}
		}
		// End id3 because no matter what classificaiton is the same.
		if (isSame)
		{
			decisionTree.setValue(classification);
			decisionTree.setChildren(null);
			return;
		}

		// Else continue setting predictors and call igID3 or entropyID3
		List<Integer> allPredictors = new ArrayList<>();
		for (int i = 0; i < allEntities.get(0).getParameters().length; i++)
			allPredictors.add(i);

		if (useInformationGain)
			informationGainID3(allEntities, allPredictors, decisionTree);
		else
			throw new NotImplementedException();
	}

	private void informationGainID3(List<Entity> allEntities, List<Integer> allPredictors, Node node) throws Exception
	{
		// DECLARE STATES WHERE ID3 IS FINISHED

		// If all atributes are proccessed
		// if (allPredictors.size() == 0)
		// {
		// // ADD CLASSIFICATION
		// return;
		// }

		InformationGain ig = new InformationGain();

		// ig entropy is different after for loop is ended
		List<Node> children = new ArrayList<>();

		// Calculate max ig (information gain)
		// Use max ig atribute as root node
		double maxInformationGain = Double.MIN_VALUE;
		int predictor = -1;

		// Goes through all predictors or atributes
		// to find max ig and set children
		for (int _predictor : allPredictors)
		{
			double _temp = ig.informationGain(allEntities, _predictor);
			if (maxInformationGain < _temp)
			{
				maxInformationGain = _temp;
				predictor = _predictor;
				children = new ArrayList<>();
				for (String key : ig.getEntropy().getMapppedSet().keySet())
				{
					Node _child = new Node();
					_child.setValue(key);
					_child.setParent(node);
					_child.setIsAttributeName(false);
					children.add(_child);
				}
			}
		}

		if (predictor == -1)
		{
			String classification = null;
			for (Entity ent : allEntities)
				if (classification == null)
					classification = ent.getClassification();
				else if (!classification.equals(ent.getClassification()))
					throw new Exception("Something went from predictor is -1 but classification isnt the same");
			Node classificated = new Node();
			classificated.setChildren(null);
			classificated.setParent(node);
			classificated.setValue(classification);
			node.getChildren().add(classificated);
			return;
		}

		if (node.getParent() == null)
		{
			node.setValue(attributes[predictor]);
			node.setIsAttributeName(true);
			node.setChildren(children);
		}

		else
		{
			Node _child = new Node();
			_child.setValue(attributes[predictor]);
			_child.setParent(node);
			_child.setChildren(children);
			_child.setIsAttributeName(true);
			node.getChildren().add(_child);
		}

		allPredictors.remove((Object) predictor);

		// Check if node child is Atribute and set node to be atribute
		// because it has children which are values
		if (node.getChildren().size() == 1 && node.getChildren().get(0).isAttributeName())
		{
			node = node.getChildren().get(0);
		}

		for (Node child : node.getChildren())
		{
			// Split set to subset
			List<Entity> subset = getSubset(child.getValue(), allEntities, predictor);
			informationGainID3(subset, allPredictors, child);
		}

	}

	private List<Entity> getSubset(String value, List<Entity> allEntities, int predictor)
	{
		List<Entity> subset = new ArrayList<>();
		for (Entity ent : allEntities)
		{
			if (ent.getParameters()[predictor].equals(value))
				subset.add(ent);
		}
		return subset;
	}

}
