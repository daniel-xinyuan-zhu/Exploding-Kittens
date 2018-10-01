import java.util.*; 

public class GameEngine {
	private Deck d; 
	private List<Player> playerList;
	private int playersAlive; 
	private Scanner s; 

	public GameEngine(int humans, int computers)
	{
		s = new Scanner(System.in);
		playerList = new ArrayList<Player>();
		playersAlive = humans + computers; 
		Player p; 
		for(int i = 0; i < humans; i++)
		{ 
			System.out.println("\nPlayer " + (i+1) + ", what would you like your name to be? ");
			String playerName = s.nextLine(); 
			p = new Player(playerName); 
			playerList.add(p); 
		}
		for(int i = 0; i < computers; i++)
		{
			Computer c = new Computer("CPU " + (i+1)); 
			playerList.add(c); 
		}
		System.out.println(playerList.size());
		InputChecker.setPlayers(playerList);
		d = new Deck(); 
	} // end constructor

	public void loadGame()
	{
		dealInitialCards(); 
		d.addExplodingKittens();
	}

	public void startGame()
	{
		while(playersAlive != 1)
		{
			for(int i = 0; i < playerList.size(); i++)
			{
				Player p = playerList.get(i);
				Map<String, Player> otherPlayers = new HashMap<String, Player>();
				for(Player other : playerList)
				{
					if(!other.equals(p))
					{
						otherPlayers.put(other.getName(), other);
					}
				}
				if(!p.takeTurn(d, otherPlayers))
				{
					playerList.remove(i); 
					playersAlive--;
				}
			}
		}
		System.out.println("\n\n\n"+playerList.get(0).getName() + " is the winner!");
	}

	// Private Methods:
	private void dealInitialCards()
	{
		// Deal defuse cards first 
		for(Player p : playerList)
		{
			Card c = d.drawCard(); 
			p.addCard(c); 
		}
		// Shuffle cards, then deal initial cards 
		d.shuffleDeck(); 
		for(Player p : playerList)
		{
			for(int i = 0; i < 4; i++)
			{
				System.out.println("CARD DEALT");
				Card c = d.drawCard();
				p.addCard(c); 
			}
		}
	}
}