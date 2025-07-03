| Functionality                  | Description                                                          |
| ------------------------------ | -------------------------------------------------------------------- |
| **Apply for Leave**            | Employee can apply for leave (start date, end date, leave type).     |
| **Leave Type Validation**      | Validates leave type (e.g., casual, sick).                           |
| **Leave Balance Check**        | Prevents applying leave if balance is insufficient.                  |
| **Request ID Generation**      | Auto-generates unique request ID for each application.               |
| **Approve by Team Lead**       | Team Lead can approve requests with status `pending_teamlead`.       |
| **Approve by HR/Admin**        | Admin can approve requests with status `pending_hr`.                 |
| **Reject by TL or Admin**      | TL can reject `pending_teamlead`, Admin can reject `pending_hr`.     |
| **Cancel Leave (any stage)**   | Employee can cancel leave, refunding balance if already approved.    |
| **Reapply after Cancellation** | Allows reapplying after a cancellation.                              |
| **Leave Balance Refund**       | Adds days back to balance on approved leave cancellation.            |
| **View Own Leave Requests**    | Employee can see their leave history.                                |
| **View Requests by Status**    | TL/Admin can see all requests by status (pending/approved/rejected). |
| **Leave Report Generation**    | Prints summary of leave taken by each employee.                      |
| **Invalid Input Handling**     | Handles invalid leave type, dates, request IDs.                      |
| **Role-Based Console Actions** | CLI separated logic for employee, team lead, and admin.              |
