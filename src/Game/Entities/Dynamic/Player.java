package Game.Entities.Dynamic;

import Main.Handler;


import java.math.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JOptionPane;

import Game.GameStates.State;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

	public int lenght;
	public boolean justAte;
	private Handler handler;

	public int xCoord;
	public int yCoord;

	public int moveCounter;
	public int pSteps;
	
	public double scoreTracker = 0;

	public String direction;//is your first name one?

	public Player(Handler handler){
		this.handler = handler;
		xCoord = 0;
		yCoord = 0;
		moveCounter = 0;
		direction= "Right";
		justAte = false;
		lenght= 1;

	}

	public void tick(){
		moveCounter++;
		pSteps++;
		
		if(moveCounter>=5) {
			checkCollisionAndMove();
			moveCounter=0;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_ADD)){
			moveCounter=4;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)){
			moveCounter=-4;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) && direction != "Down"){
			direction="Up";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN) && direction != "Up"){
			direction="Down";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT) && direction != "Right"){
			direction="Left";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT) && direction != "Left"){
			direction="Right";
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
			lenght++;
			Tail tail= null;

			tail = new Tail(this.xCoord+1,this.yCoord,handler);

			handler.getWorld().body.addLast(tail);
			handler.getWorld().playerLocation[tail.x][tail.y] = true;
		}

	}

	public void checkCollisionAndMove(){
		handler.getWorld().playerLocation[xCoord][yCoord]=false;
		int x = xCoord;
		int y = yCoord;
		switch (direction){
		case "Left":
			if(xCoord==0){
				xCoord = handler.getWorld().GridWidthHeightPixelCount -1;
			}else{
				xCoord--;
			}
			break;
		case "Right":
			if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				xCoord = 0;
			}else{
				xCoord++;
			}
			break;
		case "Up":
			if(yCoord==0){
				yCoord = handler.getWorld().GridWidthHeightPixelCount -1;
			}else{
				yCoord--;
			}
			break;
		case "Down":
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				yCoord = 0;
			}else{
				yCoord++;
			}
			break;
		}
		handler.getWorld().playerLocation[xCoord][yCoord]=true;


		if(handler.getWorld().appleLocation[xCoord][yCoord]){
			Eat();
		}

		if(!handler.getWorld().body.isEmpty()) {
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
			handler.getWorld().body.removeLast();
			handler.getWorld().body.addFirst(new Tail(x, y,handler));
		}
		for (int i = 0; i <= handler.getWorld().body.size()-1; i++) {
			if (xCoord == handler.getWorld().body.get(i).x && yCoord == handler.getWorld().body.get(i).y) {
				int n = JOptionPane.showConfirmDialog(
					    null,
					    "YOU DIED\n" + "Do you want to get back to the menu?",
					    "An Inane Question",
					    JOptionPane.YES_NO_OPTION);
				if(n == JOptionPane.YES_OPTION) {
					State.setState(handler.getGame().menuState);
				}
				else if(n == JOptionPane.NO_OPTION) {
					System.exit(1);
				}
			}
		}

	}

	public void render(Graphics g,Boolean[][] playeLocation){
		Random r = new Random();
		int nr = r.nextInt(256); int ng = r.nextInt(256); int nb = r.nextInt(256);
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				if(playeLocation[i][j]){
					g.setColor(new Color(nr,ng,nb));
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}

			}
		}
		if(pSteps == 500) {
			handler.getWorld().apple.appleGood = false;
		}
		//Show score
		Font font = new Font ("SansSerif", Font.BOLD, 22);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("SCORE" + String.valueOf(scoreTracker), 10, 20);

	}

	public void Eat(){
		lenght++;
		pSteps = 0;
		Tail tail= null;
		handler.getWorld().appleLocation[xCoord][yCoord]=false;
		handler.getWorld().appleOnBoard=false;
		switch (direction){
		case "Left":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail = new Tail(this.xCoord+1,this.yCoord,handler);
					justAte = true;
				}else{
					if(this.yCoord!=0){
						tail = new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail =new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
					justAte = true;
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
					}else{
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

					}
				}

			}
			break;
		case "Right":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=0){
					tail=new Tail(this.xCoord-1,this.yCoord,handler);
					justAte = true;
				}else{
					if(this.yCoord!=0){
						tail=new Tail(this.xCoord,this.yCoord-1,handler);
						justAte = true;
					}else{
						tail=new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					justAte = true;
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
					}
				}

			}
			break;
		case "Up":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(this.xCoord,this.yCoord+1,handler));
					justAte = true;
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					}
				}
			}else{
				if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		case "Down":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=0){
					tail=(new Tail(this.xCoord,this.yCoord-1,handler));
					justAte = true;
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body.getLast().y!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		}
		
		//Score of player
		if(handler.getWorld().apple.appleGood == false) {
			scoreTracker = scoreTracker - Math.sqrt(2*scoreTracker+1);
		}
		
		if(handler.getWorld().player.justAte == true && handler.getWorld().apple.appleGood == true) {
			scoreTracker = Math.sqrt(2*scoreTracker+1);
			handler.getWorld().body.addLast(tail);
			handler.getWorld().playerLocation[tail.x][tail.y] = true;
			moveCounter=moveCounter+4;
			
		}
		if (handler.getWorld().player.justAte == true && handler.getWorld().apple.appleGood == false) {
			if(!handler.getWorld().body.isEmpty()){
				handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
				handler.getWorld().body.removeLast();
				kill();
			}
			else {
				kill();
			}
				
		}
	}

	public void kill(){
		lenght = 0;
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				handler.getWorld().playerLocation[i][j]=false;

			}
		}
	}

	public boolean isJustAte() {
		return justAte;
	}

	public void setJustAte(boolean justAte) {
		this.justAte = justAte;
	}
}