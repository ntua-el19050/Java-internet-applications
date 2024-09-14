package exam2022db;

import java.util.Date;

public class Cases {
	
	
	private final int Id;
	private final int UserId;
	private final Date DiagnosisDate;
	private final int Method;
	private final String Location;
	private final String ReportId;
	private final String MethodName;
	
	public Cases(int id, int userId, Date diagnosisDate, int method, String location, String reportId,String methodname) {
		super();
		this.Id = id;
		this.UserId = userId;
		this.DiagnosisDate = diagnosisDate;
		this.Method = method;
		this.Location = location;
		this.ReportId = reportId;
		this.MethodName = methodname;
	}

	public int getId() {
		return Id;
	}

	public int getUserId() {
		return UserId;
	}

	public Date getDiagnosisDate() {
		return DiagnosisDate;
	}

	public int getMethod() {
		return Method;
	}

	public String getLocation() {
		return Location;
	}

	public String getReportId() {
		return ReportId;
	}
	public String getMethodName() {
		return MethodName;
	}

	@Override
	public String toString() {
		return "Cases [id=" + Id + ", userId=" + UserId + ", DiagnosisDate=" + DiagnosisDate + ", Method=" + Method
				+ ", Location=" + Location + ", ReportId=" + ReportId + "]";
	}
	
	
}
