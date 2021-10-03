/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacklamp.entities;

import blacklamp.Controls;
import blacklamp.Level;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author a
 */
public class Player extends Creature{
	Controls controls;

	public double maxSpeed=2.5;
	public double speed=0.4;
	public double jumpForce=4;

	public boolean onLadder;
	public boolean onStairs;
	public boolean jumping=false;

	public boolean colisionDown=false;
	public boolean colisionUp=false;
	public boolean colisionLeft=false;
	public boolean colisionRight=false;

	public String nextLevel="";

	public boolean lamps[]=new boolean[8];

	int marginx=5;
	int marginy=5;
	

	Animation walkAnimation;
	Animation clmbingAnimation;

	public void checkColisions(Item items[],int scroll){
		colisionDown=false;
		colisionUp=false;
		colisionLeft=false;
		colisionRight=false;

		
		onLadder=false;
		onStairs=false;

		nextLevel="";
		for(int i=0;i<items.length;i++){
			if(items[i].colision){
			
			if(this.checkColisionDown(items[i]))colisionDown=true;

			if(!items[i].type.equalsIgnoreCase("platform")){
					if(this.checkColisionUp(items[i]))colisionUp=true;
					if(this.checkColisionLeft(items[i]))colisionLeft=true;
					if(this.checkColisionRight(items[i]))colisionRight=true;
				}
			}else if(items[i].type.equalsIgnoreCase("ladder")){
				int centered=(x+10)%30;
				if(this.isColiding(items[i]) && centered>13 && centered<22){
					onLadder=true;
					if(yspd!=0){
						xspd=0;
						x=items[i].x+7;
					}
				}
			}
			else if(items[i].type.equalsIgnoreCase("stairs") ){
				if(this.isColiding(items[i])){
					onStairs=true;
					y=(-height)+items[i].y+25;
				}
			}else if(items[i].type.equalsIgnoreCase("stairt")){
				//System.out.println("Minus height: "+minusHeight);
				if(this.isColiding(items[i])){
					onStairs=true;
					y=(-height)+items[i].y;
				}
			}
			else if(items[i].type.startsWith("lamp")){
				int index=items[i].type.charAt(4)-48;
				int carriedLamps=0;
				for(int j=0;j<8;j++)if(this.lamps[j])carriedLamps++;
				if(items[i].isColiding(this) && lamps[index]==false && carriedLamps<1){
					//System.out.println("ing up "+items[i].type);
					items[i].move(-100,-100);
					lamps[index]=true;
				}
			}else if(items[i].type.equalsIgnoreCase("door")){
				if(this.isColiding(items[i])){
					if(x<33)nextLevel="left";
					else if(x>=33 && x<180)nextLevel="up";
					else nextLevel="right";
				}
			}else if(items[i].type.equalsIgnoreCase("doorF")){
				if(this.isColiding(items[i]))nextLevel="up";
			}
		}
	}

	boolean checkColisionRight(Item item){
		if(item.colision){
			return item.isColiding(this.x+this.width-marginx, this.y-marginy) || item.isColiding(this.x+this.width-marginx, this.y+this.height-marginy);
		}
		return false;
	}
	
	boolean checkColisionLeft(Item item){
		if(item.colision){
			return item.isColiding(this.x+marginx, this.y-marginy) || item.isColiding(this.x+marginx, this.y+this.height-marginy);
		}
		return false;
	}

	boolean checkColisionUp(Item item){
		if(item.colision){
			return item.isColiding(this.x+marginx, this.y) || item.isColiding(this.x+width-marginx, this.y);
		}
		return false;
	}
	
	boolean checkColisionDown(Item item){
		if(item.colision){
			return item.isColiding(this.x+marginx, this.y+this.height+marginy) || item.isColiding(this.x+this.width-marginx, this.y+this.height+marginy);
		}
		return false;
	}
	
	public Player(Controls _controls,String name,int x, int y) {
		super(name,x, y);
		this.controls=_controls;
		for(int i=0;i<8;i++)lamps[i]=false;

		walkAnimation=new Animation("/sprites/levels/shared/enemies/joker/walk", 4);
		clmbingAnimation=new Animation("/sprites/levels/shared/enemies/joker/back", 3);
	}

	public void gravity(){
		if(y>=250)colisionDown=true;
		if( !colisionDown && !onStairs && !onLadder){
			yspd+=1;
			y+=yspd;
		}else{
			yspd=0;
		}
	}

	public void walk(){
		if(xspd>0){
			if(!colisionRight)x+=xspd;
		}else{
			if(!colisionLeft)x+=xspd;
		}
	}
	public void update(Level level,Controls key,int scroll){
		checkColisions(level.items,scroll);

		gravity();


		
		if(colisionDown && jumping){
			jumping=false;
		}

		if(key.down()){
			if(onLadder)
			moved(0,3);
		}
		if(key.up()){
			//System.out.println("Jump:"+jumping);
			if(colisionDown)jumping=false;
			if(!colisionUp && colisionDown && !jumping){
				y-=10;
				yspd=-jumpForce;
				
				jumping=true;
			}
			
			if(onLadder)
			moved(0,-3);
		}
		if(key.left()){
			if(xspd>=-maxSpeed)
				xspd-=speed;
		}
		else if(key.right()){
			if(xspd<=maxSpeed)
				xspd+=speed;
		}
		if(!key.left() && !key.right()){
			if(xspd>0.1)xspd-=speed*0.3;
			else if(xspd<-0.1)xspd+=speed*0.3;
			else xspd=0;
		}
		walk();

		if(key.ok()){
			shot();
			//bullet2.shoot(x, y,10*(int)(xspd/Math.abs(xspd)),0);
		}
	}
	
	public void draw(Graphics g,int scroll){

		if(onLadder)
			g.drawImage(clmbingAnimation.getFrame(xspd), x-scroll, y, 0);
		else{
			if(xspd==0)
				g.drawImage(sprite, x-scroll, y, 0);
			else
				g.drawImage(walkAnimation.getFrame(xspd), x-scroll, y, 0);
		}

			
		drawBullets(g, scroll);
	};
	
}
