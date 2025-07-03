package elm;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/*
 * 
 * 21 June 25
 * Sandhya
 * 
 * Holds employee data, leave balance
 */
public class Employee implements Serializable
{
	
	private int id;
	private String name;
	private String empRole;  //admin or employee
	String username;
	String password;
	
	private Map<String,Integer> leaveBalance;   // update for advance
	
	 public Employee(int id, String name, String username, String password, String empRole)  {
		super();
		this.id = id;
		this.name = name;
		this.empRole = empRole;
		this.username = username;
		this.password = password;
		this.leaveBalance = new HashMap<>();
        leaveBalance.put("sick", 5);
        leaveBalance.put("casual", 5);
        leaveBalance.put("earned", 10);
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
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

	public String getEmpRole() {
		return empRole;
	}

	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}


	public Map<String, Integer> getLeaveBalance() {
		return leaveBalance;
	}


	public int getBalance(String type)
	{
	   return leaveBalance.getOrDefault(type.toLowerCase(), 0);
	}
	
	public void deductLeave(String type,int days)
	{
		leaveBalance.put(type.toLowerCase(), getBalance(type) - days);
	}
	
	
	

	@Override
	public String toString() {
		return "Employee [empId=" + id + ", name=" + name + ", empRole=" + empRole + ", leaveBalance=" + leaveBalance
				+ "]";
	}
	
	
	
	
	

}
