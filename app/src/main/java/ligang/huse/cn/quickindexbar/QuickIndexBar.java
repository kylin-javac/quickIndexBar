package ligang.huse.cn.quickindexbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 创建时间 javac on 2016/5/4.
 * <p/>
 * 文  件 quickIndexBar
 * <p/>
 * 描  述 自定义快速检索
 */
public class QuickIndexBar extends View{
    private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };

    private Paint mPaint;
    private int mHeight;
    private float mCellHight;

    public QuickIndexBar(Context context) {
        super(context);
        init();
    }




    public QuickIndexBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getMeasuredWidth();
        mCellHight = getMeasuredHeight()*1f / indexArr.length;


    }

    public  void init() {
        //设置抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(42);
       //
        mPaint.setTextAlign(Paint.Align.CENTER);
    }


    /**
     *绘制文本x坐标: width/2;
     *绘制文本y坐标: 格子高度的一半 + 文本高度的一半 + position*格子高度
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0;i<indexArr.length;i++){
            int x=mHeight/2;
            int height = getTextHeight(indexArr[i]);
            float y=mCellHight/2+height/2+i*mCellHight;//格子高度的一半 + 文本高度的一半 + position*格子高度
            mPaint.setColor(Lastindex==i?Color.WHITE:Color.BLACK);//判断是否按下，按下则把画笔的颜色改为白色(实现按下的效果)
            canvas.drawText(indexArr[i],x,y,mPaint);
        }
    }

    /**
     * 计算触摸点对应的字母:根据触摸点的y坐标除以cellHeight,得到的值就是字母对应的索引;
     * @param event
     * @return
     */
    private int Lastindex=-1;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) (y / mCellHight);
                if(index!=Lastindex) {
                    //接口回调第四步 调用接口,让外部去实现
                    if(index>=0&&index<26){
                        listern.onTochWord(indexArr[index]);
                    }
                    Log.i("onTouchEvent", indexArr[index]);
                }
                Lastindex=index;
                break;
            case MotionEvent.ACTION_UP:
                Lastindex=-1;
                break;
        }
        invalidate();//重新回调onDraw方法重新绘制
        return true;
    }

    /**
     * 获取格子的高度
     * @param text
     * @return
     */
    public int getTextHeight(String text) {
        Rect rect = new Rect();
        mPaint.getTextBounds(text,0, text.length(),rect);
        return rect.height();
    }

    /**
     * 接口回调第二步 定义一个接口变量
     */
    private OntouchWordListen listern;

    /**
     * 接口回调第三步 生成对外部的方法
     * @param listern
     */
    public void setListern(OntouchWordListen listern) {
        this.listern = listern;
    }

    /**
     * 接口回调第一步 定义一个接口
     */
    public interface  OntouchWordListen{
        void onTochWord(String word);
    }

}
