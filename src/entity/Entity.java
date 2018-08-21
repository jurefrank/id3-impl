package entity;

public class Entity
{
	private String[] parameters;
	private String classification;

	public Entity()
	{
		this(null, null);
	}

	public Entity(String[] parameters, String classification)
	{
		this.parameters = parameters;
		this.classification = classification;
	}

	public String[] getParameters()
	{
		return parameters;
	}

	public void setParameters(String[] parameters)
	{
		this.parameters = parameters;
	}

	public String getClassification()
	{
		return classification;
	}

	public void setClassification(String classification)
	{
		this.classification = classification;
	}

}
