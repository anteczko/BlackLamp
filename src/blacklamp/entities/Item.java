/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacklamp.entities;

import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.m3g.Image2D;

/**
 *
 * @author a
 */
public class Item {
	public int x,y;
	public int width,height;
	public Image sprite;
	boolean colision;

	public String name;
	public String type;

	public Item(String name){
		this(name,0,0);
	}
	public Item(String name,int x, int y) {
		this.name=name;
		this.x=x;
		this.y=y;
		this.colision=false;
		this.type=extractType();

		try{
			sprite=Image.createImage(name);
			width=sprite.getWidth();
			height=sprite.getHeight();
		}catch(IOException e){
			System.out.println("IMage for item "+name+" was not found!!!");
		}
	}
	public Item(String name,int x, int y,boolean colision){
		this(name,x,y);
		this.colision=colision;
	}

	public void move(int _x,int _y){
		this.x=_x;
		this.y=_y;
	}

	public void moved(int _x,int _y){
		this.x=x+_x;
		this.y=y+_y;
	}
	public boolean isColiding(int _x, int _y){
		if(_x<=x+height && _x>=x && _y<=y+width && _y>y)return true;
		return false;
	}
	public boolean isColiding(Item item){
		if(x>=item.x-width && x<=item.x+item.width && y>=item.y-height && y<item.y+item.height )return true;
		return false;
	
	}

	public void draw(Graphics g,int scroll){
		g.drawImage(sprite,x-scroll,y,0);
	}

	public Image flipImage(Image image){
		int[] source=new int[image.getHeight()*image.getWidth()];
		int[] ret=new int[image.getHeight()*image.getWidth()];
		image.getRGB(source, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
		int w=image.getWidth();
		int h=image.getHeight();

		for(int j=0;j<image.getHeight();j++){
			for(int i=0;i<image.getWidth();i++){
				int org=w*j+i;
				int cha=w*j+(w-i-1);
				ret[org]=source[cha];
			}
		}
		Image flipped=Image.createRGBImage(ret, w, h, true);
		return flipped;
	}
	
	String extractType(){
		String temp="";
		String type="";

		boolean start=false;
		for(int i=name.length()-1;i>0;i--){
			if(name.charAt(i)=='.')start=true;
			else if(name.charAt(i)=='/')break;
			else if(start)temp+=name.charAt(i);
		}
		for(int i=0;i<temp.length();i++)type+=temp.charAt(temp.length()-1-i);
		return type;
	}

}
