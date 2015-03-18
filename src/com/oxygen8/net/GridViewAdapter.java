package com.oxygen8.net;

import java.util.ArrayList;
import java.util.List;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter{

	  private LayoutInflater layoutInflater;
	  @SuppressWarnings("rawtypes")
	  private ArrayList listData;
	  List<Movie> rowItem;
	  List<Movie> rowItems;
	  
	  static class ViewHolder{
	    TextView details;
	    ImageView imageView;
	    TextView titlt;
	    TextView price;
	  }
	  
	  @SuppressWarnings("rawtypes")
	public GridViewAdapter(Context paramContext, ArrayList paramArrayList){
		  
	    this.listData = paramArrayList;
	    this.layoutInflater = LayoutInflater.from(paramContext);
	  }
	  
	  public GridViewAdapter(Context paramContext, List<Movie> paramList){
		  
	    this.rowItem = paramList;
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 ViewHolder localViewHolder;
		 
		    if (convertView == null){
		    	
		      convertView = this.layoutInflater.inflate(R.layout.grid_item, null);
		      localViewHolder = new ViewHolder();
		      localViewHolder.titlt = ((TextView)convertView.findViewById(R.id.projectName));
		      localViewHolder.details = ((TextView)convertView.findViewById(R.id.companyName));
		      localViewHolder.imageView = ((ImageView)convertView.findViewById(R.id.listicon));
		      localViewHolder.price = (TextView)convertView.findViewById(R.id.Name);
		      convertView.setTag(localViewHolder);
		      
		    }
		    
		    localViewHolder = (ViewHolder)convertView.getTag();
		    
		    for(int i=0; i < rowItem.size(); i++){
		    	
		    	Movie localMovie = (Movie)this.rowItem.get(position);
		        localViewHolder.titlt.setText(localMovie.getTitle());
		        localViewHolder.details.setText(localMovie.getDetails());
		        localViewHolder.price.setText(localMovie.getPrice());
		        
		        if (localViewHolder.imageView != null) {
		        	
		          UrlImageViewHelper.setUrlDrawable(localViewHolder.imageView, localMovie.getThumbnailUrl(), R.drawable.placeholder);
		          
		        }

		    }
		    
		return convertView;
	}

}
