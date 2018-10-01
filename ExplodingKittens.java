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
		while (numHumans <= 0 || numHumans > 4)
		{
			System.out.print("How many human players will be playing today? ");
			numHumans = Integer.parseInt(s.nextLine());
		}
		int numComputers = 0;
		do
		{
			System.out.print("\nHow many computer players will be playing today? ");
			numComputers = Integer.parseInt(s.nextLine()); 
		} while(numComputers + numHumans > 4 || numComputers + numHumans < 2);
		
		System.out.println("\nPerfect! Let's get started!\n");
		GameEngine ge = new GameEngine(numHumans, numComputers); 
		ge.loadGame(); 
		ge.startGame(); 
	}
}