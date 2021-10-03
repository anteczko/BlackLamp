/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacklamp.entities;

import java.util.Random;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author a
 */
public class Enemy extends Creature{
	Animation animationFly;
	int cooldown=300;
	int counter=0;

	public Enemy(String name) {
		this(name,150);
	}
	
	public Enemy(String name, int cooldown) {
		this(name,-100,10,cooldown);
		
	}
	public Enemy(String name, int x, int y,int cooldown) {
		this(name, x, y);
		this.cooldown=cooldown;
	}
	public Enemy(String name, int x, int y) {
		super(name, x, y);

		if(name.equalsIgnoreCase("hawk")){
			animationFly=new Animation("/sprites/levels/shared/enemies/"+name+"/"+name, 2);
		}else if(name.equalsIgnoreCase("witch")){
			animationFly=new Animation("/sprites/levels/shared/enemies/"+name+"/"+name, 1);
			
		}else if(name.equalsIgnoreCase("orc")){
			animationFly=new Animation("/sprites/levels/shared/enemies/"+name+"/"+name, 2);
			this.y=250;
		}
		xspd=1;
		maxCooldown=70;
	}
	public void ai(int scroll,int playerX,int playerY){
		if(name.equalsIgnoreCase("hawk")){
			x+=xspd;
			shotDown();
			if(x-scroll<0){
				if(cooldown<0){
					x=0;
					xspd=-xspd;
					cooldown=300;
				}else cooldown--;
			}
			else if(x-scroll>240){
				if(cooldown<0){
					x=240;
					xspd=-xspd;
					cooldown=300;
				}else cooldown--;
			}
		}else if(name.equalsIgnoreCase("witch")){
			x+=xspd;
			shot();
			if(x-scroll<0){
				y=playerY;
				if(cooldown<0){
					x=0;
					xspd=-xspd;
					cooldown=300;
				}else cooldown--;
			}
			else if(x-scroll>240){
				y=playerY;
				if(cooldown<0){
					x=240;
					xspd=-xspd;
					cooldown=300;
				}else cooldown--;
			}
		}else if(name.equalsIgnoreCase("orc")){
			int m=60;
			if(x<playerX-m)xspd=1;
			else if(x>playerX-m && x<playerX+m){
				shot();
			}
			else if(x>playerX+m)xspd=-1;
			x+=Math.floor(xspd);
		}
	}
	public void draw(Graphics g,int scroll,int playerX,int playerY){
		ai(scroll,playerX,playerY);
		super.drawBullets(g, scroll);
		g.drawImage(animationFly.getFrame(xspd), x-scroll, y, 0);
	}
	
	
}
