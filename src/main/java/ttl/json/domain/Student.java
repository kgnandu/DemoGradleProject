package ttl.json.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * 
 * 
 * @author Anil Pal
 * 
 */
public class Student implements Comparable<Student> {
	private int id;

	private String name;

	private Status status;

	private Date birthday;

	private BigDecimal grantAmount;
	
	private String phoneNumber;

	public enum Status {
		FULL_TIME, PART_TIME, HIBERNATING
	};

	private static int nextId = 0;

	public Student() {
		this("Unknown");
	}

	public Student(String name) {
		this(name, Status.FULL_TIME, new BigDecimal(0), null);
	}

	public Student(String name, Status status, BigDecimal grantAmount,
			Date birthday) {
		super();
		this.id = nextId++;
		this.name = name;
		this.status = status;
		this.grantAmount = grantAmount;
		this.birthday = birthday;
		phoneNumber = "";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public Status[] getStatusList() {
		return Status.values();
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public BigDecimal getGrantAmount() {
		return grantAmount;
	}

	public void setGrantAmount(BigDecimal grantAmount) {
		this.grantAmount = grantAmount;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", status=" + status
				+ ", birthday=" + birthday + ", grantAmount=" + grantAmount
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Integer.toBinaryString(id).hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return this.id == other.id;
	}

	@Override
	public int compareTo(Student other) {
		// compare id's
		return this.id < other.id ? -1 : this.id > other.id ? 1 : 0;
	}

}
