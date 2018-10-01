import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CardTest
{
	@Test
	public void testGetType()
	{
		Card c = new Card("defuse", 2);
		assertEquals(c.getType(), "defuse");
	}
}