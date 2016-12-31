package ftaghn.arpg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Door extends Entity{

	private Paint mBitmapPaint = new Paint();
	// bounding box size
	int sizeX=16;
	int sizeY=16;
	boolean mover;
	// used to access respawn point's coords 
	// (in front of a door)
	public static int doorX, doorY;

	public Door(int x, int y, String type, int doorNum, Context mContext)
	{
		super(x, y, type, x+16, y+16);
		this.doorNum=doorNum;
	}

	public void update()
	{
	  /*
		if (!(mover))
		{
			// ==============================
			// adjust door coords around Hero
			// ==============================

      // for X
			
      if (MyGame.heroX/2<0 && MyGame.heroX/2<-192)
        x-=-192;
      else if (MyGame.heroX/2>0 && MyGame.heroX/2>192)
        x-=192;
      else
        x-=MyGame.heroX/2;
      	
      // for Y
      if (MyGame.heroY/2<0 && MyGame.heroY/2<-290)
        y-=-290;
      else if (MyGame.heroY/2>0 && MyGame.heroY/2>290)
        y-=290;
      else
        y-=MyGame.heroY/2;
			
			
			bounds.left=x;
			bounds.right=x+16;
			bounds.top=y;
			bounds.bottom=y+16;
			
			// ==========================
			// save respawn point coords
			// ==========================
			if (MyGame.doorType.equals("doorLeft") && this.type.equals("doorRight"))
			{
				doorX=x;
				doorY=y;
			}
			if (MyGame.doorType.equals("doorRight")&& this.type.equals("doorLeft"))
			{
				doorX=x;
				doorY=y;
			}
			if (MyGame.doorType.equals("doorUp")   && this.type.equals("doorDown"))
			{
				doorX=x;
				doorY=y;
			}
			if (MyGame.doorType.equals("doorDown") && this.type.equals("doorUp"))
			{
				doorX=x;
				doorY=y;
			}
			//Hero.heroMover();
			mover=true;
		}
		*/
		// =================================
		// when hero move update door coords
		// =================================
		if (MyGame.left && !Map.blockMapRight)
		{
			bounds.left+=4;
			bounds.right+=4;
		}
		if (MyGame.right && !Map.blockMapLeft)
		{
			bounds.left-=4;
			bounds.right-=4;
		}
		if (MyGame.up && !Map.blockMapDown)
		{
			bounds.top+=4;
			bounds.bottom+=4;
		}
		if (MyGame.down && !Map.blockMapUp)
		{
			bounds.top-=4;
			bounds.bottom-=4;
		}
	}

	public boolean collidedWith(Entity entity) {
		return true;
	}

	public void paint(Canvas g)
	{    
		if (debugMode)
		{
			mBitmapPaint.setStyle(Paint.Style.STROKE);
			mBitmapPaint.setColor(Color.WHITE);
			g.drawRect(bounds, mBitmapPaint);
		}
	}  
}
