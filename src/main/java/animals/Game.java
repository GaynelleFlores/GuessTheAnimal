package animals;
import java.lang.Exception;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import utils.Communicator;
import binaryTree.AnimalTree;
import binaryTree.Node;
import java.io.File;
import java.util.ResourceBundle;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Game {
    AnimalTree animals;
    Scanner scan;
    ObjectMapper objectMapper;
    String fileName;
    public void initGame(ResourceBundle bundle) {
        this.scan = new Scanner(System.in);
        if (this.animals == null) {
            createFirstAnimal(bundle);
        }
        System.out.println(bundle.getObject("welcome"));
        while (mainMenu(bundle)) { }
        saveAnimalsTree();
    }
    private boolean mainMenu(ResourceBundle bundle) {
        int i = -1;
        System.out.println(bundle.getObject("menu"));
        while (i == -1) {
            try {
                i = Integer.parseInt(scan.nextLine());
                if (i < 0 || i > 5) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println(bundle.getObject("menu.error"));
                i = -1;
            }
        }
        switch (i) {
            case(1) : {
                playGame(bundle);
                return true;
            }
            case(2) : {
                animals.printAllAnimals(bundle);
                return true;
            }
            case(3) : {
                searchForAnAnimal(bundle);
                return true;
            }
            case (4) : {
                animals.printStatistic(bundle);
                return true;
            }
            case(5): {
                animals.printTree(bundle);
                return true;
            }
        }
        return false;
    }
    private void searchForAnAnimal(ResourceBundle bundle) {
        System.out.println(bundle.getObject("animal.prompt"));
        String animal = Communicator.takeAnimal(scan, bundle);
        if(!animals.searchForAnAnimal(animals.getRoot(), animal, bundle)) {
            System.out.println(bundle.getObject("tree.search.noFacts") + animal.substring(animal.indexOf(' ') + 1) + ".");
        }
    }
    private void playGame(ResourceBundle bundle) {
        while (true) {
            animals.setCurrent(animals.getRoot());
            System.out.println(bundle.getObject("game.think"));
            while (!scan.nextLine().equals("")) {
                System.out.println(bundle.getObject("game.enter"));
            }
            askingQuestion(bundle);
            System.out.println(bundle.getObject("game.again"));
            int answer = Communicator.YesOrNo(scan, bundle);
            if (answer == 0)
                break ;
        }
    }
    private void createFirstAnimal(ResourceBundle bundle) {
        System.out.println(bundle.getObject("animal.wantLearn"));
        this.animals = new AnimalTree(Communicator.takeAnimal(scan, bundle), bundle);
        System.out.println(this.animals.getRoot().getValue());
        //System.out.println(Communicator.getExclamation() + "I've learned so much about animals!");
    }
    private void saveAnimalsTree() {
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File(fileName), animals);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private void askingQuestion(ResourceBundle bundle) {
        int answer;
        boolean flag = true;
        while (flag) {
            animals.askQuestion(animals.getCurrent(), bundle);
            answer = Communicator.YesOrNo(scan, bundle);
            if (answer == 1 && (animals.getCurrent().isAnimal())) {
                return ;
            }
            else
            {
                flag = animals.changeCurrNode(answer);
            }
        }
        learnNewAnimal(bundle);
    }
    private void learnNewAnimal(ResourceBundle bundle) {
        System.out.println(bundle.getObject("game.giveUp"));
        String animal2 = Communicator.takeAnimal(scan, bundle);
        AnimalComparison compare = new AnimalComparison(animals.getCurrent().getValue(), animal2);
        compare.compareAnimals(bundle);
        String question = compare.getQuestion();
        if (compare.getFactCorrectsFor() == 1) {
            animals.addNewQuestion(question, animals.getCurrent(), new Node(animal2, bundle), bundle);
        }
        else {
            animals.addNewQuestion(question, new Node(animal2, bundle), animals.getCurrent(), bundle);
        }
    }
    public void createMemoryFile(String file, ObjectMapper mapper) {
        this.fileName = file;
        this.objectMapper = mapper;
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            this.animals = objectMapper.readValue(new File(fileName), AnimalTree.class);
        }
        catch (Exception e) {
            //System.out.println(e.getMessage() + "\n" + e.getLocalizedMessage());
        }
    }
}
