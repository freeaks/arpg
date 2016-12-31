package ftaghn.arpg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Block extends Entity{

  private Paint bitmapPaint = new Paint();
  boolean mover;


  public Block(int x, int y, String type, Context ctx)
  {
    super(x, y, type, x+16, y+16);

  }

  public void update()
  {
    x=Map.getX();
    y=Map.getY();
  }

  public boolean collidedWith(Entity entity) {
    return true;
  }

  public void paint(Canvas g)
  {
    g.translate(x, y);
    if (debugMode)
    {
      g.drawBitmap(MapLoader.colPicDest, 0,0,bitmapPaint);
    }
    g.translate(-x, -y);
  }

  public static void setColPicDest(Bitmap colPicDest) {
    MapLoader.colPicDest = colPicDest;
  }

  public static Bitmap getColPicDest() {
    return MapLoader.colPicDest;
  }  
}
