package com.shuang.letter.ui;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class QuickIndexBar extends View { 

	//格子的宽高
	private float mCellWidth;
	private float mCellHeight;
	//数据
	private static final String[] LETTERS = new String[] { "A", "B", "C", "D", "E", "F", "G", "H",
		"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
	"Z" };
	private Rect mBounds;
	private Paint paint;
	//当前的索引的字母
	private int mCurrentIndex;
	private float y;
	//回调函数
	private OnIndexChangeListener mOnIndexChangeListener;


	public QuickIndexBar(Context context) {
		this(context, null);
	}

	public QuickIndexBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	private void init() {

		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setTextSize(14f);
		//矩形
		mBounds = new Rect();
	}

	/**
	 * TODO 测量
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	/**
	 * TODO 获取宽高
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mCellWidth = getMeasuredWidth();
		mCellHeight = getMeasuredHeight() * 1.0f / LETTERS.length;
	}

	/**
	 * TODO 会话
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		System.out.println(LETTERS.length);
		for(int i=0;i<LETTERS.length;i++){

			String text=LETTERS[i];
			//测量字体的宽
			float textWidth = paint.measureText(text);
			//测量字体的高
			paint.getTextBounds(text, 0, text.length(), mBounds);
			float textHeight = mBounds.height();
			float x = mCellWidth * 0.5f - textWidth * 0.5f;
			float y = mCellHeight * 0.5f + textHeight * 0.5f + i * mCellHeight;
			paint.setColor(mCurrentIndex == i ? Color.BLUE : Color.WHITE);
			canvas.drawText(text, x, y, paint);
		}
	}
	/**
	 * TODO 触摸事件
	 * 
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action=event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:

			y = event.getY();
			int index=(int) (y/mCellHeight);
			if(index<LETTERS.length&&mCurrentIndex!=index){
				mCurrentIndex=index;
				if(mOnIndexChangeListener!=null){
					mOnIndexChangeListener.OnIndexChange(LETTERS[index]);
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			y = event.getY();
			int index2=(int) (y/mCellHeight);
			if(index2<LETTERS.length&&mCurrentIndex!=index2){
				mCurrentIndex=index2;
				if(mOnIndexChangeListener!=null){
					mOnIndexChangeListener.OnIndexChange(LETTERS[index2]);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			mCurrentIndex=-1;
			break;
		}
		invalidate();
		return true;
	}

	/**
	 * 设置回调函数
	 */
	public interface OnIndexChangeListener{

		public void OnIndexChange(String Letter);
	}
	public void setOnIndexChangeListener(OnIndexChangeListener indexChangeListener){
		mOnIndexChangeListener=indexChangeListener;
	}
    /**
     * 设置搜索
     */
	public void setCurrentLetter(String letter){
		int binarySearch = Arrays.binarySearch(LETTERS, letter);
		mCurrentIndex=binarySearch;
		invalidate();
	}
	
}
