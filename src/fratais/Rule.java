package fratais;

public class Rule {
	
	private char symbol;
	private String string;
	
	public Rule(char symbol, String string ) {
		this.symbol = symbol;
		this.string = string;
	}
	
	public char getSymbol() {
		return this.symbol;
	}
	
	public String getString() {
		return this.string;
	}


}
