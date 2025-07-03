package elm;

import java.time.LocalDate;
import java.util.*;
import java.util.Scanner;

/*
 * 
 *Sandhya Sharma
 * The main class (console menu + entry point)
 * 
 */
public class LeaveApp 
{
	public static void main(String[] args)
	{
		
		
		LeaveManager leaveManager = new LeaveManager();
		Scanner sc = new Scanner(System.in);
		
		
		leaveManager.addEmployee(new Employee(101, "sandy", "sandy","sandy" ,"employee"));
		leaveManager.addEmployee(new Employee(102, "Mimi", "mimi","mimi" ,"teamlead"));
		leaveManager.addEmployee(new Employee(103, "lily", "lily","lily" ,"employee"));		
		leaveManager.addEmployee(new Employee(104, "admin", "admin","admin" ,"admin"));
		
		
		System.out.println("****Welcome to Employee LEave Management system******");
		
		
		while(true)
		{
			
			 System.out.print("\nLogin (or type 'exit' to quit)\nUsername: ");
	            String username = sc.nextLine();
	            if (username.equalsIgnoreCase("exit")) break;

	            System.out.print("Password: ");
	            String password = sc.nextLine();

	            Employee current = leaveManager.login(username, password);
	            if (current == null) {
	                System.out.println("Invalid username or password. Try again.");
	                continue;
	            }

	            System.out.println("Logged in as: " + current.getName() + " [" + current.getEmpRole()+ "]");
	            
	            
			boolean logout = false;
			
			while(!logout)
			{
				System.out.println("*****Menu****");
				
				if(current.getEmpRole().equalsIgnoreCase("employee"))
				{
					  System.out.println("1. Apply for Leave");
	                    System.out.println("2. View Leave History");
	                    System.out.println("3. View Leave Balance");
	                    System.out.println("4. Cancel a Leave Request");
	                    System.out.println("5. Logout");
	                    System.out.print("Choose option: ");
	                    
	                    
	                    int option = sc.nextInt();
	                    sc.nextLine();
	                    
	                    
	                    switch (option) {
                        case 1:
                            System.out.print("Enter leave type (sick/casual): ");
                            String type = sc.nextLine();
                            System.out.print("Enter start date (yyyy-mm-dd): ");
                            LocalDate from = LocalDate.parse(sc.nextLine());
                            System.out.print("Enter end date (yyyy-mm-dd): ");
                            LocalDate to = LocalDate.parse(sc.nextLine());
                            leaveManager.applyLeave(current.getId(), type, from, to);
                            break;
                        case 2:
                            leaveManager.showEmployeeLeaveHistory(current.getId());
                            break;
                        case 3:
                            System.out.println("Leave Balance: " );
                            for (Map.Entry<String, Integer> entry : current.getLeaveBalance().entrySet()) {
                                System.out.println(entry.getKey() + " → " + entry.getValue() + " days");
                            }
                            break;
                        case 4:{
                        	leaveManager.showEmployeeLeaveHistory(current.getId());
                        	System.out.println("Enter the leave request id:");
                        	int cancelId = sc.nextInt();
                        	leaveManager.cancelLeave(current.getId(), cancelId);
                        	break;
                        }
                        case 5:
                            logout = true;
                            break;
                        default:
                            System.out.println("Invalid option!");
                    }
                }else if (current.getEmpRole().equalsIgnoreCase("admin")) {
                    System.out.println("1. View All Leave Requests");
                    System.out.println("2. Approve Leave");
                    System.out.println("3. Reject Leave");
                    System.out.println("4. View All Employees");
                    System.out.println("5. Generate Leave Reports");
                    System.out.println("6. View Holidays");
                    System.out.println("7. Add Holiday");
                    System.out.println("8. Remove Holiday");
                    System.out.println("9. Generate Leave Reports");
                    System.out.println("10. Logout");
                   
                    
                    System.out.print("Choose option: ");
                    int option = sc.nextInt();

                    switch (option) {
                        case 1:
                            leaveManager.showAllLeaveRequest();
                            break;
                        case 2:
                            System.out.print("Enter Request ID to approve: ");
                            int approveId = sc.nextInt();
                            leaveManager.approveLeave(approveId);
                            break;
                        case 3:
                            System.out.print("Enter Request ID to reject: ");
                            int rejectId = sc.nextInt();
                            leaveManager.rejectLeave(rejectId);
                            break;
                        case 4:
                            for (Employee e : leaveManager.getAllEmployee()) {
                                System.out.println(e);
                            }
                            break;
                        case 5:
                            leaveManager.generateLeavReports();
                            break;
                        case 6:
                            leaveManager.showHolidays();
                            break;
                        case 7:
                        	System.out.println("Enter date as holiday  year-month-date");
                            LocalDate date1 = LocalDate.parse(sc.next());
                            leaveManager.addHoliday(date1);
                            break;
                        case 8:
                        	System.out.println("Enter date as holiday  year-month-date");
                            LocalDate date2 = LocalDate.parse(sc.next());
                            leaveManager.removeHoliday(date2);
                            break;
                        case 9:
                            leaveManager.generateLeavReports();
                            break;
                        case 10:
                            logout = true;
                            break;
                        default:
                            System.out.println("Invalid option!");
                    }
                } else {
                    System.out.println("Unknown role!");
                    logout = true;
                }
            }
        }

        sc.close();
    }
                	
                	
                	
             
		
}
