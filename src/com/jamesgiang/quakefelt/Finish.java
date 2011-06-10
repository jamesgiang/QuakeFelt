/**
 * QuakeFelt: A humanitarian project for Random hacks of Kindness (Melbourne)
 * Finish.java
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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class Finish extends Activity {    
	private static final int MENU1 = Menu.FIRST;
	private TextView name;
	private TextView description;
	private Bundle b;
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
        setContentView(R.layout.finish);
        b = getIntent().getExtras();
        name = (TextView) findViewById(R.id.name);
        description = (TextView) findViewById(R.id.description);
        name.setText(b.getString("name"));
        description.setText(b.getString("story") + "\n\n" + b.getString("experience"));
        ImageView logo = (ImageView) findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClassName("com.jamesgiang.quakefelt", "com.jamesgiang.quakefelt.Launch");
				startActivity(i);
			}
		});
        ImageView back = (ImageView) findViewById(R.id.imgBack);
        back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClassName("com.jamesgiang.quakefelt", "com.jamesgiang.quakefelt.MainMap");
				startActivity(i);
			}
		});
        ImageView share = (ImageView) findViewById(R.id.imgShare);
        share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_SEND);
	    		i.setType("text/plain");
	    		String subject = null;
	    		String text = null;
	    		subject = "Share from QuakeFelt";
	    		text = "I just contributed my earthquake experience to #quakefelt. Did you feel it? Read about it and contribute at http://bit.ly/bjk34";
				i.putExtra(Intent.EXTRA_SUBJECT, subject);
	    		i.putExtra(Intent.EXTRA_TEXT, text);
	    		startActivity(Intent.createChooser(i, "Share your story"));
			}
		});
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        
    }
}