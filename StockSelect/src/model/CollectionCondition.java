package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.eclipse.swt.widgets.Text;

public class CollectionCondition {
	
	private String name;
	private ArrayList<Condition> conditionList;
	private JSONObject jsonConditon;
	
	public CollectionCondition(String name, ArrayList<Condition> conditionList){
		this.name = name;
		this.conditionList = conditionList;
	}

	public JSONObject toJsonCondition() throws JSONException{
		jsonConditon = new JSONObject();
		JSONArray ja = new JSONArray();
		for(int i = 0; i < conditionList.size(); ++i ){
			Condition c = conditionList.get(i);
			JSONObject jo = new JSONObject();
			jo.put("isChosen", c.getIsChosen());
			jo.put("index", c.getIndex());
			jo.put("min", ( (Text) c.getMin()).getText());
			jo.put("max", ((Text) c.getMax()).getText());
			ja.put(i, jo);
		}
		jsonConditon.put("name", name);
		jsonConditon.put("condition", ja);
		
		return jsonConditon;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Condition> getConditionList() {
		return conditionList;
	}

	public void setConditionList(ArrayList<Condition> conditionList) {
		this.conditionList = conditionList;
	}
}
