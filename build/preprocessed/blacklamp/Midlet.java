/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacklamp;

import blacklamp.canvases.LevelCanvas;
import blacklamp.canvases.Menu;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

/**
 * @author a
 */
public class Midlet extends MIDlet {

	public void startApp() {
		Display.getDisplay(this).setCurrent(new LevelCanvas(this));
	}
	
	public void pauseApp() {
	}
	
	public void destroyApp(boolean unconditional) {
	}
}
