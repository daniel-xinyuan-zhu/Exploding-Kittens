import java.util.*;
import java.util.Scanner;
import javafx.util.Pair;

public class Player {
	public String playerName;
	public TreeMap <String, List<Card>> hand;  
	private Scanner s; 
	
	public Player(String name)
	{
		s = new Scanner(System.in);
		playerName = name; 
		hand = new TreeMap <>(); 
	}

	public TreeMap<String, List<Card>> getHand()
	{
		return hand;
	}

	public void setHand(String cardType, List<Card> cardList)
	{
		hand.put(cardType, cardList);
	}

	public void addCard(Card c)
	{
		if(!hand.containsKey(c.getType()))
		{
			List<Card> cList = new ArrayList<Card>();
			cList.add(c); 
			hand.put(c.getType(), cList);
		}
		else
		{
			List<Card> updatedList = hand.get(c.getType()); 
			updatedList.add(c); 
			hand.put(c.getType(), updatedList); 
		}
	}

	public boolean takeTurn(Deck d, Map<String, Player> otherPlayers)
	{
		//Scanner s = new Scanner(System.in);
		boolean skipTurn = false; 
		boolean endTurn = false; 
		hideCards(); 
		System.out.println("It is now " + playerName + "'s turn!");
		System.out.print("Please press enter to start your turn: ");
		String input = s.nextLine();
		d.printDeck();
		showCards(); 
		while(!endTurn)
		{
			String cardChoice = "";
			while(parseCardInput(cardChoice) == null)
			{
				System.out.println("\nWhat cards and how many of them would you like to play? (i.e. \"2x potato cat\") Or, you can end your turn by typing \"end\"");
				cardChoice = s.nextLine(); 
				if(cardChoice.equals("end"))
				{
					endTurn = true;
					break;
				}
				if(parseCardInput(cardChoice) == null)
				{
					System.out.println("\nThat did not seem to be a valid combo . . . Try again?");
				}
				else
				{
					Pair<Boolean, Boolean> nextSteps = playCards(parseCardInput(cardChoice), d, otherPlayers, endTurn); 
					endTurn = nextSteps.getKey(); 
					skipTurn = nextSteps.getValue(); 
					if(skipTurn) // if the player is opting to skip their turn 
					{
						break; 
					}
					showCards();
				}
			}
			if(endTurn)
			{
				break; 
			}
		}
		if(!skipTurn)
		{
			if (!endTurn(d))
			{
				return false; 
			} 
		}
		System.out.print("\nYou opted to skip your turn. You will not draw a card. Please press enter to finish your turn : ");
		String space = s.nextLine();
		return true; 
	}

	private Pair<Boolean, Boolean> playCards(Pair<String, Integer> cardsToPlay, Deck d, Map<String, Player> otherPlayers, boolean endTurn)
	{
		// discard the cards from the player's hand
		boolean skipTurn = false;
		List<Card> cardsToDiscardFrom = hand.get(cardsToPlay.getKey());
		for(int i = 0; i < cardsToPlay.getValue(); i++)
		{
			cardsToDiscardFrom.remove(0);
		}
		hand.put(cardsToPlay.getKey(), cardsToDiscardFrom);
		if(hand.get(cardsToPlay.getKey()).size() == 0)
		{
			hand.remove(cardsToPlay.getKey()); 
		}

		// play the corresponding cards 
		int cardsPlayed = cardsToPlay.getValue(); 
		if(cardsToPlay.getValue() == 1)
		{
			skipTurn = playOneCard(cardsToPlay.getKey(), d);
			if(skipTurn)
			{
				return new Pair<Boolean, Boolean>(true, skipTurn);  
			}
		}					
		else if(cardsToPlay.getValue() == 2)
		{
			stealRandomCard(otherPlayers); 
		}
		else
		{
			stealAnyCard(otherPlayers);
		}
		return new Pair<Boolean, Boolean> (true, skipTurn);
	}

	private boolean playOneCard(String cardType, Deck d)
	{
		switch (cardType)
		{
			case "shuffle":
				d.shuffleDeck();
			break; 
			case "see the future":
				int cardNumber = 1; 
				for(Card c : d.viewTop(3))
				{
					System.out.println("Card " + cardNumber + ": " + c.getType()); 
					cardNumber++;
				}
			break; 
			// skip turn
			default: 
				return true;
		}
		return false; 
	}

