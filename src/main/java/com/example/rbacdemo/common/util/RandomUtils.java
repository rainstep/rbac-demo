package com.example.rbacdemo.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class RandomUtils {
	private static Logger logger = LoggerFactory.getLogger(RandomUtils.class);

	private static String[] strArrays = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
					"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
					"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
					"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
					"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};

	public static List<Integer> getNumberList(int min, int max, int count, boolean isRepeat) {

		if (!isRepeat) {
			int maxCount = max - min + 1;
			if (count > maxCount) {
				logger.error("count超出最大数量");
				return null;
			}
		}
		List<Integer> numberList = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			int num = getNumber(min, max);
			if (!isRepeat) {
				while (numberList.contains(num)) {
					num = getNumber(min, max);
				}
			}
			numberList.add(num);
		}
		return numberList;
	}
	
	public static int getNumber(int min, int max) {
		int result = (int) (Math.random() * (max + 1 - min) + min);
		return result;
	}

	public static String getStringNumber(int min, int max) {
		int result = getNumber(min, max);
		return Integer.toString(result);
	}

	// 得到随机字符
	public static String getString(int count) {
		StringBuffer stringBuffer = new StringBuffer();
		int maxNum = strArrays.length - 1;
		for (int i = 0; i < count; i++) {
			int randomNum = getNumber(0, maxNum);
			stringBuffer.append(strArrays[randomNum]);
		}
		return stringBuffer.toString();
	}

    public static void main(String[] args) {
        System.out.println(getNumberList(1, 5, 5, true));
    }

}
