package binaryTree;
import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.SortedSet;

//@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnimalTree {
	//@JsonInclude
	private ArrayList<String> allAnimals;
	private Node root;
	private Node current;
	private int NodesNumber;
	@JsonIgnore
	private int temp;
	public int getNodesNumber() {
		return NodesNumber;
	}
	private void setNodesNumber(int n) {
		NodesNumber = n;
	}
	public AnimalTree(Node root) {
		this.root = root;
		allAnimals = new ArrayList<String>();
		allAnimals.add(root.getSecValue());
		NodesNumber = 1;
	}
	public AnimalTree(String root, ResourceBundle bundle) {
		this.root = new Node(root, bundle);
		allAnimals = new ArrayList<String>();
		allAnimals.add(this.root.getSecValue());
		NodesNumber = 1;
	}
	public AnimalTree() {
		this.root = null;
		allAnimals = new ArrayList<String>();
		NodesNumber = 0;
	}
	public Node getCurrent() {
		return(this.current);
	}
	public void setCurrent(Node curr) {
		this.current = curr;
	}
	public void setRoot(Node root) {
	this.root = root;
	}
	public Node getRoot() {
		return(this.root);
	}
	public void addNewQuestion(String value, Node yesChild, Node noChild, ResourceBundle bundle) {
		Node temp;
		if(current == yesChild) {
			temp = new Node(yesChild);
			current.clearFacts();
			current.addFact("Test", bundle);
			current.setValue(value, bundle);
			current.setYesChild(temp);
			current.setNoChild(noChild);
			current.getNoChild().setFacts(current.getYesChild().getFacts());
			allAnimals.add(current.getNoChild().getSecValue());
		}
		if(current == noChild) {
			temp = new Node(noChild);
			current.clearFacts();
			current.addFact("Test", bundle);
			current.setValue(value, bundle);
			current.setYesChild(yesChild);
			current.setNoChild(temp);
			current.getYesChild().setFacts(current.getNoChild().getFacts());
			allAnimals.add(current.getYesChild().getSecValue());
		}
		//System.out.println("value " + current.getValue());
		if (current.getNoChild().isAnimal())
			current.getNoChild().addFact("no" + current.getSecValue(), bundle);
		if (current.getYesChild().isAnimal())
			current.getYesChild().addFact("yes" + current.getSecValue(), bundle);
		//current.getYesChild().printFacts();
		NodesNumber += 2;
	}
	public void printAllAnimals(ResourceBundle bundle) {
		Collections.sort(allAnimals);
		System.out.println(bundle.getObject("tree.list.animals"));
		for(String s : allAnimals) {
			System.out.println(" - " + s);
		}
	}
	public ArrayList<String> getAllAnimals() {
		return allAnimals;
	}
	public void setAllAnimals(ArrayList<String> list) {
		allAnimals = list;
	}
	public void printTree(ResourceBundle bundle) {
	if (this.root == null) {
		System.out.println(bundle.getObject("tree.print.empty"));
	}
	Node temp = this.root;
	System.out.print("└");
	recursionPrintTree(temp, " ");
}
	public void recursionPrintTree(Node node, String print) {
		if (node == null) {
			return ;
		}
		String val;

		val = node.getValue();
		if (val != null) {
			System.out.println(print + val);
			//System.out.println(node.getSecValue());
			//node.printFacts();
		}
		recursionPrintTree(node.getYesChild(), changePrintLevel(print, "├"));
		recursionPrintTree(node.getNoChild(), changePrintLevel(print, "└"));
	}

	private String changePrintLevel(String str, String add) {
		char[] s = str.toCharArray();
		int i = 0;
		while (i < s.length) {
			if (s[i] == '└') {
				s[i] = ' ';
			}
			if (s[i] == '├') {
				s[i] = '│';
			}
			i++;
		}
				return(String.valueOf(s) + add);
	}
	//Old methods:
	public void askQuestion(Node node, ResourceBundle bundle) {
		if (node.isAnimal()) {
			System.out.printf((String)bundle.getObject("guessing.isIt"), node.getValue());
			//System.out.println("Is it " + node.getValue() + "?");
		}
		else {
			System.out.println(node.getValue());
		}
	}
		public boolean changeCurrNode(int i) {
		if (i == 1)
		{
			if (current.getYesChild() == null)
				return false;
			current = current.getYesChild();
		}
		else
		{
			if (current.getNoChild() == null)
				return false;
			current = current.getNoChild();
		}
		return true;
	}
	public void printStatistic(ResourceBundle bundle) {
		System.out.println(bundle.getObject("tree.stats.title"));
		System.out.printf("%-31s%s\n", (String)bundle.getObject("tree.stats.root"), this.getRoot().getSecValue().substring(0, this.getRoot().getSecValue().length() - 1));
		System.out.printf("%-31s%s\n", (String)bundle.getObject("tree.stats.nodes"), this.getNodesNumber());
		System.out.printf("%-31s%s\n", (String)bundle.getObject("tree.stats.animals"), this.allAnimals.size());
		System.out.printf("%-31s%s\n", (String)bundle.getObject("tree.stats.statements"), this.getNodesNumber() - this.allAnimals.size());
		System.out.printf("%-31s%s\n", (String)bundle.getObject("tree.stats.height"), this.maxHeight(this.getRoot()));
		temp = this.maxHeight(this.getRoot());
		minAnimalDepth(this.getRoot(), 0);
		System.out.printf("%-31s%s\n", (String)bundle.getObject("tree.stats.minimum"), temp);
		temp = 0;
		this.averageDepth(this.getRoot(), 0);
		System.out.printf("%-31s%.1f\n", (String)bundle.getObject("tree.stats.average"), (float)((float)temp / (float)this.allAnimals.size()));
	}
	private int maxHeight(Node current) {
		if (current == null) {
			return -1;
		}
		int maxYes = maxHeight(current.getNoChild());
		int maxNo = maxHeight(current.getYesChild());
		if (maxYes > maxNo) {
			return maxYes + 1;
		} else
			return maxNo + 1;
	}
	private void averageDepth(Node current, int num) {
		if(current.isAnimal()) {
			temp += num;
			return ;
		}
		averageDepth(current.getNoChild(), num + 1);
		averageDepth(current.getYesChild(), num + 1);
	}
	private void minAnimalDepth(Node current, int num) {
		if (current.isAnimal()) {
			//System.out.println(current.getValue() + " " + num);
			if (temp > num) {
				temp = num;
			}
			return ;
		}
		minAnimalDepth(current.getNoChild(), num + 1);
		//System.out.println(current.getValue() + " " + num + 1);
		minAnimalDepth(current.getYesChild(), num + 1);
		//System.out.println(current.getValue() + " " + num + 1);

	}
	public boolean searchForAnAnimal(Node curr, String animal, ResourceBundle bundle) {
	if (curr == null) {
		return false;
	}
	if (curr.getValue().equals(animal)) {
		System.out.println((String)bundle.getObject("tree.search.facts") + curr.getSecValue() + ":");
		curr.printFacts();
		return true;
	}

	boolean f = searchForAnAnimal(curr.getNoChild(), animal, bundle);
	boolean s = searchForAnAnimal(curr.getYesChild(), animal, bundle);
		return f || s;
	}
}

