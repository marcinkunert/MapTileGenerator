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

/**
 * 
 * @author Marcin Kunert
 * 
 */
public class Main {

	public static void main(String[] args) throws Exception {

		long start = System.currentTimeMillis();

		/**
		 * Przykładowe wartości wejściowe
		 */

		String fileLocation = "C:\\Users\\Marcin\\Desktop\\mapy przerobione\\kosakowo2.png";
		String resizedFileLocation = "C:\\Users\\Marcin\\Desktop\\tilegenerator\\source-resized.png";
		// String resultDir =
		// "C:\\Users\\Marcin\\Desktop\\tilegenerator\\result";
		String resultDir = "C:\\Users\\Marcin\\Desktop\\mapy przerobione\\kosakowo_result";
		//
		// double leftTopLat = 51.214561;
		// double leftTopLng = 16.184033;
		//
		// double rightBottomLat = 51.209303;
		// double rightBottomLng = 16.198026;

		/*
		 * Kosakowo
		 * 
		 * gora 54.512674 lewo 18.511425 prawo 18.52183 dol 54.505486
		 */
		//
		// gora: 54.60076
		// lewo 18.476852
		// prawo: ,18.482834
		// dol: 54.595514

		double topLat = 54.60076;
		double leftLng = 18.476852;

		double bottomLat = 54.595514;
		double rightLng = 18.482834;

		// gora 54.512674
		// lewo 18.511425
		// prawo 18.52183
		// dol 54.505486

		// witomski
		// double leftTopLat = 54.512674;
		// double leftTopLng = 18.511425;
		//
		// double rightBottomLat = 54.505486;
		// double rightBottomLng = 18.52183;

		int zoom = 21;

		// if(args.length != 7) {
		// System.out.println("Kolejność parametrów: <mapa_zrodlowa> <folder_docelowy> <zoom> <punkt_polozony_maksymalnie_na_polnoc> <punkt_polozony_maksymalnie_na_zachod> <punkt_polozony_maksymalnie_na_poludnie> <punkt_polozony_maksymalnie_na_wschod>");
		// return;
		// } else {
		// try {
		// fileLocation = args[0];
		// resizedFileLocation = fileLocation.substring(0,
		// fileLocation.length()-4) + "_resized" +
		// fileLocation.substring(fileLocation.length()-4,
		// fileLocation.length());
		// resultDir = args[1];
		// zoom = Integer.parseInt(args[2]);
		//
		// leftTopLat = Double.parseDouble(args[3]);
		// leftTopLng = Double.parseDouble(args[4]);
		//
		// rightBottomLat = Double.parseDouble(args[5]);
		// rightBottomLng = Double.parseDouble(args[6]);
		// } catch(Exception e) {
		// System.out.println("Niepoprawne dane wejściowe. "+e.getMessage());
		// }
		// }

		Generator generator = new Generator();

		generator.setMapImageLocation(fileLocation);
		generator.setResizedMapImageLocation(resizedFileLocation);
		generator.setResultDir(resultDir);
		generator.setLeftTopLat(topLat);
		generator.setLeftTopLng(leftLng);
		generator.setRightBottomLat(bottomLat);
		generator.setRightBottomLng(rightLng);
		generator.setZoom(zoom);

		generator.generateTiles();

		System.out.println("Zakończono.\nCzas trwania: " + (System.currentTimeMillis() - start) + "ms");
	}
}
