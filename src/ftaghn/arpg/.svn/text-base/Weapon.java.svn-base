package ftaghn.arpg;

import java.io.IOException;

//import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Weapon extends Entity{

  private Paint bitmapPaint = new Paint();
  private Bitmap[] shootUp;
  private Bitmap[] shootDown;
  private Bitmap[] shootLeft;
  private Bitmap[] shootRight;
  private String direction;
  private int width;
  private int height;
  
  public Weapon(int x, int y, String type, int width, int height, String direction) {
    super(x, y, type, width, height);
    this.width=width;
    this.height=height;
    this.direction=direction;
    try 
    {
      shootUp = new Bitmap[]{ BitmapFactory.decodeStream(AndActiv.context.getAssets().open("weapon/"+type+"up.png")) };
      shootDown = new Bitmap[]{ BitmapFactory.decodeStream(AndActiv.context.getAssets().open("weapon/"+type+"down.png")) };
      shootLeft = new Bitmap[]{ BitmapFactory.decodeStream(AndActiv.context.getAssets().open("weapon/"+type+"left.png")) };
      shootRight = new Bitmap[]{ BitmapFactory.decodeStream(AndActiv.context.getAssets().open("weapon/"+type+"right.png")) };
      //System.out.printf("weapon created = [%s]\n",type);
    } 
    catch (IOException e) 
    {
      e.printStackTrace();
    }    
  }
  public void update()
  {
    bounds.top = y;
    bounds.bottom=y+height;
    bounds.left=x;
    bounds.right=x+width;
    for (int i=MyGame.entities.size()-1;i>=0;i--)
    {
      Entity eTemp = (Entity) MyGame.entities.get(i);        
      if (eTemp instanceof Enemy)
        if (bounds.intersect(eTemp.bounds))
        {
          MyGame.entities.remove(i);
          MyGame.entities.remove(this);
        }
    }
    //if (this.bounds.intersect(bounds)){}
    System.out.printf("bleh \n");
    
  }

  public void paint(Canvas g)
  {
    g.translate(Map.getX(),Map.getY());

    if (direction.equals("up"))      
    g.drawBitmap(shootUp[0], x, y, bitmapPaint);
    if (direction.equals("down"))      
      g.drawBitmap(shootDown[0], x, y, bitmapPaint);
    if (direction.equals("left"))      
      g.drawBitmap(shootLeft[0], x, y, bitmapPaint);
    if (direction.equals("right"))      
      g.drawBitmap(shootRight[0], x, y, bitmapPaint);
    if (debugMode)
    {
      bitmapPaint.setStyle(Paint.Style.STROKE);
      bitmapPaint.setColor(Color.WHITE);
      g.drawRect(bounds, bitmapPaint);
    }    
    g.translate(-Map.getX(),-Map.getY());
    MyGame.entities.remove(this);
  }  
}
