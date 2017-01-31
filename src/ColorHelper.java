import java.awt.*;

/**
 * Created by maciej on 31.01.17.
 */
public class ColorHelper {

static String[] colorStrings = {"BLUE", "GREEN", "ORANGE", "PINK", "RED"};

static String colorToString(Color color) {
    if(color == Color.BLUE)
        return "BLUE";
    else if(color == Color.GREEN)
        return "GREEN";
    else if(color == Color.ORANGE)
        return "ORANGE";
    else if(color == Color.PINK)
        return "PINK";
    else
        return "RED";
}

static Color stringToColor(String text) {
    if(text.equals("BLUE"))
        return Color.BLUE;
    else if(text.equals("GREEN"))
        return Color.GREEN;
    else if(text.equals("ORANGE"))
        return Color.ORANGE;
    else if(text.equals("PINK"))
        return Color.PINK;
    else
        return Color.RED;
}

static int colorToIndex(Color color) {
    String colorString = colorToString(color);
    return stringToIndex(colorString);
}

static int stringToIndex(String text) {
    for (int i=0; i<colorStrings.length; i++) {
        if(colorStrings[i].equals(text))
            return i;
    }
    return -1;
}

}
