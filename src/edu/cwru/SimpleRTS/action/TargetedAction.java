package edu.cwru.SimpleRTS.action;

public class TargetedAction extends Action
{
	private final int targetid;
	public TargetedAction(int unitid, ActionType type, int targetid)
	{
		super(unitid, type);
		this.targetid = targetid;
	}
	public int getTargetId()
	{
		return targetid;
	}
	@Override
	public String toString() {
		return "TargetedAction [targetid=" + targetid + ", type=" + type
				+ ", unitId=" + unitId + "]";
	}
	@Override public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}
		else if (other == null || !this.getClass().equals(other.getClass()))
		{
			return false;
		}
		else
		{
			
			TargetedAction aother = (TargetedAction)other;
			return super.equals(aother) && aother.targetid == targetid;
		}
	}
	@Override public int hashCode()
	{
		int prime = 61;
		return prime * targetid + super.hashCode();
	}
}
