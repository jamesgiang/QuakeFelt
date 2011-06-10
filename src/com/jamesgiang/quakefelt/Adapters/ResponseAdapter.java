/**
 * QuakeFelt: A humanitarian project for Random hacks of Kindness (Melbourne)
 * ResponseAdapter.java
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
 
package com.jamesgiang.quakefelt.Adapters;

import java.util.ArrayList;

import com.jamesgiang.quakefelt.R;
import com.jamesgiang.quakefelt.Objects.Response;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ResponseAdapter extends ArrayAdapter<Response> {
	
	public ArrayList<Response> items;
	private Context mContext;
	
	public ResponseAdapter(Context context, int textViewResourceId, ArrayList<Response> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.mContext = context;
    }
	
	public void setItems(ArrayList<Response> items) {
		this.items = items;
	}
    
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		Response o = items.get(position);
		LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = vi.inflate(R.layout.response_row, null);   
        if (o != null) {
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView description = (TextView) v.findViewById(R.id.description);
            if(name != null){
            	name.setText(o.getReporter_name());
            }
            if(description != null){
            	description.setText(o.getD_text());
            }
        }
        return v;
    }	
}