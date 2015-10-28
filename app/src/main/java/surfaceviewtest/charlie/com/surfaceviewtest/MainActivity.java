package surfaceviewtest.charlie.com.surfaceviewtest;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * 画制小动画，
 */
public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, Runnable {

    private Thread thread;
    private boolean running;
    private SurfaceHolder surfaceHolder;
    private TextPaint textPaint;
    private float cx, cy;// 随意圆形位置
    private float cr;// 随机圆形半径
    private int cColor;// 随机颜色
    private Random random;// 随机对象
    private Paint ciclePaint;
    private int textY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        random = new Random();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_view);
        SurfaceHolder holder = surfaceView.getHolder();
        holder.addCallback(this);


    }

    @Override
    public void run() {
        running = true;

        textPaint = new TextPaint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(50);

        ciclePaint = new Paint();
        ciclePaint.setStyle(Paint.Style.FILL);
        try {
            while (surfaceHolder != null && running) {
                if (surfaceHolder.getSurface() != null) {


                    // 获取当前surface宽高
                    Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
                    cx = random.nextInt(surfaceFrame.right);// 水平方向随机

                    cy = random.nextInt(surfaceFrame.bottom);// 垂直方向随机
                    cr = random.nextInt(20) + 30;

                    int r = random.nextInt(255);
                    int g = random.nextInt(255);
                    int b = random.nextInt(255);
                    cColor = Color.argb(0x99,r,g,b);

                    ciclePaint.setColor(cColor);
                    updateRender();// 更新内容

                }

                Thread.sleep(300);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void updateRender() {
        // 开始绘制
        // 获取当前屏幕切出的区域，然后获取其中的canvas
        // 通过canvas就可以画内容，
        // 绘制的内容可以显示在屏幕上
        Canvas canvas = surfaceHolder.lockCanvas();
        // 下面方法是清空屏，其实就是原理将背景覆盖
        canvas.drawColor(Color.BLACK);

//        canvas.drawText("Hello World", 10, textY, textPaint);
        canvas.drawCircle(cx,cy,cr,ciclePaint);

        // surfaceView绘制的时候，只有使用unlockCanvasAndPost（canvas）才会绘制到屏幕上
        // 实际上采用了绘制的双缓冲技术 TODO 查资料
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // 用于线程使用的
        surfaceHolder = holder;

        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        running = false;
        surfaceHolder = null;
        thread.interrupted();
    }


}
