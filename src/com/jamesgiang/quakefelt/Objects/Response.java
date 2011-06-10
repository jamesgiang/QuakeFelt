/**
 * QuakeFelt: A humanitarian project for Random hacks of Kindness (Melbourne)
 * Response.java
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
 
package com.jamesgiang.quakefelt.Objects;

public class Response {
	private int ID;
	private String reporter_name;
	private String situation;
	private String building;
	private String asleep;
	private String felt;
	private String other_felt;
	private String motion;
	private String duration;
	private String reaction;
	private String response;
	private String stand;
	private String sway;
	private String creak;
	private String shelf;
	private String picture;
	private String furniture;
	private String heavy_appliance;
	private String walls;
	private String d_text;
	private String mmi;
	private String distance_from_epicentre;
	private String created_at;
	private String latitude;
	private String longitude;
	
	public void setID(int iD) {
		ID = iD;
	}
	public int getID() {
		return ID;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setReporter_name(String reporter_name) {
		this.reporter_name = reporter_name;
	}
	public String getReporter_name() {
		return reporter_name;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getSituation() {
		return situation;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getBuilding() {
		return building;
	}
	public void setAsleep(String asleep) {
		this.asleep = asleep;
	}
	public String getAsleep() {
		return asleep;
	}
	public void setFelt(String felt) {
		this.felt = felt;
	}
	public String getFelt() {
		return felt;
	}
	public void setOther_felt(String other_felt) {
		this.other_felt = other_felt;
	}
	public String getOther_felt() {
		return other_felt;
	}
	public void setMotion(String motion) {
		this.motion = motion;
	}
	public String getMotion() {
		return motion;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDuration() {
		return duration;
	}
	public void setReaction(String reaction) {
		this.reaction = reaction;
	}
	public String getReaction() {
		return reaction;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getResponse() {
		return response;
	}
	public void setStand(String stand) {
		this.stand = stand;
	}
	public String getStand() {
		return stand;
	}
	public void setSway(String sway) {
		this.sway = sway;
	}
	public String getSway() {
		return sway;
	}
	public void setCreak(String creak) {
		this.creak = creak;
	}
	public String getCreak() {
		return creak;
	}
	public void setShelf(String shelf) {
		this.shelf = shelf;
	}
	public String getShelf() {
		return shelf;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getPicture() {
		return picture;
	}
	public void setFurniture(String furniture) {
		this.furniture = furniture;
	}
	public String getFurniture() {
		return furniture;
	}
	public void setHeavy_appliance(String heavy_appliance) {
		this.heavy_appliance = heavy_appliance;
	}
	public String getHeavy_appliance() {
		return heavy_appliance;
	}
	public void setWalls(String walls) {
		this.walls = walls;
	}
	public String getWalls() {
		return walls;
	}
	public void setMmi(String mmi) {
		this.mmi = mmi;
	}
	public String getMmi() {
		return mmi;
	}
	public void setDistance_from_epicentre(String distance_from_epicentre) {
		this.distance_from_epicentre = distance_from_epicentre;
	}
	public String getDistance_from_epicentre() {
		return distance_from_epicentre;
	}
	public void setD_text(String d_text) {
		this.d_text = d_text;
	}
	public String getD_text() {
		return d_text;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getCreated_at() {
		return created_at;
	}
}