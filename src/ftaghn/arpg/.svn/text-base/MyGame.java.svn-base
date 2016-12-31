package ftaghn.arpg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyGame extends View
{
  private Paint bitmPaint = new Paint();
  private Paint textPaint = new Paint();
  private Paint mapPaint = new Paint();
  private Paint manaPaint = new Paint();
  private Paint healthPaint = new Paint();
  public static boolean touchEvent, up, down, left, right, action1,action2;
  public boolean titleState,gameState,mapState,invState;
  private Bitmap padbg,item1,item2;
  private Bitmap mapScreen, invScreen,titleScreen;
  private Rect  inputUp,inputDown,inputLeft,inputRight,inputButton1,inputButton2;
  private Rect healthMeter, manaMeter;
  public static int eX, eY;
  public static MapLoader mapLoader;
  //public static Hero xyz;
  public static int heroX,heroY;
  public int screenWidth, screenHeight;
  public static ArrayList<Entity> entities;
  public static String doorType,lvlName;
  public static enum rrr {start,up,down,left,right};
  GameLoop gameloop;
  Timer t;

  class GameLoop extends TimerTask {
    private volatile boolean running=true;
    public void run() {
      if(running){
        postInvalidate();
        pause();  
      }
    }
    public void pause() { running=false; }
    public void start() { running=true; run(); }
    public void safeStop() { running=false;  }
  }

  // ================
  // constructor
  // ================	
  public MyGame(Context context, AttributeSet attrs, int defStyle) { super(context, attrs, defStyle);init(context); }
  public MyGame(Context context, AttributeSet attrs) { super(context, attrs);init(context); }
  public MyGame(Context context) { super(context);init(context); }	
  public void unload() { gameloop.safeStop(); }

  // =================
  // init basic stuffs
  // =================
  protected void init(Context ctx) {
    this.setFocusable(true);
    screenWidth=AndActiv.displayWidth;
    screenHeight=AndActiv.displayHeight;
    System.out.printf("game width=[%d]  height=[%d]\n",screenWidth,screenHeight);
    gameState=true;
    textPaint.setARGB(255, 255, 0, 0);
    mapPaint.setARGB(255, 0, 255, 96);
    mapPaint.setStyle(Paint.Style.FILL);
    healthPaint.setARGB(255,255,0,0);
    manaPaint.setARGB(255,0,24,255);

    //load some gfx
    try {
      padbg       = BitmapFactory.decodeStream(ctx.getAssets().open("iface/paddle6.png"));
      item1       = BitmapFactory.decodeStream(ctx.getAssets().open("weapon/buckler2.png"));
      item2       = BitmapFactory.decodeStream(ctx.getAssets().open("weapon/sword2.png"));
      invScreen   = BitmapFactory.decodeStream(ctx.getAssets().open("iface/invscreen3.png"));
      mapScreen   = BitmapFactory.decodeStream(ctx.getAssets().open("iface/mapscreen2.png"));
      titleScreen = BitmapFactory.decodeStream(ctx.getAssets().open("iface/titlescreen.png"));
    } catch (IOException e) { e.printStackTrace(); }

    // virtual controller
    inputUp       = new Rect(80,screenHeight-65,130,screenHeight-35);
    inputDown     = new Rect(80,screenHeight-25,130,screenHeight);
    inputLeft     = new Rect(35, screenHeight-45,70,screenHeight-15);
    inputRight    = new Rect(140,screenHeight-45,175,screenHeight-15);
    inputButton1  = new Rect(screenWidth-130,screenHeight-45,screenWidth-90,screenHeight-10);
    inputButton2  = new Rect(screenWidth-80,screenHeight-45,screenWidth-40,screenHeight-10);
    manaMeter     = new Rect(screenWidth-2,screenHeight-55,screenWidth-15,screenHeight-5);
    healthMeter   = new Rect(2,screenHeight-55,15,screenHeight-5);

    //entities = new ArrayList<Entity>();
    
    
    //start initial map
    loadMap(ctx, "castle1", "start");   
    gameloop = new GameLoop();
    t = new Timer();
    t.schedule(gameloop, 0, 20);
  }
  
  public static void loadMap(Context ctx, String mapName, String type)  
  {
    lvlName=mapName;
    doorType=type;
    //xyz = null;
    mapLoader = null;
    entities = null;
    entities = new ArrayList<Entity>();
    
    mapLoader = new MapLoader(mapName, ctx);
    System.out.println("how much mapLoader do we create ???????????????????????????");
    // create hero
    switch (rrr.valueOf(type))
    {
    case start:      
      Hero.tempX = 220; 
      Hero.tempY = 340;
      break;
    case up:
      Hero.tempX =  MapLoader.downDoorAxisX;
      Hero.tempY =  MapLoader.downDoorAxisY-60;
      break;
    case down:
      Hero.tempX =  MapLoader.upDoorAxisX;
      Hero.tempY =  MapLoader.upDoorAxisY+60;
      break;
    case left:
      Hero.tempX  = MapLoader.rightDoorAxisX-60;
      Hero.tempY  = MapLoader.rightDoorAxisY;
      break;
    case right:
      Hero.tempX  = MapLoader.leftDoorAxisX+60;
      Hero.tempY  = MapLoader.leftDoorAxisY;
      break;
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) 
  {
    int action = event.getAction();
    eX = (int) event.getX();
    eY = (int) event.getY();

    switch (action) {
    case MotionEvent.ACTION_UP:
      touchEvent=false;
      up     = false;
      down   = false;
      left   = false;
      right  = false;
      action1= false;
      action2= false;
      break;
    case MotionEvent.ACTION_DOWN:
      //eX = (int) event.getX();
      //eY = (int) event.getY();
      touchEvent=true;
      if (inputUp.contains(eX, eY)){up     = true; }
      if (inputDown.contains(eX, eY)){down   = true; }
      if (inputLeft.contains(eX, eY)){left   = true; }
      if (inputRight.contains(eX, eY)){right  = true; }
      if (inputButton1.contains(eX,eY)){action1=true;}
      if (inputButton2.contains(eX,eY)){Entity.debugMode=!Entity.debugMode;}
      break;
    }
    return true;
  }



  @Override
  protected void onDraw(Canvas g) 
  {
    if (titleState)
    {
      g.drawBitmap(titleScreen,0,0,bitmPaint);
      textPaint.setARGB(255, 255, 255, 255);
      String s1 = "touch screen to start";
      String s2 = "touch screen to quit";
      g.drawText(s1, 150, 280, textPaint);
      g.drawText(s2, 150, 295, textPaint);
    }
    if (gameState)
    {
      /*
      for (int i=entities.size()-1;i>=0;i--)
      {
        Entity eTemp = (Entity) entities.get(i);

        eTemp.update();
        eTemp.paint(g);
        
        if (eTemp instanceof Map)
        {
          eTemp.update();
          eTemp.paint(g);
        }
        if (eTemp instanceof Enemy)
        {
          eTemp.update();
          eTemp.paint(g);
        }
        if (eTemp instanceof Hero)
        {
          eTemp.update();
          eTemp.paint(g);
        }
        if (eTemp instanceof Weapon)
        {
          eTemp.update();
          eTemp.paint(g);
        }
      }
      */
      
      for (int i=0;i<entities.size();i++)
      {        
        Entity eTemp = (Entity) entities.get(i);
        System.out.printf("entities = %s\n",entities.get(i).type);
          eTemp.update();
          eTemp.paint(g);
      }
      
      postInvalidate();


      // draw virtual controller
      textPaint.setARGB(255, 0, 0, 0);
      g.drawRect(healthMeter,healthPaint);
      g.drawRect(manaMeter,manaPaint);
      g.drawBitmap(padbg, 0, screenHeight-padbg.getHeight(), bitmPaint);      
      g.drawBitmap(item1, inputButton1.left,inputButton1.top, bitmPaint);  
      g.drawBitmap(item2, inputButton2.left,inputButton2.top, bitmPaint);  
      if (Entity.debugMode)
      {				
        //put debug stuffs here
        textPaint.setARGB(255, 255, 0, 255);
        g.drawRect(inputUp,textPaint );
        g.drawRect(inputDown,textPaint );
        g.drawRect(inputLeft, textPaint);
        g.drawRect(inputRight, textPaint);
        g.drawRect(inputButton1, textPaint);
        g.drawRect(inputButton2, textPaint);
      }
    }

    if (mapState)
    {
      g.drawBitmap(mapScreen,0,0,bitmPaint);
      g.drawText("cursed dungeon：　lvl１", 80, 20, textPaint);
      g.drawRect(104, 88, 118, 102, mapPaint);
    }
    if (invState)
    {
      g.drawBitmap(invScreen,0,0,bitmPaint);
      g.drawText("item１：" , 20, 180, textPaint);
      g.drawText("item description string" , 20, 195, textPaint);
      g.drawText("item description string2" , 20, 210, textPaint);
    }
    gameloop.start();
  }
}
