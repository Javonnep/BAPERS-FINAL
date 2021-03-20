package PROCESS;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class TaskInAJob {


//	private int JobTaskID;
	private int jobID;
	private int taskID;
	private int accountNumber;
	private String taskStartTime;
	private String employeeCompletedBy;
	private int shiftCompleted;
	private String jobUrgency;
	private String taskDeadline;
	private int actualDuration;
	private int isCompleted = 0;
	private static ArrayList<String> sqlStatements = new ArrayList<>();

	public static void addToSqlStatements(String sql) {
		sqlStatements.add(sql);
	}

	static String url = "jdbc:mysql://localhost:3306/Bapers";
	static String username = "jaroviadb";
	static String password = "Jarovia123#@!";
	static Connection connection;

	static {
		try {
			connection = DriverManager.getConnection(
					url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public static void UpdateJob(String columnToEdit, String newValue, String identifierOfTableToEdit, String identifierCurrentValue) throws SQLException {
		String sql = "UPDATE TaskInAJob SET " + columnToEdit + " = " + newValue + " WHERE " + identifierOfTableToEdit + " = " + identifierCurrentValue + ";" ;
		System.out.println(sql);
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}

	public static void UpdateJob(String columnToEdit, int newValue, String identifierOfTableToEdit, int identifierCurrentValue) throws SQLException {

		// TaskInAJob.UpdateJob("TaskPrice", 76, "TaskID", 1);
		String sql = "UPDATE TaskInAJob SET " + columnToEdit + " = " + newValue + " WHERE " + identifierOfTableToEdit + " = " + identifierCurrentValue + ";" ;
		System.out.println(sql);
		System.out.println(sql);
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);

	}

	// you must create the object
	public TaskInAJob(int jobID, int taskID, int accountNumber, String jobUrgency) throws SQLException {

		this.jobID = jobID;
		this.taskID = taskID;
		this.accountNumber = accountNumber;
		this.taskStartTime = taskStartTime;
		this.employeeCompletedBy = employeeCompletedBy;
		this.shiftCompleted = shiftCompleted;
		this.jobUrgency = jobUrgency;
		this.taskDeadline = taskDeadline;
		this.actualDuration = actualDuration;
		this.isCompleted = isCompleted;

		java.util.Date date = new java.util.Date();
		String DateOfJob = date.toString();

		/**
		 * calendar code built up from https://stackoverflow.com/questions/18733582/calculating-a-future-date#18733637
		 * */
		// TODO TaskDeadline is not needed
		if (jobUrgency == "normal"){
			Calendar c = Calendar.getInstance(); // create calendar object
			c.setTime(new java.util.Date()); //
			c.add(Calendar.DATE, 1);
			java.util.Date JobDeadlineDate = c.getTime();
			taskDeadline = JobDeadlineDate.toString();
		}
		else if (jobUrgency == "urgent"){
			Calendar c = Calendar.getInstance();
			c.setTime(new java.util.Date());
			c.add(Calendar.HOUR, 6);
			java.util.Date JobDeadlineDate = c.getTime();
			taskDeadline = JobDeadlineDate.toString();
		}
		else if (jobUrgency == "vurgent"){
			Calendar c = Calendar.getInstance();
			c.setTime(new java.util.Date());
			c.add(Calendar.HOUR, 3);
			java.util.Date JobDeadlineDate = c.getTime();
			taskDeadline = JobDeadlineDate.toString();
		}

		// public TaskInAJob(int jobID, int taskID, int accountNumber, String jobUrgency) throws SQLException {

		String sql =
				"INSERT INTO TaskInAJob (JobID, TaskID, AccountNumber, JobUrgency) VALUES ("
						+ Job.getAccountNumber()+ ", " + taskID + ", " + accountNumber + ", \"" + jobUrgency + "\"" + ");";


		System.out.println(sql);
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}

	// "insert into tasks", runs when someone presses "insert task"
	public static void TaskInAJobString(int JobID, int taskID, int accountNumber, String jobUrgency) throws SQLException {
		/*possible bug is the taskID not changing when the user switches in the dropdown menu.
		* solution will be to have each different dropdown call TaskDescription.setTaskID()
		* and change it to the updated
		* */

		/*Clicking on a task from the dropdown should create a TaskDescription object. This would avoid the bug. */



		String sql =
		"INSERT INTO TaskInAJob (JobID, TaskID, AccountNumber, JobUrgency) VALUES ("
		+ Job.getAccountNumber() + ", " + TaskDescription.getTaskIDStatic() + ", " + Job.getAccountNumber() + ", \"" + Job.getUrgency() + "\"" + ");";

		TaskInAJob.addToSqlStatements(sql);


	}

	//	This method should be called everytime the user selects insert job
	public static void EnterTasksIntoJob() throws SQLException {


		for(String sqlInsert : sqlStatements){
			System.out.println(sqlInsert);
//			Statement statement = connection.createStatement();
//			statement.executeUpdate(sqlInsert);
		}
	}

	public static void main(String[] args) throws SQLException {

		Job job = new Job(9, "normal");
		TaskInAJobString(Job.getJobID(), TaskDescription.getTaskIDStatic(), Job.getAccountNumber(), Job.getUrgency());
		EnterTasksIntoJob();

	}


	public static ArrayList<String[]> GetTIJList() throws SQLException {
		Statement statement = connection.createStatement();
		String sql = "SELECT * FROM TaskInAJob;";
		ResultSet resultSet = statement.executeQuery(sql);

		ArrayList<String[]> arrayList = new ArrayList<String[]>();
		String tuple;
		// adding changes to an array list
		while (resultSet.next()){

			int jobID = resultSet.getInt("JobID");
			int taskID = resultSet.getInt("TaskID");
			int accountNumber = resultSet.getInt("AccountNumber");
			String taskStartTime = resultSet.getString("TaskStartTime");
			String employeeCompletedBy = resultSet.getString("EmployeeCompletedBy");
			int shiftCompleted = resultSet.getInt("ShiftCompleted");
			String jobUrgency = resultSet.getString("JobUrgency");
			int actualDuration = resultSet.getInt("ActualDuration");
			int isCompleted = resultSet.getInt("IsCompleted");
			String taskDeadline = resultSet.getString("TaskDeadline");



			tuple = jobID + "`"
					+ taskID + "`"
					+ accountNumber + "`"
					+ taskStartTime + "`"
					+ employeeCompletedBy + "`"
					+ shiftCompleted + "`"
					+ jobUrgency + "`"
					+ actualDuration + "`"
					+ isCompleted + "`"
					+ taskDeadline;

			arrayList.add(tuple.split("`"));

		}
		return arrayList;
	}


	/**
	 * 
	 * @param TaskDescription
	 * @param TaskLocation
	 * @param TaskPrice
	 * @param TaskDuration
	 */
	public boolean AddNewTask(String TaskDescription, String TaskLocation, float TaskPrice, int TaskDuration) {
		// TODO - implement Task.AddNewTask
		throw new UnsupportedOperationException();
	}

	public void StartTask() {
		// TODO - implement Task.StartTask
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param TaskID
	 */
	public boolean RemoveTask(int TaskID) {
		// TODO - implement Task.RemoveTask
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param TaskID
	 */
	public TaskInAJob RetrieveTasks(int TaskID) {
		// TODO - implement Task.RetrieveTask
		throw new UnsupportedOperationException();
	}

	public void CompleteTask() {
		// TODO - implement Task.CompleteTask
		throw new UnsupportedOperationException();
	}

	public void DestroyTask() {
		// TODO - implement Task.DestroyTask
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param TaskData
	 */
	public boolean UpdateTask(String TaskData) {
		// TODO - implement Task.UpdateTask
		throw new UnsupportedOperationException();
	}

	public int getTaskID() {
		// TODO - implement Task.getTaskID
		throw new UnsupportedOperationException();
	}

	public float getTaskPrice() {
		// TODO - implement Task.getTaskPrice
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param TaskData
	 */
	public static TaskInAJob Task(String TaskData) {
		// TODO - implement Task.Task
		throw new UnsupportedOperationException();
	}

	public boolean AddTaskToJob(String TaskData){
		//TODO
		throw new UnsupportedOperationException();
	}

}