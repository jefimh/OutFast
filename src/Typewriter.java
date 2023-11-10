import java.lang.Thread;

/**
 * The Typewriter class is responsible for printing
 * text to the console using a delayed typewriter effect.
 * 
 * @author Jefim Holmgren
 */
public class Typewriter {

    /**
     * Prints text on screen gradually with a delay effect
     * using Thread.sleep which basically pauses the application
     * for certain amount of time.
     * The delay is given in milliseconds.
     * 
     * @param text string to print
     * 
     * @param speed the effect speed in milliseconds.
     */
    public static void typeRegular(String text, int speed) {
        char[] characters = text.toCharArray();

        for (char character : characters) {
            System.out.print(character);

            try {
                Thread.sleep(speed);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println("ERROR: Something is wrong with the typewriter effect!");
            }
        }

        System.out.println();
    }

        /**
     * Displays italic text to the terminal using
     * typewriter effect.
     * 
     * @param text text to print.
     * 
     * @param speed the effect speed in milliseconds.
     */
    public static void typeItalic(String text, int speed) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_ITALIC = "\u001B[3m";
        char[] characters = text.toCharArray();

        for (char character : characters) {
            System.out.print(ANSI_ITALIC + character);

            try {
                Thread.sleep(speed);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println("ERROR: Something is wrong with the typewriter effect!");
            }
        }

        System.out.println(ANSI_RESET);
    }

    /**
     * Writes bold text using typewriter effect.
     * 
     * @param text text to print.
     * 
     * @param speed the effect speed in milliseconds.
     */
    public static void typeBold(String text, int speed) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_BOLD = "\u001B[1m";
        char[] characters = text.toCharArray();

        for (char character : characters) {
            System.out.print(ANSI_BOLD + character);

            try {
                Thread.sleep(speed);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                System.out.println("ERROR: Something is wrong with the typewriter effect!");
            }
        }

        System.out.println(ANSI_RESET);
    }
}