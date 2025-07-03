package elm;

import java.io.Serializable;
import java.time.LocalDate;

/*
 * 
 * Contains leave details (type, date, status)
 */
public class LeaveRequest implements Serializable
{
	
	private int empId;
	private int requestId;
	private String leaveType;
	private LocalDate startDate;
	private LocalDate endDate;
	private String status;
	
	
	public LeaveRequest(int empId, int requestId, String leaveType, LocalDate startDate, LocalDate endDate) {
		super();
		this.empId = empId;
		this.requestId = requestId;
		this.leaveType = leaveType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = "pending";
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "LeaveRequest [empId=" + empId + ", requestId=" + requestId + ", leaveType=" + leaveType + ", startDate="
				+ startDate + ", endDate=" + endDate + ", status=" + status + "]";
	}
	
	
	
	
	
	

}
