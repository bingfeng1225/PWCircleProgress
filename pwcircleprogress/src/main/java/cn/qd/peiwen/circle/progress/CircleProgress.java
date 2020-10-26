package cn.qd.peiwen.circle.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cn.qd.peiwen.progress.R;

public class CircleProgress extends View {
    // 当前进度
    private int progress;
    // 方向
    private int direction;
    // 圆环宽度
    private int ringWidth;
    // 起始角度
    private int startAngle;
    // 圆环颜色
    private int ringColor;
    private int progressColor;

    private static final int TOTAL_PROGRESS = 100;

    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initCircleProgress(context, attrs, defStyle);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
        postInvalidate();
    }

    public int getRingWidth() {
        return ringWidth;
    }

    public void setRingWidth(int ringWidth) {
        this.ringWidth = ringWidth;
        postInvalidate();
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
        postInvalidate();
    }

    public int getRingColor() {
        return ringColor;
    }

    public void setRingColor(int ringColor) {
        this.ringColor = ringColor;
        postInvalidate();
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        postInvalidate();
    }

    private void initCircleProgress(Context context, AttributeSet attrs, int defStyle) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress, defStyle, 0);
        try {
            this.ringWidth = ta.getDimensionPixelSize(R.styleable.CircleProgress_cp_ring_width, 10);

            this.progress = ta.getInt(R.styleable.CircleProgress_cp_progress, 0);
            this.direction = ta.getInt(R.styleable.CircleProgress_cp_direction, 0);
            this.startAngle = ta.getInt(R.styleable.CircleProgress_cp_start_angle, -90);

            this.ringColor = ta.getColor(R.styleable.CircleProgress_cp_ring_color, Color.GRAY);
            this.progressColor = ta.getColor(R.styleable.CircleProgress_cp_progress_color, Color.RED);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float centerX = getWidth() / 2.f;
        float centerY = getHeight() / 2.f;
        float radius = (-this.ringWidth + Math.min(getWidth(), getHeight())) / 2.f;

        Paint progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(this.progressColor);
        progressPaint.setStrokeWidth(this.ringWidth);

        RectF oval = new RectF();
        oval.top = centerY - radius;
        oval.bottom = centerY + radius;
        oval.left = centerX - radius;
        oval.right = centerX + radius;

        Paint ringPaint = new Paint();
        ringPaint.setAntiAlias(true);
        ringPaint.setColor(this.ringColor);
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setStrokeWidth(this.ringWidth);

        if(direction == 0) {
            canvas.drawArc(oval, this.startAngle, 360, false, ringPaint);
            canvas.drawArc(oval, this.startAngle, ((float) this.progress / TOTAL_PROGRESS) * 360, false, progressPaint);
        } else {
            canvas.drawArc(oval, this.startAngle, 360, false, progressPaint);
            canvas.drawArc(oval, this.startAngle, 360 - ((float) this.progress / TOTAL_PROGRESS) * 360, false, ringPaint);
        }
    }
}
