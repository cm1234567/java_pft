package ru.stqa.pft.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {
   public static void main(String[] args) {
      String[] langs = {"Java", "C#", "Python", "PHP"};

//      List<String> languages = new ArrayList<String>();
      List<String> languages = Arrays.asList("Java", "C#", "Python", "PHP");

      System.out.println("langs.length = " + langs.length + " languages.size() = " + languages.size());

      for (Object o : languages)
         System.out.println("Я хочу выучить " + o);
   }
}
