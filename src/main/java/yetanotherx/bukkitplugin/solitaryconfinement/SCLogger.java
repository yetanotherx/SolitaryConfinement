package yetanotherx.bukkitplugin.solitaryconfinement;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SCLogger {
    
    public static final Logger logger = Logger.getLogger("Minecraft");
    
    public void info( String s ) {
	logger.log(Level.INFO, "[SolitaryConfinement] " + s);
    }
    
    public void severe( String s ) {
	logger.log(Level.SEVERE, "[SolitaryConfinement] " + s);
    }
    
    public void warning( String s ) {
	logger.log(Level.WARNING, "[SolitaryConfinement] " + s);
    }
    
}
