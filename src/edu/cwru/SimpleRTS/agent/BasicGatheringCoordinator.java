package edu.cwru.SimpleRTS.agent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import edu.cwru.SimpleRTS.action.Action;
import edu.cwru.SimpleRTS.agent.ScriptedGoalAgent.RelevantStateView;
import edu.cwru.SimpleRTS.environment.State.StateView;
import edu.cwru.SimpleRTS.model.resource.ResourceNode;
import edu.cwru.SimpleRTS.model.resource.ResourceNode.ResourceView;
import edu.cwru.SimpleRTS.model.resource.ResourceType;
import edu.cwru.SimpleRTS.model.unit.Unit.UnitView;
import edu.cwru.SimpleRTS.model.unit.UnitTask;


/**
 * A basic AI that does simple commanding of gatherer units
 *
 */
public class BasicGatheringCoordinator implements Serializable {
	private static final long serialVersionUID = 3922892996550559953L;
	
	private boolean verbose;
	public void setVerbose(boolean verbosity) {
		this.verbose=verbosity;
	}
	public boolean getVerbose() {
		return this.verbose;
	}
	private int playerID;
	private List<Integer> miners;
	private Map<Integer, Action> others;
	private List<Integer> idles;
	private List<Integer> lumberjacks;
	private Random r;
	public BasicGatheringCoordinator(int playerID, Random r) {
		miners = new ArrayList<Integer>();
		lumberjacks = new ArrayList<Integer>();
		others = new HashMap<Integer,Action>();
		idles = new ArrayList<Integer>();
		this.r=r;
	}
	public Integer getGoldWorker() {
		return miners.get(r.nextInt(miners.size()));
	}
	public Integer getWoodWorker() {
		return lumberjacks.get(r.nextInt(lumberjacks.size()));
	}
	public Integer getIdleWorker() {
		return idles.get(r.nextInt(idles.size()));
	}
	public int numGoldWorkers() {
		return miners.size();
	}
	public int numWoodWorkers() {
		return lumberjacks.size();
	}
	public int numIdleWorkers() {
		return idles.size();
	}
	public boolean hasGoldWorker(Integer id) {
		return miners.contains(id);
	}
	public boolean hasWoodWorker(Integer id) {
		return lumberjacks.contains(id);
	}
	public boolean hasIdleWorker(Integer id) {
		return idles.contains(id);
	}
	public boolean hasOtherWorker(Integer id) {
		return others.containsKey(id);
	}
	public void removeUnit(Integer unitID) {
		lumberjacks.remove(unitID);
		others.remove(unitID);
		idles.remove(unitID);
		miners.remove(unitID);
	}
	public void assignGold(Integer unitID) {
		lumberjacks.remove(unitID);
		others.remove(unitID);
		idles.remove(unitID);
		miners.add(unitID);
	}
	public void assignWood(Integer unitID) {
		miners.remove(unitID);
		others.remove(unitID);
		idles.remove(unitID);
		lumberjacks.add(unitID);
	}
	public void assignOther(Integer unitID, Action assignment) {
		miners.remove(unitID);
		lumberjacks.remove(unitID);
		idles.remove(unitID);
		others.put(unitID,assignment);
	}
	public void assignIdle(Integer unitID) {
		miners.remove(unitID);
		lumberjacks.remove(unitID);
		idles.add(unitID);
		others.remove(unitID);
	}
	public void assignActions(StateView state, RelevantStateView relstate, Map<Integer,Action> actions) {
		if (verbose)
		{
			System.out.println(miners.size() + " miners");
			System.out.println(lumberjacks.size() + " lumberjacks");
			System.out.println(idles.size() + " idle");
			System.out.println(others.size() + " others");
		}
		//for each builder or something
		for (Entry<Integer,Action> indact : others.entrySet()) {
			actions.put(indact.getKey(), indact.getValue());
		}
		//for each miner/lumberjack
		for (Integer minerID : miners)
		{
			UnitView miner = state.getUnit(minerID);
			//detect if it is carrying a resource
			if (verbose) {
				System.out.println("A miner (id:"+minerID+") is carrying: " + miner.getCargoAmount());
			}
			if (miner.getCargoAmount()>0)
			{
				//if it is, find the nearest base and tell it to go there
				assignWorkerToReturn(state, miner, actions);
			}
			else
			{
				//if it is not, find the nearest appropriate resource and tell it to go there
				int closestDist = Integer.MAX_VALUE;
				int closestID = Integer.MIN_VALUE;
				for (Integer mineID :state.getResourceNodeIds(ResourceNode.Type.GOLD_MINE)) {
					ResourceView mine = state.getResourceNode(mineID);
					int dist = Math.abs(miner.getXPosition() - mine.getXPosition()) + Math.abs(miner.getYPosition() - mine.getYPosition());
					if (dist < closestDist) {
						closestID = mineID;
						closestDist = dist;
					}
				}
				if (closestID != Integer.MIN_VALUE) {
					if (verbose) {
						System.out.println("Tossing in an action for a miner");
					}
					actions.put(minerID, Action.createCompoundGather(minerID, closestID));
				}
				else {
					if (verbose) {
						System.out.println("Couldn't find a mine");
					}
				}
				
			}
		}
		for (Integer lumberjackID : lumberjacks)
		{
			UnitView lumberjack = state.getUnit(lumberjackID);
			//detect if it is carrying a resource
			if (lumberjack.getCargoAmount()>0)
			{
				
				//if it is, find the nearest base and tell it to go there
				assignWorkerToReturn(state, lumberjack, actions);
			}
			else
			{
				//if it is not, find the nearest appropriate resource and tell it to go there
				int closestDist = Integer.MAX_VALUE;
				int closestID = Integer.MIN_VALUE;
				for (Integer treeID :state.getResourceNodeIds(ResourceNode.Type.TREE)) {
					ResourceView tree = state.getResourceNode(treeID);
					int dist = Math.abs(lumberjack.getXPosition() - tree.getXPosition()) + Math.abs(lumberjack.getYPosition() - tree.getYPosition());
					if (dist < closestDist) {
						closestID = treeID;
						closestDist = dist;
					}
				}
				if (closestID != Integer.MIN_VALUE) {
					actions.put(lumberjackID, Action.createCompoundGather(lumberjackID, closestID));
				}
				
			}
		}
	}
	private void assignWorkerToReturn(StateView state, UnitView worker, Map<Integer,Action> actions) {
		//find the nearest base and tell it to go there
		int closestID = Integer.MIN_VALUE;
		int closestDist = Integer.MAX_VALUE;
		for (Integer potentialStoragePitID : state.getUnitIds(playerID))
		{
			if (verbose) {
				System.out.println("Evaluating Unit with id "+potentialStoragePitID);
			}
			UnitView potentialStoragePit = state.getUnit(potentialStoragePitID);
			//it can still be carrying the other resource, it is better to have it choose to return with what it has than just go straight for the other resource
			if (worker.getCargoType() == ResourceType.GOLD && potentialStoragePit.getTemplateView().canAcceptGold() || worker.getCargoType() == ResourceType.WOOD && potentialStoragePit.getTemplateView().canAcceptWood())
			{
				int dist = Math.abs(worker.getXPosition() - potentialStoragePit.getXPosition()) + Math.abs(worker.getYPosition() - potentialStoragePit.getYPosition());
				if (dist < closestDist)
				{
					closestID = potentialStoragePitID;
					closestDist = dist;
				}
			}
			if (closestID != Integer.MIN_VALUE) {
				actions.put(worker.getID(), Action.createCompoundDeposit(worker.getID(), closestID));
			}
		}
	}

}
