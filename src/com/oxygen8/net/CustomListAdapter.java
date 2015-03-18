package com.oxygen8.net;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class CustomListAdapter extends BaseAdapter{

	  private Context context;
	  private LayoutInflater layoutInflater;
	  @SuppressWarnings({ "rawtypes", "unused" })
	  private ArrayList listData;
	  List<Movie> rowItem;
	  List<Movie> rowItems;
	  
	  static class ViewHolder{
		  
	    TextView details;
	    ImageView imageView;
	    TextView titlt;
	    TextView price;
	    TextView post_date;
	    
	  }
	  
	  @SuppressWarnings("rawtypes")
	  public CustomListAdapter(Context paramContext, ArrayList paramArrayList){
		  
	    this.listData = paramArrayList;
	    this.layoutInflater = LayoutInflater.from(paramContext);
	    
	  }
	  
	  public CustomListAdapter(Context paramContext, List<Movie> paramList){
		  
	    this.rowItem = paramList;
	    this.context = paramContext;
	    this.layoutInflater = LayoutInflater.from(paramContext);
	    
	  }
	 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.rowItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.rowItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint({ "InflateParams", "SimpleDateFormat" })
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewholder;
		
		if (convertView == null){
			
			convertView = this.layoutInflater.inflate(R.layout.plc_row, null);
			viewholder = new ViewHolder();
			viewholder.titlt = ((TextView)convertView.findViewById(R.id.PG_TITLE));
			viewholder.details = ((TextView)convertView.findViewById(R.id.PG_DETAILS));
			viewholder.imageView = ((ImageView)convertView.findViewById(R.id.PG_IMAGE));
			viewholder.price = (TextView)convertView.findViewById(R.id.price);
			viewholder.post_date = (TextView)convertView.findViewById(R.id.publish_date);
			convertView.setTag(viewholder);
	    }
		viewholder = (ViewHolder)convertView.getTag();
	
		for (int i = 0; i < rowItem.size(); i++){
			
		      Animation localAnimation = AnimationUtils.loadAnimation(this.context, R.anim.slide_top_to_bottom);
		      localAnimation.setDuration(500L);
		      convertView.startAnimation(localAnimation);
		      Movie localMovie = (Movie)this.rowItem.get(position);
		      viewholder.titlt.setText(localMovie.getTitle());
		      viewholder.details.setText(localMovie.getDetails());
		      viewholder.price.setText(localMovie.getPrice());
		      viewholder.post_date.setText(localMovie.getPostDate());
				
			
		      if (viewholder.imageView != null) {
		    	  
		        UrlImageViewHelper.setUrlDrawable(viewholder.imageView, localMovie.getThumbnailUrl(), R.drawable.placeholder);
		        
		      }
	
	    }
		return convertView;
	}
	public String elaspsedtime(Date startDate, Date endDate){
		
		//milliseconds
		long different = endDate.getTime() - startDate.getTime();
		
		long secondsInMilli = 1000;
		long minutesInMilli = secondsInMilli * 60;
		long hoursInMilli = minutesInMilli * 60;
		long daysInMilli = hoursInMilli * 24;
 
		long elapsedDays = different / daysInMilli;
		different = different % daysInMilli;
 
//		long elapsedHours = different / hoursInMilli;
//		different = different % hoursInMilli;
// 
//		long elapsedMinutes = different / minutesInMilli;
//		different = different % minutesInMilli;
// 
//		long elapsedSeconds = different / secondsInMilli;
		
		// String timettacker = "posted"+" "+elapsedDays+"days ago @"+" "+elapsedHours+":"+elapsedMinutes+":"+elapsedSeconds;
		 
		 String timettacker = "posted"+" "+elapsedDays+" "+"days ago @"+" "+new SimpleDateFormat("HH:mm").format(endDate.getTime());
		 
		 return timettacker;
		
	}

}
