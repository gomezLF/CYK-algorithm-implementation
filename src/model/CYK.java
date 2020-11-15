package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class CYK {
	/**
	 * Represent all variables with contains the GIC
	 */
	ArrayList<String> variables;
	/**
	 * Matrix initial with productions
	 */
	String[][] mainMatrix;
	/**
	 * Matrix final
	 */
	String[][] matrixResult;
	
	/**
	 * Map with values of the matrix inital
	 */
	HashMap<String,List<String>> map;
	
	public CYK() {
		
	}
	
	/**
	 * CYK class constructor
	 * @param matrix Initial matrix containing the productions
	 * @param stringSize String size to later create the dimensions of the result matrix
	 */
	public CYK(String[][] matrix,int stringSize) {	
		mainMatrix = matrix;
		matrixResult = new String[stringSize][stringSize];
		variables = new ArrayList<String>();
		map = new HashMap<>();
	}
	
	/**
	 * Pre:The initial matrix with the productions has been passed to the constructor of this class
	 * This method allows pass elements from matrix to hashmap
	 */
	public void addValueToMap() {
		String variableProductora = "";
		variables = new ArrayList<String>();
		for (int i = 0; i < mainMatrix.length; i++) {
			List<String> producciones = new ArrayList<String>();
			for (int j = 0; j < mainMatrix[i].length; j++) {
					if(j == 0) {
						variables.add(mainMatrix[i][j]);
						variableProductora = mainMatrix[i][j];												
					}else {
						String[] arrayProducciones = mainMatrix[i][j].split("\\|");
						producciones = Arrays.asList(arrayProducciones);	
					}
			}
			addValueToMapAux(variableProductora,producciones);
		}		
	}	
	/**
	 * This method helps the add value to map method to add values ​​to the hasmap 
	 * @param variableProductora It is the producing variable of production
	 * @param producciones they are the productions of the producing variable
	 */
	public void addValueToMapAux(String variableProductora, List<String> producciones) {
		 map.put(variableProductora, producciones);
	}
	
	/**
	 * This method allows calculate the algorithm CYK
	 * @param w string 
	 */
	public void calculateCYK(String w) {
		for (int j = 2; j <= w.length(); j++) {
			
			for (int i = 1; i <= (w.length()-j+1); i++) {
				List<String> nTuplas = new ArrayList<String>();
				for (int k = 1; k <= j-1; k++) {
					//X (i,j) -> X(i,k) X(i+k),(j-k)
					//Elements to use the method called cartesian product
					String[] partitions1 = matrixResult[i-1][k-1].split(",");
					String[] partitions2 = matrixResult[(i-1)+(k)][(j-1)-(k)].split(",");
					// Result the method cartesian product
					String[] resultCartesianProduct = cartesianProduct(partitions1,partitions2);
					nTuplas.addAll(Arrays.asList(resultCartesianProduct));					
				}
				//Values to generate
				matrixResult[i-1][j-1] = whoGenerate(nTuplas);
				
				
				
				
			}
		}
		
	}
	
	/**
	 * The method allows to verify if a string is contained in one in the last position of the result matrix
	 * @return returns true if it contains the string and not otherwise
	 */
	public boolean containsString() {
		String[] checkValueInitial = matrixResult[0][matrixResult[0].length-1].split(",");
		boolean found = false;
		for (int i = 0; i < checkValueInitial.length && !found; i++) {
			if(checkValueInitial[i].equals(variables.get(0))) {
				found = true;
			}
		}
			
		
		return found;
	}
	
	/**
	 * This method allow add the first column to result matrix 
	 * @param w string to analize
	 */
	public void addTofirstColumn(String w){
		for (int j = 1; j <= w.length(); j++) {
			String value = "";
				for (int k = 0; k < variables.size(); k++) {
					String subCadena = Character.toString(w.charAt(j-1));
					String key = variables.get(k);
						if(map.get(key).contains(subCadena)) {
							value += key+",";	
						}						
				}
				
				// Para quitar la coma
				//Ejemplo: si es "B," entonces el resultado seria "B"
				if(value != "" && value.charAt(value.length()-1) == ',') {
					value = value.substring(0,value.length()-1);
				}
				matrixResult[j-1][0] = value; 					
				
			}
		}
	

	/**
	 * What variable generates a string
	 * @param nTuplas 
	 * @return
	 */
	public String whoGenerate(List<String> nTuplas) {
		List<String> resultTupla = new ArrayList<String>();
		String result = "";
		
		for (int i = 0; i < nTuplas.size(); i++) {			
			for (int j = 0; j < variables.size(); j++) {
				String key = variables.get(j);
				
				if(map.get(key).contains(nTuplas.get(i))) {
					if(!resultTupla.contains(key)) {
						resultTupla.add(key);
						result += key+",";
					}
					
				}
			}
					
		}
		//Para quitar la coma
		//Ejemplo: si es "B," entonces el resultado seria "B"
			if(result != "" && result.charAt(result.length()-1) == ',') {
				result = result.substring(0,result.length()-1);
			}		
		return result;	
	}
	
	/**
	 * Allows realizing the Cartesian product between two partitions
	 * @param partitions1 - Arrys to String
	 * @param partitions2 - Arrys to String
	 * @return result to cartesian product between partitions1 and partitions2
	 */
	public String[] cartesianProduct(String[] partitions1, String[] partitions2) {
		
		int size = partitions1.length * partitions2.length;
		String[] combinations = new String[size];
		int counter = 0;
		for (int i = 0; i < partitions1.length; i++) {
			for (int j = 0; j < partitions2.length; j++) {
				combinations[counter] = partitions1[i]+""+partitions2[j];
				counter++;
			}
		}
				
		return combinations;
	}
	
	
	
	
	public ArrayList<String> getVariables() {
		return variables;
	}

	public void setVariables(ArrayList<String> variables) {
		this.variables = variables;
	}

	public String[][] getMainMatrix() {
		return mainMatrix;
	}

	public void setMainMatrix(String[][] mainMatrix) {
		this.mainMatrix = mainMatrix;
	}

	public String[][] getMatrixResult() {
		return matrixResult;
	}

	public void setMatrixResult(String[][] matrixResult) {
		this.matrixResult = matrixResult;
	}

	public HashMap<String, List<String>> getMap() {
		return map;
	}

	public void setMap(HashMap<String, List<String>> map) {
		this.map = map;
	}
	
}