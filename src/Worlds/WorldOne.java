package Worlds;

import Game.Entities.Static.Apple;
import Game.GameStates.State;
import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class WorldOne extends WorldBase{
	
	public int pSteps = 0;

    public WorldOne (Handler handler) {
        super(handler);

        //has to be a number bigger than 20 and even
        GridWidthHeightPixelCount = 60;
        GridPixelsize = (800/GridWidthHeightPixelCount);
        playerLocation = new Boolean[GridWidthHeightPixelCount][GridWidthHeightPixelCount];
        appleLocation = new Boolean[GridWidthHeightPixelCount][GridWidthHeightPixelCount];

    }

    @Override
    public void tick() {
        super.tick();
        player.tick();
        pSteps+=1;
        if(!appleOnBoard){
            appleOnBoard=true;
            int appleX = new Random().nextInt(handler.getWorld().GridWidthHeightPixelCount-1);
            int appley = new Random().nextInt(handler.getWorld().GridWidthHeightPixelCount-1);

            //change coordinates till one is selected in which the player isnt standing
            boolean goodCoordinates=false;
            do{
                if(!handler.getWorld().playerLocation[appleX][appley]){
                    goodCoordinates=true;
                }
            }while(!goodCoordinates);

            apple = new Apple(handler,appleX,appley);
            appleLocation[appleX][appley]=true;
            
            if (goodCoordinates) {
				
			}
            if (pSteps == 1) {
				handler.getWorld().apple.appleGood=false;
				pSteps=0;
				//System.out.println(1);
				//Color purple2 = new Color(204,153,255);
			}

        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
        	State.setState(handler.getGame().pauseState);
        }
        
    }

    @Override
    public void render(Graphics g){
        super.render(g);
        player.render(g,playerLocation);
        apple.render(g, appleLocation);
        
        Font font = new Font ("SansSerif", Font.PLAIN, 24);
		g.setFont(font);
		g.drawString("SCORE", 10, 20);
		g.drawString(String.valueOf(player.scoreTracker), 20, 40);
    }
    

}
