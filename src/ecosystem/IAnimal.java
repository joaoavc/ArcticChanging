package ecosystem;

public interface IAnimal {
	public Animal reproduce(Terrain terrain);
	public void eat(Terrain terrain);
	public void energyConsumption(float dt, Terrain terrain);
	public boolean die();
}
