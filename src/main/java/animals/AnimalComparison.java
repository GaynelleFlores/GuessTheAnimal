package animals;

import java.util.*;
import utils.Communicator;

public class AnimalComparison {
    private String animal1;
    private String animal2;
    private String negVerb;

    private String fact;
    private String verb;
    private String question;

    public AnimalComparison (String animal1, String animal2) {
        this.animal1 = animal1;
        this.animal2 = animal2;
    }

    private int factCorrectsFor;
    public String getAnimal1() {
        return animal1;
    }
    public String getAnimal2() {
        return animal2;
    }
    public String getQuestion() {return question;}
    public int getFactCorrectsFor() {return factCorrectsFor;}
    public void compareAnimals(ResourceBundle bundle)
    {
        int correctAnswer;
        Scanner scan = new Scanner(System.in);
        findFact(scan, bundle);
        System.out.printf(bundle.getString("game.isCorrect"), animal2);
        correctAnswer = Communicator.YesOrNo(scan, bundle);
        if (correctAnswer == 1)
            factCorrectsFor = 2;
        else
           factCorrectsFor = 1;
        printConclusion(bundle);
    }
    private void printConclusion(ResourceBundle bundle)
    {

        String tempAnimal1;
        String tempAnimal2;
        if (((String)bundle.getObject("article.an")).equals("")) {
            tempAnimal1 = ' ' + animal1;
            tempAnimal2 = ' ' + animal2;
        }
        else {
            tempAnimal1 = animal1.substring(animal1.indexOf(' '));
            tempAnimal2 = animal2.substring(animal2.indexOf(' '));
            fact = fact.substring(fact.indexOf(" "));
        }
        //System.out.println("All things:\n" + fact + "\n" + question + "\n" + verb + "\n" + negVerb + "\n" + tempAnimal1 + verb + fact + '|');
        makeQuestion(verb, bundle);

        if (factCorrectsFor == 1)
        {
            System.out.println(bundle.getString("article.the") + tempAnimal1 + " " + verb + fact + ".");
            System.out.println(bundle.getString("article.the") + tempAnimal2 + " " + negVerb + fact + ".");
        }
        else
        {
            System.out.println(bundle.getString("article.the") + tempAnimal2 + " " + verb + fact + ".");
            System.out.println(bundle.getString("article.the") + tempAnimal1 + " " + negVerb + fact +".");
        }
        System.out.println(bundle.getObject("game.distinguish"));
        System.out.println(this.question);
    }
    private void makeQuestion(String verb, ResourceBundle bundle) {
        if (bundle.getString("article.a").equals("")) {
            this.question = "Ĉu gi " + fact.toLowerCase(Locale.ROOT) + "?";
            return ;
        }
        if (verb.equals("has"))
        {
            System.out.println(fact.substring(fact.indexOf(" ")));
            this.question = "Does it have" + fact.substring(fact.indexOf(" ")) + "?";
        }
        else
            this.question = verb.substring(0, 1).toUpperCase() + verb.substring(1) + " it" + fact + "?";
    }
    private void findNegativeVerb(ResourceBundle bundle) {
        if (verb.equals("can"))
            negVerb = verb + "'t";
        else if (verb.equals("has"))
            negVerb = "doesn't have";
        else
            negVerb = verb + "n't";
    }
    private void findFact(Scanner scan, ResourceBundle bundle)
    {
        String regexp = (String)bundle.getObject("statement.second");
        while (true) {
            System.out.printf(bundle.getString("statement.prompt") + "\n", animal1, animal2);
            fact = scan.nextLine();
            fact = fact.toLowerCase();
            if (!fact.matches(regexp))
            {
                System.out.println(bundle.getString("statement.error"));
            }
            else
                break;
        }
        if (((String)bundle.getObject("article.an")).equals("")) {
            fact = fact.substring(fact.indexOf(' ') + 1);
            verb = "";
            negVerb = "ne ";
            return ;
        }
        fact = fact.substring(3);
        regexp = "[a-zA-Z\\s]*[?,!.]";
        if (fact.matches(regexp))
        {
            fact = fact.substring(0, fact.length() - 1);
        }
        verb = fact.substring(0, fact.indexOf(" "));
        findNegativeVerb(bundle);
        fact = fact.substring(fact.indexOf(" "));
    }
}
