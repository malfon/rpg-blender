import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/* I was struggling with the gamestate,
 * so I'm trying some boolean systems
 * to run the title screen, game, and some other possibilities.
 * I'll still upload the gamestate java file.
 * Also, excuse the messiness lol.
 */

public class Game extends BasicGame
{
	//game images
	Image character_up, character_down, character_left, character_right;
	Image character_left_walk_1;
	Image map;
	Image[] npc = new Image[2];
	Image fightbg;
	Image playerhp, enemyhp;
	Image titleScreen;
	Image playbutton;

	int CharacterSize = 50;
	float PlayerSpeed = 0.1f;

	int TileSize = 50;
	
	boolean left=false, right=false, up=false, down=false;
	
	boolean titlescreen=true;
	boolean key_space=false;
	boolean play=false;
	boolean pause=false;
	boolean dialog = false;
	boolean walk=false;

	int Player_x = width/2;  // Player position at x 
	int Player_y = height/2;    // Player position at y 

	static String title = "RPG";
	static int width=700, height=700;

	public Game(String title)
	{
		super(title);
	}

	public static void main(String args[]) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new Game(title));
		app.setDisplayMode(width, height, false);
		app.start();
	}

	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		Input input = gc.getInput();

		if(titlescreen)
		{
			g.drawImage(titleScreen, 0, 0);
			g.drawImage(playbutton, width*3/5, height*4/5);

			if((input.getMouseX()>width*3/5 && input.getMouseX()<(width*3/5)+200)&&(input.getMouseY()>height*4/5 && input.getMouseY()<(height*4/5)+100))
			{
				g.setColor(new Color(255, 0, 0, 100));
				g.fillRect(width*3/5, height*4/5, 200, 100);
			}

			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON) && ((input.getMouseX()>width*3/5 && input.getMouseX()<(width*3/5)+200)&&(input.getMouseY()>height*4/5 && input.getMouseY()<(height*4/5)+100)))
			{
				titlescreen=false;
				play=true;
			}
		}

		if(!titlescreen && play && !pause)
		{
			g.setColor(new Color(75, 75, 75));
			g.fillRect(0, 0, width, height);
			//drawing map
			for(int j=0; j<14; j++)
			{
				for(int i=0; i<14; i++)
				{
					g.drawImage(map, i*50+1, j*50+1);
				}
			}
			//drawing character
			if(up)
			{
				g.drawImage(character_up, Player_x, Player_y);
			}
			else if(down)
			{
				g.drawImage(character_down, Player_x, Player_y);
			}
			else if(left)
			{
				int count=0;
				if(!walk)
				{
					g.drawImage(character_left, Player_x, Player_y);
				}
				else if(walk)
				{
					g.drawImage(character_left_walk_1, Player_x, Player_y);
				}
				if(count==1000)
				{
					walk=true;
					count=0;
				}
				else if(count!=1000)
				{
					walk=false;
					count++;
				}
			}
			else if(right)
			{
				g.drawImage(character_right, Player_x, Player_y);
			}
			else if(!up && !down && !left && !right)
			{
				g.drawImage(character_down, Player_x, Player_y);
			}

			//drawing npc
			g.drawImage(npc[0], 351, 1);

			//mouseover
			for(int j=0; j<14; j++)
			{
				for(int i=0; i<14; i++)
				{
					if((input.getMouseX()>i*50+1 && input.getMouseX()<i*50+50) && (input.getMouseY()>j*50+1 && input.getMouseY()<j*50+50))
					{
						g.setColor(new Color(255, 255, 255, 50));
						g.fillRect(i*50+1, j*50+1, 48, 48);
					}
				}
			}
		}
		
		if(dialog)
		{
			g.setColor(Color.white);
			g.drawString("Hello!", 351, 1);
			System.out.println("hello");
		}

		if(key_space)
		{
			g.drawImage(fightbg, 0, 0);
			g.drawImage(playerhp, 0, height-50);
			g.drawImage(enemyhp, width-200, 0);
		}

		while(key_space)
		{
			if(input.isKeyPressed(Input.KEY_SPACE))
			{

			}
		}
	}

	public void init(GameContainer gc) throws SlickException
	{
		character_up = new Image("character_up.png");
		character_down = new Image("character_down.png");
		character_left = new Image("character_left.png");
		character_left_walk_1 = new Image("character_left_walk_1.png");
		character_right = new Image("character_right.png");
		map = new Image("map.png");
		npc[0] = new Image("npc0.png");
		fightbg=new Image("fightbg.png");
		playerhp=new Image("playerhp.png");
		enemyhp=new Image("enemyhp.png");
		titleScreen=new Image("titlescreen.png");
		playbutton=new Image("play.png");
	}

	public void update(GameContainer gc, int delta) throws SlickException
	{
		Input input = gc.getInput();

		if(input.isKeyDown(Input.KEY_UP))
		{
			down=false;
			left=false;
			right=false;
			up=true;
			Player_y-=PlayerSpeed*delta;
			if(Player_y<1)
				Player_y=1;
		}
		if(input.isKeyDown(Input.KEY_DOWN))
		{
			up=false;
			left=false;
			right=false;
			down=true;
			Player_y += PlayerSpeed*delta*2.5;
			if(Player_y >= height-CharacterSize)
				Player_y = height-CharacterSize;
		}
		if(input.isKeyDown(Input.KEY_LEFT))
		{
			down=false;
			up=false;
			right=false;
			left=true;
			Player_x-=PlayerSpeed * delta * 1.1;
			if(Player_x<1)
				Player_x = 1;
		}
		if(input.isKeyDown(Input.KEY_RIGHT))
		{
			down=false;
			left=false;
			up=false;
			right=true;
			Player_x += PlayerSpeed * delta*2.5;
			if(Player_x > width-CharacterSize)
				Player_x = width-CharacterSize;
		}
		if(input.isKeyPressed(Input.KEY_SPACE))
		{
			key_space=true;
		}
		if(Player_x>350 && Player_x<400-CharacterSize && Player_y>50 && Player_y<100-CharacterSize)
		{
			if(input.isKeyPressed(Input.KEY_T))
			{
				dialog=true;
			}
		}
	}
}
