/*
 * Copyright (C) 2013 Marcin Kunert
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
 */

package com.tilegenerator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

/**
 * 
 * @author Marcin Kunert
 * 
 */
public class Generator {

	public static final int TILE_WIDTH = 256;
	public static final int TILE_HEIGHT = 256;

	private String mapImageLocation;
	private String resizedMapImageLocation;
	private String resultDir;

	private double leftTopLat;
	private double leftTopLng;

	private double rightBottomLat;
	private double rightBottomLng;

	private int zoom;

	public void generateTiles() throws Exception {

		double szer = Math.abs(leftTopLng - rightBottomLng);
		double wys = Math.abs(leftTopLat - rightBottomLat);

		System.out.println("Dostarczona mapa ma rozmiary geograficzne: (" + szer + "," + wys + ")");
		BufferedImage image = ImageIO.read(new FileInputStream(new File(mapImageLocation)));
		System.out.println("Rozmiar mapy z pliku: (" + image.getWidth() + "px , " + image.getHeight() + "px)");

		double pxPerLng = image.getWidth() / szer;
		double pxPerLat = image.getHeight() / wys;

		double[] resizeValues = calculateMargins(leftTopLat, leftTopLng, rightBottomLat, rightBottomLng, zoom);

		double dodacLewo = resizeValues[0] * pxPerLng;
		double dodacGora = resizeValues[1] * pxPerLat;
		double dodacPrawo = resizeValues[2] * pxPerLng;
		double dodacDol = resizeValues[3] * pxPerLat;

		resizeImage(mapImageLocation, resizedMapImageLocation, dodacLewo, dodacGora, dodacPrawo, dodacDol);

		int[] tileCoords = getTileNumber(leftTopLat, leftTopLng, zoom);

		int size[] = getSizeInTiles(leftTopLat, leftTopLng, rightBottomLat, rightBottomLng, zoom);
		splitImage(resizedMapImageLocation, resultDir, size[1], size[0], zoom, tileCoords[0], tileCoords[1]);
	}

	private void resizeImage(String fileLocation, String resizedFileLocation, double left, double top, double right, double bottom) throws Exception {
		BufferedImage image = ImageIO.read(new FileInputStream(new File(fileLocation)));

		int finalWidth = (int) (image.getWidth() + left + right);
		int finalHeight = (int) (image.getHeight() + top + bottom);

		BufferedImage result = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_ARGB); // TYPE_4BYTE_ABGR

		Graphics2D gr = result.createGraphics();
		gr.setColor(Color.red);
		gr.setComposite(AlphaComposite.SrcOver);
		gr.drawImage(image, (int) left, (int) top, finalWidth, finalHeight, 0, 0, (int) (image.getWidth() + right), (int) (image.getHeight() + bottom), null);

		gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		ImageIO.write(result, "png", new File(resizedFileLocation));
		gr.dispose();
	}

	private double[] calculateMargins(double leftTopLat, double leftTopLng, double rightBottomLat, double rightBottomLng, int zoom) {

		int[] tileCoords = getTileNumber(leftTopLat, leftTopLng, zoom);
		LatLngBounds boundsLeftTop = boundsOfTile(tileCoords[0], tileCoords[1], zoom);

		LatLng lewoGora = new LatLng(boundsLeftTop.end.getLat(), boundsLeftTop.start.getLng());
		System.out.println(lewoGora);

		tileCoords = getTileNumber(rightBottomLat, rightBottomLng, zoom);
		LatLngBounds boundsRightBottom = boundsOfTile(tileCoords[0], tileCoords[1], zoom);

		LatLng prawoDol = new LatLng(boundsRightBottom.start.getLat(), boundsRightBottom.end.getLng());
		System.out.println(prawoDol);

		double topDiff = Math.abs(lewoGora.getLat() - leftTopLat);
		double leftDiff = Math.abs(lewoGora.getLng() - leftTopLng);

		double bottomDiff = Math.abs(prawoDol.getLat() - rightBottomLat);
		double rightDiff = Math.abs(prawoDol.getLng() - rightBottomLng);

		return new double[] { leftDiff, topDiff, rightDiff, bottomDiff };
	}

	private int[] getSizeInTiles(double leftTopLat, double leftTopLng, double rightBottomLat, double rightBottomLng, int zoom) {
		int[] startTileCoords = getTileNumber(leftTopLat, leftTopLng, zoom);
		int[] endTileCoords = getTileNumber(rightBottomLat, rightBottomLng, zoom);

		System.out.println("Width: " + (Math.abs(startTileCoords[0] - endTileCoords[0]) + 1) + " Height: "
				+ (Math.abs(startTileCoords[1] - endTileCoords[1]) + 1));
		return new int[] { Math.abs(startTileCoords[0] - endTileCoords[0]) + 1, Math.abs(startTileCoords[1] - endTileCoords[1]) + 1 };
	}

	private void splitImage(String imageName, String resultDir, int rows, int cols, int zoom, int startX, int startY) throws Exception {
		BufferedImage image = ImageIO.read(new FileInputStream(new File(imageName)));

		int chunkWidth = image.getWidth() / cols;
		int chunkHeight = image.getHeight() / rows;

		double scaleWidth = TILE_WIDTH / (double) chunkWidth;
		double scaleHeight = TILE_HEIGHT / (double) chunkHeight;

		BufferedImage tile;

		for (int x = 0; x < rows; x++) {
			int xNow = startX;
			for (int y = 0; y < cols; y++) {
				tile = new BufferedImage(TILE_WIDTH, TILE_HEIGHT, image.getType());

				Graphics2D gr = tile.createGraphics();
				gr.scale(scaleWidth, scaleHeight);
				gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight,
						null);

				gr.setComposite(AlphaComposite.Src);
				gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
				gr.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
				gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				ImageIO.write(tile, "png", new File(resultDir + "\\" + (xNow++) + "-" + (startY) + "-" + zoom + ".png"));
				gr.dispose();
			}
			startY++;
		}
	}

	public static int[] getTileNumber(final double lat, final double lon, final int zoom) {
		int[] result = new int[2];
		result[0] = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
		result[1] = (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1 << zoom));
		return result;
	}

	private LatLngBounds boundsOfTile(int x, int y, int zoom) {
		int noTiles = (1 << zoom);
		double longitudeSpan = 360.0 / noTiles;
		double longitudeMin = -180.0 + x * longitudeSpan;

		double mercatorMax = 180 - (((double) y) / noTiles) * 360;
		double mercatorMin = 180 - (((double) y + 1) / noTiles) * 360;
		double latitudeMax = toLatitude(mercatorMax);
		double latitudeMin = toLatitude(mercatorMin);

		LatLngBounds bounds = new LatLngBounds(new LatLng(latitudeMin, longitudeMin), new LatLng(latitudeMax, longitudeMin + longitudeSpan));
		return bounds;
	}

	public static double toLatitude(double mercator) {
		double radians = Math.atan(Math.exp(Math.toRadians(mercator)));
		return Math.toDegrees(2 * radians) - 90;
	}

	public String getMapImageLocation() {
		return mapImageLocation;
	}

	public void setMapImageLocation(String mapImageLocation) {
		this.mapImageLocation = mapImageLocation;
	}

	public String getResizedMapImageLocation() {
		return resizedMapImageLocation;
	}

	public void setResizedMapImageLocation(String resizedMapImageLocation) {
		this.resizedMapImageLocation = resizedMapImageLocation;
	}

	public String getResultDir() {
		return resultDir;
	}

	public void setResultDir(String resultDir) {
		this.resultDir = resultDir;
	}

	public double getLeftTopLat() {
		return leftTopLat;
	}

	public void setLeftTopLat(double leftTopLat) {
		this.leftTopLat = leftTopLat;
	}

	public double getLeftTopLng() {
		return leftTopLng;
	}

	public void setLeftTopLng(double leftTopLng) {
		this.leftTopLng = leftTopLng;
	}

	public double getRightBottomLat() {
		return rightBottomLat;
	}

	public void setRightBottomLat(double rightBottomLat) {
		this.rightBottomLat = rightBottomLat;
	}

	public double getRightBottomLng() {
		return rightBottomLng;
	}

	public void setRightBottomLng(double rightBottomLng) {
		this.rightBottomLng = rightBottomLng;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

}
