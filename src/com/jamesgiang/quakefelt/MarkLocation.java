/**
 * QuakeFelt: A humanitarian project for Random hacks of Kindness (Melbourne)
 * MarkLocation.java
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

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MarkLocation extends ItemizedOverlay<OverlayItem> {

	private OverlayItem overlayItem;
	private List<OverlayItem> locations = new ArrayList<OverlayItem>();
	private Context mContext;
	
	public MarkLocation(Drawable defaultMarker, double lat, double lng, String venue, Context c) {
		
		super(boundCenterBottom(defaultMarker));
		mContext = c;
		Double geoLat = lat;
		Double geoLng = lng;
		GeoPoint location = new GeoPoint(geoLat.intValue(), geoLng.intValue());
		overlayItem = new OverlayItem(location, venue, "");
		locations.add(overlayItem);
		populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return locations.get(i);
	}
	
	@Override
	public int size() {
		return locations.size();	
	}
	
	@Override
	protected boolean onTap(int index) {
		if(locations.get(index).getTitle().compareTo("") != 0) {
			Toast.makeText(mContext, locations.get(index).getTitle(), Toast.LENGTH_SHORT).show();
		}
		return true;
	}
}