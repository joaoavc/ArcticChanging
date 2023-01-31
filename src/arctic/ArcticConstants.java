package arctic;

public class ArcticConstants {
	
	//world
	public final static double[] WINDOW = {-10,10,-10,10};
	
	//Terrain 
	public final static int NROWS = 45;
	public final static int NCOLS = 60;
	public static enum PatchType{
		ICE,WATER
	}
	public final static double[] PATCH_TYPE_PROB = {.7f,.3f};
	public final static int NSTATES = PatchType.values().length;
	public static int[][]TERRAIN_COLORS = {
			{250,200,60},{160,30,70},{200,200,60},{40,200,20}
	};
	public final static float[] REGENERATION_TIME = {10.f,20.f};//seconds
	
	public static int POLAR_BEAR_POPULATION = 5;
	public static int SEAL_POPULATION = 15;
	public static int COD_POPULATION = 25;
	public static int SHRIMP_POPULATION = 50;
	public static int PLANKTON_POPULATION = 100;
	
}
