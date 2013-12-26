import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class Main extends BasicGame
{	
	//game images
	static Image p1, p2, ball, scoreboard;
	
	//Physics variables
	double CurrentPosition = ballx;
	
	//Paddle variables
	float x = 10, x1 = 580, y = 275, y1 = 275;
	int p1width=10, p1height=52;
	int p2width=10, p2height=52;
	float pspeed=0.60f;
	
	//Ball variables
	static float ballx = 295, bally = 295;
	float ballxspeed = 0.2f*2, ballyspeed = 0.15f*2;
	int ballsize=10;
	
	//miscellaneous variables
	int p1count=0, p2count=0;
	public static String title = "Pong";

	public static int width = 600, height = 600;

	public Main(String title)
	{
		super(title);
	}

	public static void main(String args[]) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new Main("Pong"));
		app.setDisplayMode(600, 600, false);
		app.start();
	}

	//draw all the graphics
	public void render(GameContainer gc, Graphics g)
	{
		//background
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 600);
		
		//paddles
		g.setColor(Color.white);
		g.drawImage(p1, x, y);
		g.fillRect(x1, y1, 10, 50);

		//ball
		g.setColor(Color.white);
		g.drawImage(ball, ballx, bally);
		
		//scores
		g.drawImage(scoreboard, width/3, 15);
		g.setColor(new Color(93, 12, 249));
		g.drawString(" "+p1count, width/3, height/24);
		g.drawString(" "+p2count, (width*2/3)-(width/6), height/24);		
	}

	//load all the stuff
	public void init(GameContainer gc) throws SlickException
	{
		p1 = new Image("p1.png");
		ball = new Image("ball.png");
		scoreboard = new Image("scoreboard.png");
	}

	//game logic
	public void update(GameContainer gc, int delta) throws SlickException
	{
		Input input = gc.getInput();//asked Slick2D what keys are being pressed

		//if up key is being pressed
		if(input.isKeyDown(Input.KEY_UP))
		{	//boundaries
			y-=pspeed*delta;
			if(y<=10)
				y=10;
		}

		//if down key is being pressed
		if(input.isKeyDown(Input.KEY_DOWN))
		{	//boundaries
			if(y>=538)
				y=538;
			else
				y+=pspeed*delta;
		}

		//if w key is being pressed
		if(input.isKeyDown(Input.KEY_W))
		{
			y1-=pspeed*delta;
			if(y1<=10)
				y1=10;
		}

		//if s key is being pressed
		if(input.isKeyDown(Input.KEY_S))
		{
			if(y1>=540)
				y1=540;
			else
				y1+=pspeed*delta;
		}

		//ball reset
		if(ballx<-10)
		{
			p2count++;
			ballx = width/2-(ballsize/2);
			bally = height/2-(ballsize/2);
		}
		if(ballx>width+20)
		{
			p1count++;
			ballx = width/2-(ballsize/2);
			bally = height/2-(ballsize/2);
		}

		//collision
		if((ballx<=x+10 && ballx>x) && (bally>y && bally<y+p1height))
		{
			ballxspeed=-ballxspeed;
			CurrentPosition=ballx;
			CurrentPosition+=ballxspeed;
			bally+=ballyspeed*delta;
		}
		if(ballx<=x1 && ballx>=x1-10 && (bally>y1 && bally<y1+p2height))
		{
			ballxspeed=-ballxspeed;
			CurrentPosition=ballx;
			CurrentPosition-=ballxspeed;
			bally+=ballyspeed*delta;
		}
		
		//top and bottom border collisions
		if(bally<=0 || bally>=height)
		{
			ballyspeed=-ballyspeed;
			CurrentPosition=bally;
			CurrentPosition-=ballyspeed;
			ballx+=ballxspeed*delta;
		}
		
		//speed updates
		ballx-=ballxspeed*delta;
		bally-=ballyspeed*delta;
	}
}
