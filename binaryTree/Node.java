package binaryTree;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.ResourceBundle;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node {
	protected String value;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final ArrayList<String> facts;
	private String secValue;
	protected Node no;
	protected Node yes;

	public Node () {
		facts = new ArrayList<String>();
		facts.add("Test");
		no = null;
		yes = null;
		value = null;
		secValue = null;
	}
	public Node (String value, ResourceBundle bundle) {
		facts = new ArrayList<String>();
		facts.add("Test");
		this.value = value;
		yes = null;
		createValueWithoutArticle(bundle);
	}
	public void addFact(String fact, ResourceBundle bundle){
		if (!((String)bundle.getObject("article.an")).equals("")) {
			if(fact.matches("no.*")) {
				facts.add(createNoFact(fact));
			}
			else {
				facts.add(fact.substring(3));
			}
			return ;
		}
		if (fact.contains("no")) {
			if (!fact.equals("Test"))
				fact = fact.substring(7);
			fact = "Ĝi ne" + fact;
		}
		else {
			if (!fact.equals("Test"))
				fact = fact.substring(8);
			fact = "Ĝi" + fact;
		}
		facts.add(fact);
	}
	private String createNoFact(String fact) {
		fact = fact.substring(2);
		if(fact.matches("It is.*")) {
			fact = fact.substring(5);
			fact = "It isn't" + fact;
		}
		else if(fact.matches("It can.*")) {
			fact = fact.substring(6);
			fact = "It can't" + fact;
		}
		else {

			fact = fact.substring(6);
			fact = "It doesn't have" + fact;
		}
		return fact;
	}
	public Node (Node animal) {
		no = null;
		yes = null;
		if (animal != null) {
			this.facts = new ArrayList<String>();
			this.facts.addAll(animal.getFacts());
			this.value = animal.getValue();
			this.secValue = animal.getSecValue();
			if (animal.getYesChild() != null)
				this.yes = new Node(animal.getYesChild());
			if (animal.getNoChild() != null)
				this.no = new Node(animal.getNoChild());
		}
		else {
			this.facts = new ArrayList<String>();
			value = null;
			secValue = null;
		}
	}
	public void printFacts() {
		for (String s : facts) {
			if (!s.equals("Test"))
				System.out.println(" - " + s);
		}
	}
	public void setValue(String val, ResourceBundle bundle) {
		value = val;
		createValueWithoutArticle(bundle);
	}
	public String getSecondValue() {
		return secValue;
	}
	private void createValueWithoutArticle(ResourceBundle bundle) {
		if(((String)bundle.getObject("article.a")).equals("")) {
			secValue = value;
			return ;
		}
		String verb;
		String animal = "a(n | ).*";
		secValue = value;
		if(value.matches(animal)) {
			secValue = value.substring(value.indexOf(' ') + 1);
		}
		else {
			if (value.contains("Can")) {
				verb = "It can";
			}
			else if (value.contains("Does")) {
				verb = "It has";
				secValue = secValue.substring(secValue.indexOf(" ") + 1);
			}
			else {
				verb = "It is";
			}
			secValue = secValue.substring(secValue.indexOf(" ") + 1, secValue.length() - 1);
			secValue = verb + secValue.substring(secValue.indexOf(" ")) + '.';
		}
	}
	public ArrayList<String> getFacts() {
		return (facts);
	}
	public void setFacts(ArrayList<String> facts) {
		this.facts.addAll(facts);
	}
	public String getSecValue() {
		return secValue;
	}
	public String getValue() {
		return value;
	}
	public boolean isAnimal() {
		//System.out.println("this is animal: " + this.getValue() + this.no + this.yes);
		return this.getNoChild() == null && this.getYesChild() == null;
	}
	public void clearFacts() {
		this.facts.clear();
	}
	public void setNoChild(Node child) {
		if (child != null)
			this.no = new Node(child);
		else
			this.no = new Node();
	}
	public void setYesChild(Node child) {
		if (child != null)
			this.yes = new Node(child);
		else
			this.yes = new Node();
	}
	public Node getNoChild() {
		return no;
	}
	public Node getYesChild() {
		return yes;
	}
	public void printInfo() {
		System.out.println(this.getValue());
	}
}
