package ftaghn.arpg;

import java.io.IOException;
import java.util.Random;

//import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

// ========================
// NOT IN USE YET !!!
// ========================

public class Enemy extends Entity{

  // animations
  private Bitmap[] enemyLeft;
  private Bitmap[] enemyRight;
  private Bitmap[] enemyUp;
  private Bitmap[] enemyDown;
  // private Bitmap[] enemyStandStill;
  private String lastDir="right";
  private Paint bitmapPaint = new Paint();
  // bounding box size
  private int sizeX=21;
  private int sizeY=27;
  public boolean beamUp,beamDown,beamLeft,beamRight;
  public int upValue,downValue,leftValue,rightValue;
  public boolean stop, move, attack;
  private Random random;
  //animation frame counter
  // private int fc;
  // private boolean mover;
  private int x,y;
  //public static int xPos, yPos, tempX, tempY;
  private int speed =1;

  public Enemy(int x, int y, String type)
  {
    super(x, y, type, x+21, y+27);
    this.x=x;
    this.y=y;
    stop = true;
    random = new Random();
    /*
    int w1 = (bounds.right-5)  - (bounds.left+6);
    int h1 = bounds.bottom-10 - bounds.top+4;
    pxlLeft = new int[h1];
    pxlRight = new int[h1];
    pxlTop = new int[w1];
    pxlBottom = new int[w1];
    for (int h = 0; h < height; h++) {
      for (int l = 0; l < width; l++) {

        tileNbr = MapLoader.block[l][h];

        // ============
        // normal block
        // ============
        if (tileNbr==174)
        {
          //System.out.printf("********* enemy have been created\n");
          tempX=l*16;
          tempY=h*16;
        }
      }
    }
     */
    //tempX=x;
    //tempY=y;

    try {
      enemyLeft = new Bitmap[]{
          BitmapFactory.decodeStream(AndActiv.context.getAssets().open("skeleton/skelleft0.png")),
          BitmapFactory.decodeStream(AndActiv.context.getAssets().open("skeleton/skelleft1.png")),
          BitmapFactory.decodeStream(AndActiv.context.getAssets().open("skeleton/skelleft0.png"))          
      };
      enemyRight = new Bitmap[]{
          BitmapFactory.decodeStream(AndActiv.context.getAssets().open("skeleton/skelright0.png")),
          BitmapFactory.decodeStream(AndActiv.context.getAssets().open("skeleton/skelright1.png")),
          BitmapFactory.decodeStream(AndActiv.context.getAssets().open("skeleton/skelright0.png"))
      };
      enemyUp = new Bitmap[]{
          BitmapFactory.decodeStream(AndActiv.context.getAssets().open("skeleton/skelup0.png")),
          BitmapFactory.decodeStream(AndActiv.context.getAssets().open("skeleton/skelup1.png")),
          BitmapFactory.decodeStream(AndActiv.context.getAssets().open("skeleton/skelup0.png"))
      };
      enemyDown = new Bitmap[]{
          BitmapFactory.decodeStream(AndActiv.context.getAssets().open("skeleton/skeldown0.png")),
          BitmapFactory.decodeStream(AndActiv.context.getAssets().open("skeleton/skeldown1.png")),
          BitmapFactory.decodeStream(AndActiv.context.getAssets().open("skeleton/skeldown0.png"))
      };
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void update()
  {
    bounds.top=y;
    bounds.bottom=y+sizeY;
    bounds.left=x;
    bounds.right=x+sizeX;

    // ==================================
    // detect box according to direction
    // ==================================
    if (beamUp){
      detect.top=y-60;
      detect.bottom=y+sizeY+30;
      detect.left=x-30;
      detect.right=x+sizeX+30;
    }
    else if(beamDown){
      detect.top=y-30;
      detect.bottom=y+sizeY+60;
      detect.left=x-30;
      detect.right=x+sizeX+30;
    }
    else if(beamLeft){
      detect.top=y-30;
      detect.bottom=y+sizeY+30;
      detect.left=x-60;
      detect.right=x+sizeX+30;
    }
    else if(beamRight){
      detect.top=y-30;
      detect.bottom=y+sizeY+30;
      detect.left=x-30;
      detect.right=x+sizeX+60;
    }

    //xPos=tempX;
    //yPos=tempY;




    //if (Hero.bounds.intersect(detect)){
    //  attack=true;
    //}
    //else {
      attack=false;
      // ==============
      // stop
      // ==============
      if (stop){
        int xx = random.nextInt(10);

        switch (xx) {      
        case 1: // UP
          for (int a=0;a<MapLoader.getMapHeight();a++){  
            int xyz = MapLoader.colPicDest.getPixel((int)bounds.left+(sizeX/2), bounds.top-a);

            if (xyz==Color.RED){
              if (a>sizeY*2) {
                upValue=y-a+sizeY;
                beamUp=true;beamDown=false; beamLeft=false; beamRight=false;
                move=true;
                stop=false;
                break;
              }
              else {
                upValue=0;
                beamUp=false;beamDown=false; beamLeft=false; beamRight=false;
                break;
              }
            }
          }
          break;

        case 2: // DOWN
          for (int a=0;a<MapLoader.getMapHeight();a++){        
            int xyz = MapLoader.colPicDest.getPixel((int)bounds.left+11, bounds.bottom+a);

            if (xyz==Color.RED){
              if (a>sizeY*2) {
                downValue=y+sizeY+a-sizeY;
                beamUp=false;beamDown=true; beamLeft=false; beamRight=false;
                move=true;
                stop=false;
                break;
              }
              else {
                downValue=0;
                beamUp=false;beamDown=false; beamLeft=false; beamRight=false;
                break;
              }            
            }
          }
          break;

        case 3: // LEFT
          for (int a=0;a<MapLoader.getMapWidth();a++){        
            int xyz = MapLoader.colPicDest.getPixel((int)bounds.left-a, (int)bounds.top+(sizeY/2));

            if (xyz==Color.RED){
              if (a>sizeX*2) {
                leftValue=x-a+(sizeX);
                beamUp=false;beamDown=false; beamLeft=true; beamRight=false;
                move=true;
                stop=false;
                break;
              }
              else {
                leftValue=0;
                beamUp=false;beamDown=false; beamLeft=false; beamRight=false;
                break;
              }            
            }
          }
          break;

        case 4: // RIGHT
          for (int a=0;a<MapLoader.getMapWidth();a++){        
            int xyz = MapLoader.colPicDest.getPixel((int)bounds.right+a, (int)bounds.top+(sizeY/2));
            if (xyz==Color.RED){
              if (a>sizeX*2) {
                rightValue=x+sizeX+a-(sizeX);
                beamUp=false;beamDown=false; beamLeft=false; beamRight=true;
                move=true;
                stop=false;
                break;
              }
              else {
                rightValue=0;
                beamUp=false;beamDown=false; beamLeft=false; beamRight=false;
                break;
              }
            }
          }
          break;        
        }
      }

      // ==============
      // move
      // ==============

      if (move){
        if (beamUp)
        {
          if (y>upValue){
            lastDir="up";
            y-=speed;        
          }
          else {
            upValue=0;
            stop=true;
            move=false;
            beamUp=false;
          }
        }
        if (beamDown)
        {
          if (y+sizeY<downValue){
            lastDir="down";
            y+=speed;
          }
          else {
            downValue=0;
            stop=true;
            move=false;
            beamDown=false;
          }
        }
        if (beamLeft)
        {
          if (x>leftValue){
            lastDir="left";
            x-=speed;
          }
          else {
            leftValue=0;
            stop=true;
            move=false;
            beamLeft=false;
          }
        }
        if (beamRight)
        {
          if (x+sizeX<rightValue){
            lastDir="right";
            x+=speed;
          }
          else {
            rightValue=0;
            stop=true;
            move=false;
            beamRight=false;
          }
        }
      }
  // }
    
    // ==============
    // attack
    // ==============
    if (attack){
      // atm, just stop, get ready for being killed
    }
  }


  public void paint(Canvas g)
  {
    g.translate(Map.getX(),Map.getY());

    if (lastDir.equals("left"))
      g.drawBitmap(enemyLeft[0], x, y, bitmapPaint);

    if (lastDir.equals("right"))
      g.drawBitmap(enemyRight[0], x, y, bitmapPaint);

    if (lastDir.equals("up"))
      g.drawBitmap(enemyUp[0], x, y, bitmapPaint);

    if (lastDir.equals("down"))
      g.drawBitmap(enemyDown[0], x, y, bitmapPaint);

    //bitmapPaint.setARGB(255, 255, 255, 0);
    //bitmapPaint.setStyle(Paint.Style.FILL);
    //g.drawRect(myBeam, bitmapPaint);
    if (debugMode)
    {
      bitmapPaint.setStyle(Paint.Style.STROKE);
      bitmapPaint.setColor(Color.WHITE);
      g.drawRect(bounds, bitmapPaint);
      g.drawRect(detect, bitmapPaint);
    }
    g.translate(-Map.getX(),-Map.getY());//g.drawRect(myBeam, bitmapPaint);
  }   
}
