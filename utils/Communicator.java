package utils;

import java.time.LocalTime;
import java.util.*;

public class Communicator {
    public static void sayBye(ResourceBundle bundle)
    {
        Random random = new Random();
        System.out.println(bundle.getObject("farewell" + random.nextInt(3)));
    }
    public static boolean askQuestion(String animal, Scanner scan, ResourceBundle bundle) {
        System.out.println("Is it " + animal + '?');
        if (YesOrNo(scan, bundle) == 1) {
            return true;
        }
        else if (YesOrNo(scan, bundle) == 0) {
            return false;
        }
        return false;
    }
    public static String takeAnswer(Scanner scan, ResourceBundle bundle) {
        String answer = scan.nextLine();
        String regexp = "[\\s\\d\\W]*";
        while (answer.equals("") || answer.matches(regexp)) {
            clarifyQuestion(bundle);
            answer = scan.nextLine();
        }
        answer = answer.toLowerCase().trim();
        if (answer.charAt(answer.length() - 1) == '.' || answer.charAt(answer.length() - 1) == '!')
        {
            answer = answer.substring(0, answer.length() - 1);
        }
        return answer;
    }
    public static int YesOrNo(Scanner scan, ResourceBundle bundle) {
        String answer;
        while (true) {
            answer = takeAnswer(scan, bundle);
            if (answer.matches(bundle.getString("positiveAnswer"))) {
                return 1;
            } else if (answer.matches(bundle.getString("negativeAnswer")))  {
                return 0;
            } else {
                clarifyQuestion(bundle);
            }
        }
    }
    public static void clarifyQuestion(ResourceBundle bundle) {
        Random random = new Random();
        System.out.println(bundle.getString("ask.again" + random.nextInt((5 - 1) + 1)));
    }
    public static String getExclamation(ResourceBundle bundle) {
        Random random = new Random();
        return(bundle.getString("animal.nice" + random.nextInt((5 - 1) + 1)));
    }
    public static String takeAnimal(Scanner scan, ResourceBundle bundle) {
        String regexp = "[\\w- ]*";
        String animal;
        while (true) {
            animal = scan.nextLine();
            if (animal == null || animal.equals("") || !animal.matches(regexp)) {
                System.out.println(bundle.getString("enterAnAnimal"));
            }
            else
                break ;
        }
        animal = animal.toLowerCase();
        if (animal.indexOf((String)bundle.getObject("article.a")) == 0 || animal.indexOf((String)bundle.getObject("article.an")) == 0)
            return animal;
        else
            return(selectArticle(animal));
    }
    public static String selectArticle(String animal) {
        if (animal.indexOf("the ") == 0)
            animal = animal.substring(4);
        if (isVowel(animal.charAt(0)))
            return ("an ".concat(animal));
        return ("a ".concat(animal));
    }
    public static boolean isVowel(char c)
    {
        String vowels = "aeiouy";
        return vowels.indexOf(c) != -1;
    }
    public static void greeting(ResourceBundle bundle) {
        int time = LocalTime.now().getHour();
        if (time >= 5 && time <= 12) {
            if (time <= 7)
                System.out.println(bundle.getObject("greeting.early"));
            else
                System.out.println(bundle.getObject("greeting.morning"));
        }
        else if (time > 12 && time <= 18) {
            System.out.println(bundle.getObject("greeting.afternoon"));
        }
        else if (time > 18) {
            System.out.println(bundle.getObject("greeting.evening"));
        }
        else {
            System.out.println(bundle.getObject("greeting.night"));
        }
    }
}
