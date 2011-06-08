package edu.cwru.SimpleRTS.model.unit;

import edu.cwru.SimpleRTS.model.Direction;
import edu.cwru.SimpleRTS.model.Target;

public abstract class Unit {
	private static int nextID = 0;
	
	protected Target target;
	protected final int ID;
	protected int currentHealth;
	protected int player;
	protected int xPosition;
	protected int yPosition;
	protected UnitTemplate template;
	
	protected Unit(UnitTemplate template) {
		ID = nextID++;
		this.template = template;
		this.currentHealth = template.getBaseHealth();
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public int getxPosition() {
		return xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public UnitTemplate getTemplate() {
		return template;
	}
	public void move(Direction direction) {
		switch(direction) {
			case NORTH:
				yPosition--;
				break;
			case NORTHEAST:
				yPosition--;
				xPosition++;
				break;
			case EAST:
				xPosition++;
				break;
			case SOUTHEAST:
				yPosition++;
				xPosition++;
				break;
			case SOUTH:
				yPosition++;
				break;
			case SOUTHWEST:
				yPosition++;
				xPosition--;
				break;
			case WEST:
				xPosition--;
				break;
			case NORTHWEST:
				yPosition--;
				xPosition--;
				break;				
		}
	}
	@Override
	public int hashCode() {
		return ID;
	}
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Unit))
			return false;
		return ((Unit)o).ID == ID;
	}

	@Override
	public String toString() {
		return "Unit [ID=" + ID + ", unitType=" + template.getUnitName() 
				+ ", target=" + target + ", currentHealth="
				+ currentHealth + ", player=" + player + ", xPosition="
				+ xPosition + ", yPosition=" + yPosition +  "]";
	}
	
}
