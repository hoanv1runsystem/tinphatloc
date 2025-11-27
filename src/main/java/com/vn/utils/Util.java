package com.vn.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Util {
	public static String removeAccent(String s) {
		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		temp = pattern.matcher(temp).replaceAll("");
		return temp.replaceAll("đ", "d").toLowerCase().replace(" ", "-");
	}
	
}
