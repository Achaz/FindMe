package com.oxygen8.net;

import java.util.Random;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.oxygen8.net.materialmenu.MaterialMenuDrawable;
import com.oxygen8.net.materialmenu.MaterialMenuIconSherlock;
import com.oxygen8.net.materialmenu.MaterialMenuDrawable.IconState;
import com.oxygen8.net.materialmenu.MaterialMenuDrawable.Stroke;

public class PostDetailActivity extends SherlockActivity{

	  TextView description;
	  String details;
	  String imageUrl;
	  String price,location,email,categories,contact_name,phone_contact;
	  ImageView imageView;
	  Bitmap bitmap;
	  String name;
	  Movie smovie;
	  TextView title,phone;
	  ActionBar actionbar;
	  private SlidingMenu slidingMenu ;
	  MaterialMenuIconSherlock materialMenu;
	  int actionBarMenuState;
	  
	  private Drawable mActionBarBackgroundDrawable;
	  
	  private Drawable.Callback mDrawableCallback = new Drawable.Callback() {
		    @Override
		    public void invalidateDrawable(Drawable who) {
		        getActionBar().setBackgroundDrawable(who);
		    }

		    @Override
		    public void scheduleDrawable(Drawable who, Runnable what, long when) {
		    }

		    @Override
		    public void unscheduleDrawable(Drawable who, Runnable what) {
		    }
		};

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_details);
//		FadingActionBarHelper helper = new FadingActionBarHelper()
//        .actionBarBackground(R.drawable.ab_background_light)
//        .contentLayout(R.layout.post_details)
//        .lightActionBar(true);
//		setContentView(helper.createView(this));
//		helper.initActionBar(this);
		AdView mAdView = (AdView) findViewById(R.id.postdetail_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

		actionbar = getSupportActionBar();
		
		this.mActionBarBackgroundDrawable = getResources().getDrawable(R.drawable.ab_background);
	    this.mActionBarBackgroundDrawable.setAlpha(0);
	    getSupportActionBar().setBackgroundDrawable(mActionBarBackgroundDrawable);
	    
        ((NotifyingScrollView) findViewById(R.id.scroll_view)).setOnScrollChangedListener(mOnScrollChangedListener);

	    if (Build.VERSION.SDK_INT < 17) {
	      this.mActionBarBackgroundDrawable.setCallback(this.mDrawableCallback);
	    }

		actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1f2649")));
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
        
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		ViewHolder viewholder = new ViewHolder();
		
		viewholder.details = (TextView)findViewById(R.id.txt_desc);
		viewholder.titlt = (TextView)findViewById(R.id.txt_name);
		viewholder.imageView = (ImageView)findViewById(R.id.img_background);
		viewholder.phone = (TextView)findViewById(R.id.txt_phone);
		viewholder.email = (TextView)findViewById(R.id.txt_email);
		viewholder.location = (TextView)findViewById(R.id.txt_location);
		viewholder.price = (TextView)findViewById(R.id.txt_price);
		viewholder.contactName = (TextView)findViewById(R.id.txt_contactName);
		
		smovie = (Movie)getIntent().getParcelableExtra("name_of_extra");
		Intent in = getIntent();
		
		name = in.getStringExtra("title");
		details = in.getStringExtra("details");
		imageUrl = in.getStringExtra("thumbnailUrl");
		price = in.getStringExtra("price");
		location = in.getStringExtra("location");
		email = in.getStringExtra("email");
		categories = in.getStringExtra("category");
		contact_name = in.getStringExtra("contact_name");
		phone_contact = in.getStringExtra("phone_contact");

		viewholder.details.setText(details);
		viewholder.titlt.setText(name);
		viewholder.phone.setText(phone_contact);
		viewholder.contactName.setText(contact_name);
		viewholder.email.setText(email);
		viewholder.location.setText(location);
		viewholder.price.setText(price);

		UrlImageViewHelper.setUrlDrawable(viewholder.imageView, this.imageUrl, R.drawable.placeholder);
		
	}
    private NotifyingScrollView.OnScrollChangedListener mOnScrollChangedListener = new NotifyingScrollView.OnScrollChangedListener() {
    	
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        	
            final int headerHeight = findViewById(R.id.img_background).getHeight() - getActionBar().getHeight();
            final float ratio = (float) Math.min(Math.max(t, 0), headerHeight) / headerHeight;
            final int newAlpha = (int) (ratio * 255);
            mActionBarBackgroundDrawable.setAlpha(newAlpha);
        }
    };

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
	
	static class ViewHolder{
		
	    TextView details;
	    ImageView imageView;
	    TextView titlt;
	    TextView phone;
	    TextView location;
	    TextView email;
	    TextView price;
	    TextView contactName;
	}
	

}
