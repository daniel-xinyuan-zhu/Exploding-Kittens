import java.util.*; 

public class Deck 
{
	private int cardsRemaining; 
	private List<Card> cards; 
	
	public Deck()
	{
		System.out.println("Constructor invoked");
		cards = new ArrayList<Card>(); 
		generateDeck(); 
	}

	public Deck(TreeMap<String, List<Card>> hand)
	{
		cards = new ArrayList<Card>();
		for(Map.Entry<String, List<Card>> entry : hand.entrySet())
		{
			for(Card c : entry.getValue())
			{
				cards.add(0, c);
			}
		}
	}

	public void generateDeck()
	{
		System.out.println("GENERATING CARDS");
		for(int i = 0; i < 6; i++)
		{
			Card c = new Card("defuse", 3); 
			cards.add(c); 
		}
		generateTwoValueCards(); 
		generateOneValueCards(); 
	}

	public void addExplodingKittens()
	{
		for(int i = 0; i < 4; i++)
		{
			Card c = new Card("exploding kitten", 0);
			cards.add(c); 
		}
		shuffleDeck();
	}

	public void shuffleDeck()
	{
		//Fisher Yates Algorithm 
		int n = cards.size();
		System.out.println("BEFORE size: "+n);
        Random random = new Random();
        for (int i = 0; i < n; i++) 
        {
            int randomValue = i + random.nextInt(n - i);
            Collections.swap(cards, i, randomValue); 
        }
        System.out.println("AFTER SIZE: "+cards.size());
	}

	public void insertCard(int index, Card c)
	{
		cards.add(index, c); 
	}

	public List<Card> viewTop(int number)
	{
		List<Card> view = new ArrayList<Card>(); 
		for(int i = 0; i < number; i++)
		{
			view.add(cards.get(i)); 
		}
		return view;
	}

	public Card drawCard()
	{
		return cards.remove(0); 
	}

	public Card getCard(int index)
	{
		return cards.remove(index);
	}

	public int getDeckSize()
	{
		return cards.size();
	}

	public void printDeck()
	{
		System.out.println("Total Cards: " + cards.size());
		for(Card c : cards)
		{
			System.out.println(c.getType());
		}
	}

	// Private Methods 

	private void generateTwoValueCards()
	{
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				Card c; 
				switch(i)
				{
					case 1:
						c = new Card("shuffle", 2); 
					break; 
					case 2: 
						c = new Card("see the future", 2); 
					break; 
					default:
						c = new Card("skip turn", 2); 
					break; 
				}
				cards.add(c); 
			}
		}
	}

	private void generateOneValueCards()
	{
		for(int i = 0; i < 5; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				Card c; 
				switch(i)
				{
					case 1:
						c = new Card("taco cat", 1); 
					break; 
					case 2: 
						c = new Card("beard cat", 1); 
					break; 
					case 3: 
						c = new Card("rainbow cat", 1);
					break;
					case 4: 
						c = new Card("melon cat", 1); 
					break; 
					default:
						c = new Card("potato cat", 1); 
					break; 
				}
				cards.add(c); 
			}
		}
	}
}