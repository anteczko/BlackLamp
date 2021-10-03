/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacklamp.entities;

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author a
 */
public class Creature extends Item{
	public double xspd=0;
	public double yspd=0;
	public boolean flipped=false;

	public int bulletIndex=0;
	public Bullet bullets[];

	public int cooldown=0;
	public int maxCooldown=20;

	public Creature(String name,int x, int y) {
		super(name,x,y);
		bullets=new Bullet[10];
		for(int i=0;i<10;i++)
			bullets[i]=new Bullet();
	}

	public void shotDown(){
		cooldown++;
		if(cooldown>=maxCooldown){
			bullets[bulletIndex].shoot(x, y,0, 10);
			bulletIndex++;
			if(bulletIndex>=10)bulletIndex=0;
			cooldown=0;
		}
	}
	public void shot(){
		cooldown++;
		if(cooldown>=maxCooldown){
			bullets[bulletIndex].shoot(x, y, 10*(int)(xspd/Math.abs(xspd)), (int)yspd);
			bulletIndex++;
			if(bulletIndex>=10)bulletIndex=0;
			cooldown=0;
		}
	}
	public void move(){
		this.x+=xspd;
		this.y+=yspd;
	}

	public void drawBullets(Graphics g, int scroll){
		for(int i=0;i<10;i++){
			bullets[i].draw(g, scroll);
		}
	}
	public void draw(Graphics g,int scroll){
		super.draw(g, scroll);
		drawBullets(g, scroll);
	}
}

