/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blacklamp;

/**
 *
 * @author a
 */
public class Controls {
	boolean arrows[];
	boolean num[];

	public Controls() {
		arrows=new boolean[10];
		num=new boolean[60];
	}

	public void press(int keyCode){
		if(keyCode<0){
			arrows[-keyCode]=true;
		}else if(keyCode>0){
			num[keyCode]=true;
		}
	}

	public void release(int keyCode){
		if(keyCode<0){
			arrows[-keyCode]=false;
		}else if(keyCode>0){
			num[keyCode]=false;
		}
	}

	public boolean isPressed(int keyCode){
		if(keyCode<0){
			return arrows[-keyCode];
		}else if(keyCode>0){
			return num[keyCode];
		}
		return false;
	}

	public boolean up(){
		return arrows[1];
	}
	public boolean down(){
		return arrows[2];
	}
	public boolean left(){
		return arrows[3];
	}
	public boolean right(){
		return arrows[4];
	}
	public boolean ok(){
		return arrows[5];
	}

}
