import java.util.*; 

public class Card {

	private String cardType; 
	private int value; 

	public Card(String type, int v)
	{
		cardType = type; 
		value = v; 
	}

	public String getType()
	{
		return cardType; 
	}

	public int getValue()
	{
		return value; 
	}

}