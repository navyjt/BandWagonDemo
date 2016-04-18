package com.laomaizi.bandwagondemo.lightpainting;

/**
 * 
 * @author Tony
 * 选取色彩界面
 * 通过FragmentSetLP界面中的选取颜色按钮打开此对话框
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class ColorPickerView extends View {


	private static final float PI = 3.1415926f;  
	  
  
    private Paint paintCircleShadow = new Paint(Paint.ANTI_ALIAS_FLAG);   
    private Paint paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);   
    private Paint paintCenterShadow = new Paint(Paint.ANTI_ALIAS_FLAG);   
    private Paint paintCenter = new Paint(Paint.ANTI_ALIAS_FLAG);   
    private Paint paintGrayShadow = new Paint(Paint.ANTI_ALIAS_FLAG);   
    private Paint paintGray = new Paint(Paint.ANTI_ALIAS_FLAG);   
    private Paint paintLightShadow = new Paint(Paint.ANTI_ALIAS_FLAG);   
    private Paint paintLight = new Paint(Paint.ANTI_ALIAS_FLAG);   
      
//  private Paint paintText = new Paint(Paint.ANTI_ALIAS_FLAG);  
  
    private double Zoom = 1;   
  
    private int[] arrColorGray;   
    private final int[] arrColorCircle = new int[] { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF,   
            0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };   
    private boolean mRedrawHSV;  
    private boolean IsPressCenter;   
    private boolean IsMoveCenter;  
  
    private int CenterX = 100;  
    private int CenterY = 100;  
    private int CenterRadius = 30;  
  
//  private LDColorDialog dlg = null;  
    private String strColor = "";  
    private TextView textColor;  
  
    public ColorPickerView(Context context, int color, double Zoom,TextView colortext)  
    {  
        super(context);  
        textColor = colortext;  
          
        this.Zoom = Zoom;  
  
        CenterX = (int) (100 * Zoom);  
        CenterY = (int) (100 * Zoom);  
        CenterRadius = (int) (30 * Zoom);  
  
        paintCircleShadow.setColor(0xFF000000);  
        paintCircleShadow.setStyle(Paint.Style.STROKE);  
        paintCircleShadow.setStrokeWidth((float) (32 * Zoom));  
  
        paintCircle.setShader(new SweepGradient(0, 0, arrColorCircle, null));  
        paintCircle.setStyle(Paint.Style.STROKE);  
        paintCircle.setStrokeWidth((float) (32 * Zoom));  
  
        paintCenterShadow.setColor(0xFF000000);  
        paintCenterShadow.setStrokeWidth((float) (5 * Zoom));  
      
        paintCenter.setColor(color);  
        paintCenter.setStrokeWidth((float) (5 * Zoom));  
  
        paintGrayShadow.setColor(0xFF000000);  
        paintGrayShadow.setStrokeWidth((float) (30 * Zoom));  
  
        arrColorGray = new int[] { 0xFFFFFFFF, color, 0xFF000000 };   
        paintGray.setStrokeWidth((float) (30 * Zoom));  
  
        paintLightShadow.setColor(0xFF000000);  
        paintLightShadow.setStrokeWidth((float) (60 * Zoom));  
  
        paintLight.setStrokeWidth((float) (60 * Zoom));  
  
        mRedrawHSV = true;  
          
//      paintText.setColor(0xFFFFFFFF);  
//      paintText.setAntiAlias(true);  
//      paintText.setTextAlign(Paint.Align.CENTER);  
                  
    }  
  
    @Override  
    protected void onDraw(Canvas canvas)  
    {  
      
        canvas.translate(CenterX, CenterY);  
        float r = CenterX - paintCircle.getStrokeWidth() * 0.5f;   
        int color = paintCenter.getColor();  
        textColor.setText("#" + Integer.toHexString(color).substring(2).toUpperCase());  
//      canvas.drawText("#" + Integer.toHexString(color).substring(2).toUpperCase(), CenterX, CenterY, paintText);  
        strColor = "#" + Integer.toHexString(color).substring(2).toUpperCase();  
  
        if (mRedrawHSV)  
        {  
            arrColorGray[1] = color;  
            paintGray.setShader(new LinearGradient(CenterX, -CenterY, CenterX, (float) (100 * Zoom), arrColorGray, null, Shader.TileMode.CLAMP));  
        }  
  
        canvas.drawOval(new RectF(-r + 3, -r + 3, r + 3, r + 3), paintCircleShadow);   
        canvas.drawOval(new RectF(-r, -r, r, r), paintCircle);  
        canvas.drawCircle(3, 3, CenterRadius, paintCenterShadow);   
        canvas.drawCircle(0, 0, CenterRadius, paintCenter);   
        canvas.drawRect(new RectF(CenterX + (float) (18 * Zoom), -CenterY + 3, CenterX + (float) (48 * Zoom), (float) (103 * Zoom)), paintGrayShadow); // �Ҷȵȼ���Ӱ  
        canvas.drawRect(new RectF(CenterX + (float) (15 * Zoom), -CenterY, CenterX + (float) (45 * Zoom), (float) (100 * Zoom)), paintGray); // �Ҷȵȼ�  
  
        if (IsPressCenter)  
        {  
            paintCenter.setStyle(Paint.Style.STROKE);   
  
            if (IsMoveCenter)   
                paintCenter.setAlpha(0xFF);  
            else  
                // ��ָ�ƿ�����  
                paintCenter.setAlpha(0x66);  
  
            canvas.drawCircle(0, 0, CenterRadius + paintCenter.getStrokeWidth(), paintCenter); // ��Ȧ  
            paintCenter.setStyle(Paint.Style.FILL);   
            paintCenter.setColor(color);   
        }  
  
        mRedrawHSV = true;    
          
    }  
      
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
        setMeasuredDimension(CenterX * 2 + 50, CenterY * 2 + 23);  
    }  
  
    private int ave(int s, int d, float p)  
    {  
        return s + java.lang.Math.round(p * (d - s));  
    }  
  
    private int interpColor(int colors[], float unit)  
    {  
        if (unit <= 0)  
        {  
            return colors[0];  
        }  
        if (unit >= 1)  
        {  
            return colors[colors.length - 1];  
        }  
  
        float p = unit * (colors.length - 1);  
        int i = (int) p;  
        p -= i;  
  
        int c0 = colors[i];  
        int c1 = colors[i + 1];  
        int a = ave(Color.alpha(c0), Color.alpha(c1), p);  
        int r = ave(Color.red(c0), Color.red(c1), p);  
        int g = ave(Color.green(c0), Color.green(c1), p);  
        int b = ave(Color.blue(c0), Color.blue(c1), p);  
  
        return Color.argb(a, r, g, b);  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent event)  
    {  
        float x = event.getX() - CenterX;  
        float y = event.getY() - CenterY;  
        boolean inCenter = java.lang.Math.sqrt(x * x + y * y) <= CenterRadius;  
  
        switch (event.getAction())  
        {  
            case MotionEvent.ACTION_DOWN:  
            {  
                IsPressCenter = inCenter;  
                if (inCenter)  
                {  
                    IsMoveCenter = true;  
                    invalidate();  
                    break;  
                }  
            }  
            case MotionEvent.ACTION_MOVE:  
            {  
                if (IsPressCenter)  
                {  
                    if (IsMoveCenter != inCenter)  
                    {  
                        IsMoveCenter = inCenter;  
                        invalidate();  
                    }  
                }  
                else if ((x >= -CenterX && x <= CenterX) && (y >= -CenterY && y <= CenterY))  
                {  
                    float angle = (float) java.lang.Math.atan2(y, x);  
                    float unit = angle / (2 * PI);  
                    if (unit < 0)  
                        unit += 1;  
                    paintCenter.setColor(interpColor(arrColorCircle, unit));  
                    invalidate();  
                }  
                else  
                {  
                    int a, r, g, b, c0, c1;  
                    float p;  
  
                    if (y < 0)  
                    {  
                        c0 = arrColorGray[0];  
                        c1 = arrColorGray[1];  
                        p = (y + 100) / 100;  
                    }  
                    else  
                    {  
                        c0 = arrColorGray[1];  
                        c1 = arrColorGray[2];  
                        p = y / 100;  
                    }  
  
                    a = ave(Color.alpha(c0), Color.alpha(c1), p);  
                    r = ave(Color.red(c0), Color.red(c1), p);  
                    g = ave(Color.green(c0), Color.green(c1), p);  
                    b = ave(Color.blue(c0), Color.blue(c1), p);  
  
                    paintCenter.setColor(Color.argb(a, r, g, b));  
                    mRedrawHSV = false;  
                    invalidate();  
                }  
  
                break;  
            }  
            case MotionEvent.ACTION_UP:  
            {  
                if (IsPressCenter)  
                {  
                    IsPressCenter = false;  
                    invalidate();  
                }  
                break;  
            }  
        }  
  
        return true;  
    }  
  
    public Paint getPaintCircleShadow()  
    {  
        return paintCircleShadow;  
    }  
  
    public void setPaintCircleShadow(Paint paintCircleShadow)  
    {  
        this.paintCircleShadow = paintCircleShadow;  
    }  
  
    public Paint getPaintCircle()  
    {  
        return paintCircle;  
    }  
  
    public void setPaintCircle(Paint paintCircle)  
    {  
        this.paintCircle = paintCircle;  
    }  
  
    public Paint getPaintCenterShadow()  
    {  
        return paintCenterShadow;  
    }  
  
    public void setPaintCenterShadow(Paint paintCenterShadow)  
    {  
        this.paintCenterShadow = paintCenterShadow;  
    }  
  
    public Paint getPaintCenter()  
    {  
        return paintCenter;  
    }  
  
    public void setPaintCenter(Paint paintCenter)  
    {  
        this.paintCenter = paintCenter;  
    }  
  
    public Paint getPaintGrayShadow()  
    {  
        return paintGrayShadow;  
    }  
  
    public void setPaintGrayShadow(Paint paintGrayShadow)  
    {  
        this.paintGrayShadow = paintGrayShadow;  
    }  
  
    public Paint getPaintGray()  
    {  
        return paintGray;  
    }  
  
    public void setPaintGray(Paint paintGray)  
    {  
        this.paintGray = paintGray;  
    }  
  
    public int[] getArrColorGray()  
    {  
        return arrColorGray;  
    }  
  
    public void setArrColorGray(int[] arrColorGray)  
    {  
        this.arrColorGray = arrColorGray;  
    }  
  
    public boolean ismRedrawHSV()  
    {  
        return mRedrawHSV;  
    }  
  
    public void setmRedrawHSV(boolean mRedrawHSV)  
    {  
        this.mRedrawHSV = mRedrawHSV;  
    }  
  
    public boolean isIsPressCenter()  
    {  
        return IsPressCenter;  
    }  
  
    public void setIsPressCenter(boolean isPressCenter)  
    {  
        IsPressCenter = isPressCenter;  
    }  
  
    public boolean isIsMoveCenter()  
    {  
        return IsMoveCenter;  
    }  
  
    public void setIsMoveCenter(boolean isMoveCenter)  
    {  
        IsMoveCenter = isMoveCenter;  
    }  
  
    public int[] getArrColorCircle()  
    {  
        return arrColorCircle;  
    }  
  
  
    public String getStrColor()  
    {  
        return strColor;  
    }  
  
    public void setStrColor(String strColor)  
    {  
        this.strColor = strColor;  
    }  
  
    public double getZoom()  
    {  
        return Zoom;  
    }  
  
    public void setZoom(double zoom)  
    {  
        Zoom = zoom;  
    }  



}
