package elm;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/*
 * 
 * Handles logic for applying/approving/rejecting
 * 
 */
public class LeaveManager 
{
	
	private  List<LeaveRequest> leaveRequests;
	private Map<Integer,Employee> employees;  // for look by id
	private List<LocalDate> holidays = new ArrayList<LocalDate>();
	int requestCounter = 1;
			
			
	public LeaveManager() {
		super();
		this.leaveRequests = DataStore.loadLeaveRequests();
		if(leaveRequests == null)
		{
			leaveRequests = new ArrayList<LeaveRequest>();
		}
		
		
		this.employees = DataStore.loadEmployees();
		if(employees == null)
		{
			employees = new HashMap<Integer, Employee>();
		}
		
		  // Update requestCounter to avoid ID clashes
		for(LeaveRequest lr : leaveRequests)
		{
			requestCounter = Math.max(requestCounter, lr.getRequestId() +1);
		}
	}
	
	public Employee login(String username, String password) {
	    for (Employee emp : employees.values()) {
	        if (username != null && password != null &&
	            username.equals(emp.getUsername()) &&
	            password.equals(emp.getPassword())) {
	            return emp;
	        }
	    }
	    return null;
	}
	
	//add employee
	public void addEmployee(Employee emp)
	{
		employees.put(emp.getId(), emp);
		DataStore.saveEmployee(employees);
	}
	
	
	// get employee
	public Employee getEmployeeById(int id)
	{
		return employees.get(id);
	}
	
	
	// getall employee
	public Collection<Employee> getAllEmployee()
	{
		return employees.values();
	}
	
	
	// apply for leave 
	//Checks if the employee exists.

//Validates the leave type (like sick/casual).

//Makes sure the leave doesn’t fall on holidays.

//Ensures they have enough leave balance.

//Creates and saves the leave request.

//Confirms the application with a request ID.
	
	public void applyLeave(int id,String leaveType,LocalDate from,LocalDate to)
	{
		Employee emp = employees.get(id);
		
		if(emp == null)
		{
			System.out.println("Employee not found");
			return;
		}
		
		leaveType = leaveType.toLowerCase();
		if(!emp.getLeaveBalance().containsKey(leaveType))
		{
			System.out.println("Invalliud leave type : sick,casual,earned ");
			return;
		}
		
		//check for holidays conflict
		for(LocalDate date =from ; !date.isAfter(to);date = date.plusDays(1))
		{
			if(holidays.contains(date))
			{
				System.out.println("cannot apply leave on holiday" +date);
				return;
			}
		}
		
		
		long days = ChronoUnit.DAYS.between(from, to) + 1;
		
		if(days > emp.getBalance(leaveType))
		{
			System.out.println("Insufficient leave balance");
			return;
		}
		
		LeaveRequest leaveReq = new LeaveRequest(requestCounter++, id, leaveType, from, to);
		leaveReq.setStatus("pending_teamlead ");
		leaveRequests.add(leaveReq);
		DataStore.saveLeaveRequests(leaveRequests);
		System.out.println("Leave applied : Request id :" +leaveReq.getRequestId());
		
	}
	
	//approve leave 
	
	public void approveLeave(int reqId)
	{
		for(LeaveRequest req : leaveRequests)
		{
			if(req.getRequestId()== reqId && req.getStatus().equals("pending"))
			{
				Employee emp = employees.get(req.getRequestId());
			     long days = ChronoUnit.DAYS.between(req.getStartDate(),req.getEndDate()) + 1;
			     emp.deductLeave(req.getLeaveType(), (int)days);
			     req.setStatus("approved");
			     
			     DataStore.saveLeaveRequests(leaveRequests);
			     DataStore.saveEmployee(employees);
			     System.out.println("LEave req approved");
			     return;
			}
		}
		System.out.println("Reuqest not found or already processed :");
		
	}
	
	//Reject leave 
	public void rejectLeave(int reqId)
	{
		for(LeaveRequest req : leaveRequests)
		{
			if(req.getRequestId()== reqId && req.getStatus().equals("pending"))
			{
				req.setStatus("rejected");
				DataStore.saveLeaveRequests(leaveRequests);
				System.out.println("leave rejected");
				
				return;
			}
			
		}
	}
	

	//show all leave reuest
	public void showAllLeaveRequest()
	{
		for(LeaveRequest req : leaveRequests)
		{
			System.out.println(req);
		}
	}
	
	

