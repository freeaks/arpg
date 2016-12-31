package ftaghn.arpg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Map extends Entity{

  private Paint bitmapPaint = new Paint();
  public static boolean 
  blockMapLeft,
  blockMapRight,
  blockMapUp,
  blockMapDown,
  blockMap;

  private Bitmap tilePicDest = MapLoader.tilePicDest;

  private static int xPos, yPos, tempX, tempY;
  int speed=2;

  
  public Map(int x, int y, String type, Context ctx)
  {
    // do not create bounding box for map
    super(x, y, type, 0, 0); 
		tempX=x;
		tempY=y;
  }

  public void update()
  {
    // ================= 
    // blockedMap rules
    // =================
    if (x-320<=-tilePicDest.getWidth())  { blockMapLeft=true; }else{ blockMapLeft=false; }
    if (y-430<=-tilePicDest.getHeight()) { blockMapUp=true;  }else{ blockMapUp=false; } 
    if (x>=0) { blockMapRight=true; }else{ blockMapRight=false; }
    if (y>=0) { blockMapDown=true;  }else{ blockMapDown=false;  }
    
    // ==============================
    // prevent map of sliding too far
    // ==============================
    if (y<-(tilePicDest.getHeight() - AndActiv.displayHeight)){tempY=-(tilePicDest.getHeight() - AndActiv.displayHeight);}
    

    // ========================
    // map movement if rules ok
    // ========================
    // player going left map right
    if (!Hero.stopLeft && MyGame.left && !blockMapRight  && Hero.centerX) { tempX+=speed; }
    // player going right map left
    if (!Hero.stopRight && MyGame.right && !blockMapLeft && Hero.centerX) { tempX-=speed; }    
    // player going up map down
    if (!Hero.stopUp &&  MyGame.up && !blockMapDown && Hero.centerY) { tempY+=speed; }
    // player going down map up
    if (!Hero.stopDown &&  MyGame.down && !blockMapUp && Hero.centerY) { tempY-=speed; }
		x=tempX;
		y=tempY;
		xPos=tempX;
		yPos=tempY;
	}

	public static int getX() { return xPos;	}
	public static int getY() { return yPos; }  
  public void paint(Canvas g) { g.drawBitmap(tilePicDest,x,y,bitmapPaint); }
  public boolean collidedWith(Entity entity) { return true; }
}
