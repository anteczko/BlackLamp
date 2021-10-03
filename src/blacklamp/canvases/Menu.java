/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacklamp.canvases;

import blacklamp.Midlet;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author a
 */
public class Menu extends Canvas implements CommandListener{
	public int Option=0;
	int NoOfOptions=3;

	public Menu(Midlet _midlet){
		
	}

	
	
	protected void paint(Graphics g) {
		g.setColor(0x000000);
		g.fillRect(0,0,getWidth(),getHeight());
		
		g.setColor(0xffffff);
		g.drawString("Kokoszka", 100, 100, Graphics.TOP | Graphics.LEFT);
	}

	protected void keyPressed(int keyCode){
		switch(keyCode){
			case -1:
				Option=(Option+1+NoOfOptions)%NoOfOptions;
				break;
			case -2:
				Option=(Option-1+NoOfOptions)%NoOfOptions;
				break;
			case -5:
/// INSERT ACTIONS HERE!!!
				break;
			default:
				System.out.println("Unknown key pressed!!!");
				break;
		}
		System.out.println("Option "+Option+" selected.");
	}
	
	public void commandAction(Command c, Displayable d) {
	}
	
}
