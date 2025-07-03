package elm;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;

public class DataStore 
{
	
	private static final String EMPLOYEE_FILE = "employee.ser";
	private static final String LEAVE_FILE = "leaves.ser";
	
	
	public static void saveEmployee(Map<Integer, Employee> employees)
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EMPLOYEE_FILE));
			oos.writeObject(employees);
			
		}catch(IOException io)
		{
			io.printStackTrace();
		}		
	}
	
	public static Map<Integer, Employee> loadEmployees()
	{
		try
		{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(EMPLOYEE_FILE));
			return   (Map<Integer, Employee>) ois.readObject();
			
		}catch(Exception io)
		{
			return null;
		}		
	}
	
	 public static void saveLeaveRequests(List<LeaveRequest> requests) {
	        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LEAVE_FILE))) {
	            oos.writeObject(requests);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public static List<LeaveRequest> loadLeaveRequests() {
	        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(LEAVE_FILE))) {
	            return (List<LeaveRequest>) ois.readObject();
	        } catch (Exception e) {
	            return null;
	        }
	    }
}
