package com.oxygen8.net;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

public class Page extends SherlockListActivity{
	
	private static final String url = "http://www.erpmastersltd.com/AndroidFileUpload/fetch_json.php";
	String TAG_OS = "feeds";
	ListView lv;
	JSONArray json;
	private List<Movie> rowItems;
	private SlidingMenu slidingMenu ;
	ActionBar actionbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_content);
		actionbar = getSupportActionBar();
		actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8658ce")));
		getSupportActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
		slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
       // slidingMenu.setShadowDrawable(R.color.et_white);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slidingmenu);
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		new PagesFetcher().execute();
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if ( slidingMenu.isMenuShowing()) {
			
            slidingMenu.toggle();
            
        }else {
        	
            super.onBackPressed();
        }
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if ( keyCode == KeyEvent.KEYCODE_MENU ) {
			
            this.slidingMenu.toggle();
            	return true;
        }
		return super.onKeyDown(keyCode, event);
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		
        case android.R.id.home:
        	
            this.slidingMenu.toggle();
            
            return true;
            
        default:
        	
            return super.onOptionsItemSelected(item);
        }
	}
	
	
	private class PagesFetcher extends AsyncTask<String, String, JSONObject>{
        
		ProgressDialog dialog = new ProgressDialog(Page.this);
		
		@Override
		protected void onPreExecute() {
			
			// TODO Auto-generated method stub
			  dialog.setMessage("Loading,Please Wait");
		      dialog.show();
		      dialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
		    	  
		        public void onCancel(DialogInterface paramAnonymousDialogInterface){
		        	
		          Page.PagesFetcher.this.cancel(true);
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
		        	rowItems.add(movie);
		        	
		        	
		        	
		        }
		        
		        setListAdapter(new CustomListAdapter(Page.this, rowItems));
				
			}catch(JSONException exp){
				
				exp.printStackTrace();
			}
		}
		
		
		
	}

}
