package enemies;

public abstract class AbstractSimpleMovementEnemy extends AbstractEnemy {

	protected double xvel, yvel;
	
	protected AbstractSimpleMovementEnemy(double startingHealth) {
		super(startingHealth);
	}

	@Override
	public double xvel() {
		return xvel;
	}
	
	@Override
	public double yvel() {
		return yvel;
	}
	
	@Override
	public void setxvel(double xvel) {
		this.xvel = xvel;
	}
	
	@Override
	public void setyvel(double yvel) {
		this.yvel = yvel;
	}
	
}
