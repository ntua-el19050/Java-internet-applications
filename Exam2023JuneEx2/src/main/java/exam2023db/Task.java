package exam2023db;

import java.util.Date;

public class Task {
	
	
	private final Integer id;
	private final Integer userid;
	private final String title;
	private final String description;
	private final Integer statusId;
	private final Date dateUpdate;
	
	
	public Task(Integer userid, String title, String description, Integer statusId) {
		super();
		this.id = null;
		this.userid = userid;
		this.title = title;
		this.description = description;
		this.statusId = statusId;
		this.dateUpdate = null;
	}
	public Task(Integer id, Integer userid, String title, String description, Integer statusId, Date dateUpdate) {
		super();
		this.id = id;
		this.userid = userid;
		this.title = title;
		this.description = description;
		this.statusId = statusId;
		this.dateUpdate = dateUpdate;
	}
	public Integer getId() {
		return id;
	}
	public Integer getUserid() {
		return userid;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public Date getDateUpdate() {
		return dateUpdate;
	}
	@Override
	public String toString() {
		return "Task [id=" + id + ", userid=" + userid + ", title=" + title + ", description=" + description
				+ ", statusId=" + statusId + ", dateUpdate=" + dateUpdate + "]";
	}
	
	
	
	
	
	
	
}
