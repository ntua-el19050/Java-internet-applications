package exam2023db;

public class Status {

	
	private final Integer id;
	private final String name;
	
	public Status(String name) {
		this.id = null;
		this.name = name;
	}
	
	public Status(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Status [id=" + id + ", name=" + name + "]";
	}
}