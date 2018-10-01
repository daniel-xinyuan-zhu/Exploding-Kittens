import java.util.*; 

public class GameEngine {
	private Deck d; 
	private List<Player> playerList;
	private int playersAlive; 

	public GameEngine(int humans, int computers)
	{
		playerList = new ArrayList<Player>();
		playersAlive = humans + computers; 
		for(int i = 0; i < humans; i++)
		{ 
			Player p = new Player("Player " + (i+1)); 
			playerList.add(p); 
		}
		for(int i = 0; i < computers; i++)
		{
			Computer c = new Computer("CPU " + (i+1)); 
			playerList.add(c); 
		}
		d = new Deck(); 
	}

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