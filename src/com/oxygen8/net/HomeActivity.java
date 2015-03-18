package com.oxygen8.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.etsy.android.grid.StaggeredGridView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.oxygen8.net.materialmenu.MaterialMenuDrawable;
import com.oxygen8.net.materialmenu.MaterialMenuIconSherlock;
import com.oxygen8.net.materialmenu.MaterialMenuDrawable.IconState;
import com.oxygen8.net.materialmenu.MaterialMenuDrawable.Stroke;

public class HomeActivity extends SherlockActivity implements OnScrollListener, OnItemClickListener{

	  String TAG_OS = "feeds";
	  private StaggeredGridView mGridView;
	  private List<Movie> rowItems;
	  JSONArray json = null;
	  private SlidingMenu slidingMenu ;
	  ActionBar actionbar;
	  MaterialMenuIconSherlock materialMenu;
	  int actionBarMenuState;
	  String tag;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sgv);
		
		AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

		
//		FadingActionBarHelper helper = new FadingActionBarHelper()
//        .actionBarBackground(R.drawable.ab_background_light)
//        .headerLayout(R.layout.header_light)
//        .contentLayout(R.layout.activity_sgv)
//        .lightActionBar(true);
//		setContentView(helper.createView(this));
//		helper.initActionBar(this);

		new PagesFetcher().execute();
		mGridView = ((StaggeredGridView)findViewById(R.id.grid_view));
	    mGridView.setOnScrollListener(this);
	    mGridView.setOnItemClickListener(this);
	    
	    actionbar = getSupportActionBar();
		actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1f2649")));
		getSupportActionBar().setTitle("Home");
		//getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		materialMenu = new MaterialMenuIconSherlock(this, Color.WHITE, Stroke.THIN);
		slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        //slidingMenu.setShadowDrawable(R.color.et_white);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slidingmenu);
        
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
	
	private class PagesFetcher extends AsyncTask<String, String, JSONObject>{

		ProgressDialog dialog = new ProgressDialog(HomeActivity.this);
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			  this.dialog.setMessage("Loading,Please Wait");
		      this.dialog.show();
		      this.dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
		    	  
		        public void onCancel(DialogInterface paramAnonymousDialogInterface){
		          HomeActivity.PagesFetcher.this.cancel(true);
		          paramAnonymousDialogInterface.dismiss();
		        }
		    });
		}

		@Override
		protected JSONObject doInBackground(String... params) {
			// TODO Auto-generated method stub
			return new JSONParser().getJSONFromUrl("http://www.erpmastersltd.com/AndroidFileUpload/fetch_all_json.php");
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			this.dialog.dismiss();
			
			try{
				
				json = result.getJSONArray(TAG_OS);
				rowItems = new ArrayList<Movie>();
				
				JSONObject jObj;
		        Movie movie;
		        
		        for(int i = 0; i < json.length(); i++){
		        	
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
		        	rowItems.add(movie);
		        	
		        }
		        
		        mGridView = ((StaggeredGridView)findViewById(R.id.grid_view));
		        mGridView.setAdapter(new GridViewAdapter(HomeActivity.this, rowItems));
		        
			}catch(JSONException exp){
				
				exp.printStackTrace();
			}
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
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
	    
	    Intent postdetails = new Intent(HomeActivity.this,PostDetailActivity.class);
	    
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

}
