package Game.Entities.Static;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

	private Handler handler;

	public int xCoord;
	public int yCoord;
	public boolean appleGood = true;

	public Apple(Handler handler,int x, int y){
		this.handler=handler;
		this.xCoord=x;
		this.yCoord=y;

	}
	public void render(Graphics a,Boolean[][] appleLocation){
		Random r = new Random();
		Color green = new Color(43,148,53);
		Color brown = new Color(102,0,0);
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				if(handler.getWorld().appleLocation[i][j]){
					if (appleGood == true) {
						a.setColor(green);
						
						a.fillRect((i*handler.getWorld().GridPixelsize),
								(j*handler.getWorld().GridPixelsize),
								handler.getWorld().GridPixelsize,
								handler.getWorld().GridPixelsize);

					}
					if (appleGood == false) {
						a.setColor(brown);
						a.fillRect((i*handler.getWorld().GridPixelsize),
								(j*handler.getWorld().GridPixelsize),
								handler.getWorld().GridPixelsize,
								handler.getWorld().GridPixelsize);
					}
				}

			}
		}


	}
}

