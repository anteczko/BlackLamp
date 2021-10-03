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
public class LevelCanvas extends Canvas implements CommandListener{
	Controls key;
	boolean GameRunning=true;
	Loop loop;

	Player player;
	Level level;

	LampSlot lampSlot=new LampSlot(-100,-100);
	int scroll=0;

	int coordinates[]=new int[3];

	Enemy enemies[]=new Enemy[3];
	
	public LevelCanvas(Midlet _midlet){
		key=new Controls();
		loop=new Loop();
		loop.start();
		System.out.println("Hello midlet");
		System.out.print("Width:"+getWidth()+" Height:"+getHeight());

		coordinates[0]=0;
		coordinates[1]=0;
		coordinates[2]=0;
	

		player=new Player(key,"/sprites/test.png",50,230);
		loadLevel();

		lampSlot.move(level.lampSlotX, level.lampSlotY);
	}
	public void loadLevel(){
		String tempName="level";
		int x=coordinates[0];
		int y=coordinates[1];
		int w=coordinates[2];

		if(player.nextLevel=="left")x--;
		else if(player.nextLevel=="right")x++;
		else if(player.nextLevel=="up")y--;
		else if(player.nextLevel=="down")y++;
		
		if(x>=0)tempName+='+';
		tempName+=x;
		
		if(y>=0)tempName+='+';
		tempName+=y;

		if(w>=0)tempName+='+';
		tempName+=w;
		
		System.out.println("Trying to load level named:"+tempName);
		loadLevel(tempName);

		if(player.nextLevel=="left")player.move(level.width-30,220);
		else if(player.nextLevel=="right")player.move(33,220);
	}
	public void loadLevel(String name){
		coordinates[0]=name.charAt(6)-48;
		coordinates[1]=name.charAt(8)-48;
		coordinates[2]=name.charAt(10)-48;
		//load level from file
		name="/resources/levels/"+name+".txt";
		Level temp=new Level(name);
		this.level=temp;

		//you should set lamp's x and y if it's present on level
		lampSlot.move(level.lampSlotX, level.lampSlotY);
		enemies[0]=new Enemy("hawk",10,10);
		enemies[1]=new Enemy("witch",150);
		enemies[2]=new Enemy("orc",200);
	}

	protected void paint(Graphics g) {
		if(player.x<120)scroll=0;
		else if(player.x>=120 && player.x<level.width-120)scroll=player.x-120;
		else scroll=level.width-240;

		level.RednerFully(g,scroll);

		lampSlot.draw(g, scroll);
		player.draw(g,scroll);
		player.update(level, key, scroll);
		lampSlot.updateLamps(player);

		if(key.isPressed('0')){
			System.out.println("Zero key pressed!!!!");
			loadLevel("file2");
		}
		for(int i=0;i<3;i++)
			enemies[i].draw(g, scroll,player.x,player.y);

		
		if(player.nextLevel.equalsIgnoreCase("left") && key.left()){
			System.out.println("Level width "+level.width);
			loadLevel();
		}
		else if(player.nextLevel.equalsIgnoreCase("right") && key.right()){
			loadLevel();
		}
		else if(player.nextLevel.equalsIgnoreCase("up") && key.up()){
			loadLevel();
		}
		else if(player.nextLevel.equalsIgnoreCase("down") && key.down()){
			loadLevel();
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
