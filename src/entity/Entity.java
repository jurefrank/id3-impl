package entity;

import java.util.Queue;

public class Entity
{
	private String[] atributes;
	private Queue<String[]> parameters;

	public Entity()
	{
		this(null, null);
	}

	public Entity(String[] atributes, Queue<String[]> parameters)
	{
		this.atributes = atributes;
		this.parameters = parameters;
	}

	public boolean readFromParser(Queue<String[]> read)
	{
		if (read != null)
		{
			if (read.size() > 0)
			{
				atributes = read.remove();
				parameters = read;
				return true;
			}
		}
		return false;
	}

	public String[] getAtributes()
	{
		return atributes;
	}

	public void setAtributes(String[] atributes)
	{
		this.atributes = atributes;
	}

	public Queue<String[]> getParameters()
	{
		return parameters;
	}

	public void setParameters(Queue<String[]> parameters)
	{
		this.parameters = parameters;
	}

}
