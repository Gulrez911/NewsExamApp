package com.gul.num;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NumberStringArray {

	public static void main(String[] args) {
		ArrayList<String> ar = new ArrayList<String>();
		String s1 = "Test1";
		String s2 = "Test2";
		String s3 = "Test3";
		ar.add(s1);
		ar.add(s2);
		ar.add(s3);
		List<String> ar2 = new ArrayList<String>();
		for (String ss : ar) {
			String string = '"' + ss + '"';
			ar2.add(string);
		}
		String sss="";
		sss = ar2.toString().replace("[", "").replace("]", "");
		System.out.println(ar);
		System.out.println(sss);
	}

}
