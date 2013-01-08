package abz.chand.supermarketassistant.guide;

import org.opencv.core.Point;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class PreviewOverlay extends View {

	private int width;
	private int height;
	private Point startDrawPoint;
	private Point endDrawPoint;
	private Paint paint;
	private ProcessFrame processFrame;

	public PreviewOverlay(Context context, ProcessFrame processFrame) {
		super(context);		
		this.processFrame = processFrame;
	}

	public PreviewOverlay(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PreviewOverlay(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void init(){
		startDrawPoint = new Point(width/2, height/2);
		endDrawPoint = new Point(width/2, height/2);
		setAngle(225);
		paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setDither(true);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setARGB(255, 0, 255, 0);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		setCenterPoint(processFrame.getCenterPoint());
		setAngle(processFrame.getAngle());
//		setAngle(0);
		
		canvas.drawLine((float) startDrawPoint.x, (float) startDrawPoint.y, (float) endDrawPoint.x, (float) endDrawPoint.y, paint);
		invalidate();
	}

	private void setCenterPoint(Point centerPoint) {		
		double widthRatio = width/ (double) processFrame.getWidth();
		double heightRatio = height/ (double) processFrame.getHeight();
		startDrawPoint.x = centerPoint.x * widthRatio;
		startDrawPoint.y = centerPoint.y * heightRatio;
	}

	public void setAngle(double angle){
		int length = width;
		double radians = Math.toRadians(angle);
		double x = Math.cos(radians) * length;
		double y = Math.sin(radians) * length;
		endDrawPoint.x = startDrawPoint.x + y;
		endDrawPoint.y = startDrawPoint.y - x;
	}

	public void setZ(double z) {
		//setAngle()
		if (startDrawPoint != null && endDrawPoint != null){
			setAngle(z);
		}
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);		
		width = w;
		height = h;
		init();	
	}
}