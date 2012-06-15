package edu.cwru.SimpleRTS.model;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.prefs.BackingStoreException;

import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.cwru.SimpleRTS.action.Action;
import edu.cwru.SimpleRTS.action.ActionResult;
import edu.cwru.SimpleRTS.action.DirectedAction;
import edu.cwru.SimpleRTS.agent.Agent;
import edu.cwru.SimpleRTS.agent.MimicAgent;
import edu.cwru.SimpleRTS.agent.ScriptedGoalAgent;
import edu.cwru.SimpleRTS.agent.visual.VisualAgent;
import edu.cwru.SimpleRTS.environment.Environment;
import edu.cwru.SimpleRTS.environment.SequentialTurnTracker;
import edu.cwru.SimpleRTS.environment.State;
import edu.cwru.SimpleRTS.environment.StateCreator;
import edu.cwru.SimpleRTS.model.resource.ResourceNode;
import edu.cwru.SimpleRTS.model.resource.ResourceType;
import edu.cwru.SimpleRTS.model.unit.Unit;
import edu.cwru.SimpleRTS.model.unit.UnitTemplate;
import edu.cwru.SimpleRTS.util.DefaultConfigurationGenerator;
import edu.cwru.SimpleRTS.util.TypeLoader;

public class ModelAndPlannerTimeTest {

	static Model model;
	static int player=0;
	static int enemy = 1;
	static int seed = 324234;
	
