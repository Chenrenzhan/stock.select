package controller;

import java.io.IOException;

import model.CollectionCondition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CollectConditionCtrl {
	private static final String PATH = "data/collection_condition.json";
	//保存收藏条件的最大数
	private static final int MAX_COLLECTIONS_LEN = 8;
	
	private JSONArray conditionArray;
	
	public CollectConditionCtrl(){
		String str = IORW.read(PATH);
		System.out.println("sssssssssssss    " + str);
		if(str.isEmpty()){
			str = "[]";
		}
		try {
			conditionArray = new JSONArray(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(){
		String str = conditionArray.toString();
		try {
			IORW.write(PATH, str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addCollection(CollectionCondition cc) throws JSONException{
		JSONObject jo = cc.toJsonCondition();
		int len = Math.min(MAX_COLLECTIONS_LEN, conditionArray.length());
		if(len > 0){
			for(int i = len; i > 0; --i){
				JSONObject jotemp = conditionArray.getJSONObject(i-1);
				conditionArray.put(i, jotemp);
			}
		}
		
		conditionArray.put(0, jo);
		checkOver();
		System.out.println("add    " + conditionArray.toString());
	}
	
	//检查收藏条件是否大于8
	public void checkOver(){
		for(int i = 8; i < conditionArray.length(); ++i){
			try {
				deleteConllection(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Boolean deleteConllection(int index) throws JSONException{
//		conditionArray
		if(index > conditionArray.length()){
			return false;
		}
		JSONArray ja = new JSONArray();
		for(int i = 0; i < conditionArray.length(); ++i){
			if(i == index){
				continue;
			}
			JSONObject jo = conditionArray.getJSONObject(i);
			ja.put(jo);
		}
		conditionArray = null;
		conditionArray = ja;
		return true;
	}

	public JSONArray getConditionArray() {
		return conditionArray;
	}
	
	public static void main(String[] argv){
		CollectConditionCtrl cc = new CollectConditionCtrl();
		JSONArray ja = cc.getConditionArray();
		System.out.println(ja.toString());
	}
}
