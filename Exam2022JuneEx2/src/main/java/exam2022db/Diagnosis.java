package exam2022db;

public class Diagnosis {

	
	public Diagnosis(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	private final int id;
	private final String name;
	
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "Diagnosis [id=" + id + ", name=" + name + "]";
	}
	
	
	
}
