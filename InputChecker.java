import java.util.*;

public class InputChecker 
{
	private static Set<String> playerList; 
	public InputChecker()
	{
		System.out.println("yeah");
		playerList = new HashSet<String>(); 
	}

	public static void setPlayers(List<Player> players)
	{
		for(int i = 0; i < players.size(); i++)
		{
			playerList.add(players.get(i).getName());
		}
	}

	public static boolean checkInvalidOpponent(String s, String currPlayer)
	{
		return !playerList.contains(s) || currPlayer.equals(s);
	}

	public static boolean checkBetweenBounds(int lower, int upper, int number)
	{
		return number >= lower && number <= upper;
	}

	
}
