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

	public Id3()
	{
		this(true, null);
	}

	public Id3(boolean useInformationGain)
	{
		this(useInformationGain, null);
	}

	public Id3(boolean useInformationGain, Node decisionTree)
	{
		this.setUseInformationGain(useInformationGain);
		this.setDecisionTree(decisionTree);
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

	public void startId3(List<Entity> allEntities)
	{
		List<Integer> allPredictors = new ArrayList<>();
		for (int i = 0; i < allEntities.get(0).getParameters().length; i++)
			allPredictors.add(i);
		if (useInformationGain)
			informationGainID3(allEntities, allPredictors);
		else
			throw new NotImplementedException();
	}

	private void informationGainID3(List<Entity> allEntities, List<Integer> allPredictors)
	{
		// Calculate max ig (information gain)
		// Use max ig atribute as root node
		double maxInformationGain = Double.MIN_VALUE;
		int predictor = -1;
		for (int _predictor : allPredictors)
		{
			double _temp = new InformationGain().informationGain(allEntities, _predictor);
			if (maxInformationGain < _temp)
			{
				maxInformationGain = _temp;
				predictor = _predictor;
			}
		}
		allPredictors.remove(predictor);
		System.out.println(maxInformationGain + ", " + allPredictors);

	}

}
