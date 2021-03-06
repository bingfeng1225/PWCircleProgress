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

public class DashboardProgress extends View {

    private float value;// 当前进度
    private int minValue;// 最小值
    private int maxValue;// 最大值

    private int direction;// 绘制方向
    private int ringWidth;// 圆环宽度

    private int spanAngle;// 多大角度
    private int startAngle;// 起始角度

    private int ringColor;// 圆环颜色
    private int progressColor;// 进度颜色

    public DashboardProgress(Context context) {
        this(context, null);
    }

    public DashboardProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashboardProgress(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initCircleProgress(context, attrs, defStyle);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
        postInvalidate();
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        postInvalidate();
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
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


    public int getSpanAngle() {
        return spanAngle;
    }

    public void setSpanAngle(int spanAngle) {
        this.spanAngle = spanAngle;
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
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DashboardProgress, defStyle, 0);
        try {
            this.minValue = ta.getInt(R.styleable.DashboardProgress_dbp_min_value, 0);
            this.maxValue = ta.getInt(R.styleable.DashboardProgress_dbp_max_value, 100);

            this.value = ta.getFloat(R.styleable.DashboardProgress_dbp_value, this.minValue);

            this.direction = ta.getInt(R.styleable.DashboardProgress_dbp_direction, 0);
            this.ringWidth = ta.getDimensionPixelSize(R.styleable.DashboardProgress_dbp_ring_width, 10);

            this.spanAngle = ta.getInt(R.styleable.DashboardProgress_dbp_span_angle, 90);
            this.startAngle = ta.getInt(R.styleable.DashboardProgress_dbp_start_angle, -90);

            this.ringColor = ta.getColor(R.styleable.DashboardProgress_dbp_ring_color, Color.GRAY);
            this.progressColor = ta.getColor(R.styleable.DashboardProgress_dbp_progress_color, Color.RED);
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

        Paint progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setColor(this.progressColor);
        progressPaint.setStrokeWidth(this.ringWidth);

        Paint ringCircle = new Paint();
        ringCircle.setAntiAlias(true);
        ringCircle.setColor(this.ringColor);
        ringCircle.setStyle(Paint.Style.FILL);;
        ringCircle.setStrokeWidth(1);

        Paint progressCircle = new Paint();
        progressCircle.setAntiAlias(true);
        progressCircle.setColor(this.progressColor);
        progressCircle.setStyle(Paint.Style.FILL);
        progressCircle.setStrokeWidth(1);

        float progress = 1.0f * (this.value - this.minValue) / (this.maxValue - this.minValue);

        double startRadians = ((360 + startAngle % 360) % 360) * Math.PI / 180.0;
        float startCos = (float) Math.cos(startRadians);
        float startSin = (float) Math.sin(startRadians);
        float startX = (centerX + radius * startCos);
        float startY = (centerY + radius * startSin);

        double endRadians = ((360 + (startAngle + this.spanAngle) % 360) % 360) * Math.PI / 180.0;
        float endCos = (float) Math.cos(endRadians);
        float endSin = (float) Math.sin(endRadians);
        float endX = (centerX + radius * endCos);
        float endY = (centerY + radius * endSin);

        if (direction == 0) {
            if(progress <= 0.0f) {
                canvas.drawCircle(endX, endY, this.ringWidth / 2.0f, ringCircle);
                canvas.drawCircle(startX, startY, this.ringWidth / 2.0f, ringCircle);
            } else if(progress >= 1.0f) {
                canvas.drawCircle(endX, endY, this.ringWidth / 2.0f, progressCircle);
                canvas.drawCircle(startX, startY, this.ringWidth / 2.0f, progressCircle);
            } else {
                canvas.drawCircle(endX, endY, this.ringWidth / 2.0f, ringCircle);
                canvas.drawCircle(startX, startY, this.ringWidth / 2.0f, progressCircle);
            }
            canvas.drawArc(oval, this.startAngle, this.spanAngle, false, ringPaint);
            if(progress <= 0.0f) {
                canvas.drawArc(oval, this.startAngle, 0, false, progressPaint);
            } else if(progress >= 1.0f) {
                canvas.drawArc(oval, this.startAngle, this.spanAngle, false, progressPaint);
            } else {
                canvas.drawArc(oval, this.startAngle, progress * this.spanAngle, false, progressPaint);
            }
        } else {
            if(progress <= 0.0f) {
                canvas.drawCircle(endX, endY, this.ringWidth / 2.0f, ringCircle);
                canvas.drawCircle(startX, startY, this.ringWidth / 2.0f, ringCircle);
            } else if(progress >= 1.0f) {
                canvas.drawCircle(endX, endY, this.ringWidth / 2.0f, progressCircle);
                canvas.drawCircle(startX, startY, this.ringWidth / 2.0f, progressCircle);
            } else {
                canvas.drawCircle(endX, endY, this.ringWidth / 2.0f, progressCircle);
                canvas.drawCircle(startX, startY, this.ringWidth / 2.0f, ringCircle);
            }
            canvas.drawArc(oval, this.startAngle, this.spanAngle, false, progressPaint);
            if(progress <= 0.0f) {
                canvas.drawArc(oval, this.startAngle, this.spanAngle, false, ringPaint);
            } else if(progress >= 1.0f) {
                canvas.drawArc(oval, this.startAngle, 0, false, ringPaint);
            } else {
                canvas.drawArc(oval, this.startAngle, this.spanAngle - progress * this.spanAngle, false, ringPaint);
            }
        }
    }
}
