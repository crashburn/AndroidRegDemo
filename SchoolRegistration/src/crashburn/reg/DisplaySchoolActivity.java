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
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class DisplaySchoolActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_school);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		// Get the schoolId passed from the other screen
		String schoolId = getIntent().getStringExtra(DisplaySchoolsActivity.EXTRA_SCHOOL_ID);
		new MyGetter("http://reg-crashburn.cloudfoundry.com/schools/" + schoolId + "/detail.json").execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_school, menu);
		return true;
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
        	
        	School school = getSchoolFromJsonString(result);

        	TextView schoolName = (TextView) findViewById(R.id.school_details_name);
        	schoolName.append(school.getName());
        	
        	TextView schoolStreet = (TextView) findViewById(R.id.school_details_street);
        	schoolStreet.append(school.getAddress().getStreet());

        	TextView schoolCityStateZip = (TextView) findViewById(R.id.school_details_citystatezip);
        	schoolCityStateZip.append(school.getAddress().getCity());
        	schoolCityStateZip.append(", ");
        	schoolCityStateZip.append(school.getAddress().getState());
        	schoolCityStateZip.append(" ");
        	schoolCityStateZip.append(school.getAddress().getZip());

        	TextView schoolGradeLevels = (TextView) findViewById(R.id.school_details_gradelevels);
        	schoolGradeLevels.append(school.getMinGradeLevel().getDisplayValue());
        	schoolGradeLevels.append(" - ");
        	schoolGradeLevels.append(school.getMaxGradeLevel().getDisplayValue());

        	
        	List<Student> students = getStudentListFromJsonString(result);
        	if(students.size() > 0) {
	            TableLayout table = (TableLayout) findViewById(R.id.school_details_students_table);
	            
	            for(Student student : students) {
		            TableRow row = createRow(table.getContext(), student);
		            table.addView(row);
	            }
        	}
        }
        private TableRow createRow(final Context context, Student student) {
        	TableRow row = new TableRow(context);
            row.addView(getCell(context, student.getLastName()));
            row.addView(getCell(context, student.getFirstName()));
            row.addView(getCell(context, student.getSex().name()));
            row.addView(getCell(context, student.getAge()));
            row.addView(getCell(context, student.getGradeLevel().getDisplayValue()));
        	return row;
        }
        private View getCell(Context context, String text) {
            TextView txt = new TextView(context);
            txt.setText(text);
            return txt;
        }
        private View getCell(Context context, int text) {
            TextView txt = new TextView(context);
            txt.setText(String.valueOf(text));
            return txt;
        }
        private School getSchoolFromJsonString(String string) {
        	School school = null;
        	try {
	        	JSONObject obj = (JSONObject) new JSONTokener(string).nextValue();
	        	JSONObject schoolObj = obj.getJSONObject("school");
        		school = getSchoolFromJSON(schoolObj);
	    	}
	    	catch(JSONException jsone) {
	    		jsone.printStackTrace();
	    	}
        	return school;
        }
        private School getSchoolFromJSON(JSONObject jsonSchool) throws JSONException {
    		School school = new School();
    		school.setName(jsonSchool.getString("name"));
    		school.setMinGradeLevel(GradeLevel.valueOf(jsonSchool.getString("minGradeLevel")));
    		school.setMaxGradeLevel(GradeLevel.valueOf(jsonSchool.getString("maxGradeLevel")));
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
        private List<Student> getStudentListFromJsonString(String string) {
        	List<Student> students = new ArrayList<Student>();
        	try {
            	JSONObject obj = (JSONObject) new JSONTokener(string).nextValue();
            	JSONArray studentArray = obj.getJSONArray("students");
            	for(int i=0, len=studentArray.length(); i < len; i++) {
            		students.add(getStudentFromJSON(studentArray.getJSONObject(i)));
            	}
        	}
        	catch(JSONException jsone) {
        		jsone.printStackTrace();
        	}
        	return students;
        }
        private Student getStudentFromJSON(JSONObject jsonStudent) throws JSONException {
    		Student student = new Student();
    		student.setFirstName(jsonStudent.getString("firstName"));
    		student.setLastName(jsonStudent.getString("lastName"));
    		student.setGradeLevel(GradeLevel.valueOf(jsonStudent.getString("gradeLevel")));
    		student.setSex(Sex.valueOf(jsonStudent.getString("sex")));
    		student.setBirthdate(jsonStudent.getLong("birthdate"));
    		return student;
        }
	}
	
}
