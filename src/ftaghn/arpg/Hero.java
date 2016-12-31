package ftaghn.arpg;

import java.io.IOException;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Hero extends Entity
{
  Bitmap bMap;
  private Bitmap heroWalk;
  private Rect sourceRect;
  private int currentFrame;
  private int framePeriod;
  private long frameTicker;
  private int frameNr;
  //private Bitmap[] sword;
  private String lastDir="right";
  public static boolean centerX, centerY;
  public static int colX, colY;
  private Paint bitmapPaint = new Paint();
  int sizeX=20;
  int sizeY=32;
  int speed=2;
  int fc;
  private long LastExecUpdateTime;
  static boolean mover;
  public static int xPos, yPos, tempX, tempY;

  public Hero(int x, int y, String type, Context ctx) 
  {
    super(x, y, type, x+20, y+32);
    int w1 = (bounds.right-5)  - (bounds.left+6);
    int h1 = bounds.bottom-10 - bounds.top+4;
    pxlLeft = new int[h1];
    pxlRight = new int[h1];
    pxlTop = new int[w1];
    pxlBottom = new int[w1];
    currentFrame = 0;
    sourceRect = new Rect(0,0,20,32);
    //destRect = new Rect();
    framePeriod = 100 / 1;
    frameTicker = 0l;
    currentFrame = 0;
    frameNr = 2;

    try { heroWalk = BitmapFactory.decodeStream(ctx.getAssets().open("hero/herosprite.png")); } 
    catch (IOException e) { e.printStackTrace(); }
    mover=false;
    tempX=x;
    tempY=y;
  }

  public void update()
  {

    if(this.LastExecUpdateTime == 0)
      this.LastExecUpdateTime = System.currentTimeMillis();  

    int elapsedTime = (int)(System.currentTimeMillis() - this.LastExecUpdateTime);
    int realSpeed = (int)(elapsedTime * speed);
    this.LastExecUpdateTime = System.currentTimeMillis();
/*
    if (System.currentTimeMillis()>frameTicker + framePeriod)
    {
      frameTicker =System.currentTimeMillis();
      currentFrame++;
      if (currentFrame >= frameNr)
      {
        currentFrame = 0;
      }
    }
    this.sourceRect.left = currentFrame * 20;
    this.sourceRect.right = this.sourceRect.left + 20;
*/
    bounds.top=tempY;
    bounds.bottom=tempY+sizeY;
    bounds.left=tempX;
    bounds.right=tempX+sizeX;
    xPos=tempX;
    yPos=tempY;
    //MyGame.wayPoint();
    for (int a=0;a<pxlLeft.length;a++)
    {
      MapLoader.colPicDest.getPixels(pxlLeft, 0, 1, bounds.left+1, bounds.top+4, 1, a);
      if (pxlLeft[a]== Color.RED) { MyGame.left=false; stopLeft=true; ;} else {  stopLeft=false; }
      if (pxlLeft[a]== Color.WHITE) { MyGame.loadMap(AndActiv.context, "castle"+MapLoader.doorLeftNu, "left"); break;}

    }
    for (int b=0;b<pxlRight.length;b++)
    {
      MapLoader.colPicDest.getPixels(pxlRight, 0, 1, bounds.right-1, bounds.top+4, 1, b);
      if (pxlRight[b]== Color.RED) { MyGame.right=false; stopRight=true; } else { stopRight=false; }
      if (pxlRight[b]== Color.YELLOW) { MyGame.loadMap(AndActiv.context, "castle"+MapLoader.doorRightNu, "right"); break; }
    }
    for (int c=0;c<pxlTop.length;c++)
    {
      MapLoader.colPicDest.getPixels(pxlTop, 0, pxlTop.length, bounds.left+6, bounds.top+1, c, 1);
      if (pxlTop[c]== Color.RED)  { MyGame.up=false; stopUp=true; } else { stopUp=false;}
      if (pxlTop[c]== Color.GREEN) { MyGame.loadMap(AndActiv.context, "castle"+MapLoader.doorUpNu, "up"); break; }
    }
    for (int d=0;d<pxlBottom.length;d++)
    {
      MapLoader.colPicDest.getPixels(pxlBottom, 0, pxlBottom.length, bounds.left+6, bounds.bottom-1, d, 1);
      if (pxlBottom[d]== Color.RED) { MyGame.down=false; stopDown=true; } else { stopDown=false; }
      if (pxlBottom[d]== Color.BLUE) { MyGame.loadMap(AndActiv.context, "castle"+MapLoader.doorDownNu, "down"); break;}
    }
    //MyGame.collisionTest(this, pxlLeft,pxlRight,pxlTop,pxlBottom);

    if (Hero.stopLeft)
      tempX+=realSpeed;
    if (Hero.stopRight)
      tempX-=realSpeed;
    if (Hero.stopUp)
      tempY+=realSpeed;
    if (Hero.stopDown)
      tempY-=realSpeed;

    x=tempX;
    y=tempY;

    if (x>106 && x<362) {	centerX=true; } else { centerX=false; }
    if (y>210 && y<570) { centerY=true; } else { centerY=false; }

    if (MyGame.left)
    {
      //if (MyGame.lastCol.equals("right")){MyGame.lastCol="";}
      lastDir="left";
      tempX-=speed;
    }   
    if (MyGame.right)
    {
      lastDir="right";
      tempX+=speed;
    }
    if (MyGame.up)
    {
      lastDir="up";
      tempY-=speed;
    }
    if (MyGame.down)
    {
      lastDir="down";
      tempY+=speed;
    }
  }

  public static int getX()
  {
    return xPos;
  }
  public static int getY()
  {
    return yPos;
  }

  public boolean collidedWith(Entity entity) {

    return false;
  }

  public void paint(Canvas g)
  {
    g.translate(Map.getX(),Map.getY());
    
    //destRect.top=y;
    //destRect.bottom=y+32;
    //destRect.left=x;
    //destRect.right=x+20;
    //sourceRect.left=0;
    //sourceRect.right=20;
    
    if (MyGame.touchEvent) 
    {
      if (System.currentTimeMillis()>frameTicker + framePeriod)
      {
        frameTicker = System.currentTimeMillis();
        currentFrame++;
        if (currentFrame >= frameNr)
        {
          currentFrame = 0;
        }
      }
      this.sourceRect.left = currentFrame * 20;
      this.sourceRect.right = this.sourceRect.left + 20;
    }
    
    if (MyGame.up || lastDir.equals("up"))
    {
      Rect destRect = new Rect(x, y, x+ 20, y+32);
      sourceRect.top=0;
      sourceRect.bottom=sourceRect.top+32;
      g.drawBitmap(heroWalk,sourceRect, destRect, null);
      lastDir="up";
    }
    else if (MyGame.down || lastDir.equals("down"))
    {
      Rect destRect = new Rect(x, y, x+ 20, y+32);
      sourceRect.top=32;
      sourceRect.bottom=sourceRect.top+32;
      g.drawBitmap(heroWalk,sourceRect, destRect, null);
      lastDir="down";
    }
    else if (MyGame.left || lastDir.equals("left"))
    {
      Rect destRect = new Rect(x, y, x+ 20, y+32);
      sourceRect.top=64;
      sourceRect.bottom=sourceRect.top+32;
      g.drawBitmap(heroWalk,sourceRect, destRect, null);
      lastDir="left";
    }
    else if (MyGame.right || lastDir.equals("right"))
    {
      Rect destRect = new Rect(x, y, x+ 20, y+32);
      sourceRect.top=96;
      sourceRect.bottom=sourceRect.top+32;
      g.drawBitmap(heroWalk,sourceRect, destRect, null);
      lastDir="right";
    }
   // }
    
    /*
    else
    {
      if (lastDir.equals("up")){
        Rect destRect = new Rect(x, y, x+ 20, y+32);
        sourceRect.top=0;
        sourceRect.bottom=sourceRect.top+32;
        g.drawBitmap(heroWalk,sourceRect, destRect, null);
        //g.drawBitmap(heroWalk, x, y, bitmapPaint);
        if (MyGame.action1){ MyGame.entities.add(new Weapon(x, y-35, "sword", sword[0].getWidth(),sword[0].getHeight(),"up")); }
      }
      
      if (lastDir.equals("down")){
        g.drawBitmap(heroDown[0], x, y, bitmapPaint);
        if (MyGame.action1){ MyGame.entities.add(new Weapon(x, y+35, "sword", sword[1].getWidth(),sword[1].getHeight(),"down")); }
      }
      if (lastDir.equals("left")){
        g.drawBitmap(heroLeft[0], x, y, bitmapPaint);
        if (MyGame.action1){ MyGame.entities.add(new Weapon(x-35, y+10, "sword", sword[2].getWidth(),sword[2].getHeight(),"left")); }
      }
      if (lastDir.equals("right")){
        g.drawBitmap(heroRight[0], x, y, bitmapPaint);
        if (MyGame.action1){ MyGame.entities.add(new Weapon(x+25, y+10, "sword", sword[3].getWidth(),sword[3].getHeight(),"right")); }
      }
      
    }
  */

    if (debugMode)
    {
      bitmapPaint.setStyle(Paint.Style.STROKE);
      bitmapPaint.setColor(Color.WHITE);
      g.drawRect(bounds, bitmapPaint);
    }
    g.translate(-Map.getX(),-Map.getY());
  }  
}
