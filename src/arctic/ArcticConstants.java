package arctic;

public class ArcticConstants {
	
	//world
	public final static double[] WINDOW = {-10,10,-10,10};
	
	//Terrain 
	public final static int NROWS = 45;
	public final static int NCOLS = 60;
	public static enum PatchType{
		ICE,
		WATER
	}
	public final static double[] PATCH_TYPE_PROB = {.15f, .85f};
	public final static int NSTATES = PatchType.values().length;
	public static int[][]TERRAIN_COLORS = {
			{255,255,255}, {9, 195, 219}
	};
	public final static float[] REGENERATION_TIME = {100.f,200.f};//seconds
	
	public static int POLAR_BEAR_POPULATION = 5;
	public static int SEAL_POPULATION = 15;
	public static int COD_POPULATION = 25;
	public static int SHRIMP_POPULATION = 50;
	public static int PLANKTON_POPULATION = 100;
	
}
