package ca;

import processing.core.PApplet;
import processing.core.PVector;
import tools.CustomRandomGenerator;
import tools.SubPlot;

// Class que suporta a estrutura de um automato celular 2d
public class CellularAutomata {
	
	protected int nrows;
	protected int ncols;
	protected int nStates;
	private int radiusNeigh;
	protected Cell[][] cells;
	private int[] colors;
	protected float cellWidth;
	protected float cellHeight;
	protected float xmin, ymin;
	private SubPlot plt;
	
	//Construtor
	public CellularAutomata(PApplet p, SubPlot plt, int nrows, int ncols, int nStates, int radiusNeigh) {
		this.nrows = nrows;
		this.ncols = ncols;
		this.nStates = nStates;
		this.radiusNeigh = radiusNeigh;
		this.cells = new Cell [nrows][ncols];
		this.colors = new int[nStates];
		float[] bb = plt.getBoundingBox();
		xmin = bb[0];
		ymin = bb[1];
		this.cellWidth = bb[2]/ncols;
		this.cellHeight = bb[3]/nrows;
		createCells();
		setStateColors(p);
		this.plt = plt;
	}
	
	// Devolve a largura da celula
	public float getCellWidth() {
		return cellWidth;
	}
	
	// Devolve o comprimento da celula
	public float getCellHeight() {
		return cellHeight;
	}
	
	// Define as diferentes cores dos diferentes estados
	public void setStateColors(PApplet p) {
		for (int i = 0; i < this.nStates; i++) {
			colors[i] = p.color(p.random(255), p.random(255), p.random(255));
		}
	}
	
	
	// Define as diferentes cores dos diferentes estados
	public void setStateColors(int[] colors) {
		this.colors = colors;
	}
	
	
	// Devolve as diferentes cores de estado
	public int[] getStateColors() {
		return this.colors;
	}
	
	// Cria todas as celulas
	
	protected void createCells() {
		for(int i = 0; i<nrows; i++) {
			for(int j = 0; j<ncols; j++) {
				cells[i][j] = new Cell(this, i, j);
			}
		}
		setMooreNeighbors();
	}
	
	// As celulas iniciam com estados aleatorios
	public void initRandom() {
		for(int i = 0; i<nrows; i++) {
			for(int j = 0; j<ncols; j++) {
				cells[i][j].setState((int)((nStates)*Math.random()));
			}
		}
	}
	
	// As celulas iniciam com estados aleatorios
	public void initRandomCustom(double[]pmf){
		CustomRandomGenerator crg = new CustomRandomGenerator(pmf);
		for(int i=0;i<nrows;i++) {
			for(int j=0;j<ncols;j++) {
				cells[i][j].setState(crg.getRandomClass());
			}
		}
	}
	
	public PVector getCenterCell(int row, int col) {
		float x = (col+0.5f)*cellWidth;
		float y = (row+0.5f)*cellHeight;
		double[] w = plt.getWorldCoord(x,y);
		return new PVector((float) w[0],(float)w[1]);
	}
	
	public Cell world2Cell(double x, double y) {
		float[] xy = plt.getPixelCoord(x, y);
		return this.pixel2Cell(xy[0], xy[1]);
	}
	
	
	// Obter a cell na posição (x,y) em pixeis
	public Cell pixel2Cell(float x, float y) {
		int row = (int)((y-ymin)/cellHeight);
		int col = (int)((x-xmin)/cellWidth);
		if(row >= nrows) row = nrows - 1;
		if(col >= ncols) col = ncols -1 ;
		if(row < 0) row = 0;
		if(col < 0) col = 0;
		return cells[row][col];
	}
	
	// Definir a vizinhança de Moore
	protected void setMooreNeighbors() {
		int NN = (int)Math.pow(2*radiusNeigh+1, 2);
		for(int i = 0; i<nrows; i++) {
			for(int j = 0; j<ncols; j++) {
				Cell[] neigh = new Cell[NN];
				int n = 0;
				for(int ii = -radiusNeigh; ii<=radiusNeigh; ii++) {
					int row = (i + ii + nrows) % nrows;
					for(int jj = -radiusNeigh; jj<=radiusNeigh; jj++) {
						int col = (j + jj + ncols) % ncols;
						neigh[n++] = cells[row][col];
					}
				}
				cells[i][j].setNeighbors(neigh);
			}
		}
	}
	
	// Display gráfica do Automato cellular
	public void display(PApplet p) {
		for(int i = 0; i<nrows; i++) {
			for(int j = 0; j<ncols; j++) {
				cells[i][j].display(p);
			}
		}
	}
}
