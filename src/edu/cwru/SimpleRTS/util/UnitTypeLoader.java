package edu.cwru.SimpleRTS.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import org.json.*;

import edu.cwru.SimpleRTS.model.Template;
import edu.cwru.SimpleRTS.model.unit.UnitTemplate;

public final class UnitTypeLoader {
	private UnitTypeLoader(){}
	public static List<Template> loadFromFile(String filename) throws FileNotFoundException, JSONException {
		List<Template> list = new ArrayList<Template>();
		StringBuilder sb = new StringBuilder();
		Scanner in = new Scanner(new File(filename));
		while(in.hasNextLine())
		{
			sb.append(in.nextLine());
			sb.append("\n");
		}
		in.close();
		JSONObject templateFile = new JSONObject(sb.toString());
		String[] keys = JSONObject.getNames(templateFile);
		for(String key : keys)
		{
			JSONObject template = templateFile.getJSONObject(key);
			String templateType = template.getString("TemplateType");
			if("Upgrade".equals(templateType))
				list.add(handleUpgrade(template,key));
			else
				list.add(handleUnit(template,key));
		}
		return list;
	}
	private static Template handleUnit(JSONObject obj, String name) throws JSONException {
		UnitTemplate template = new UnitTemplate();
		template.setCanMove(obj.getBoolean("Mobile"));
		template.setCanBuild(obj.getBoolean("Builder"));
		template.setCanGather(obj.getBoolean("Gatherer"));
		template.setBaseHealth(obj.getInt("HitPoints"));
		template.setArmor(obj.getInt("Armor"));
		template.setAttack(obj.getInt("Attack"));
		template.setPiercingAttack(obj.getInt("Piercing"));
		template.setRange(obj.getInt("Range"));
		template.setSightRange(obj.getInt("SightRange"));
		template.setTimeCost(obj.getInt("TimeCost"));
		template.setFoodCost(obj.getInt("FoodCost"));
		template.setGoldCost(obj.getInt("GoldCost"));
		template.setWoodCost(obj.getInt("WoodCost"));
		if(obj.has("Produces"))
		{
			JSONArray produces = obj.getJSONArray("Produces");
			for(int i = 0; i < produces.length(); i++)
				template.addProductionItem(produces.getString(i));		
		}
		return template;
	}
	private static Template handleUpgrade(JSONObject obj, String name) {
		return null;
	}
}
