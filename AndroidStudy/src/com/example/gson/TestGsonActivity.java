package com.example.gson;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * GSON����JSON����
 * @author dream
 *
 */
public class TestGsonActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		/*
		//����josn����ת������Ӧ����
		String jsonData = "{id:1, name=\"zhangsan\", sex=\"��\"}";
		Person p = new Gson().fromJson(jsonData, Person.class);   //
		TextView text = new TextView(this);
		text.setText(p.getId()+" "+ p.getName()+ "��"+p.getSex());
		*/
		
		//����json����ת����list
		String jsonData = "[{id:1, name=\"zhangsan\", sex=\"��\"}," +
				"{id:2, name=\"lisi\", sex=\"��\"}]";

		List<Person> list = new Gson().fromJson(jsonData,
				new TypeToken<List<Person>>(){}.getType());
		StringBuilder sd = new StringBuilder();
		for(int t=0; t<list.size(); ++t){
			sd.append(list.get(t));
		}
		TextView text = new TextView(this);
		text.setText(sd.toString());
		setContentView(text);
	}
}
