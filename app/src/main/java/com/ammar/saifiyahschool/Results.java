package com.ammar.saifiyahschool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

public class Results extends Activity {

    private class NexusTypes {
        private final String name;
        Integer totalMarks;
        private final List<Nexus> list;

        NexusTypes(String name,Integer totalMarks) {
            this.name = name;
            this.totalMarks= totalMarks;
            list = new ArrayList<Nexus>();
        }

        public int size() {
            return list.size();
        }

        public Nexus get(int i) {
            return list.get(i);
        }
    }

    private class Nexus {
        private final String[] data;

        private Nexus(String name, String company, String version, String api, String storage, String inches, String ram) {
            data = new String[] {
                    name,
                    company,
                    version,
                    api,
                    storage,
                    inches,
                    ram };
        }
    }


    String url = "http://192.168.1.11/school_cms/ResultStudentMarks/index.json";

    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        requestQueue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap();
        params.put("student_class_id", String.valueOf(1));
        params.put("student_id", String.valueOf(1));
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        requestQueue.add(jsonObjectRequest);


        TableFixHeaders tableFixHeaders = (TableFixHeaders) findViewById(R.id.table);
        BaseTableAdapter baseTableAdapter = new FamilyNexusAdapter(this);
        tableFixHeaders.setAdapter(baseTableAdapter);

    }

    public class FamilyNexusAdapter extends BaseTableAdapter {

        private final NexusTypes familys[];
        private final String headers[] = {
                "Subjects",
                "1st Test",
                "2nd Test",
                "Half Yearly",
                "3rd Test",
                "Annually",
                "So.Sci",
        };

        private final String totalMarks[] = {
                "10",
                "10",
                "70",
                "10",
                "100",
        };

        private final int[] widths = {
                120,
                100,
                140,
                60,
                70,
                60,
                60,
        };
        private final float density;

        public FamilyNexusAdapter(Context context) {
            familys = new NexusTypes[] {
                    new NexusTypes("Total",10),
                    new NexusTypes("Tablets",20),
                    new NexusTypes("Others",30),

            };
            density = context.getResources().getDisplayMetrics().density;

            familys[0].list.add(new Nexus("Hindi", "HTC", "Gingerbread", "10", "512 MB", "3.7\"", "512 MB"));
            familys[0].list.add(new Nexus("English", "Samsung", "Gingerbread", "10", "16 GB", "4\"", "512 MB"));
            familys[0].list.add(new Nexus("Maths", "Samsung", "Ice cream Sandwich", "15", "16 GB", "4.65\"", "1 GB"));
            familys[0].list.add(new Nexus("Science", "Samsung", "Ice cream Sandwich", "15", "32 GB", "4.65\"", "1 GB"));
            familys[0].list.add(new Nexus("Urdu", "LG", "Jelly Bean", "17", "8 GB", "4.7\"", "2 GB"));
            familys[0].list.add(new Nexus("Social Science", "LG", "Jelly Bean", "17", "16 GB", "4.7\"", "2 GB"));
            familys[1].list.add(new Nexus("Nexus 7 (16 GB)", "Asus", "Jelly Bean", "16", "16 GB", "7\"", "1 GB"));
            familys[1].list.add(new Nexus("Nexus 7 (32 GB)", "Asus", "Jelly Bean", "16", "32 GB", "7\"", "1 GB"));
            familys[1].list.add(new Nexus("Nexus 10 (16 GB)", "Samsung", "Jelly Bean", "17", "16 GB", "10\"", "2 GB"));
            familys[1].list.add(new Nexus("Nexus 10 (32 GB)", "Samsung", "Jelly Bean", "17", "32 GB", "10\"", "2 GB"));
            familys[2].list.add(new Nexus("Nexus Q", "--", "Honeycomb", "13", "--", "--", "--"));
        }

        @Override
        public int getRowCount() {
            return 7;
        }

        @Override
        public int getColumnCount() {
            return 5;
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
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[0]);
            return convertView;
        }

        private View getHeader(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_header, parent, false);
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(headers[column + 1]);
            return convertView;
        }

        private View getFirstBody(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table_first, parent, false);
            }
            convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getDevice(row).data[column + 1]);
            return convertView;
        }

        private View getBody(int row, int column, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_table, parent, false);
            }
            convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
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
                string = totalMarks[column];
            }
            ((TextView) convertView.findViewById(android.R.id.text1)).setText(string);
            return convertView;
        }

        @Override
        public int getWidth(int column) {
            return Math.round(widths[column + 1] * density);
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
                row -= familys[family].size() + 1;
                family++;
            }
            return row == 0;
        }

        private NexusTypes getFamily(int row) {
            int family = 0;
            while (row >= 0) {
                row -= familys[family].size() + 1;
                family++;
            }
            return familys[family - 1];
        }

        private Nexus getDevice(int row) {
            int family = 0;
            while (row >= 0) {
                row -= familys[family].size() + 1;
                family++;
            }
            family--;
            return familys[family].get(row + familys[family].size());
        }

        @Override
        public int getViewTypeCount() {
            return 5;
        }
    }


}
