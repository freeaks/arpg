package ftaghn.arpg;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Entity {

  public int x,y;
  public String type;
  public  Rect bounds;
  public Rect detect;
  public static boolean stopUp, stopDown, stopLeft, stopRight;
  public int pxlLeft[],pxlRight[],pxlTop[],pxlBottom[];
  public static boolean debugMode=false;
  // grab door number (append to map name to load)
  public int doorNum;
  
  public Entity( int x, int y, String type, int width, int height) 
  {    
    this.x = x;
    this.y = y;
    this.type = type;
    if (!(this instanceof Map))
    {
      bounds = new Rect(x, y, width, height);
    }
    if ((this instanceof Enemy))
    {
      detect = new Rect(x-30, y-30, width+30, height+30);
    }
  }

  public void update() {  }
  public void paint(Canvas g) {  }  

  public boolean isCollisionWith(Entity other)  
  {
    return  Rect.intersects(bounds, other.bounds);
  }
  public boolean collidedWith(Entity entity) 
  {
    return false;
  }
  public String getDoorNu() {
    return Integer.toString(doorNum);
  }
}
