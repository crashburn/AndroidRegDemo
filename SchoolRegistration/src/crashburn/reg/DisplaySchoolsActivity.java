package crashburn.reg;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DisplaySchoolsActivity extends Activity {
	
	public final static String EXTRA_SCHOOL_ID = "crashburn.reg.school.id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_schools);
		// Show the Up button in the action bar.
		setupActionBar();
		new MyGetter("http://reg-crashburn.cloudfoundry.com/schools.json").execute();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_schools, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class MyGetter extends RestfulGetter {
		MyGetter(String uri) {
			super(uri);
		}
        @Override
        protected void onPostExecute(String result) {
        	
        	List<School> schools = getSchoolListFromJsonString(result);
        	if(schools.size() > 0) {
	            TableLayout table = (TableLayout) findViewById(R.id.schools_table);
	            
	            for(School school : schools) {
		            TableRow row = createRow(table.getContext(), school);
		            table.addView(row);
	            }
        	}
        }
        private TableRow createRow(final Context context, final School school) {
        	TableRow row = new TableRow(context);
            row.addView(getCell(context, school.getName()));
            row.addView(getCell(context, school.getAddress().getCity()));
            row.addView(getCell(context, school.getAddress().getState()));
            row.addView(getCell(context, school.getAddress().getZip()));
            row.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
			    	Intent intent = new Intent(context, DisplaySchoolActivity.class);
			    	intent.putExtra(EXTRA_SCHOOL_ID, school.getId());
			    	startActivity(intent);
				}
			});
        	return row;
        }
        private View getCell(Context context, String text) {
            TextView txt = new TextView(context);
            txt.setText(text);
            return txt;
        }
        private List<School> getSchoolListFromJsonString(String string) {
        	List<School> schools = new ArrayList<School>();
        	try {
	        	JSONObject obj = (JSONObject) new JSONTokener(string).nextValue();
	        	JSONArray schoolArray = obj.getJSONArray("schools");
	        	for(int i=0, len=schoolArray.length(); i < len; i++) {
	        		schools.add(getSchoolFromJSON(schoolArray.getJSONObject(i)));
	        	}
        	}
        	catch(JSONException jsone) {
        		jsone.printStackTrace();
        	}
        	return schools;
        }
        private School getSchoolFromJSON(JSONObject jsonSchool) throws JSONException {
    		School school = new School();
    		school.setId(jsonSchool.getString("id"));
    		school.setName(jsonSchool.getString("name"));
    		school.setAddress(getAddressfromJSON(jsonSchool.getJSONObject("address")));
    		return school;
        }
        private Address getAddressfromJSON(JSONObject jsonAddress) throws JSONException {
        	Address address = new Address();
        	address.setStreet(jsonAddress.getString("street"));
        	address.setCity(jsonAddress.getString("city"));
        	address.setState(jsonAddress.getString("state"));
        	address.setZip(jsonAddress.getString("zip"));
        	return address;
        }
	}
}
