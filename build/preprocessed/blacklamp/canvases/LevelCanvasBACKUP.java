/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacklamp.canvases;

import blacklamp.Controls;
import blacklamp.Level;
import blacklamp.Midlet;
import blacklamp.entities.Animation;
import blacklamp.entities.Bullet;
import blacklamp.entities.Creature;
import blacklamp.entities.Enemy;
import blacklamp.entities.Item;
import blacklamp.entities.LampSlot;
import blacklamp.entities.Player;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author a
 */
public class LevelCanvasBACKUP extends Canvas implements CommandListener{
	Controls key;
	boolean GameRunning=true;
	Loop loop;

	Image testo;
	Item chicken;
	Player player;

	Creature bullet;
	
	Level level;

	LampSlot lampSlot=new LampSlot(30*3,31*7);

	Enemy witch;

	Bullet bullet2;

	Enemy witch2;
	Enemy orc;

	int scroll=0;
	
	public LevelCanvasBACKUP(Midlet _midlet){
		key=new Controls();
		loop=new Loop();
		loop.start();
		System.out.println("Hello midlet");
		System.out.print("Width:"+getWidth()+" Height:"+getHeight());
	
		try{
			testo=Image.createImage("/sprites/testo.jpg");
		}catch(IOException e){
			System.out.println("No testo image found");
		}
		chicken=new Item("/sprites/chicken.png",150,200);
		chicken.sprite=chicken.flipImage(chicken.sprite);

		player=new Player(key,"/sprites/test.png",100,100);

		level=new Level("/resources/levels/file.txt");
		lampSlot.move(level.lampSlotX, level.lampSlotY);

		bullet=new Creature("/sprites/bullet.png",10,10);
		witch=new Enemy("hawk",10,10);
		witch2=new Enemy("witch",150);
		orc=new Enemy("orc",200);

		bullet2=new Bullet();

	}

	protected void paint(Graphics g) {
		g.setColor(255,255, 255);
		g.setColor(0,0,0);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(255, 0, 0);
		g.fillRect(150, 150, 50, 50);

		

		if(player.x>=120 && player.x<level.width-120)scroll=player.x-120;
		level.RednerFully(g,scroll);


		lampSlot.draw(g, scroll);
		
		//g.drawImage(testo, 0, 0, 0);
		chicken.draw(g,scroll);
		player.draw(g,scroll);
		bullet2.draw(g, scroll);

		checkColisions();
		player.gravity();

		witch.draw(g, scroll,player.x,player.y);
		witch2.draw(g, scroll,player.x,player.y);
		orc.draw(g, scroll,player.x,player.y);


		
		if(player.colisionDown && player.jumping){
			player.jumping=false;
		}

		if(key.down()){
			if(player.onLadder)
			player.moved(0,3);
		}
		if(key.up()){
			//System.out.println("Jump:"+player.jumping);
			if(player.colisionDown)player.jumping=false;
			if(!player.colisionUp && player.colisionDown && !player.jumping){
				player.y-=10;
				player.yspd=-player.jumpForce;
				
				player.jumping=true;
			}
			
			if(player.onLadder)
			player.moved(0,-3);
		}
		if(key.left()){
			if(player.xspd>=-player.maxSpeed)
				player.xspd-=player.speed;
		}
		else if(key.right()){
			if(player.xspd<=player.maxSpeed)
				player.xspd+=player.speed;
		}
		if(!key.left() && !key.right()){
			if(player.xspd>0.1)player.xspd-=player.speed*0.3;
			else if(player.xspd<-0.1)player.xspd+=player.speed*0.3;
			else player.xspd=0;
		}
		player.walk();

		if(key.ok()){
			player.shot();
			//bullet2.shoot(player.x, player.y,10*(int)(player.xspd/Math.abs(player.xspd)),0);
		}
		if(key.isPressed('0')){
			System.out.println("Zero key pressed!!!!");
		}
	}

	public void commandAction(Command c, Displayable d) {
	}

	protected void keyPressed(int keyCode){
		key.press(keyCode);
		//System.out.println("Key pressed"+keyCode);
		//if(key.up())System.out.println("Pressed up!!!");
	}

	protected void keyReleased(int keyCode){
		key.release(keyCode);
		//System.out.println("Key released"+keyCode);
	}
	
	void checkColisions(){
		if(player.isColiding(chicken))System.out.println("Chickeeeeenn!!!!");

		player.checkColisions(level.items,scroll);

		if(lampSlot.isColiding(player)){
			for(int i=0;i<8;i++){
				if(!lampSlot.lamps[i] && player.lamps[i]){
					lampSlot.lamps[i]=true;
					player.lamps[i]=false;
				}
			}
		}

	}
	
	class Loop extends Thread{
		public void run() {
			while(GameRunning){
				try{
					sleep(15);

		
					//System.out.println("Threaded!!!");
					repaint();
					serviceRepaints();
				}catch(Exception e){
					GameRunning=false;
				}
			
			}
		}
	}
}