	private void stealRandomCard(Map<String, Player> otherPlayers)
	{
		//Scanner s = new Scanner(System.in);
		for(Map.Entry<String, Player> entry : otherPlayers.entrySet())
		{
			System.out.println(entry.getValue().getName());
		}
		System.out.print("\nYou discarded two of the same card, pick an opponent from above to steal from: "); 
		String player = s.nextLine(); 
		Deck flattenedHand = new Deck(otherPlayers.get(player).getHand());
		flattenedHand.shuffleDeck();
		System.out.print("\n"+player + " has " + flattenedHand.getDeckSize() + " cards. Pick a number from 1 to " + flattenedHand.getDeckSize() + ": ");
		int number = Integer.parseInt(s.nextLine()); 
		String stolenCardType = flattenedHand.getCard(number-1).getType();			
		takeCard(otherPlayers.get(player), stolenCardType);
	}

	private void stealAnyCard(Map<String, Player> otherPlayers)
	{
		//Scanner s = new Scanner(System.in);
		for(Map.Entry<String, Player> entry : otherPlayers.entrySet())
		{
			System.out.println(entry.getValue().getName());
		}
		System.out.println("\nYou discarded three of the same card, time to pick any card and steal it from anyone!");
		System.out.print("\nSelect an opponent from above to steal from: ");
		String player = s.nextLine(); 
		System.out.print("\nSelect the type of card you'd like to steal: "); 
		String type = s.nextLine();
		if(otherPlayers.get(player).getHand().containsKey(type))
		{
			takeCard(otherPlayers.get(player), type);
		}
		else
		{
			System.out.println(player + " did not have the " + type + " card you were looking for."); 
		}
	}

	private void takeCard(Player victim, String cardType)
	{
		Card stolenCard = victim.getHand().get(cardType).remove(0);
		List<Card> victimsCards = victim.getHand().get(cardType); 
		victim.setHand(cardType, victimsCards);
		addCard(stolenCard);
	}

	private Pair<String, Integer> parseCardInput(String cardChoice)
	{
		if(cardChoice.length() > 4)
		{
			int quantity = Integer.parseInt(cardChoice.substring(0, cardChoice.indexOf('x')));
			if(quantity < 1 || quantity > 3)
			{
				return null;
			}
			String card = cardChoice.substring(cardChoice.indexOf('x') + 2); 
			if(hand.containsKey(card) && hand.get(card).size() >= quantity)
			{
				if(hand.get(card).get(0).getValue() == 3 && quantity == 1 || hand.get(card).get(0).getValue() == 1 && quantity < 2)
				{
					return null;
				}
				Pair<String, Integer> output = new Pair<>(card, quantity); 
				return output;
			}
		}
		return null;
	}

	public String getName()
	{
		return playerName;
	}

	public void showCards()
	{
		System.out.println("\nHere's an updated list of your cards: \n");
		int cardNumber = 1; 
		for(Map.Entry<String, List<Card>> entry : hand.entrySet())
		{
			for(int i = 0; i < entry.getValue().size(); i++)
			{
				System.out.println("Card " + cardNumber + ": " + entry.getKey()); 
				cardNumber++;
			}
		}
	}

	private void hideCards()
	{
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public boolean endTurn(Deck d)
	{
		//Scanner s = new Scanner(System.in);
		Card c = d.drawCard();
		if(c.getType().equals("exploding kitten"))
		{
			System.out.println("\nBOOM! YOU DREW AN EXPLODING KITTEN\n"); 
			if(hand.containsKey("defuse"))
			{
				System.out.print("Fortunately, you still have a defuse. Press enter to continue by using a defuse: "); 
				String entry = s.nextLine();
				List<Card> defuseCards = hand.get("defuse");
				defuseCards.remove(0); 
				hand.put("defuse", defuseCards);
				if(hand.get("defuse").size() == 0)
				{
					hand.remove("defuse");
				}
				System.out.println("\nYou also get to put this exploding kitten back wherever you want :).");
				System.out.print("\nPlease enter a location in the deck (0 (TOP) - " + (d.getDeckSize()-1) + " (BOTTOM)): ");
				d.insertCard(Integer.parseInt(s.nextLine()), c); 
			}
			else
			{
				System.out.println("Yikes . . . you're out of defuses . . . game over!\n\n");
				return false;
			}
		}
		else
		{
			System.out.println("\nYou drew a " + c.getType() + " card. It has been added to your hand.");
			addCard(c);
		}
		showCards();
		System.out.print("\nPlease press enter to finish your turn : ");
		String space = s.nextLine();
		return true; 
	}
}