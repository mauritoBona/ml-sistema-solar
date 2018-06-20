package com.ml.wp.utils;

import com.ml.wp.model.Coordinates;

public abstract class CoordinatesUtil {

	/**
	 * this method checks that the three coordinates are aligned, 
	 * checking that the difference between the ranges of 
	 * the coordinates1-2 and coordinates2-3 are equal
	 *
	 * @param c1 first coordinates
	 * @param c2 second coordinates
	 * @param c3 thirst coordinates
	 * @return true if the three coordinates are aligned - ((x2-x1)*(y3-y2))-((y2-y1)*(x3-x2)) == 0
	 */
	public static boolean areAligned(Coordinates c1, Coordinates c2, Coordinates c3) {
		double x1 = c1.getX();
		double y1 = c1.getY();
		double x2 = c2.getX();
		double y2 = c2.getY();
		double x3 = c3.getX();
		double y3 = c3.getY();
		return (((x2-x1)*(y3-y2))-((y2-y1)*(x3-x2))) == 0.00;
	}
	
	/**
	 * this method checks if a point (coordinate)
	 * is inside of the triangle, this have the Area of the triangle
	 * and the point form three triangles with the other coordinate, and get this three area
	 * and if the sum of this is equals to the triangle area, the point is inside
	 *
	 * @param c1 coordinate of the triangle
	 * @param c2 coordinate of the triangle
	 * @param c3 coordinate of the triangle
	 * @param x check if this coordinate is inside in the triangle
	 * @return true if the coordinate X is inside of the triangle
	 */
	public static boolean isCoordinateInsideTriangle(Coordinates c1, Coordinates c2, Coordinates c3, Coordinates x){
		return isInside(c1.getX(), c1.getY(), c2.getX(), c2.getY(), c3.getX(), c3.getY(), x.getX(), x.getY());
	}
	
	/**
	 * this method calculate the perimeter of the triangle -
	 * calculate the distance between the coordinates c1-c2 / c1-c3 / c2-c3
	 * and sum the three distances
	 *
	 * @param c1 coordinate
	 * @param c2 coordinate
	 * @param c3 coordinate
	 * @return the value of the perimeter
	 */
	public static Double getTrianglePerimeter(Coordinates c1, Coordinates c2, Coordinates c3) {
		Double c1c2 = getDistanceBetweenTwoCoordinates(c1, c2);
		Double c1c3 = getDistanceBetweenTwoCoordinates(c1, c3);
		Double c2c3 = getDistanceBetweenTwoCoordinates(c2, c3);
		return RoundUtil.getDoubleWith3Decimals(c1c2+c1c3+c2c3);
	}
	
	private static Double getDistanceBetweenTwoCoordinates(Coordinates c1, Coordinates c2) {
		return RoundUtil.getDoubleWith3Decimals(Math.sqrt(Math.pow((c2.getX()-c1.getX()),2)+Math.pow((c2.getY()-c1.getY()),2)));
	}
	
	private static double area(double x1, double y1, double x2, double y2, double x3, double y3) {
		return Math.abs((((x1 * y2) + (x2 * y3) + (x3 * y1)) - ((x1 * y3) + (x3 * y2) + (x2 * y1))) / 2.0);
	}
	
	private static boolean isInside(double x1, double y1, double x2, double y2, double x3, double y3, double x, double y) {
		double A = area(x1, y1, x2, y2, x3, y3);
		double A1 = area(x, y, x2, y2, x3, y3);
		double A2 = area(x1, y1, x, y, x3, y3);
		double A3 = area(x1, y1, x2, y2, x, y);
		return (A == A1 + A2 + A3);
	}
}
