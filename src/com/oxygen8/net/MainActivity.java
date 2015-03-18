package com.oxygen8.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.oxygen8.net.AndroidMultiPartEntity.ProgressListener;
import com.oxygen8.net.materialmenu.MaterialMenuDrawable;
import com.oxygen8.net.materialmenu.MaterialMenuDrawable.IconState;
import com.oxygen8.net.materialmenu.MaterialMenuDrawable.Stroke;
import com.oxygen8.net.materialmenu.MaterialMenuIconSherlock;

import eu.janmuller.android.simplecropimage.CropImage;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends SherlockActivity {
	
   Spinner select_category;
   EditText publish_text_description,publish_text_price,publish_text_title,
   publish_text_contact_name,publish_text_contact_email,publish_text_contact_phone,publish_text_contact_location;
   ProgressBar progressBar;
   TextView txtPercentage;
   Button publish_post;
   ImageView user_photo;
   long totalSize = 0;
   private SlidingMenu slidingMenu ;
   ActionBar actionbar;
   MaterialMenuIconSherlock materialMenu;
   int actionBarMenuState;

   public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
   private static final int PICK_FROM_CAMERA = 1;
   private static final int CROP_FROM_CAMERA = 2;
   private static final int PICK_FROM_FILE = 3;
   
   private File  mFileTemp;
   
   public static final String TAG = "MainActivity";
   
   //private String filePath = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish);
		
//		FadingActionBarHelper helper = new FadingActionBarHelper()
//        .actionBarBackground(R.drawable.ab_background_light)
//        .headerLayout(R.layout.header_light)
//        .contentLayout(R.layout.publish)
//        .lightActionBar(true);
//		setContentView(helper.createView(this));
//		helper.initActionBar(this);
		
		AdView mAdView = (AdView) findViewById(R.id.publish_page_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

		actionbar = getSupportActionBar();
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
        
        String state = Environment.getExternalStorageState();
        
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        
    	if (Environment.MEDIA_MOUNTED.equals(state)) {
    		
    		mFileTemp = new File(Environment.getExternalStorageDirectory()+ File.separator + "IMG_" + timeStamp + ".jpg");
    		
    	}else {
    		
    		mFileTemp = new File(getFilesDir()+ File.separator + "IMG_" + timeStamp + ".jpg");
    	}
		
		select_category = (Spinner)findViewById(R.id.publish_category_spinner);
		publish_text_description = (EditText)findViewById(R.id.publish_text_description);
		publish_text_price = (EditText)findViewById(R.id.publish_text_price);
		publish_text_title = (EditText)findViewById(R.id.publish_text_title);
		publish_text_contact_name = (EditText)findViewById(R.id.publish_text_contact_name);
		publish_text_contact_email = (EditText)findViewById(R.id.publish_text_contact_email);
		publish_text_contact_phone = (EditText)findViewById(R.id.publish_text_contact_phone);
		publish_text_contact_location = (EditText)findViewById(R.id.publish_text_contact_location);
		progressBar = (ProgressBar)findViewById(R.id.progressBar);
		txtPercentage = (TextView)findViewById(R.id.txtPercentage);
		publish_post = (Button)findViewById(R.id.publish_post);
		user_photo = (ImageView)findViewById(R.id.publish_image);
		LinearLayout photo_sec = (LinearLayout)findViewById(R.id.publish_photos);
		
		publish_post.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new UploadFileToServer().execute();
			}
		});
		
		photo_sec.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Image_Picker_Dialog();
			}
		});
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
	private void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
        	
        	Uri mImageCaptureUri = null;
        	String state = Environment.getExternalStorageState();
        	
        	if (Environment.MEDIA_MOUNTED.equals(state)) {
        		
        		mImageCaptureUri = Uri.fromFile(mFileTemp);
        		
        	}else{
	        	
	        	mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
        	}	
        	
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, PICK_FROM_CAMERA);
            
        } catch (ActivityNotFoundException e) {

            Log.d(TAG, "cannot take picture", e);
        }
    }

    private void openGallery() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, PICK_FROM_FILE);
    }
    
    private void startCropImage() {

        Intent intent = new Intent(this, CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, true);

        intent.putExtra(CropImage.ASPECT_X, 3);
        intent.putExtra(CropImage.ASPECT_Y, 2);

        startActivityForResult(intent, CROP_FROM_CAMERA);
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
		//MaterialMenuDrawable mm;
		
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

	public void Image_Picker_Dialog(){
		
		AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(this);
		myAlertDialog.setTitle("Pictures Selection Options");
		myAlertDialog.setMessage("Select Picture From ...");
	
		myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener(){
			
			public void onClick(DialogInterface arg0, int arg1){
	
				openGallery();
				
			}
	
		});

		myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener(){
			
			public void onClick(DialogInterface arg0, int arg1){
				
				takePicture();

			}
			
		});
		
		myAlertDialog.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		
		if (resultCode != RESULT_OK) return;
		Bitmap bitmap;
		
	    switch (requestCode) {
	    
		    case PICK_FROM_CAMERA:

		    	startCropImage();
		    	
		    	break;
		    	
		    case PICK_FROM_FILE: 
	
		    	try {

	                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
	                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
	                    copyStream(inputStream, fileOutputStream);
	                    fileOutputStream.close();
	                    inputStream.close();

	                    startCropImage();

                } catch (Exception e) {

                    Log.e(TAG, "Error while creating temp file", e);
                }
	    
		    	break;	    	
	    
		    case CROP_FROM_CAMERA:	
		    	
		    	String path = data.getStringExtra(CropImage.IMAGE_PATH);
//		    	
//		    	UploadModel model = new UploadModel();
//		    	model.setFilepath(path);
		    	
                if (path == null) {

                    return;
                }

                bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
                user_photo.setImageBitmap(bitmap);

		        break;

	    	}

		}
	
		public static void copyStream(InputStream input, OutputStream output)
	            throws IOException {
	
	        byte[] buffer = new byte[1024];
	        int bytesRead;
	        while ((bytesRead = input.read(buffer)) != -1) {
	            output.write(buffer, 0, bytesRead);
	        }
	    }

	    private class UploadFileToServer extends AsyncTask<Void, Integer, String>{

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}

			@Override
			protected void onProgressUpdate(Integer... progress) {
				// TODO Auto-generated method stub
				// Making progress bar visible
				progressBar.setVisibility(View.VISIBLE);
	
				// updating progress bar value
				progressBar.setProgress(progress[0]);
	
				// updating percentage value
				txtPercentage.setText(String.valueOf(progress[0]) + "%");
			}

		@SuppressWarnings("deprecation")
		private String uploadFile() {
			
			String responseString = null;

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);

			try {
				
				AndroidMultiPartEntity entity = new AndroidMultiPartEntity(new ProgressListener() {

							@Override
							public void transferred(long num) {
								
								publishProgress((int) ((num / (float) totalSize) * 100));
								
							}
						});

				File sourceFile = new File(mFileTemp.getPath());
				
				String email = publish_text_contact_email.getText().toString();
			    String location = publish_text_contact_location.getText().toString();
                String contact_name = publish_text_contact_name.getText().toString();
                String contact_phone = publish_text_contact_phone.getText().toString();
                String description = publish_text_description.getText().toString();
                String price = publish_text_price.getText().toString();
                String title = publish_text_title.getText().toString();
                String category = String.valueOf(select_category.getSelectedItem().toString());
				
				entity.addPart("image", new FileBody(sourceFile));
                // Extra parameters if you want to pass to server
				entity.addPart("location",new StringBody(location));
				entity.addPart("category", new StringBody(category));
				entity.addPart("email", new StringBody(email));
				entity.addPart("contact_name",new StringBody(contact_name));
				entity.addPart("contact_phone", new StringBody(contact_phone));
				entity.addPart("description", new StringBody(description));
				entity.addPart("price", new StringBody(price));
				entity.addPart("title", new StringBody(title));

				totalSize = entity.getContentLength();
				httppost.setEntity(entity);

				// Making server call
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity r_entity = response.getEntity();

				int statusCode = response.getStatusLine().getStatusCode();
				
				if (statusCode == 200) {
					// Server response
					responseString = EntityUtils.toString(r_entity);
					
				} else {
					
					responseString = "Error occurred! Http Status Code: "+ statusCode;
				}

			} catch (ClientProtocolException e) {
				
				responseString = e.toString();
				
			} catch (IOException e) {
				
				responseString = e.toString();
			}

			return responseString;

		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			return uploadFile();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			Intent in = new Intent(MainActivity.this,HomeActivity.class);
			startActivity(in);
			
			}
	
	 }
  
}
