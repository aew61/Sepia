package edu.cwru.SimpleRTS.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import edu.cwru.SimpleRTS.Log.ActionLogger;
import edu.cwru.SimpleRTS.Log.EventLogger;
import edu.cwru.SimpleRTS.action.*;
import edu.cwru.SimpleRTS.agent.Agent;
import edu.cwru.SimpleRTS.environment.State;
import edu.cwru.SimpleRTS.model.resource.ResourceNode;
import edu.cwru.SimpleRTS.model.resource.ResourceType;
import edu.cwru.SimpleRTS.model.unit.Unit;
import edu.cwru.SimpleRTS.model.unit.UnitTask;
import edu.cwru.SimpleRTS.model.unit.UnitTemplate;
import edu.cwru.SimpleRTS.model.upgrade.UpgradeTemplate;
import edu.cwru.SimpleRTS.util.Configuration;
/**
 * A "Simple" Model
 *
 */
public class SimpleModel implements Model {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -8289868580233478749L;
	private Random rand;
	private State state;
	private HashMap<Unit, ActionQueue> queuedPrimitives;
	private SimplePlanner planner;

	public SimpleModel(State init, int seed) {
		state = init;
		rand = new Random(seed);
		planner = new SimplePlanner(init);
		queuedPrimitives = new HashMap<Unit, ActionQueue>();

	}
	
	
	@Override
	public void createNewWorld() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean isTerminated() {
		int numLivePlayers = 0;
		for(int i = 0; i <= Agent.maxId() && numLivePlayers < 2; i++)
		{
			for(Unit u : state.getUnits(i).values())
			{
				if(u.getCurrentHealth() > 0)
				{
					numLivePlayers++;
					break;
				}
			}
		}
		return numLivePlayers <= 1;
	}

