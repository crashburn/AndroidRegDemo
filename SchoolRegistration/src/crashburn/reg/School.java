package crashburn.reg;

public class School {
	
	private String id;
	private String name;
	private Address address;
	private GradeLevel minGradeLevel;
	private GradeLevel maxGradeLevel;	

	public School() {
		address = new Address();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public GradeLevel getMinGradeLevel() {
		return minGradeLevel;
	}

	public void setMinGradeLevel(GradeLevel aGradeLevel) {
		minGradeLevel = aGradeLevel;
	}

	public GradeLevel getMaxGradeLevel() {
		return maxGradeLevel;
	}

	public void setMaxGradeLevel(GradeLevel aGradeLevel) {
		maxGradeLevel = aGradeLevel;
	}
}