	// Show leave requests of specific employee
	public void showEmployeeLeaveHistory(int empId)
	{
		for (LeaveRequest req : leaveRequests) {
		    System.out.println("Checking request for empId: " + req.getEmpId());
		    if (req.getEmpId() == empId) {
		    	System.out.println("Request ID: " + req.getRequestId());
		    	System.out.println("Type: " + req.getLeaveType());
		    	System.out.println("From: " + req.getStartDate() + " To: " + req.getEndDate());
		    	System.out.println("Status: " + req.getStatus());
		    	System.out.println("-------------------------------");
		    }
		}
	}
	
	
	//cancel request
	public void cancelLeave(int empId,int reuestId)
	{
		for(LeaveRequest lr : leaveRequests)
		{
			
			if(lr.getRequestId() == reuestId && lr.getEmpId() == empId)
			{
				if(lr.getStatus().equals("cancelled"))
				{
					System.out.println("The request is already cancell");
					return;
				}
				
				if(lr.getStatus().equals("approved"))
				{
					 long days = ChronoUnit.DAYS.between(lr.getStartDate(), lr.getEndDate()) + 1;
		                Employee emp = employees.get(empId);
		                emp.getLeaveBalance().put(
		                    lr.getLeaveType(),
		                    emp.getBalance(lr.getLeaveType()) + (int) days
		                );
		                DataStore.saveEmployee(employees);
				}
				   lr.setStatus("cancelled");
		            DataStore.saveLeaveRequests(leaveRequests);
		            System.out.println("Leave request cancelled successfully.");
		            return;
				
			}
			
			System.out.println("Leave request not found or not accessible.");
		}	
		
		  
	}
	
	// generate leave reports
	public void generateLeavReports()
	{
		
		Map<Integer,Integer> approvedLeaveCount = new HashMap<Integer, Integer>();
		Map<String,Integer> leaveTypeCount  = new HashMap<String, Integer>();
		
		for(LeaveRequest req : leaveRequests)
		{
			//count approved leave only
			if(req.getStatus().equals("approved"))
			{
				int days = (int) (ChronoUnit.DAYS.between(req.getStartDate(), req.getEndDate()) +1);				
				approvedLeaveCount.put(req.getEmpId(), approvedLeaveCount.getOrDefault(req.getEmpId(), 0) + days);
				
				
				String type = req.getLeaveType().toLowerCase();
				leaveTypeCount.put(type, leaveTypeCount.getOrDefault(type, 0) + days);
			}		
		}
		System.out.println("*******Approved leave summary ****************");
		for(Map.Entry<Integer,Integer> entry : approvedLeaveCount.entrySet())
		{
			Employee emp = employees.get(entry.getKey());
			if (emp != null) {
			    System.out.println(emp.getName() + " -> " + entry.getValue() + " days taken");
			} else {
			    System.out.println("Unknown Employee (ID: " + entry.getKey() + ") -> " + entry.getValue() + " days taken");
			}
		}
		
		
		System.out.println("*******Leave type breakdown  ****************");
		for(Map.Entry<String,Integer> entry : leaveTypeCount.entrySet())
		{
			System.out.println(entry.getKey() + " -> " + entry.getValue() + " days taken");
		}
		
		// top 3 leave takers
		System.out.println("*****Top 3 leave takers ");
		approvedLeaveCount.entrySet().stream()
		                         .sorted((a,b) -> b.getValue() - a.getValue())
		                         .limit(3)
		                         .forEach(entry -> {
		                        	 Employee emp = employees.get(entry.getKey());
		                        	 if (emp != null) {
		                        	     System.out.println(emp.getName() + " -> " + entry.getValue() + " days taken");
		                        	 } else {
		                        	     System.out.println("Unknown Employee (ID: " + entry.getKey() + ") -> " + entry.getValue() + " days taken");
		                        	 }
		                         });
		
	}
	//**********holidays ************//
	public void addHoliday(LocalDate date)
	{
		if(!holidays.contains(date))
		{
			holidays.add(date);
			System.out.println("Holiday added"+date);
		}else
		{
			System.out.println("Holidays are already exits");
		}
		
		
	}
	
	public void removeHoliday(LocalDate date)
	{
		
		 if (holidays.remove(date)) {
		        System.out.println("Holiday removed: " + date);
		    } else {
		        System.out.println("Holiday not found.");
		    }
		
	}
	
	public void showHolidays()
	{
		
		if (holidays.isEmpty()) {
	        System.out.println("No holidays added yet.");
	    } else {
	        System.out.println("=== Holiday Calendar ===");
	        for (LocalDate date : holidays) {
	            System.out.println(date);
	        }
	    }
	}
	
	
	
}
