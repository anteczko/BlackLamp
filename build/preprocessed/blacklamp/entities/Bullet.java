/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacklamp.entities;

import javax.microedition.lcdui.Graphics;

/**
 *
 * @author a
 */
public class Bullet extends Item{
	public double xspd=0;
	public double yspd=0;
	public boolean flipped=false;

	public boolean onScreen;

	public Bullet() {
		this(-10,-10,0,0);
	}

	public Bullet(int x, int y, int xspd,int yspd) {
		super("/sprites/bullet.png", x, y);
		this.xspd=xspd;
		this.yspd=yspd;
		this.onScreen=false;
	}
	private void update(int scroll){
		x+=xspd;
		y+=yspd;
		if(x-scroll<0 || x-scroll>240 || y<0 || y>290){
			xspd=0;
			yspd=0;
			x=-100;
			y=-100;
			onScreen=false;
		}
	}
	public void shoot(int x,int y, int xspd,int yspd){
		this.x=x;
		this.y=y;
		this.xspd=xspd;
		this.yspd=yspd;	
		onScreen=true;
	}
	public void draw(Graphics g,int scroll){
		update(scroll);
		if(onScreen)g.drawImage(sprite, x-scroll, y,0);
	}

	
}
