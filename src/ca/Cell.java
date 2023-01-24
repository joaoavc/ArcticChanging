package ca;

import processing.core.PApplet;

// Class que suporta a estrutura de uma celula
public class Cell {
	protected int row, col;
	protected int state;
	protected Cell[] neighbors;
	protected CellularAutomata ca;
	
	// Constructor
	public Cell(CellularAutomata ca, int row, int col) {
		this.ca = ca;
		this.row = row;
		this.col = col;
		this.state = 0;
		this.neighbors = null;
	}
	
	// Define os vizinhos
	public void setNeighbors(Cell[] neigh) {
		this.neighbors = neigh;
	}
	
	//Devolve os vizinhos
	public Cell[] getNeighbors() {
		return this.neighbors;
	}
	
	// Define o estado
	public void setState(int state) {
		this.state = state;
	}
	
	// Devolve o estado
	public int getState() {
		return this.state;
	}
	
	// Faz o display gráfico das cells
	public void display(PApplet p) {
		p.pushStyle();
		p.fill(ca.getStateColors()[state]);
		p.rect(ca.xmin+col*ca.cellWidth, ca.ymin+row*ca.cellHeight, ca.cellWidth, ca.cellHeight);
		p.popStyle();
	}

}
