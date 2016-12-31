package ftaghn.arpg;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.res.XmlResourceParser;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.util.Log;

public class MapLoader {

  private static final String TAG = "TestParser";
  private static int tileWidth;
  private static int tileHeight;
  private static int tileSize;
  private static int mapWidth;
  private static int mapHeight;
  // tiles and blocks
  static int tiles[][];
  static int block[][];
  // tiles and blocks counter
  private int countX;
  private int countY;
  //private XmlPullParser xrp;
  private static String tileSheetName="";
  private static String layer="";
  private static String tileSet="";
  // save the tiles as a bitmap
  // doors
  private static String doorName;//,enemyName;
  public static int doorLeftNu, doorRightNu, doorUpNu, doorDownNu;
  // hero position in new map
  public static int heroX;
  public static int heroY;

  private int sheetWidthInTile;
  private int sheetHeightInTile;
  private Bitmap tileSheet;
  private Bitmap tilePic[][];
  public static Bitmap tilePicDest;
  private int tileNbr, row, col;
  private Canvas tileCanvas;
  private Paint bitmapPaint = new Paint();
  public static int leftDoorAxisX, leftDoorAxisY;
  public static int rightDoorAxisX,rightDoorAxisY;
  public static int upDoorAxisX,   upDoorAxisY;
  public static int downDoorAxisX, downDoorAxisY;
  private static Bitmap colSheet;
  private static Bitmap colPic[][];
  public static  Bitmap colPicDest;
  private static Canvas colCanvas;
 //public static ArrayList<int[][]> enemiesCoordsList = new ArrayList<int[][]>();
  public ArrayList<Point> enemiesCoordsList = new ArrayList<Point>();
  public static Hero xyz;
  
