/**
 * QuakeFelt: A humanitarian project for Random hacks of Kindness (Melbourne)
 * MainList.java
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
import java.util.ArrayList;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jamesgiang.quakefelt.Adapters.ResponseAdapter;
import com.jamesgiang.quakefelt.Objects.Response;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainList extends ListActivity {
	private String quake_name;
	private String quake_description;
	private static final int MENU1 = Menu.FIRST;
	private static final int MENU2 = Menu.FIRST + 1;
	private TextView txtName;
	private TextView txtDescription;
	private Button btnChange;
	private ProgressDialog m_ProgressDialog = null; 
	private ArrayList<Response> m_array= null;
    private ResponseAdapter m_adapter;
    private Runnable viewQuakes;
    private Thread quakeThread;
    private Runnable returnRes = new Runnable() {
    	@Override
    	public void run() {
    		m_adapter.clear();
            setListAdapter(m_adapter);
    		if(m_array != null && m_array.size() > 0){
    			m_adapter.notifyDataSetChanged();
    			for(int i=0;i<m_array.size();i++)
    				m_adapter.add((Response) m_array.get(i));
    		}
    		m_adapter.notifyDataSetChanged();
    		m_ProgressDialog.dismiss();
    	}
    };
    
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlist);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClassName("com.jamesgiang.quakefelt", "com.jamesgiang.quakefelt.Launch");
				startActivity(i);
			}
		});
        txtName = (TextView) findViewById(R.id.txtName);
        txtDescription = (TextView) findViewById(R.id.txtDescription);
        btnChange = (Button) findViewById(R.id.btnChange);
        m_array = new ArrayList<Response>();
        m_adapter = new ResponseAdapter(this, R.layout.response_row, m_array);
        setListAdapter(m_adapter);
        try {
			quake_name = Utils.ReadSettings(this, "quake_name");
			txtName.setText(quake_name);
			quake_description = Utils.ReadSettings(this, "quake_description");
			txtDescription.setText(quake_description);
			getResponses(Integer.parseInt(Utils.ReadSettings(this, "quake_id")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
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
				Dialog selectView = new AlertDialog.Builder(MainList.this)
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
    private void getResponses(final int i) throws ClientProtocolException, IOException, JSONException {
    	m_ProgressDialog = new ProgressDialog(this);
    	m_ProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	m_ProgressDialog.setCancelable(false);
    	m_ProgressDialog.setMessage("Loading responses...");
    	m_ProgressDialog.show();
    	viewQuakes = new Runnable(){
            @Override
            public void run() {
            	m_array = new ArrayList<Response>();
                try {
                	JSONArray responses = API.getResponses(i);
            		JSONObject response;
            		Response objQuake;
            		try {
            			for(int i=0; i<responses.length();i++) {
            				objQuake = new Response();
            				response = new JSONObject(responses.getString(i)); 
            				objQuake.setID(response.getInt("id"));
            				objQuake.setReporter_name(response.getString("reporter_name"));
            				objQuake.setD_text(response.getString("d_text"));
            				objQuake.setLongitude(response.getString("longitude"));
            				objQuake.setLatitude(response.getString("latitude"));
            				m_array.add(objQuake);
            			}
                      } catch (Exception e) { 
                        //TODO
                      }
				} catch (Exception e) { 
					//TODO
                }
                runOnUiThread(returnRes);
            }
        };
        quakeThread =  new Thread(null, viewQuakes, "viewQuakes");
        quakeThread.start();
    }
}