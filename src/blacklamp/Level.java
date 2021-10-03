/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacklamp;

import blacklamp.entities.Item;
import blacklamp.entities.LampSlot;
import com.sun.j2mews.xml.rpc.Encoder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author a
 */
public class Level {

	String levelType;
	char tiles[][];
	int rows,cols;

	//parts of bacground, colision detection not needed
	Image background;
	Image floor;
	Image window;

	public Item items[];
	public int width=0;

	public int lampSlotX=-60;
	public int lampSlotY=-60;
	

	public Level(String path){
		lampSlotX=-60;
		lampSlotY=-60;
		cols=0;
		rows=1;
		levelType="insideCobblestone";
		setVariables(file(path));
		//tiles = new char[40][44];
		parse(file(path));

		try {
			background=Image.createImage("/sprites/levels/"+levelType+"/background.png");
			window=loadImg("window");
			floor=loadImg("floor");
			//System.out.println(file(path));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	private Image loadImg(String name){
		try {
			return Image.createImage(URI(name));
		} catch (IOException ex) {
			System.out.println("ERROR!!! Can't load file"+URI(name));
			ex.printStackTrace();
		}
		return null;
	}
	
	private String URI(String name){
		return "/sprites/levels/"+levelType+"/"+name+".png";
	}

	private String file(String path) {
		try {
			InputStream in = getClass().getResourceAsStream(path);
			int n=in.available();
			byte b[] = new byte[n+2];
			try {
				in.read(b, 0, n);
				String str=new String(b,"UTF-8");
				in.close();
				return str;
			} catch (IOException ex) {
				ex.printStackTrace();
				System.out.println("FILEERROE");
			}
			return null;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void setVariables(String str){
		int spaces=0;
		int n=0;
		for (int a = 0; a < str.length(); a++) {
			switch(str.charAt(a)){
				case '\n':	rows++;	break;
				case 'o': ;
				case ' ':	spaces++;	break;
				default:	n++;		break;
			}
		}
		cols=((str.length()-1-rows)/rows);
		int noOfItems=(str.length()-rows-1)-spaces;
		items=new Item[noOfItems];
		tiles=new char[cols+2][rows+2];
		System.out.println("\nCols:"+cols+" Rows:"+rows+" NoOfItems:"+noOfItems+" n:"+n+" LEngth:"+str.length());

		width=30*(cols);
	}

	private void parse(String str){
		int i = 0;
		int j = 0;
		int n=0;
		for (int a = 0; a < str.length(); a++) {
			if (str.charAt(a) == '\n') {
				i = 0;
				j++;
				System.out.println("");
			} else {
				tiles[i][j] = str.charAt(a);
				
				if( addItem(n, tiles[i][j], i, j) )n++;

				System.out.print(tiles[i][j]);
				i++;
			}
		}
	}

	private boolean addItem(int i, char c,int x, int y){
		//x and y are tile's index number, not real pixel coordinates
		String name="ERROR";
		boolean colision=false;
		switch(c){
			case '\n': ;
			case '\0': ;
			case ' ': return false;
			case 'w':	name="window";	break;
			case 'p':	name="platform";	colision=true;	break;
			case 'd':	name="door";	break;
			case 'D':	name="doorF";	break;
			case 'a':	name="ladder";	break;
			case 'c':	name="crate";	colision=true;	break;
			case 'b':	name="barrels";	colision=true;	break;
			case 's':	name="stairs";	break;
			case 'S':	name="stairt";	break;
			case 'l':	name="wall";	break;
			case 'L':	name="walL";	break;
			case '7':	
			case '6':	
			case '5':	
			case '4':	
			case '3':	
			case '2':	
			case '1':	
			case '0':	
				name="lamp"+c;	
				items[i]=new Item("/sprites/levels/shared/"+name+".png",30*x,31*y,false);
				return true;
			case 'o':
				lampSlotX=30*x;
				lampSlotY=31*y+13;
				System.out.println("Lamp slot detecrted at x:"+lampSlotX+" y:"+lampSlotY);
				break;
			default: 
				System.out.println("Encountered undefined cahracter in level file!!!! "+(int)c+" "+c+" x:"+x+" y"+y);
			return false;
		}
		if(name!="ERROR"){
			items[i]=new Item(URI(name), x*30, y*31,colision);
			return true;
		}
		System.out.println("Not showing"+name);
		return false;
	}

	public void RednerFully(Graphics g,int scroll,LampSlot lampSlot){
		RednerFully(g, scroll);
		lampSlot.draw(g, scroll);
	}
	public void RednerFully(Graphics g,int scroll){
		//g.setColor(255,255,255);
		//g.fillRect(0, 0, 240, 291);

		for(int j=0;j<=290;j+=62)
		for(int i=0;i<280;i=i+60){
			g.drawImage(background, i-scroll%60, j, 0);
			//if(j<100)g.drawImage(window, i-scroll%60, j, 0);
			g.drawImage(floor, i-scroll%60, 260, 0);
			g.drawImage(floor, i+30-scroll%60, 260, 0);
		}
		
		for(int i=0;i<items.length;i++){
			//System.out.println(i+" "+items[i].name);
			items[i].draw(g, scroll);
		}
	
	}
}
