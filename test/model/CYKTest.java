package model;


import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class CYKTest {

	HashMap<String,List<String>> map;
	ArrayList<String> variables;
	String[][] mainMatrix;
	String[][] matrixResultToCompare;
	private String w;
	
	private CYK cyk;
	
	
	private void setupEscenary1() {
		
		w = "bbab";

		mainMatrix = new String[4][2];
		matrixResultToCompare = new String[w.length()][w.length()];
		map = new HashMap<String, List<String>>();
		
		// Variables
		mainMatrix[0][0] = "S";
		mainMatrix[1][0] = "A";
		mainMatrix[2][0] = "B";
		mainMatrix[3][0] = "C";
		
		//Producciones
		mainMatrix[0][1] = "BA|AC";
		mainMatrix[1][1] = "CC|b";
		mainMatrix[2][1] = "AB|a";
		mainMatrix[3][1] = "BA|a";
		
		// values to variable S		
		ArrayList<String> valuesToS = new ArrayList<>();
		valuesToS.add("BA");
		valuesToS.add("AC");
		// values to variable A
		ArrayList<String> valuesToA = new ArrayList<>();
		valuesToA.add("CC");
		valuesToA.add("b");
		ArrayList<String> valuesToB = new ArrayList<>();
		valuesToB.add("AB");
		valuesToB.add("a");
		ArrayList<String> valuesToC = new ArrayList<>();
		valuesToC.add("BA");
		valuesToC.add("a");		
		
		// Add elements to HashMap
		map.put(mainMatrix[0][0],valuesToS);
		map.put(mainMatrix[1][0],valuesToA);
		map.put(mainMatrix[2][0],valuesToB);
		map.put(mainMatrix[3][0],valuesToC);

		cyk = new CYK(mainMatrix,w.length());
		
		//Add values to matrixResult
		
		//Column 1
		matrixResultToCompare[0][0] = "A";
		matrixResultToCompare[1][0] = "A";
		matrixResultToCompare[2][0] = "B,C";
		matrixResultToCompare[3][0] = "A";
		
		//Column 2
		matrixResultToCompare[0][1] = "";
		matrixResultToCompare[1][1] = "B,S";
		matrixResultToCompare[2][1] = "S,C";
		matrixResultToCompare[3][1] = null;
		
		//Column 3
		matrixResultToCompare[0][2] = "B";
		matrixResultToCompare[1][2] = "S,C";
		matrixResultToCompare[2][2] = null;
		matrixResultToCompare[3][2] = null;
		
		//Column 4
		matrixResultToCompare[0][3] = "S,C";
		matrixResultToCompare[1][3] = null;
		matrixResultToCompare[2][3] = null;
		matrixResultToCompare[3][3] = null;		
	}
	
	
	private void setupEscenary2() {
		cyk = new CYK();
	}
	
	private void setupEscenary3() {
		w = "aab";

		mainMatrix = new String[3][2];
		matrixResultToCompare = new String[w.length()][w.length()];
		map = new HashMap<String, List<String>>();
		
		// Variables
		mainMatrix[0][0] = "S";
		mainMatrix[1][0] = "A";
		mainMatrix[2][0] = "B";
		
		//Producciones
		mainMatrix[0][1] = "AB";
		mainMatrix[1][1] = "AA|a";
		mainMatrix[2][1] = "b";
		
		// values to variable S		
		ArrayList<String> valuesToS = new ArrayList<>();
		valuesToS.add("AB");
		// values to variable A
		ArrayList<String> valuesToA = new ArrayList<>();
		valuesToA.add("AA");
		valuesToA.add("a");
		ArrayList<String> valuesToB = new ArrayList<>();
		valuesToB.add("b");
		
		// Add elements to HashMap
		map.put(mainMatrix[0][0],valuesToS);
		map.put(mainMatrix[1][0],valuesToA);
		map.put(mainMatrix[2][0],valuesToB);
	

		cyk = new CYK(mainMatrix,w.length());
		
		//Add values to matrixResult
		
		//Column 1
		matrixResultToCompare[0][0] = "A";
		matrixResultToCompare[1][0] = "A";
		matrixResultToCompare[2][0] = "B";
		
		//Column 2
		matrixResultToCompare[0][1] = "A";
		matrixResultToCompare[1][1] = "S";
		matrixResultToCompare[2][1] = null;
		
		//Column 3
		matrixResultToCompare[0][2] = "S";
		matrixResultToCompare[1][2] = null;
		matrixResultToCompare[2][2] = null;
	}
	
	@Test
	protected void addValueToMapTest() {
		setupEscenary1();
		cyk.addValueToMap();
		checkHasMapSame(map, cyk.map);
		
		//Test other grammar
		setupEscenary3();
		cyk.addValueToMap();
		checkHasMapSame(map, cyk.map);
		
	}
	
	
	@Test
	protected void addToFirstColumnTest() {
		setupEscenary1();
		cyk.addValueToMap();
		cyk.addTofirstColumn(w);
		
		for (int i = 0; i < mainMatrix.length; i++) {
				Assert.assertEquals("La matriz en la posición "+i+" "+0+" es diferentes",mainMatrix[i][0], cyk.getMainMatrix()[i][0]);		
		}
		setupEscenary3();
		cyk.addValueToMap();
		cyk.addTofirstColumn(w);
		
		for (int i = 0; i < mainMatrix.length; i++) {
				Assert.assertEquals("La matriz en la posición "+i+" "+0+" es diferentes",mainMatrix[i][0], cyk.getMainMatrix()[i][0]);		
		}
	}
	
	
	@Test
	protected void calculateCYKTest() {
		setupEscenary1();
		cyk.addValueToMap();
		cyk.addTofirstColumn(w);
		cyk.calculateCYK(w);
		printMatrix(cyk.getMatrixResult());
		compareTwoMatrix(matrixResultToCompare, cyk.matrixResult);
		
		///Test 2
		// Other gramar
		System.out.println("--------------------------------");
		System.out.println("--------------------------------");
		System.out.println("--------------------------------");
		System.out.println("--------------------------------");

		setupEscenary3();
		cyk.addValueToMap();
		cyk.addTofirstColumn(w);
		cyk.calculateCYK(w);
		printMatrix(cyk.getMatrixResult());
		compareTwoMatrix(matrixResultToCompare, cyk.matrixResult);
		


	}
		
	@Test
	protected void cartesianProductTest() {
		setupEscenary2();
		String[] partitions1 = {"a","b"};
		String[] partitions2 = {"c","d"};
		String[] arrayToCompare = {"ac","ad","bc","bd"};
		String[] arrayResult = cyk.cartesianProduct(partitions1, partitions2);
		printArray(arrayResult);
		assertArrayEquals("No son los mismos arreglos", arrayToCompare, arrayResult);
		
		// test 2
		String[] partitions1_1 = {"AA","B"};
		String[] partitions2_1 = {"c","dB","a"};
		String[] arrayToCompare_1 = {"AAc","AAdB","AAa","Bc","BdB","Ba"};
		String[] arrayResult_1 = cyk.cartesianProduct(partitions1_1, partitions2_1);
		printArray(arrayResult_1);
		assertArrayEquals("No son los mismos arreglos", arrayToCompare_1, arrayResult_1);
	}
	
	
	@Test
	protected void whoGenerateTest() {
		setupEscenary1();
		cyk.addValueToMap();
		List<String> nTuplas = new ArrayList<String>();
		nTuplas.add("AS");
		nTuplas.add("AC");
		nTuplas.add("BA");
		nTuplas.add("SA");
		String valueToCompare = "S,C";
		String result = cyk.whoGenerate(nTuplas);
		Assert.assertEquals("No son iguales", valueToCompare, result);
		
		//test 2
		List<String> nTuplas_1 = new ArrayList<String>();
		nTuplas_1.add("AB");
		nTuplas_1.add("AC");
		String valueToCompare_1 = "B,S";
		String result_1 = cyk.whoGenerate(nTuplas_1);
		Assert.assertEquals("No son iguales", valueToCompare_1, result_1);
		
		
		
		
		
		
		
	}

	/**
	 * This method allows check if two hasmap are the same.
	 * where map1 and map 2 are the HasMap to compare.
	 * @param map1
	 * @param map2
	 */
	private void checkHasMapSame(HashMap<String, List<String>> map1,HashMap<String, List<String>> map2) {
	
		for (Entry<String, List<String>> entry1 : map1.entrySet()) {
		      String key = entry1.getKey();                    		      
		          List<String> value1 = entry1.getValue();
		          List<String> value2 = map2.get(key); 
		         
		          for (int i = 0; i < value1.size(); i++) {
					Assert.assertEquals(value1, value2);
				
				}
		      }		
		      
	}
	/**
	 * This method allows print values of an matrix
	 * @param matriz
	 */
	private void printMatrix(String[][] matriz) {
		for (int x=0; x < matriz.length; x++) {
			  System.out.print("|");
			  for (int y=0; y < matriz[x].length; y++) {
				String value = matriz[x][y];
				if(value!=null) {
				    System.out.print ("{"+value+"}");
				}else {
					System.out.print("");
				}
			    if (y!=matriz[x].length-1) System.out.print("\t");
			  }
			  System.out.println("|");
			}
	}
	
	/**
	 * This method print the elements of an array
	 * @param array
	 */
	private void printArray(String[] array) {
		for (int i = 0; i <array.length; i++) {
			System.out.print(array[i]+" ");
		}
		System.out.println();
	}
	
	/**
	 * This method allows compare two-dimensional array
	 * @param matrix1
	 * @param matrix2
	 */
	private void compareTwoMatrix(String[][] matrix1, String[][] matrix2) {
		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix1[i].length; j++) {
				Assert.assertEquals("La matriz en la posición "+i+" "+j+" es diferentes",matrix1[i][j],matrix2[i][j]);
			}
		}
	}

}
