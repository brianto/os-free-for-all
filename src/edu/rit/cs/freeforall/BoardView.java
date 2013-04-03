package edu.rit.cs.freeforall;

import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BoardView extends View implements Observer {
	private Paint paint;

	public BoardView(Context context, AttributeSet attributes) {
		super(context, attributes);
		
		// TODO Fill with real stuff
		this.paint = new Paint();
		this.paint.setColor(getResources().getColor(R.color.team_green));
	}

	@Override
	public void update(Observable target, Object data) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		canvas.drawLine(10, 10, 220, 220, paint);
	}
}
