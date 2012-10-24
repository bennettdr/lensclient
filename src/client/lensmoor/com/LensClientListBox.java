package client.lensmoor.com;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LensClientListBox extends ArrayAdapter<String> {
	private Context context;
	private String [] stringList;
	private int count;
	private int selected;

	public LensClientListBox(Context context, String [] stringList, int count, int selected) {
		super(context, R.layout.lensclientlistbox, R.id.lensclientlistitem, stringList);
		this.context = context;
		this.stringList = stringList;
		this.count = count;
		this.selected = selected;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int id;
		
		if(selected == position) {
			id = R.id.lensclientselecteditem;
		} else {
			id = R.id.lensclientlistitem;
		}
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.lensclientlistbox, parent, false);
		TextView textView = (TextView)view.findViewById(id);
		
		textView.setText(stringList[position]);
		textView.setTextColor(Color.GRAY);	
		return view;
	}
}
