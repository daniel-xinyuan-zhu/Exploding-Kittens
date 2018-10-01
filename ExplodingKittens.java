import java.util.Scanner; 

public class ExplodingKittens {

	public static void main(String [] args)
	{
		System.out.println("Welcome to Exploding Kittens! \n\n");
		ExplodingKittens ek = new ExplodingKittens(); 
		ek.setupGame(); 
	}

	// Private Methods
	private void setupGame()
	{
		System.out.println("This game can be played with up to 4 players total. \n\n"); 
		Scanner s = new Scanner(System.in);
		int numHumans = 0; 
		while (numHumans < 2)
		{
			System.out.print("How many human players will be playing today? ");
			numHumans = Integer.parseInt(s.nextLine());
		}
		System.out.println("\nPerfect! Let's get started!\n");
		InputChecker ic = new InputChecker();
		GameEngine ge = new GameEngine(numHumans); 
		ge.loadGame(); 
		ge.startGame(); 
	}
}