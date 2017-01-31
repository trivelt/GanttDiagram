import org.junit.*;
import java.awt.*;
import static org.junit.Assert.*;

public class ColorHelperTest {

    @Test
    public void testStringConversions(){
        Color green = Color.GREEN;
        assertEquals("GREEN", ColorHelper.colorToString(green));
        assertEquals("PINK", ColorHelper.colorToString(Color.PINK));

        assertEquals(Color.BLUE, ColorHelper.stringToColor("BLUE"));
        assertEquals(Color.ORANGE, ColorHelper.stringToColor("ORANGE"));
    }

    @Test
    public void testIndexConversions() {
        String[] colorStrings = ColorHelper.colorStrings;
        int index = 2;
        String colorText = colorStrings[index];
        assertEquals(index, ColorHelper.stringToIndex(colorText));

        assertEquals(0, ColorHelper.colorToIndex(Color.BLUE));
    }
}
