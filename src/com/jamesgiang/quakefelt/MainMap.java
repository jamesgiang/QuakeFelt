/**
 * QuakeFelt: A humanitarian project for Random hacks of Kindness (Melbourne)
 * MainMap.java
 *
 * @author James Giang
 *
 * Copyright 2011 James Giang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
 
package com.jamesgiang.quakefelt;

import java.io.IOException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMap extends MapActivity {
	private static final int MENU1 = Menu.FIRST;
	private static final int MENU2 = Menu.FIRST + 1;
	private MapView mapView;
	private MapController mapController;
	private int quake_id;
	private String quake_name;
	private String quake_description;
	private TextView txtName;
	private TextView txtDescription;
	private Button btnChange;
	private ProgressDialog m_ProgressDialog = null; 
    private TextView alertmessage;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmap);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClassName("com.jamesgiang.quakefelt", "com.jamesgiang.quakefelt.Launch");
				startActivity(i);
			}
		});
        mapView = (MapView) findViewById(R.id.mapview);
      //setup map
        mapView.setBuiltInZoomControls(true);
        mapController = mapView.getController();
        txtName = (TextView) findViewById(R.id.txtName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        btnChange = (Button) findViewById(R.id.btnChange);
        alertmessage = (TextView) findViewById(R.id.alertmessage);
        try {
			quake_id = Integer.parseInt(Utils.ReadSettings(this, "quake_id"));
			quake_name = Utils.ReadSettings(this, "quake_name");
			txtName.setText(quake_name);
			quake_description = Utils.ReadSettings(this, "quake_description");
			txtDescription.setText(quake_description);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		btnChange.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClassName("com.jamesgiang.quakefelt", "com.jamesgiang.quakefelt.QuakeSelect");
				startActivity(i);
			}
		});
		ImageView imgReport = (ImageView) findViewById(R.id.imgReport);
		imgReport.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClassName("com.jamesgiang.quakefelt", "com.jamesgiang.quakefelt.Form");
				startActivity(i);
			}
		});
		//extracting bundle data
        Double geoLat = null;
        Double geoLng = null;
		try {
			geoLat = Double.parseDouble(Utils.ReadSettings(this, "quake_latitude"))*1E6;
			geoLng = Double.parseDouble(Utils.ReadSettings(this, "quake_longitude"))*1E6;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	
    	//continue setting up map
    	GeoPoint point = new GeoPoint(geoLat.intValue(), geoLng.intValue());
    	mapController.animateTo(point);
    	mapController.setZoom(2);
    	
    	//marking locations onto map
    	Drawable marker;
    	marker = this.getResources().getDrawable(R.drawable.marker);
		try {
			mapView.getOverlays().add(new MarkLocation(marker, geoLat, geoLng, Utils.ReadSettings(MainMap.this, "quake_name"), getBaseContext()));
		} catch (IOException e) {
			mapView.getOverlays().add(new MarkLocation(marker, geoLat, geoLng, "", getBaseContext()));
		}
		new loadData().execute(Integer.toString(quake_id));	
    }
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
	        menu.add(0, MENU1, 0, "Switch View");
	        menu.add(0, MENU2, 0, "About");
	        return true;
	    }
	    
	    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
		        case MENU1:
		        	CharSequence[] options = {"Map View", "List View"};
					Dialog selectView = new AlertDialog.Builder(MainMap.this)
						.setTitle("Switch views")
						.setItems(options, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								if(which==0) {
									Intent i = new Intent();
									i.setClassName("com.jamesgiang.quakefelt", "com.jamesgiang.quakefelt.MainMap");
									startActivity(i);
								} else if(which==1) {
									Intent i = new Intent();
									i.setClassName("com.jamesgiang.quakefelt", "com.jamesgiang.quakefelt.MainList");
									startActivity(i);
								}
							}
						})
						.setNegativeButton("Close", null)
						.create();
					selectView.show();
					return true;
		        case MENU2:
		        	Utils.About(this);
		        	return true;
	    	}
	        return false;
	    }
	    private class loadData extends AsyncTask<String, Void, String> {
	    	protected void onPreExecute() {
				m_ProgressDialog = ProgressDialog.show(MainMap.this, "", "Augmenting response data...", true);
			}
	    	
			protected String doInBackground(String... params) {
				String results = null;
				try {
					JSONArray responses;
					responses = API.getResponses(Integer.parseInt(params[0]));
					results = Integer.toString(responses.length());
        		JSONObject response;
        		Double geoLat;
        		Double geoLng;
        		for(int i=0; i<responses.length();i++) {
    				response = new JSONObject(responses.getString(i)); 
    				Log.d("test", responses.toString());
    				Log.d("test", response.toString());
    				geoLat = Double.parseDouble(response.getString("longitude"))*1E6;
    				geoLng = Double.parseDouble(response.getString("latitude"))*1E6;
    				Drawable marker = null;
    				String real_mmi = response.getString("mmi").substring(0, response.getString("mmi").indexOf("."));
    				Log.d("test", real_mmi);
    				switch(Integer.parseInt(real_mmi)) {
    				case 1:
    					marker = MainMap.this.getResources().getDrawable(R.drawable.mmi1);
    					break;
    				case 2:
    					marker = MainMap.this.getResources().getDrawable(R.drawable.mmi2);
    					break;
    				case 3:
        				marker = MainMap.this.getResources().getDrawable(R.drawable.mmi3);
        				break;
    				case 4:
        				marker = MainMap.this.getResources().getDrawable(R.drawable.mmi4);
        				break;
    				case 5:
    					marker = MainMap.this.getResources().getDrawable(R.drawable.mmi5);
    					break;
    				default:
    					marker = MainMap.this.getResources().getDrawable(R.drawable.marker);
    				}
    				mapView.getOverlays().add(new MarkLocation(marker, geoLat, geoLng, response.getString("d_text"), getBaseContext()));
			}
			}
        		 catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return results;
			}
			protected void onPostExecute(String result) {
				if(Integer.parseInt(result) == 0) {
					alertmessage.setText("No reports have been contributed");
        			//Toast.makeText(getBaseContext(), "No reports have been contributed", Toast.LENGTH_SHORT).show();
        		} else {
        			alertmessage.setText(result + " reports have been contributed");
        			//Toast.makeText(getBaseContext(), result + " reports have been contributed", Toast.LENGTH_SHORT).show();
        		}
				m_ProgressDialog.dismiss();
			}	
	    	
	    }
}