package crashburn.reg;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Student {

	private String id;

	private String firstName;

	private String lastName;

	private Sex sex;

	private Calendar birthdate;

	private GradeLevel gradeLevel;

	private Address address;

	private PhoneNumber phoneNumber;

//	private School school;

	// Constructors:
	public Student() {
		address = new Address();
		phoneNumber = new PhoneNumber();
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Date getBirthdate() {
		return (birthdate != null) ? birthdate.getTime() : null;
	}

	public String getFormattedBirthdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(birthdate.getTime());
	}

	public void setBirthdate(Date birthdate) {
		if(this.birthdate == null) {
			this.birthdate = new GregorianCalendar();
		}
		this.birthdate.setTime(birthdate);
	}

	public void setBirthdate(long date) {
		setBirthdate(new Date(date));
	}

	public GradeLevel getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(GradeLevel gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

//	public School getSchool() {
//		return school;
//	}
//
//	public void setSchool(School school) {
//		this.school = school;
//	}

	public int getAge() {
		Calendar now = Calendar.getInstance();
		// First get the raw diffs
		int yearDiff  = now.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR);
		int monthDiff = now.get(Calendar.MONTH) - birthdate.get(Calendar.MONTH);
		int dayDiff   = now.get(Calendar.DAY_OF_MONTH) - birthdate.get(Calendar.DAY_OF_MONTH);
		// Default the age to the year difference
		int age		  = yearDiff;
		// If the birth month hasn't been reached yet, reduce the age by 1 
		if(monthDiff  < 0) {
			age--;
		}
		// If the birth month is the current month, check if the day has been reached
		else if ( (monthDiff == 0) && (dayDiff < 0) ) {
			age--;
		}
		return age;
	}

	// String Representation:
	@Override
	public String toString() {
		return  "first name: " + firstName + 
				", last name: " + lastName +
				", birthdate: " + ((birthdate != null) ? getFormattedBirthdate() : "null") +
				", age: " + ((birthdate != null) ? getAge() : "null") +
				", sex: " + sex +
				", grade level: " + gradeLevel +
				", address: " + address +
				", phone number: " + phoneNumber;
	}

}
