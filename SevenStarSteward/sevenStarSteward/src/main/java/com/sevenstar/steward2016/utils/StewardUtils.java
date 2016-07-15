package com.sevenstar.steward2016.utils;

public class StewardUtils {
	public static String getStarGrade(int grade) {
		String[] starsValues = new String[] { "һ��", "����", "����", "����", "����", "����", "����" };
		if (grade >= 0 && grade < starsValues.length) {
			return starsValues[grade];
		}
		return "��";
	}
}