  public MapLoader(String name, Context ctx)
  {

    tiles=null;
    block=null;
    setTileSize(0);
    countX=0;
    countY=0;
    tileSheetName="";
    tileWidth=0;
    tileHeight=0;
    layer="";
    tileSet="";

    // ===================
    // Tiled (tmx) parser
    // ===================
    try
    {      
      InputStream is = ctx.getAssets().open("level/"+name+".tmx");
      XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); 
      XmlPullParser xrp = factory.newPullParser(); 
      xrp.setInput(is, "UTF-8");

      while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) 
      {
        if (xrp.getEventType() == XmlResourceParser.START_TAG) 
        {
          String s = xrp.getName();
          if (s.equals("layer"))
          {
            layer = xrp.getAttributeValue(null, "name");
          }
          if (s.equals("tileset"))
          {
            tileSet = xrp.getAttributeValue(null, "name");
          }
          if (s.equals("property"))
          {
            doorName = xrp.getAttributeValue(null, "name");
          }

          if (s.equals("map")) 
          {
            int a = Integer.parseInt(xrp.getAttributeValue(null, "width"));
            setTileWidth(a);
            setMapWidth(a);
            int b = Integer.parseInt(xrp.getAttributeValue(null, "height"));
            setTileHeight(b);
            setMapHeight(b);
            int c = Integer.parseInt(xrp.getAttributeValue(null, "tilewidth"));
            setTileSize(c);
          }
          if (s.equals("property") && doorName.equals("doorLeft"))
          {

            int a = Integer.parseInt(xrp.getAttributeValue(null, "value"));
            doorLeftNu = a;
          }
          if (s.equals("property") && doorName.equals("doorRight"))
          {
            int b = Integer.parseInt(xrp.getAttributeValue(null, "value"));
            doorRightNu  = b;
          }
          if (s.equals("property") && doorName.equals("doorUp"))
          {
            int c = Integer.parseInt(xrp.getAttributeValue(null, "value"));
            doorUpNu = c;
          }
          if (s.equals("property") && doorName.equals("doorDown"))
          {
            int d = Integer.parseInt(xrp.getAttributeValue(null, "value"));
            doorDownNu=d;
          }

          if (s.equals("image")&&tileSet.equals("paint"))
          {
            //String sn = xrp.getAttributeValue(null, "source"); 
            //sn = sn.substring(0, sn.lastIndexOf('.'));
            //tileSheetName = ctx.getResources().getIdentifier(sn, "drawable", ctx.getPackageName());
            tileSheetName  = xrp.getAttributeValue(null, "source"); 
          }

          // =================
          // parse paint layer
          // =================
          if (s.equals("tile")&&layer.equals("paint")) 
          {
            int a = Integer.parseInt(xrp.getAttributeValue(null, "gid"));
            if (tiles==null)tiles=new int[tileWidth][tileHeight];

            if (countX>=tileWidth)
            {
              countX=0;
              countY++;
            }            
            if (!(countX>=tileWidth) && !(countY>=tileHeight))
            {
              tiles[countX][countY]=a;
            }            
            if (countX<tileWidth)
            {
              countX++;
            }
            if (countX==tileWidth&&countY==tileHeight-1)
            {
              // counter reset
              countX=0;
              countY=0;
            }
          }
          // =====================
          // parse collision layer
          // =====================
          if (s.equals("tile")&&layer.equals("coll"))
          {
            int a = Integer.parseInt(xrp.getAttributeValue(null, "gid"));
            if (block==null)block=new int[tileWidth][tileHeight];
            
            //if (enemyCoords==null)enemyCoords=new int[tileWidth][tileHeight];
            
            if (countX>=tileWidth)
            {
              countX=0;
              countY++;
            }            
            if (!(countX>=tileWidth) && !(countY>=tileHeight))
            {
              block[countX][countY]=a;
            }            
            if (countX<tileWidth)
            {
              countX++;
            }
          }
        } 
        xrp.next();
      }
      //xrp.close();
    }
    catch (XmlPullParserException xppe) 
    {
      Log.e(TAG, "Failure of .getEventType or .next, probably bad file format");
      xppe.toString();
    }    
    catch (IOException ioe) 
    {
      Log.e(TAG, "Unable to read resource file");
      ioe.printStackTrace();
    }

    // ===================
    // paint layer (export as bitmap)
    // ===================
    try {  tileSheet = BitmapFactory.decodeStream(ctx.getAssets().open("level/"+MapLoader.tileSheetName)); } 
    catch (IOException e) { e.printStackTrace(); }
    sheetWidthInTile =tileSheet.getWidth() /tileSize;
    setSheetHeightInTile(tileSheet.getHeight() /tileSize);
    tilePic = new Bitmap[getTileWidth()][getTileHeight()];
    tilePicDest = Bitmap.createBitmap(getTileWidth()*tileSize, getTileHeight()*tileSize, Bitmap.Config.RGB_565);
    tileCanvas = new Canvas(tilePicDest);

    for (int h = 0; h < getTileHeight(); h++) {
      for (int w = 0; w < getTileWidth(); w++) {
        tileNbr = MapLoader.tiles[w][h];
        row = (int)Math.ceil(tileNbr / sheetWidthInTile);
        col = tileNbr % sheetWidthInTile;
        if (col==0)
        {
          col=tileSheet.getWidth()/tileSize;
          row-=1;
        }
        if (col-1>=0)
        {
          col-=1;
        }
        if (row*tileSize>=0){
          tilePic[w][h]= Bitmap.createBitmap(tileSheet, col*tileSize, row*tileSize, tileSize, tileSize);
          tileCanvas.drawBitmap(tilePic[w][h],w *tileSize ,h *tileSize,bitmapPaint);
        }
      }      
    }
    tileNbr=0;

    // ===================
    // collision layer (export as bitmap)
    // ===================
    try {
      colSheet =  BitmapFactory.decodeStream(ctx.getAssets().open("level/collision16.png"));
    } catch (IOException e) { e.printStackTrace(); }
    //colSheet = BitmapFactory.decodeResource(ctx.getResources(), R.drawable.collision16);
    colPic = new Bitmap[getTileWidth()][getTileHeight()];
    //setColPicDest(Bitmap.createBitmap(width*tileSize, height*tileSize, Bitmap.Config.RGB_565));
    colPicDest = Bitmap.createBitmap(getTileWidth()*tileSize, getTileHeight()*tileSize, Bitmap.Config.RGB_565);
    colCanvas = new Canvas(colPicDest);
    // ===============
    // collision layer
    // ===============
    for (int h = 0; h < getTileHeight(); h++) {
      for (int l = 0; l < getTileWidth(); l++) {

        tileNbr = MapLoader.block[l][h];


        switch(tileNbr){
        case 169:
          // ============
          // normal block
          // ============
          colPic[l][h]= Bitmap.createBitmap(colSheet, 0, 0, tileSize, tileSize);
          colCanvas.drawBitmap(colPic[l][h],l*tileSize,h*tileSize,bitmapPaint);
          break;

        case 170:
          // ============
          // 4 doors
          // ============
          colPic[l][h]= Bitmap.createBitmap(colSheet, 16, 0, tileSize, tileSize);
          colCanvas.drawBitmap(colPic[l][h],l*tileSize,h*tileSize,bitmapPaint);
          leftDoorAxisX=l*tileSize;
          leftDoorAxisY=h*tileSize;          
          //if (MyGame.doorType.equals("right")){ MyGame.heroX=(l*16)+32; MyGame.heroY=(h*16);}
          break;
        case 171:
          colPic[l][h]= Bitmap.createBitmap(colSheet, 32, 0, tileSize, tileSize);
          colCanvas.drawBitmap(colPic[l][h],l*tileSize,h*tileSize,bitmapPaint);
          rightDoorAxisX=l*tileSize;
          rightDoorAxisY=h*tileSize;
          //if (MyGame.doorType.equals("left")){ MyGame.heroX=(l*16)-32; MyGame.heroY=(h*16);}
          break;
        case 172:
          colPic[l][h]= Bitmap.createBitmap(colSheet, 48, 0, tileSize, tileSize);
          colCanvas.drawBitmap(colPic[l][h],l*tileSize,h*tileSize,bitmapPaint);
          upDoorAxisX=l*tileSize;
          upDoorAxisY=h*tileSize;
          //if (MyGame.doorType.equals("down")){ MyGame.heroX=l*16; MyGame.heroY=(h*16)+32;}
          break;
        case 173:
          colPic[l][h]= Bitmap.createBitmap(colSheet, 64, 0, tileSize, tileSize);
          colCanvas.drawBitmap(colPic[l][h],l*tileSize,h*tileSize,bitmapPaint);
          downDoorAxisX=l*tileSize;
          downDoorAxisY=h*tileSize;
          //System.out.printf("============  door down axis Y = [%d]\n", downDoorAxisY);
          //if (MyGame.doorType.equals("up")){ MyGame.heroX=l*16; MyGame.heroY=(h*16)-32;}
          break;

        case 174:
          enemiesCoordsList.add(new Point(l*16,h*16));
          //MyGame.entities.add(new Enemy(l*16,h*16, "enemy "+l+"x"+h, ctx));
          //System.out.printf("============  creating monster\n");
          break;
        }
      }
    }

    // ===============
    // create blockmap
    // ===============
    MyGame.entities.add(new Block(0,0, "blockmap", ctx));

    // ==================================================
    // create map according to player and doors position
    // ==================================================
    switch (MyGame.rrr.valueOf(MyGame.doorType)) {

    case start: 
      // initial map
      MyGame.entities.add(new Map(0,0, "tilemap", ctx));
      MyGame.entities.add(xyz=new Hero(0, 0, "hero", ctx));
      break;

    case up: 
      // player exited last map using "up" door
      //  create new map from "up" door coords
      if (downDoorAxisX<AndActiv.displayWidth/2)
      {
        MyGame.entities.add(new Map(0,-((downDoorAxisY+16)-AndActiv.displayHeight/2), "tilemap", ctx));
        MyGame.entities.add(new Hero(0, 0, "hero", ctx));
      }
      else if (downDoorAxisX>(getMapWidth()-AndActiv.displayWidth/2))
      {
        MyGame.entities.add(new Map(-(getMapWidth()-AndActiv.displayWidth),-((downDoorAxisY+16)-AndActiv.displayHeight/2), "tilemap", ctx));
        MyGame.entities.add(new Hero(0, 0, "hero", ctx));
      }
      else 
      {
        MyGame.entities.add(new Map(-(downDoorAxisX-AndActiv.displayWidth/2),-((downDoorAxisY+16)-AndActiv.displayHeight/2), "tilemap", ctx));
        MyGame.entities.add(new Hero(0, 0, "hero", ctx));
      }
      break;

    case down:  
      // player exited last map using "down" door
      // create new map from "down" door coords
      if (upDoorAxisX<AndActiv.displayWidth/2)
      {
        MyGame.entities.add(new Map(0,0, "tilemap", ctx));
        MyGame.entities.add(new Hero(0, 0, "hero", ctx));
      }
      else if (upDoorAxisX>(getMapWidth()-AndActiv.displayWidth/2))
      {
        MyGame.entities.add(new Map(-(getMapWidth()-AndActiv.displayWidth),0, "tilemap", ctx));
        MyGame.entities.add(new Hero(0, 0, "hero", ctx));
      }
      else 
      {
        MyGame.entities.add(new Map(-(upDoorAxisX-AndActiv.displayWidth/2),0, "tilemap", ctx));
        MyGame.entities.add(new Hero(0, 0, "hero", ctx));
      }
      break;

    case left: 
      // player exited last map using "left" door
      // create new map from "left" door coords
      if (rightDoorAxisY<AndActiv.displayHeight/2)
      {
        MyGame.entities.add(new Map(-(getMapWidth()-AndActiv.displayWidth),0, "tilemap", ctx));
        MyGame.entities.add(new Hero(0, 0, "hero", ctx));
      }
      else if (rightDoorAxisY>(getMapHeight()-AndActiv.displayHeight/2))
      {
        MyGame.entities.add(new Map(-(getMapWidth()-AndActiv.displayWidth),-(rightDoorAxisY-AndActiv.displayHeight/2), "tilemap", ctx));
        MyGame.entities.add(new Hero(0, 0, "hero", ctx));
      }
      else
      {
        MyGame.entities.add(new Map(-(getMapWidth()-AndActiv.displayWidth),-(rightDoorAxisY-AndActiv.displayHeight/2), "tilemap", ctx));
        MyGame.entities.add(new Hero(0, 0, "hero", ctx));
      }
      break;

    case right: 
      // player exited last map using "right" door
      // create new map from "right" door coords
      if (leftDoorAxisY<AndActiv.displayHeight/2) 
      {
        MyGame.entities.add(new Map(0,0, "tilemap", ctx));
        MyGame.entities.add(new Hero(0, 0, "hero", ctx));
      }
      else if (leftDoorAxisY>(getMapHeight()-AndActiv.displayHeight/2))
      {
        MyGame.entities.add(new Map(0,-(leftDoorAxisY-AndActiv.displayHeight/2), "tilemap", ctx));
        MyGame.entities.add(new Hero(0, 0, "hero", ctx));
      }
      else 
      {
        MyGame.entities.add(new Map(0,-(leftDoorAxisY-AndActiv.displayHeight/2), "tilemap", ctx));
        MyGame.entities.add(new Hero(0, 0, "hero", ctx));
      }
      break;
    }
    for (int i=0;i<enemiesCoordsList.size();i++)
    {
      MyGame.entities.add(new Enemy(enemiesCoordsList.get(i).x,enemiesCoordsList.get(i).y, "enemy "));
    }

  }

  // =====================
  // getter
  // =====================
  public static int getTileWidth() { return tileWidth; }  
  public static int getTileHeight() { return tileHeight; }  
  public static int getTileSize() { return tileSize; }	
  public static int getMapWidth() {	return MapLoader.mapWidth; }	
  public static int getMapHeight() { return MapLoader.mapHeight; 	}
  // =====================
  // setter
  // =====================
  public static void setTileWidth(int tileWidth) { MapLoader.tileWidth = tileWidth;	}  
  public static void setTileHeight(int tileHeight) { MapLoader.tileHeight = tileHeight; }	
  public static void setMapWidth(int MapWidth) { MapLoader.mapWidth = MapWidth*16; }	
  public static void setMapHeight(int MapHeight) { MapLoader.mapHeight = MapHeight*16; }	
  public static void setTileSize(int tileSize) { MapLoader.tileSize = tileSize; }
  public void setSheetHeightInTile(int sheetHeightInTile) { this.sheetHeightInTile = sheetHeightInTile; }
  public int getSheetHeightInTile() { return sheetHeightInTile; }
}
