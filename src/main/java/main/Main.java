package main;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import utils.Communicator;
import animals.Game;
import java.util.ResourceBundle;

class Main {

    public static void main(String[] args) {
        ResourceBundle bundle = ResourceBundle.getBundle("animals");
        String filename = "animals.";
        if (((String)bundle.getObject("article.an")).equals("")) {
            filename = "animals_eo.";
        }
        String type = null;
        if (args.length == 0) {
            type = "json";
        }
        else if (args.length != 2 || !args[0].equals("-type") || (type = checkType(args[1])) == null) {
            System.out.println("Arguments incorrect. Please, specify a type (json/xml/yaml), for example: \"-type json\".");
            System.exit (0);
        }
        filename += type;
        Communicator.greeting(bundle);
        Game game = new Game();
        if (type.equals("json")) {
            game.createMemoryFile(filename, new JsonMapper());
        }
        else if (type.equals("xml")) {
            game.createMemoryFile(filename, new XmlMapper());
        }
        else {
            game.createMemoryFile(filename, new YAMLMapper());
        }
        game.initGame(bundle);
        Communicator.sayBye(bundle);
    }
    private static String checkType(String secondArg) {
        String []types = {"yaml", "xml", "json"};

        for(String s : types) {
            if (s.equals(secondArg)) {
                return s;
            }
        }
        return null;
    }
}