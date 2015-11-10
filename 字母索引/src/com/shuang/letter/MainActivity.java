package com.shuang.letter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shuang.letter.model.Cheeses;
import com.shuang.letter.model.GoodMan;
import com.shuang.letter.ui.QuickIndexBar;
import com.shuang.letter.ui.QuickIndexBar.OnIndexChangeListener;
import com.shuang.letter.utils.ToastUtil;


public class MainActivity extends Activity {

	private QuickIndexBar quickIndexBar;
    private ArrayList<GoodMan> mPersons = new ArrayList<GoodMan>();
    private String mFirstLetter;
	private ListView lv_main_list;
	private TextView tv_index;
	private String mPreFirstLetter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/**
		 * 设置Text
		 */
		tv_index = (TextView) findViewById(R.id.tv_index);
		
		fillNameAndSort();
		/**
		 * 设置ListView
		 */
		lv_main_list = (ListView) findViewById(R.id.lv_main);
		lv_main_list.setAdapter(new MyAdapter());
		
		/**
		 * 字母索引表
		 */
		quickIndexBar = (QuickIndexBar) findViewById(R.id.quickIndexBar1);
		quickIndexBar.setOnIndexChangeListener(new OnIndexChangeListener() {
			@Override
			public void OnIndexChange(String letter) {
				showText(letter);
				for(int i=0;i<mPersons.size();i++){
					GoodMan goodMan = mPersons.get(i);
					String firstLetter = goodMan.getPinyin().charAt(0)+"";
					if(TextUtils.equals(firstLetter, letter)){
						lv_main_list.setSelection(i);
						break;
					}
				}
				//ToastUtil.showToast(getApplicationContext(), ""+Index+"");
			}
		});
		
		/**
		 * 设置滚动事件
		 */
		lv_main_list.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
				GoodMan goodMan = mPersons.get(firstVisibleItem);
				String indexLetter = String.valueOf(goodMan.getPinyin().charAt(0));
				/**
				 * 设置文本
				 */
				showText(indexLetter);
				quickIndexBar.setCurrentLetter(indexLetter);
			}
		});
	}
	
	
	/**
	 * 显示文本
	 */
	private Handler mHandler = new Handler();
	public void showText(String letter){
		tv_index.setVisibility(View.VISIBLE);
		tv_index.setText(letter);
		mHandler.removeCallbacksAndMessages(null);
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				tv_index.setVisibility(View.GONE);
			}
		}, 1000);
	}
	
	/**
	 * TODO 给数据排序
	 */
    private void fillNameAndSort() {
        for (int i = 0; i < Cheeses.NAMES.length; i++) {
            GoodMan person = new GoodMan(Cheeses.NAMES[i]);
            mPersons.add(person);
        }
        Collections.sort(mPersons);
    }
    
    /**
     * TODO 设置Adapter
     */
    class MyAdapter extends BaseAdapter{

    	
		@Override
		public int getCount() {
			return mPersons.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mPersons.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			
			View view=null;
			ViewHolder viewHolder=null;
			if(arg1!=null){
				view=arg1;
				viewHolder=(ViewHolder) view.getTag();
			}else{
				view=View.inflate(getApplicationContext(), R.layout.lv_item, null);
				viewHolder=new ViewHolder();
				viewHolder.tv_name=(TextView) view.findViewById(R.id.tv_name);
				viewHolder.tv_pinyin=(TextView) view.findViewById(R.id.tv_pinyin);
				view.setTag(viewHolder);
			}
			GoodMan goodMan = mPersons.get(arg0);
	        mFirstLetter = String.valueOf(goodMan.getPinyin().charAt(0));
	        if (arg0 == 0) {
	        	mPreFirstLetter = "-";
	        } else {
	            GoodMan prePerson = mPersons.get(arg0 - 1);
	            mPreFirstLetter = String.valueOf(prePerson.getPinyin().charAt(0));
	        }
	        viewHolder.tv_name.setText(goodMan.getName());
	        viewHolder.tv_pinyin.setVisibility(TextUtils.equals(mPreFirstLetter, mFirstLetter) ? View.GONE
	                : View.VISIBLE);
	        viewHolder.tv_pinyin.setText(String.valueOf(goodMan.getPinyin().charAt(0)));
			return view;
		}
    }
    static class ViewHolder{
    	TextView tv_pinyin;
    	TextView tv_name;
    }
    
}
