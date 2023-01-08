package system;

public class Color {//Renk paleti
	public static final String ERASE = "\033[H\033[2J";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String BLACK_ITALIC = "\033[3;30m";   // BLACK
    public static final String RED_ITALIC = "\033[3;31m";     // RED
    public static final String GREEN_ITALIC = "\033[3;32m";   // GREEN
    public static final String YELLOW_ITALIC = "\033[3;33m";  // YELLOW
    public static final String BLUE_ITALIC = "\033[3;34m";    // BLUE
    public static final String PURPLE_ITALIC = "\033[3;35m";  // PURPLE
    public static final String CYAN_ITALIC = "\033[3;36m";    // CYAN
    public static final String WHITE_ITALIC = "\033[3;37m";   // WHITE
		
    
    static String randomcolor[]= {BLACK_ITALIC,RED_ITALIC,GREEN_ITALIC,YELLOW_ITALIC,PURPLE_ITALIC,CYAN_ITALIC,WHITE_ITALIC};
	
}
