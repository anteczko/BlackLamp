/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacklamp.entities;

import java.io.IOException;
import javax.microedition.lcdui.Image;

/**
 *
 * @author a
 */
public class Animation {
	public boolean flipped=false;
	int noOfFrames;
	int j=0;
	int animationSpeed=10;
	int animationTimer=0;
	String name;

	public Image frame[];
	Image frameReversed[];

	public Animation(String name,int noOfFrames) {
		this.noOfFrames=noOfFrames;
		frame=new Image[noOfFrames+1];
		frameReversed=new Image[noOfFrames+1];
		int i=0;
		String temp="";
		try{
			for(i=0;i<noOfFrames;i++){
				temp=name+(1+i);
				//System.out.println("Loading: "+temp);
				frame[i]=Image.createImage(temp+".png");
				frameReversed[i]=flipImage(frame[i]);
			}
		}catch(IOException e){
			System.out.println("Can't load animation by name"+temp+".png");
		}
	}

	public Image getFrame(double xspd){
		boolean flipped=xspd<0;
		animationTimer++;
		if(animationTimer>animationSpeed && xspd!=0){
			j++;
			if(j>=noOfFrames)j=0;
			animationTimer=0;
			//System.out.println("Frame switched to:"+j);
		}
		if(flipped)return this.frameReversed[j];
		return this.frame[j];
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
	
}