	@Test
	public void testTime() throws IOException, JSONException, InterruptedException, BackingStoreException {
		boolean watchCalc=false;//An easy access point in case you want to watch the process
		boolean watchActual=false;//An easy access point in case you want to watch the process
		DefaultConfigurationGenerator.main(null);//This is a horrible monstrosity and I hope someone deprecates it
		Model model = makeBaselineModel();
		//calculate the moves
		Map<Integer,Map<Integer,Action>> calculatedActions=new HashMap<Integer,Map<Integer,Action>>();
		{
			VisualAgent seer=null;
			if (watchCalc)
				seer = new VisualAgent(Agent.OBSERVER_ID, new String[]{"false","true"});
			ScriptedGoalAgent scriptedAgent = new ScriptedGoalAgent(player, new BufferedReader(new FileReader("data/timetest_script.txt")), new Random(seed), watchCalc);
			Agent[] agents;
			agents = new Agent[]{scriptedAgent};
			model.createNewWorld();
			Environment calcEnv = new Environment(agents, model, new SequentialTurnTracker(new Random(seed)));
			while (!calcEnv.isTerminated()) {
				int thisStep = model.getState().getTurnNumber(); 
				calcEnv.step();//Do the step so have the results for it.
				Map<Integer, Action> thisStepsActions = new HashMap<Integer,Action>();
				//get all of the commands issued
				Map<Integer, Action> commandsIssued = model.getHistory().getPlayerHistory(player).getCommandsIssued().getActions(thisStep);
				for (Entry<Integer, Action> a : commandsIssued.entrySet()) {
					thisStepsActions.put(a.getKey(), a.getValue());
				}
				if (watchCalc)
				{
					System.out.println("Step "+thisStep);
					System.out.println("New commands: "+commandsIssued);
					System.out.println("New command feedback: "+ model.getHistory().getPlayerHistory(player).getCommandFeedback().getActionResults(thisStep));
					System.out.println("New primitive feedback: "+ model.getHistory().getPlayerHistory(player).getPrimitiveFeedback().getActionResults(thisStep));
					//Sort of hackish, should be replaced when method becomes right
					seer.middleStep(model.getState().getView(Agent.OBSERVER_ID), model.getHistory().getView(Agent.OBSERVER_ID));
					System.out.println("now has "+model.getState().getResourceAmount(player, ResourceType.GOLD)+" gold");
					System.out.println("Unit is at "+model.getState().getUnit(0).getxPosition() + ","+model.getState().getUnit(0).getyPosition());
					System.out.println("Total nodes: "+model.getState().getResources().size());
				}
				
				calculatedActions.put(thisStep, thisStepsActions);
//				System.out.println("Step "+ thisStep + " has " + calculatedActions.get(thisStep).size() +" actions");
			}
		}
		int nrounds=100;
		long starttime = System.currentTimeMillis();
		for (int x = nrounds; x>0;x--)
		{
			MimicAgent mimic = new MimicAgent(player, calculatedActions);
			Agent[] agents;
			if (watchActual) {
				agents = new Agent[]{mimic,new VisualAgent(player, new String[]{"false","true"})};
			}
			else {
				agents = new Agent[]{mimic};
			}
			model.createNewWorld();
			Environment testEnv = new Environment(agents, model, new SequentialTurnTracker(new Random(seed)));
			while (!testEnv.isTerminated()) {
				int thisStep = model.getState().getTurnNumber(); 
				testEnv.step();//Do the step so have the results for it.
			}
		}
		long timetaken = System.currentTimeMillis()-starttime;
		System.out.println(nrounds+" repetitions took " + timetaken +" ms");
			
		
		
	}
	/**
	 * Build the model to be used as the baseline
	 * @return
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws BackingStoreException 
	 */
	private Model makeBaselineModel() throws IOException, JSONException, BackingStoreException {
		boolean durative = false;
		final String templatesource = "data/timetest_templates";
		//First build a state
		State s = new State();
		s.setSize(34, 34);
		s.addPlayer(player);
		for (Template<?> t : TypeLoader.loadFromFile(templatesource, player, s)) {
			if (!durative)
			{
				t.setTimeCost(1);
				if (t instanceof UnitTemplate)
				{
					((UnitTemplate)t).setDurationAttack(1);
					((UnitTemplate)t).setDurationMove(1);
					((UnitTemplate)t).setDurationDeposit(1);
					((UnitTemplate)t).setDurationGatherGold(1);
					((UnitTemplate)t).setDurationGatherWood(1);
					assertTrue("Bad coding, casting creates a new object",((UnitTemplate)t).getDurationGatherGold()==1);
				}
			}
			s.addTemplate(t);
		}
		s.addPlayer(enemy);
		for (Template<?> t : TypeLoader.loadFromFile(templatesource, enemy, s)) {
			if (!durative) //not strictly necessary, the enemy shouldn't be doing anything
			{
				t.setTimeCost(1);
				if (t instanceof UnitTemplate)
				{
					((UnitTemplate)t).setDurationAttack(1);
					((UnitTemplate)t).setDurationMove(1);
					((UnitTemplate)t).setDurationDeposit(1);
					((UnitTemplate)t).setDurationGatherGold(1);
					((UnitTemplate)t).setDurationGatherWood(1);
					assertTrue("Bad coding, casting creates a new object",((UnitTemplate)t).getDurationGatherGold()==1);
				}
			}
			s.addTemplate(t);
		}
		s.addResourceAmount(player, ResourceType.GOLD,1200);
		s.addResourceAmount(player, ResourceType.WOOD,800);
		s.addUnit((Unit)s.getTemplate(player, "Peasant").produceInstance(s), 12, 12);
		//Due to this version's inadequate adjustment for collisions, the script can completely lock up with even small changes here
		s.addResource(new ResourceNode(ResourceNode.Type.GOLD_MINE, 1, 2, 500, s.nextTargetID()));
		s.addResource(new ResourceNode(ResourceNode.Type.GOLD_MINE, 0, 2, 2900, s.nextTargetID()));
		s.addResource(new ResourceNode(ResourceNode.Type.GOLD_MINE, 3, 2, 200, s.nextTargetID()));
		s.addResource(new ResourceNode(ResourceNode.Type.GOLD_MINE, 4, 2, 200, s.nextTargetID()));
		s.addResource(new ResourceNode(ResourceNode.Type.GOLD_MINE, 12, 28, 2000, s.nextTargetID()));
		s.addResource(new ResourceNode(ResourceNode.Type.TREE, 2, 22, 600, s.nextTargetID()));
		s.addResource(new ResourceNode(ResourceNode.Type.TREE, 3, 22, 600, s.nextTargetID()));
		s.addResource(new ResourceNode(ResourceNode.Type.TREE, 4, 22, 600, s.nextTargetID()));
		s.addResource(new ResourceNode(ResourceNode.Type.TREE, 5, 22, 600, s.nextTargetID()));
		
		s.addUnit((Unit)s.getTemplate(enemy, "TownHall").produceInstance(s), 22, 22);
		StateCreator sc = s.getView(Agent.OBSERVER_ID).getStateCreator();
		return new LessSimpleModel(sc.createState(), seed, sc);
	}
}
