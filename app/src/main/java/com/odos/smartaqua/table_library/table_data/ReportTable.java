package com.odos.smartaqua.table_library.table_data;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.odos.smartaqua.R;
import com.odos.smartaqua.table_library.TableFixHeaders;
import com.odos.smartaqua.table_library.adapters.BaseTableAdapter;

import java.util.ArrayList;
import java.util.List;

public class ReportTable extends AppCompatActivity {

	private class NexusTypes {
		private final String name;
		private final List<DataArray> list;

		NexusTypes(String name) {
			this.name = name;
			list = new ArrayList<>();
		}

		public int size() {
			return list.size();
		}

		public DataArray get(int i) {
			return list.get(i);
		}
	}

	private class DataArray {
		private final String[] data;

		//When its dynamic we can have array
		private DataArray(String name, String b1, String b2, String b3, String b4, String b5, String b6) {
			data = new String[] {
					name,
					b1,
					b2,
					b3,
					b4,
					b5,
					b6 };
		}
	}

	ArrayList<DataArray> cultureList= new ArrayList<>();

	ArrayList<String> headersArray= new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);

//		headersArray = (ArrayList<String>) getIntent().getSerializableExtra("headers");
		headersArray.add("Pond Name");
		headersArray.add("B1");
		headersArray.add("B2");
		headersArray.add("B3");
		headersArray.add("B4");
		headersArray.add("B5");
		headersArray.add("B6");

		//Static
		cultureList.add(new DataArray("W.S.Area","1","2","3","4","",""));
		cultureList.add(new DataArray("DOC","11","12","44","5","",""));
		cultureList.add(new DataArray("Ph","8.1","2.4","3.5","4.55","",""));
		cultureList.add(new DataArray("Salinity","4.12","2.11","3.33","1.44","",""));
		cultureList.add(new DataArray("CO3","40","50","40","60","",""));
		cultureList.add(new DataArray("HCO3","110","211","103","150","",""));
		cultureList.add(new DataArray("Ca HardNess","700","810","203","550","",""));

		TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
		BaseTableAdapter baseTableAdapter = new ReportAdapter(this);
		tableFixHeaders.setAdapter(baseTableAdapter);
	}

	public class ReportAdapter extends BaseTableAdapter {

		private final NexusTypes tableArray[];
		private final float density;

		public ReportAdapter(Context context) {
			tableArray = new NexusTypes[] {
					new NexusTypes("Alkanity"),
					new NexusTypes("Hardness"),
					new NexusTypes("Minerals"),
			};

			density = context.getResources().getDisplayMetrics().density;

			tableArray[0].list.add(cultureList.get(0));
			tableArray[0].list.add(cultureList.get(1));
			tableArray[0].list.add(cultureList.get(2));
			tableArray[1].list.add(cultureList.get(3));
			tableArray[1].list.add(cultureList.get(4));
			tableArray[1].list.add(cultureList.get(5));
			tableArray[2].list.add(cultureList.get(6));
		}

		@Override
		public int getRowCount() {
			return cultureList.size()+tableArray.length;
		}

		@Override
		public int getColumnCount() {
			return headersArray.size()-1;
		}

		@Override
		public View getView(int row, int column, View convertView, ViewGroup parent) {
			final View view;
			switch (getItemViewType(row, column)) {
				case 0:
					view = getFirstHeader(row, column, convertView, parent);
				break;
				case 1:
					view = getHeader(row, column, convertView, parent);
				break;
				case 2:
					view = getFirstBody(row, column, convertView, parent);
				break;
				case 3:
					view = getBody(row, column, convertView, parent);
				break;
				case 4:
					view = getFamilyView(row, column, convertView, parent);
				break;
				default:
					throw new RuntimeException("wtf?");
			}
			return view;
		}

		private View getFirstHeader(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_table_header_first, parent, false);
			}
			((TextView) convertView.findViewById(android.R.id.text1)).setText(headersArray.get(0));
			return convertView;
		}

		private View getHeader(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_table_header, parent, false);
			}
			((TextView) convertView.findViewById(android.R.id.text1)).setText(headersArray.get(column + 1));
			return convertView;
		}

		private View getFirstBody(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_table_first, parent, false);
			}
			convertView.setBackgroundResource(row % 2 == 0 ? R.color.white : R.color.white);
			((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
			return convertView;
		}

		private View getBody(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_table, parent, false);
			}
			convertView.setBackgroundResource(row % 2 == 0 ? R.color.white : R.color.white);
			((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
			return convertView;
		}

		private View getFamilyView(int row, int column, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.item_table_family, parent, false);
			}
			final String string;
			if (column == -1) {
				string = getFamily(row).name;
			} else {
				string = "";
			}
			((TextView) convertView.findViewById(android.R.id.text1)).setText(string);
			return convertView;
		}

		@Override
		public int getWidth(int column) {
			return Math.round(150 * density);
		}

		@Override
		public int getHeight(int row) {
			final int height;
			if (row == -1) {
				height = 35;
			} else if (isFamily(row)) {
				height = 25;
			} else {
				height = 45;
			}
			return Math.round(height * density);
		}

		@Override
		public int getItemViewType(int row, int column) {
			final int itemViewType;
			if (row == -1 && column == -1) {
				itemViewType = 0;
			} else if (row == -1) {
				itemViewType = 1;
			} else if (isFamily(row)) {
				itemViewType = 4;
			} else if (column == -1) {
				itemViewType = 2;
			} else {
				itemViewType = 3;
			}
			return itemViewType;
		}

		private boolean isFamily(int row) {
			int family = 0;
			while (row > 0) {
				row -= tableArray[family].size() + 1;
				family++;
			}
			return row == 0;
		}

		private NexusTypes getFamily(int row) {
			int family = 0;
			while (row >= 0) {
				row -= tableArray[family].size() + 1;
				family++;
			}
			return tableArray[family - 1];
		}

		private DataArray getDevice(int row) {
			int family = 0;
			while (row >= 0) {
				row -= tableArray[family].size() + 1;
				family++;
			}
			family--;
			return tableArray[family].get(row + tableArray[family].size());
		}

		@Override
		public int getViewTypeCount() {
			return 5;
		}
	}
}
