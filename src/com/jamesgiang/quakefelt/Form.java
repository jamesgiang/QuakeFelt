/**
 * QuakeFelt: A humanitarian project for Random hacks of Kindness (Melbourne)
 * Form.java
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class Form extends Activity {    
	private static final int MENU1 = Menu.FIRST;
	private String the_story = "";
	private String temp1 = "";
	private String temp2 = "";
	private String temp3 = "";
	private String temp4 = "";
	private String temp5 = "";
	private String temp6 = "";
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);
        
        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClassName("com.jamesgiang.quakefelt", "com.jamesgiang.quakefelt.Launch");
				startActivity(i);
			}
		});
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
        
        final EditText txtName = (EditText) findViewById(R.id.txtName);
        final EditText txtStory = (EditText) findViewById(R.id.txtStory);
        Spinner s1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<?> adapter1 = ArrayAdapter.createFromResource(this, R.array.other_felt, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s1.setAdapter(adapter1);
        
        
        
        Spinner s2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<?> adapter2 = ArrayAdapter.createFromResource(this, R.array.asleep, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s2.setAdapter(adapter2);
        
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                temp1 = temp1 + item.toString().toLowerCase();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            	
            }
        });
        
        Spinner s3 = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<?> adapter3 = ArrayAdapter.createFromResource(this, R.array.situation, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s3.setAdapter(adapter3);
        final EditText txtOther1 = (EditText) findViewById(R.id.txtOther1);
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                if(pos ==0 || pos == 1) {
                	temp2 = temp2 + " during an earthquake while " + item.toString().toLowerCase();
                } else if(pos == 2 || pos == 3) {
                	temp2 = temp2 + " during an earthquake while in a " + item.toString().toLowerCase();
                } else {
                	temp2 = temp2 + " during an earthquake while " + txtOther1.getText().toString().toLowerCase();
                }
                
            }
            public void onNothingSelected(AdapterView<?> parent) {
            	
            }
        });
        
        Spinner s4 = (Spinner) findViewById(R.id.spinner4);
        ArrayAdapter<?> adapter4 = ArrayAdapter.createFromResource(this, R.array.motion, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s4.setAdapter(adapter4);
        
        s4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                try {
                	temp3 = temp3 + ". In Melbourne at 20km from the epicentre " + Utils.ReadSettings(Form.this, "quake_name") + ". The shaking was " + item.toString().toLowerCase() + " and lasted for ";
				} catch (IOException e) {
					temp3 = temp3 + ". In Melbourne at 20km from the epicentre. The shaking was " + item.toString().toLowerCase() + " and lasted for ";
				}
            }
            public void onNothingSelected(AdapterView<?> parent) {
            	
            }
        });
        
        Spinner s7 = (Spinner) findViewById(R.id.spinner7);
        ArrayAdapter<?> adapter7 = ArrayAdapter.createFromResource(this, R.array.response, android.R.layout.simple_spinner_item);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s7.setAdapter(adapter7);
        
        s7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                temp4 = temp4 + " and felt " + item.toString().toLowerCase() + ".";
            }
            public void onNothingSelected(AdapterView<?> parent) {
            	
            }
        });
        
        Spinner s6 = (Spinner) findViewById(R.id.spinner6);
        ArrayAdapter<?> adapter6 = ArrayAdapter.createFromResource(this, R.array.reaction, android.R.layout.simple_spinner_item);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s6.setAdapter(adapter6);
        s6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                temp5 = temp5 + " I " + item.toString().toLowerCase();
            }
            public void onNothingSelected(AdapterView<?> parent) {
            	
            }
        });
        
        Spinner s5 = (Spinner) findViewById(R.id.spinner5);
        ArrayAdapter<?> adapter5 = ArrayAdapter.createFromResource(this, R.array.duration, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s5.setAdapter(adapter5);
        s5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                temp6 = temp6 + item.toString().toLowerCase() + ".";
            }
            public void onNothingSelected(AdapterView<?> parent) {
            	
            }
        });
        Spinner s8 = (Spinner) findViewById(R.id.spinner8);
        ArrayAdapter<?> adapter8 = ArrayAdapter.createFromResource(this, R.array.sway, android.R.layout.simple_spinner_item);
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s8.setAdapter(adapter8);
        
        
        Spinner s9 = (Spinner) findViewById(R.id.spinner9);
        ArrayAdapter<?> adapter9 = ArrayAdapter.createFromResource(this, R.array.creak, android.R.layout.simple_spinner_item);
        adapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s9.setAdapter(adapter9);
        
        Spinner s10 = (Spinner) findViewById(R.id.spinner10);
        ArrayAdapter<?> adapter10 = ArrayAdapter.createFromResource(this, R.array.shelf, android.R.layout.simple_spinner_item);
        adapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s10.setAdapter(adapter10);
        
        Spinner s11 = (Spinner) findViewById(R.id.spinner11);
        ArrayAdapter<?> adapter11 = ArrayAdapter.createFromResource(this, R.array.picture, android.R.layout.simple_spinner_item);
        adapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s11.setAdapter(adapter11);
        
        Spinner s12 = (Spinner) findViewById(R.id.spinner12);
        ArrayAdapter<?> adapter12 = ArrayAdapter.createFromResource(this, R.array.heavy_appliance, android.R.layout.simple_spinner_item);
        adapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s12.setAdapter(adapter12);
        
        Spinner s13 = (Spinner) findViewById(R.id.spinner13);
        ArrayAdapter<?> adapter13 = ArrayAdapter.createFromResource(this, R.array.walls, android.R.layout.simple_spinner_item);
        adapter13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s13.setAdapter(adapter13);
        the_story = the_story + "On Monday I was " + temp1 + temp2 + temp3 + temp4 + temp5 + temp6;
        ImageView btnSubmit = (ImageView) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				Bundle b = new Bundle();
				if(txtName.getText().toString().equals("")) {
					b.putString("name", "Anonymous");
				} else {
					b.putString("name", txtName.getText().toString());
				}
				b.putString("story", "On Monday I felt a quake while inside. In Melbourne at 20km from the epicentre. The shaking was violent and I felt frightened. I hid under a table during the 30 seconds of shaking.");
				if(txtStory.getText().toString().length() > 0) {
					b.putString("experience", "Personal Note:\n\"" + txtStory.getText().toString() + "\"");
				} else {
					b.putString("experience", "");
				}
				i.setClassName("com.jamesgiang.quakefelt", "com.jamesgiang.quakefelt.Finish");
				i.putExtras(b);
				startActivity(i);
				finish();
			}
		});
    }
}