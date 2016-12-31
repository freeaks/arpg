package ftaghn.arpg;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

public class AndActiv extends Activity {
  private MyGame stv;
  public static Context context;
  public static int displayWidth = 0;
  public static int displayHeight = 0;
  
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindowManager().getDefaultDisplay();
    Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    displayWidth = display.getWidth();             
    displayHeight = display.getHeight();    
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    context=this;
    stv = new MyGame(context);        
    setContentView(stv);
    stv.requestFocus();        
  }
}
