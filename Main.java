package fouryy2;

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
		Helper hlp = new Helper();
		Scanner scn = new Scanner(System.in);

		System.out.println("<textfile name> <k> <key>");							// get text file name, k (number of search), key (search key) from console.
		String text = scn.next();
		int k = Integer.parseInt(scn.next());
		String key = scn.next();

		try 
		{
			String[] lineArray = hlp.readFile(text);									// read text

			hlp.quickSort(lineArray);												// sort by String

			int firstIndex = hlp.binarySearchString(lineArray, key);					// find first index of search key

			ArrayList<String> weight = new ArrayList<>();
			hlp.addWeight(lineArray, weight, firstIndex, key.length(), key);			// add matched ones with our key

			String[] finalArray = new String[weight.size()];
			finalArray = weight.toArray(finalArray);

			hlp.insertionSortInt(finalArray);										// sort the matched ones by weight

			hlp.printResult(k, finalArray);											// print the keys
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

	/*	Check whether user wants a new query.	*/
	private static void newQuery(String[] args, Scanner scn)
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
}
