package com.ronhalder.rgbkeyboard;

import android.inputmethodservice.InputMethodService;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Color;
import android.view.View;
import android.view.MotionEvent;
import android.view.inputmethod.InputConnection;
import android.view.KeyEvent;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.List;

public class RgbInputMethodService extends InputMethodService {
    @Override public View onCreateInputView() { return new KeyboardView(); }

    private class KeyboardView extends View {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        Handler handler = new Handler(Looper.getMainLooper());
        long start = System.currentTimeMillis();
        boolean symbols = false, shifted = false;
        String[][] letters = {{"q","w","e","r","t","y","u","i","o","p"},{"a","s","d","f","g","h","j","k","l"},{"⇧","z","x","c","v","b","n","m","⌫"},{"?123",",","☻","English",".","↵"}};
        String[][] sym = {{"1","2","3","4","5","6","7","8","9","0"},{"@","#","$","%","&","-","+","(",")","/"},{"=","*","\"","'",":",";","!","?","⌫"},{"ABC",",","☻","English",".","↵"}};
        List<RectF> rects = new ArrayList<>(); List<String> labels = new ArrayList<>();
        Runnable anim = new Runnable(){ public void run(){ invalidate(); handler.postDelayed(this, 40); }};
        KeyboardView(){ super(RgbInputMethodService.this); setLayerType(View.LAYER_TYPE_SOFTWARE,null); handler.post(anim); }
        @Override protected void onDetachedFromWindow(){ handler.removeCallbacks(anim); super.onDetachedFromWindow(); }
        @Override protected void onDraw(Canvas c){
            super.onDraw(c); c.drawColor(Color.rgb(10,11,19)); rects.clear(); labels.clear();
            float w=getWidth(), h=getHeight(); float pad=5, gap=5; float rowH=Math.max(42,(h-12*2-gap*3)/4f); String[][] rows=symbols?sym:letters;
            for(int r=0;r<rows.length;r++){
                String[] row=rows[r]; float keyW=(w-pad*2-gap*(row.length-1))/row.length; float offset=pad;
                if(r==1 && !symbols) offset=pad+keyW*.45f;
                if(r==1 && !symbols) keyW=(w-pad*2-gap*(row.length-1)-keyW*.9f)/row.length;
                if(r==3){
                    float[] widths={1.5f,0.8f,0.8f,2.4f,0.8f,1.5f}; float total=0; for(float a:widths)total+=a; float unit=(w-pad*2-gap*5)/total;
                    for(int i=0;i<row.length;i++){float kw=unit*widths[i]; drawKey(c,row[i],offset,r,kw,rowH,gap); offset+=kw+gap;} continue;
                }
                for(int i=0;i<row.length;i++){ drawKey(c,row[i],offset,r,keyW,rowH,gap); offset+=keyW+gap; }
            }
        }
        void drawKey(Canvas c,String label,float x,int row,float kw,float kh,float gap){
            float y=6+row*(kh+gap); int idx=labels.size(); float hue=((System.currentTimeMillis()-start)*0.035f+idx*31f)%360f; int color=Color.HSVToColor(new float[]{hue,0.95f,1f});
            p.setStyle(Paint.Style.FILL); p.setColor(Color.argb(225,25,26,38)); c.drawRoundRect(new RectF(x,y,x+kw,y+kh),10,10,p);
            p.setStyle(Paint.Style.STROKE); p.setStrokeWidth(2.2f); p.setColor(color); c.drawRoundRect(new RectF(x+1,y+1,x+kw-1,y+kh-1),10,10,p); p.setStyle(Paint.Style.FILL);
            p.setColor(Color.WHITE); p.setTextAlign(Paint.Align.CENTER); p.setTextSize(label.length()>4?12:19); Paint.FontMetrics fm=p.getFontMetrics(); c.drawText(label,x+kw/2,y+kh/2-(fm.ascent+fm.descent)/2,p);
            rects.add(new RectF(x,y,x+kw,y+kh)); labels.add(label);
        }
        @Override public boolean onTouchEvent(MotionEvent e){ if(e.getAction()!=MotionEvent.ACTION_UP)return true; float x=e.getX(),y=e.getY(); for(int i=0;i<rects.size();i++) if(rects.get(i).contains(x,y)){ press(labels.get(i)); invalidate(); return true;} return true; }
        void press(String s){ InputConnection ic=getCurrentInputConnection(); if(s.equals("?123")){symbols=true;invalidate();return;} if(s.equals("ABC")){symbols=false;invalidate();return;} if(s.equals("⇧")){shifted=!shifted;invalidate();return;} if(ic==null)return;
            if(s.equals("⌫")){ic.deleteSurroundingText(1,0);return;} if(s.equals("↵")){ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,KeyEvent.KEYCODE_ENTER));ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_ENTER));return;}
            if(s.equals("English")){return;} if(s.equals("☻")){ic.commitText("😊",1);return;} if(s.equals(",")||s.equals(".")){ic.commitText(s,1);return;}
            if(s.length()==1){ic.commitText(shifted?s.toUpperCase():s,1); if(shifted)shifted=false;}
        }
    }
}