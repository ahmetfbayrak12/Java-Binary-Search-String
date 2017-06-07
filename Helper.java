package fouryy2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Helper 
{



	/*	Print matched ones to number of k	*/
	void printResult(int k, String[] finalArray)
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

	/*	Read text and add them to lineArray line by line	*/
	public String[] readFile(String text)
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

	/*	Insertion sort for integer weight. I cast them to long type because of big numbers	*/
	void insertionSortInt(String[] intArray)
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

	/*	Binary search which returns first index of matches witch our key	*/
	int binarySearchString(String[] stringArray, String key)
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

	/*	Quick sort for string	*/
	void quickSort(String stringArray[])
	{
		if (stringArray.length == 0 || stringArray == null) 
		{
			return;
		}
		int n = stringArray.length;
		quickSortHelper(0, n - 1, stringArray);
	}

	/*	Helper function for quick sort	*/
	void quickSortHelper(int lowerIndex, int higherIndex, String [] stringArray)
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

	/*	Starts with firstIndex which coming from result of binary search, to end of list and add elements which match with our key	*/
	void addWeight(String[] lineArray, ArrayList<String> weight, int firstIndex, int keySize, String key)
	{													
		for(int i = firstIndex ; i < lineArray.length; i++)	
		{
			if(key.equals(lineArray[i].substring(0, keySize)))				// check whether key matches with our element
				weight.add(lineArray[i]);
		}
		if(weight.size() == 0)
			throw new NullPointerException();
	}
}
