package com.example.resolution;

import Util.Utils;

public class Test {
    public static void main(String[] args) {
        System.out.println(Utils.predictUsingFile("src\\main\\java\\Input\\rules.txt", "src\\main\\java\\Input\\clause.txt"));
    }
}
