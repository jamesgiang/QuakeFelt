/**
 * QuakeFelt: A humanitarian project for Random hacks of Kindness (Melbourne)
 * QuakeSelect.java
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

import com.jamesgiang.quakefelt.Adapters.QuakeAdapter;
import com.jamesgiang.quakefelt.Objects.Quake;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class QuakeSelect extends ListActivity {
	Button btnBegin;
	private ArrayList<Quake> m_array= null;
	private ProgressDialog m_ProgressDialog = null; 
    private QuakeAdapter m_adapter;
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
    				m_adapter.add((Quake) m_array.get(i));
    		}
    		m_adapter.notifyDataSetChanged();
    		m_ProgressDialog.dismiss();
    	}
    	
    };
    private static final int MENU1 = Menu.FIRST;

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU1, 0, "About");
        return true;
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
	        case MENU1:
	        	Utils.About(this);
	        	return true;
    	}
        return false;
    }
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quakeselect);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClassName("com.jamesgiang.quakefelt", "com.jamesgiang.quakefelt.Launch");
				startActivity(i);
			}
		});
        m_array = new ArrayList<Quake>();
        m_adapter = new QuakeAdapter(this, R.layout.quake_row, m_array);
        setListAdapter(m_adapter);
        ListView lv = getListView();
        lv.setOnItemClickListener(new OnItemClickListener() {
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        		Quake o = m_adapter.items.get(position);
        		try {
					Utils.WriteSettings(QuakeSelect.this, Integer.toString(o.getID()), "quake_id");
					Utils.WriteSettings(QuakeSelect.this, o.getName(), "quake_name");
					Utils.WriteSettings(QuakeSelect.this, o.getDescription(), "quake_description");
				} catch (IOException e) {
					e.printStackTrace();
				}
				Intent i = new Intent();
				i.setClassName("com.jamesgiang.quakefelt", "com.jamesgiang.quakefelt.MainMap");
				startActivity(i);
        	}
        });
        try {
			getQuakes();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    
    private void getQuakes() throws ClientProtocolException, IOException, JSONException {
    	m_ProgressDialog = new ProgressDialog(this);
    	m_ProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	m_ProgressDialog.setCancelable(false);
    	m_ProgressDialog.setMessage("Loading quake list...");
    	m_ProgressDialog.show();
    	viewQuakes = new Runnable(){
            @Override
            public void run() {
            	m_array = new ArrayList<Quake>();
                try {
                	JSONArray quakes = API.getTenQuakes();
            		JSONObject quake;
            		Quake objQuake;
            		try {
            			for(int i=0; i<quakes.length();i++) {
            				objQuake = new Quake();
            				quake = new JSONObject(quakes.getString(i)); 
            				objQuake.setID(quake.getInt("id"));
            				objQuake.setName(quake.getString("description"));
            				objQuake.setDescription(quake.getString("created_at"));
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