	@Override
	public void setActions(Action[] action) {
		for (Action a : action) {
			//NOTE: maybe make this not recalculate actions automatically
			Unit actor = state.getUnit(a.getUnitId());
			ActionQueue queue = new ActionQueue(a, calculatePrimitives(a));
			queuedPrimitives.put(actor, queue);
		}
	}
	private LinkedList<Action> calculatePrimitives(Action action) {
		LinkedList<Action> primitives = null;
		Unit actor = state.getUnit(action.getUnitId());
		switch (action.getType()) {
			case PRIMITIVEMOVE:
			case PRIMITIVEATTACK:
			case PRIMITIVEGATHER:
			case PRIMITIVEDEPOSIT:
			case PRIMITIVEBUILD:
			case PRIMITIVEPRODUCE:
			case FAILED:
				//The only primitive action needed to execute a primitive action is itself
				primitives = new LinkedList<Action>();
				primitives.add(action);
				break;
			case COMPOUNDMOVE:
				LocatedAction aMove = (LocatedAction)action;
				primitives = planner.planMove(actor, aMove.getX(), aMove.getY());
				break;
			case COMPOUNDGATHER:
				TargetedAction aGather = (TargetedAction)action;
				int resourceId = aGather.getTargetId();
				primitives = planner.planGather(actor, state.getResource(resourceId), 0);
				break;
			case COMPOUNDATTACK:
				TargetedAction aAttack = (TargetedAction)action;
				int targetId = aAttack.getTargetId();
				primitives = planner.planAttack(actor, state.getUnit(targetId));
				break;
			case COMPOUNDPRODUCE:
				ProductionAction aProduce = (ProductionAction)action;
				int unitTemplateId = aProduce.getTemplateId();
				primitives = planner.planProduce(actor, (UnitTemplate)state.getTemplate(unitTemplateId));
				break;
			case COMPOUNDBUILD:
				LocatedProductionAction aBuild = (LocatedProductionAction)action;
				int buildTemplateId = aBuild.getTemplateId();
				primitives = planner.planBuild(actor, aBuild.getX(), aBuild.getY(), (UnitTemplate)state.getTemplate(buildTemplateId));
				break;
			case COMPOUNDDEPOSIT:
				TargetedAction aDeposit = (TargetedAction)action;
				int depotId = aDeposit.getTargetId();
				primitives = planner.planDeposit(actor, state.getUnit(depotId), 0);
				break;
			default:
				primitives = null;
			
		}
		return primitives;
	}
	@Override
	public void executeStep() {
		state.getActionLog().nextRound();
		state.getEventLog().nextRound();
		//Set each agent to have no task
		for (Unit u : state.getUnits().values()) {
			u.setTask(UnitTask.Idle);
		}
		
		//Run the Action
		//TODO: make things happen appropriately in the case of dead unit, dead target, etc
		//TODO: add the remaining primitive actions
		for(ActionQueue queuedact : queuedPrimitives.values()) 
		{
			System.err.println("Doing full action: "+queuedact.getFullAction());
			//Pull out the primitive
			if (queuedact.hasNext()) 
				// should it be "while" instead of "if" ?? 
				// well, do you mean that every round, only one primitive action can be taken by one agent?
				// so, even the agent returns a compound action, only the first primitive action will be executed?
				// ---Feng
				//if is right, it pops the first each time -Scott
			{
				Action a = queuedact.popPrimitive();
				System.err.println("Doing primative action: "+a);
				//Execute it
				Unit u = state.getUnit(a.getUnitId());
				state.getActionLog().addAction(u.getPlayer(), a);
				if (u != null)
				{
					int x = u.getxPosition();
					int y = u.getyPosition();
					int xPrime = 0;
					int yPrime = 0;
					Action fullact = queuedact.getFullAction();
					switch (fullact.getType()) {
					case PRIMITIVEMOVE:
					{
						u.setTask(UnitTask.Move);
						break;
					}
					case PRIMITIVEGATHER:
					{
						Direction d = ((DirectedAction)a).getDirection();
						xPrime = x + d.xComponent();
						yPrime = y + d.yComponent();				
						ResourceNode r = state.resourceAt(xPrime,yPrime);
						if (r!=null)
							u.setTask(r.getType()==ResourceNode.Type.GOLD_MINE?UnitTask.Gold:UnitTask.Wood);
						else
							u.setTask(UnitTask.Idle);
						break;
					}
					case COMPOUNDMOVE:
					{
						u.setTask(UnitTask.Move);
						break;
					}
					case COMPOUNDPRODUCE:
					{	
						u.setTask(UnitTask.Build);
						break;
					}
					case PRIMITIVEPRODUCE:
					{
						u.setTask(UnitTask.Build);
						break;
					}
					case COMPOUNDBUILD:
					{	
						u.setTask(UnitTask.Build);
						break;
					}
					case PRIMITIVEBUILD:
					{	
						u.setTask(UnitTask.Build);
						break;
					}
					case COMPOUNDATTACK:
					{
						u.setTask(UnitTask.Attack);
						break;
					}
					case PRIMITIVEDEPOSIT:
					{
						if (u.getCurrentCargoAmount() > 0)
							u.setTask(u.getCurrentCargoType()==ResourceType.GOLD?UnitTask.Gold:UnitTask.Wood);
						else
							u.setTask(UnitTask.Idle);
						break;
					}
					case COMPOUNDGATHER:
					{
						TargetedAction thisact = ((TargetedAction)fullact);
						ResourceNode r = state.getResource(thisact.getTargetId());
						if (r != null)
							u.setTask(r.getType()==ResourceNode.Type.GOLD_MINE?UnitTask.Gold:UnitTask.Wood);
						else
							u.setTask(UnitTask.Idle);
						break;
					}
					}
					if(a instanceof DirectedAction)
					{
						Direction d = ((DirectedAction)a).getDirection();
						xPrime = x + d.xComponent();
						yPrime = y + d.yComponent();
					}
					else if(a instanceof LocatedAction)
					{
						xPrime = x + ((LocatedAction)a).getX();
						yPrime = y + ((LocatedAction)a).getY();
					}
					int timestried=0;
					while (timestried<2)
					{
						timestried++;
						switch(a.getType())
						{
							case PRIMITIVEMOVE:
								if (!(a instanceof DirectedAction))
									break;
								if(state.inBounds(xPrime, yPrime) && u.canMove() && empty(xPrime,yPrime)) {
									u.setxPosition(xPrime);
									u.setyPosition(yPrime);
								}
								else {
									queuedact.resetPrimitives(calculatePrimitives(queuedact.getFullAction()));
								}
								break;
							case PRIMITIVEGATHER:
								if (!(a instanceof DirectedAction))
									break;
								boolean failed=false;
								ResourceNode resource = state.resourceAt(xPrime, yPrime);
								if(resource == null) {
									failed=true;
								}
								else if(!u.canGather()) {
									failed=true;
								}
								else {
									int amountToExtract = u.getTemplate().getGatherRate(resource.getType());
									amountToExtract = Math.min(amountToExtract, resource.getAmountRemaining());
									u.pickUpResource(resource.getResourceType(), amountToExtract);
									resource.setAmountRemaining(resource.getAmountRemaining()-amountToExtract);
								}
								if (failed) {
									queuedact.resetPrimitives(calculatePrimitives(queuedact.getFullAction()));
								}
								break;
							case PRIMITIVEDEPOSIT:
								if (!(a instanceof DirectedAction))
									break;
								//only can do a primative if you are in the right position
									Unit townHall = state.unitAt(xPrime, yPrime);
									boolean canAccept=false;
									if (townHall!=null)
									{
										if (u.getCurrentCargoType() == ResourceType.GOLD && townHall.getTemplate().canAcceptGold())
											canAccept=true;
										else if (u.getCurrentCargoType() == ResourceType.WOOD && townHall.getTemplate().canAcceptWood())
											canAccept=true;
									}
									if(!canAccept)
									{
										queuedact.resetPrimitives(calculatePrimitives(queuedact.getFullAction()));
										break;
									}
									else {
										int agent = u.getPlayer();
										state.depositResources(agent, u.getCurrentCargoType(), u.getCurrentCargoAmount());
										u.clearCargo();
										break;
									}
							case PRIMITIVEATTACK:
								if (!(a instanceof TargetedAction))
									break;
								Unit target = state.getUnit(((TargetedAction)a).getTargetId());
								if (target!=null)
								{
									if (u.getTemplate().getRange() >= getRange(u, target))
									{
										int damage = calculateDamage(u,target);
										state.getEventLog().recordDamage(u.ID, target.ID, damage);
										target.takeDamage(damage);
									}
									else
										queuedact.resetPrimitives(calculatePrimitives(queuedact.getFullAction()));
								}
								break;
							case PRIMITIVEBUILD:
							{
								if (!(a instanceof ProductionAction))
									break;
								UnitTemplate template = (UnitTemplate)state.getTemplate(((ProductionAction)a).getTemplateId());
								u.incrementProduction(template, state.getView());
								if (template.timeCost == u.getAmountProduced())
								{
									Unit building = template.produceInstance();
									building.setxPosition(x);
									building.setyPosition(y);
									System.out.println("Checking on bug: unit with id "+u.ID);
									System.out.println(state.getUnit(u.ID));
									if (state.tryProduceUnit(building))
									{
									System.out.println(state.getUnit(u.ID));
									state.getEventLog().recordBirth(building.ID, u.ID, building.getPlayer());
									int[] newxy = state.getClosestPosition(x,y);
									u.setxPosition(newxy[0]);
									u.setyPosition(newxy[1]);
									System.out.println(state.getUnit(u.ID));
									}
									u.incrementProduction(null, state.getView());
								}
								
								break;
							}
							case PRIMITIVEPRODUCE:
							{
								if (!(a instanceof ProductionAction))
									break;
								Template template = state.getTemplate(((ProductionAction)a).getTemplateId());
								u.incrementProduction(template,state.getView());
								System.out.println(template.getName() + " takes "+template.timeCost);
								System.out.println("Produced"+u.getAmountProduced());
								if (template.timeCost == u.getAmountProduced())
								{
									if (template instanceof UnitTemplate)
									{
										Unit produced = ((UnitTemplate)template).produceInstance();
										int[] newxy = state.getClosestPosition(x,y);
										produced.setxPosition(newxy[0]);
										produced.setyPosition(newxy[1]);
										if (state.tryProduceUnit(produced))
										{
											state.getEventLog().recordBirth(produced.ID, u.ID, produced.getPlayer());
										}
									}
									else if (template instanceof UpgradeTemplate) {
										UpgradeTemplate upgradetemplate = ((UpgradeTemplate)template);
										if (state.tryProduceUpgrade(upgradetemplate.produceInstance()))
										{
											state.getEventLog().recordUpgrade(upgradetemplate.ID, upgradetemplate.getPlayer());
										}
									}
									u.incrementProduction(null, state.getView());
									
								}
								
								break;
							}
							case FAILED:
							{
								calculatePrimitives(a);
								break;
							}
						}
					}
				}
			}
		}
		
		
		//Take all the dead units and clear them
		//Find the dead units
		Map<Integer, Unit> allunits = state.getUnits();
		List<Integer> dead= new ArrayList<Integer>(allunits.size());
		for (Unit u : allunits.values()) {
			if (u.getCurrentHealth() <= 0)
			{
				state.getEventLog().recordDeath(u.ID,u.getPlayer());
				dead.add(u.ID);
			}
		}
		//Remove them
		for (int uid : dead)
		{
			state.removeUnit(uid);
		}
		
	}
	/**
	 * Get the range between two units, which is a chebyshev distance (IE, like manhattan, but with diagonals too)
	 * @param unit1
	 * @param unit2
	 * @return
	 */
	private int getRange(Unit unit1, Unit unit2) {
		return Math.max(Math.abs(unit1.getxPosition()-unit2.getxPosition()), Math.abs(unit1.getyPosition()-unit2.getyPosition()));
	}

	private int calculateDamage(Unit attacker, Unit defender)
	{
		int basic = attacker.getTemplate().getBasicAttackDiff() != 0 ?rand.nextInt(attacker.getTemplate().getBasicAttackDiff())+attacker.getTemplate().getBasicAttackLow():attacker.getTemplate().getBasicAttackLow();
		int b = rand.nextBoolean()?basic:(int)Math.ceil(basic/2);
		int p = rand.nextBoolean()?attacker.getTemplate().getPiercingAttack():(int)Math.ceil(attacker.getTemplate().getPiercingAttack()/2);
		return Math.max(0, b-defender.getTemplate().getArmor())+p;
	}
	private boolean empty(int x, int y) {
		return state.unitAt(x, y) == null && state.resourceAt(x, y) == null;
	}
	@Override
	public State.StateView getState() {
		return state.getView();
	}

}
