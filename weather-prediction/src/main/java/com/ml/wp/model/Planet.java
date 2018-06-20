package com.ml.wp.model;

import com.ml.wp.utils.RoundUtil;

public class Planet {

	private static final double TWO_PI = 360.00;
	
	private String civilization;
	private Double radious;
	private Double angularSpeed;
	private Coordinates coordinates;
	private Double angle;
	
	public Planet(String civilization, Double radious, Double angularSpeed, Double angle, Double positionX, Double positionY) {
		super();
		this.civilization = civilization;
		this.radious = radious;
		this.angularSpeed = angularSpeed;
		this.coordinates = new Coordinates(positionX, positionY);
		this.angle = angle;
	}

	public void simulatedOneday() {
		Double newAngle = this.getAngle() + this.getAngularSpeed();
		if (newAngle>0 && newAngle>=TWO_PI) newAngle-=TWO_PI;
		if (newAngle<0 && newAngle<=-TWO_PI) newAngle+=TWO_PI;
		Double newPositionX = RoundUtil.getDoubleWith3Decimals(this.getRadious() * Math.cos(Math.toRadians(newAngle)));
		Double newPositionY = RoundUtil.getDoubleWith3Decimals(this.getRadious() * Math.sin(Math.toRadians(newAngle)));
		this.updateAngleAndCoordinates(newAngle, newPositionX, newPositionY);
	}
	
	private void updateAngleAndCoordinates(Double angle, Double positionX, Double positionY) {
		this.setAngle(angle);
		this.coordinates.setX(positionX);
		this.coordinates.setY(positionY);
	}
	
	public String getCivilization() {
		return civilization;
	}
	
	public void setCivilization(String civilization) {
		this.civilization = civilization;
	}
	
	public Double getRadious() {
		return radious;
	}
	
	public void setRadious(Double radious) {
		this.radious = radious;
	}
	
	public Double getAngularSpeed() {
		return angularSpeed;
	}
	
	public void setAngularSpeed(Double angularSpeed) {
		this.angularSpeed = angularSpeed;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	public Double getAngle() {
		return angle;
	}
	
	public void setAngle(Double angle) {
		this.angle = angle;
	}
	
	public Double getPositionX() {
		return this.coordinates.getX();
	}
	
	public Double getPositionY() {
		return this.coordinates.getY();
	}
}
