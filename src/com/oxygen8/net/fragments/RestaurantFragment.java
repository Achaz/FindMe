package com.oxygen8.net.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.oxygen8.net.CustomListAdapter;
import com.oxygen8.net.JSONParser;
import com.oxygen8.net.Movie;
import com.oxygen8.net.PostDetailActivity;
import com.oxygen8.net.R;
import com.oxygen8.net.materialmenu.MaterialMenuDrawable;
import com.oxygen8.net.materialmenu.MaterialMenuIconSherlock;
import com.oxygen8.net.materialmenu.MaterialMenuDrawable.IconState;
import com.oxygen8.net.materialmenu.MaterialMenuDrawable.Stroke;

public class RestaurantFragment extends SherlockListActivity{
	
	private static final String url = "http://www.erpmastersltd.com/AndroidFileUpload/fetch_json.php?category=Restaurants";
	String TAG_OS = "feeds";
	ListView lv;
	JSONArray json;
	private List<Movie> rowItems;
	private SlidingMenu slidingMenu ;
	ActionBar actionbar;
	MaterialMenuIconSherlock materialMenu;
	int actionBarMenuState;
	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private ViewFlipper mViewFlipper;	
	private AnimationListener mAnimationListener;
	private Context mContext;
	@SuppressWarnings("deprecation")
	private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.list_content);
		actionbar = getSupportActionBar();
		actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1f2649")));
		getSupportActionBar().setTitle("Restaurants");
		
		mContext = this;
		mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
	
		mViewFlipper.setAutoStart(true);
		mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this,
		        android.R.anim.fade_in));
		mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, 
				android.R.anim.fade_out));
		mViewFlipper.setFlipInterval(4000);
		mViewFlipper.startFlipping();
		
//		actionBar.setBackgroundDrawable(drawable.ab_background_textured_example);
		
		mViewFlipper.setOnTouchListener(new OnTouchListener() {
			
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(final View view, final MotionEvent event) {
				detector.onTouchEvent(event);
				return true;
			}
		});

		//animation listener
		mAnimationListener = new Animation.AnimationListener() {
			public void onAnimationStart(Animation animation) {
				//animation started event
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				//TODO animation stopped event
			}
		};
		//getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		materialMenu = new MaterialMenuIconSherlock(this, Color.WHITE, Stroke.THIN);
		slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        //slidingMenu.setShadowDrawable(R.color.et_ls_down);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slidingmenu);
     
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		new PagesFetcher().execute();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if ( slidingMenu.isMenuShowing()) {
			
            slidingMenu.toggle();
            
            
        }else {
        	materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
            super.onBackPressed();
        }
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		materialMenu.syncState(savedInstanceState);
	}
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		materialMenu.onSaveInstanceState(outState);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ( keyCode == KeyEvent.KEYCODE_MENU ) {
			
            this.slidingMenu.toggle();
            materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
            	return true;
        }
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId() == android.R.id.home){
			
			 this.slidingMenu.toggle();
			 actionBarMenuState = generateState(actionBarMenuState);
	         materialMenu.animatePressedState(intToState(actionBarMenuState));
	       
	         return true;
	         
		}else{
			
			materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);
			return true;
		}
	}
	
	public static int generateState(int previous) {
        int generated = new Random().nextInt(2);
        return generated != previous ? generated : generateState(previous);
    }

    public static IconState intToState(int state) {
        switch (state) {
            case 0:
                return IconState.BURGER;
            case 1:
                return IconState.ARROW;

        }
        throw new IllegalArgumentException("Must be a number [0,3)");
    }
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Movie localMovie1 = (Movie)this.rowItems.get(position);
		
	    String str1 = localMovie1.getTitle();
	    String str2 = localMovie1.getDetails();
	    String str3 = localMovie1.getThumbnailUrl();
	    String str4 = localMovie1.getCategory();
	    String str5 = localMovie1.getContact_name();
	    String str6 = localMovie1.getPrice();
	    String str7 = localMovie1.getEmail();
	    String str8 = localMovie1.getLocation();
	    String Str9 = localMovie1.getPhone_contact();
	    
	    Movie localMovie2 = new Movie();
	    
	    localMovie2.setDetails(str2);
	    localMovie2.setTitle(str1);
	    localMovie2.setThumbnailUrl(str3);
	    
	 
	    Intent postdetails = new Intent(RestaurantFragment.this,PostDetailActivity.class);
	    
	    postdetails.putExtra("title", str1);
	    postdetails.putExtra("details", str2);
	    postdetails.putExtra("thumbnailUrl", str3);
	    postdetails.putExtra("category", str4);
	    postdetails.putExtra("contact_name",str5);
	    postdetails.putExtra("price", str6);
	    postdetails.putExtra("email", str7);
	    postdetails.putExtra("location", str8);
	    postdetails.putExtra("phone_contact", Str9);

	    startActivity(postdetails);
	}
	class SwipeGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			try {
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
					mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
					// controlling animation
					mViewFlipper.getInAnimation().setAnimationListener(mAnimationListener);
					mViewFlipper.showNext();
					return true;
				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					
					mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
					mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_out));
					// controlling animation
					mViewFlipper.getInAnimation().setAnimationListener(mAnimationListener);
					mViewFlipper.showPrevious();
					return true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			return false;
		}
	}

	private class PagesFetcher extends AsyncTask<String, String, JSONObject>{
        
		ProgressDialog dialog = new ProgressDialog(RestaurantFragment.this);
		
		@Override
		protected void onPreExecute() {
			
			// TODO Auto-generated method stub
			  dialog.setMessage("Loading,Please Wait");
		      dialog.show();
		      dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
		    	  
		        public void onCancel(DialogInterface paramAnonymousDialogInterface){
		        	
		          RestaurantFragment.PagesFetcher.this.cancel(true);
		          paramAnonymousDialogInterface.dismiss();
		          
		          }
		      });
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new JSONParser().getJSONFromUrl(url);
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			
			try{
				
				json = result.getJSONArray(TAG_OS);
				rowItems = new ArrayList<Movie>();
				
				JSONObject jObj;
		        Movie movie;
	
		        for(int i = 0;i < json.length(); i++){
		        	
		        	jObj = json.getJSONObject(i);
		        	
		        	movie = new Movie();
		        	
		        	movie.setCategory(jObj.getString("category"));
		        	movie.setContact_name(jObj.getString("contact_name"));
		        	movie.setDetails(jObj.getString("description"));
		        	movie.setEmail(jObj.getString("email"));
		        	movie.setLocation(jObj.getString("location"));
		        	movie.setPhone_contact(jObj.getString("contact_phone"));
		        	movie.setPrice(jObj.getString("price"));
		        	movie.setThumbnailUrl(jObj.getString("image"));
		        	movie.setTitle(jObj.getString("title"));
		        	movie.setPostDate(jObj.getString("post_date"));
		        	rowItems.add(movie);
		        }
		        
		        setListAdapter(new CustomListAdapter(RestaurantFragment.this, rowItems));
				
			}catch(JSONException exp){
				
				exp.printStackTrace();
			}
		}
	
	}

}
