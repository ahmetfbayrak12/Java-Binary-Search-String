package Hw2_Array;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main 
{
	public static void main(String[] args) 
	{
		Main hw = new Main();
		Scanner scn = new Scanner(System.in);
		
		System.out.println("<textfile name> <k> <key>");							// get text file name, k (number of search), key (search key) from console.
		String text = scn.next();
		int k = Integer.parseInt(scn.next());
		String key = scn.next();
		
		try 
		{
			String[] lineArray = hw.readFile(text);									// read text

			hw.quickSort(lineArray);												// sort by String

			int firstIndex = hw.binarySearchString(lineArray, key);					// find first index of search key

			ArrayList<String> weight = new ArrayList<>();
			hw.addWeight(lineArray, weight, firstIndex, key.length(), key);			// add matched ones with our key

			String[] finalArray = new String[weight.size()];
			finalArray = weight.toArray(finalArray);

			hw.insertionSortInt(finalArray);										// sort the matched ones by weight
			
			hw.printResult(k, finalArray);											// print the keys
		} 
		catch (IllegalArgumentException e1) 										// if weight is negative
		{
			System.out.println("Weight can not be negative. ");
		} 
		catch (NullPointerException e2) 											// if query is null
		{
			System.out.println("Query is null. ");
		}
		
		newQuery(args, scn);
	}

	private static void newQuery(String[] args, Scanner scn) 						// check whether user wants a new query.
	{
		System.out.println("Do you want to search anything else? (Y/N)");
		String yesNo = scn.next();
		if(yesNo.equals("Y") || yesNo.equals("y"))
			main(args);
		else if (yesNo.equals("N") || yesNo.equals("n"))
		{
			System.out.println("Program is ended.");
			return;
		}
	}

	private void printResult(int k, String[] finalArray) 		// print matched ones to number of k
	{
		for(int i = 0 ; i < k ; i++)
		{
			try 
			{
				if( k > finalArray.length)									// if k is more than matched ones, takes matched ones
					k = finalArray.length;
				System.out.println(finalArray[i].split("\t")[1].trim() + "\t" + finalArray[i].split("\t")[0].trim());
			} catch (Exception e) 
			{
					System.out.println("There is no key that you want to search. ");
			}
		}
	}

	private String[] readFile(String text)									// read text and add them to lineArray line by line
	{
		String thisLine = null;
	      String [] lineArray = null;
	      try 
	      {    	  
		      BufferedReader br = new BufferedReader(new FileReader(text));
	    	  int size = Integer.parseInt(br.readLine());
	    	  lineArray = new String[size];
	    	  int temp = 0;
	    	  while ((thisLine = br.readLine()) != null) 
	    	  {
	    		  if(Long.parseLong(thisLine.split("\t")[0].trim()) < 0)	// if weight is negative, throw IllegalArgumentException
	    		  {
	    			  throw new IllegalArgumentException();
	    		  }
	    		  lineArray[temp++] = thisLine.split("\t")[1].trim() + "\t" + thisLine.split("\t")[0].trim();	         
	    	  }       
	      } catch(IOException e) 
	      {
	    	  e.printStackTrace();
	      }
	      return lineArray;
	}
	
	private  void insertionSortInt(String[] intArray) 				// insertion sort for integer weight. i cast them to long type because of big numbers
	{
		int n = intArray.length;
		String temp = null;
		for(int i = 1 ; i < n ; i++)
		{
			for(int j = i ; j > 0 ; j--)
			{
				if(Long.parseLong((intArray[j].split("\t")[1])) > Long.parseLong((intArray[j-1].split("\t")[1])))
				{
					temp = intArray[j];
					intArray[j] = intArray[j-1];
					intArray[j-1] = temp;
				}
			}
		}
	}
	
	private  int binarySearchString(String[] stringArray, String key) 				// Binary search which returns first index of matches witch our key
	{
	     int firstIndex = 0;
	     int lastIndex  = stringArray.length;
	     while (firstIndex < lastIndex) 
	     {
	         int mid = firstIndex + (lastIndex - firstIndex) / 2;
	         if (key.compareToIgnoreCase(stringArray[mid]) < 0) 
	         {
	             lastIndex = mid;
	         } 
	         else if (key.compareToIgnoreCase(stringArray[mid]) > 0) 
	         {
	             firstIndex = mid + 1;
	         } 
	         else 
	         {
	             return mid;
	         }
	     }
	     return firstIndex;
	 }
	
    private void quickSort(String stringArray[]) 									// quick sort for string
    {
        if (stringArray.length == 0 || stringArray == null) 
        {
            return;
        }
        int n = stringArray.length;
        quickSortHelper(0, n - 1, stringArray);
    }

    private  void quickSortHelper(int lowerIndex, int higherIndex, String [] stringArray) // helper function for quick sort
    {
        int i = lowerIndex;
        int j = higherIndex;
        String pivot = stringArray[lowerIndex + (higherIndex - lowerIndex) / 2];

        while (i <= j) 
        {
            while (stringArray[i].compareToIgnoreCase(pivot) < 0) 
            {
                i++;
            }
            while (stringArray[j].compareToIgnoreCase(pivot) > 0) 
            {
                j--;
            }
            if (i <= j) 
            {
                String temp = stringArray[i];
                stringArray[i] = stringArray[j];
                stringArray[j] = temp;
                i++;
                j--;
            }
        }
        if (lowerIndex < j) 
        {
            quickSortHelper(lowerIndex, j, stringArray);
        }
        if (i < higherIndex) 
        {
            quickSortHelper(i, higherIndex, stringArray);
        }
    }

    private void addWeight(String[] lineArray, ArrayList<String> weight, int firstIndex, int keySize, String key)	// starts with firstIndex which coming from
    {																												// result of binary search, to end of list
		for(int i = firstIndex ; i < lineArray.length; i++)															// and add elements which match with our key
		{
			if(key.equals(lineArray[i].substring(0, keySize)))				// check whether key matches with our element
				weight.add(lineArray[i]);
		}
		if(weight.size() == 0)
			throw new NullPointerException();
    }
}
