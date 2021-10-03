/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacklamp.entities;

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author a
 */
public class LampSlot extends Item{
	public boolean lamps[]=new boolean[8];
	public Image lampsSprite[]=new Image[8];

	public LampSlot(int x, int y) {
		super("/sprites/levels/shared/lampsSlot.png", x, y);

		String link="/sprites/levels/shared/lampsSlot";
		for(int i=0;i<8;i++){
			lamps[i]=false;
			try{
				lampsSprite[i]=Image.createImage(link+i+".png");
			}catch(IOException e){
				System.out.println("ERROR image"+link+i+".png NOT FOUND!!!!");
			}
		
		}
	}
	public void updateLamps(Player player){
		if(isColiding(player)){
			for(int i=0;i<8;i++){
				if(!lamps[i] && player.lamps[i]){
					lamps[i]=true;
					player.lamps[i]=false;
				}
			}
		}
	}
	
	public void draw(Graphics g, int scroll){
		super.draw(g,scroll);

		for(int i=0;i<8;i++){
			if(lamps[i])
				g.drawImage(lampsSprite[i],x-scroll,y,0);
		}
	}
	
}
