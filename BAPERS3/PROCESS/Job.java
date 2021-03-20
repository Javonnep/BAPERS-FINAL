package PROCESS;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class Job implements I_PROCESS {

	private static int JobID;
	private static int AccountNumber;
	private static String Urgency = "Normal";
	//TODO set this type (was timestamp)
	private String JobDeadline;
	private String JobStatus = "Ordered";
	private int NumberOfTasks = 0;
	private int TasksCompleted = 0;
	private String DateOfJob;
	private int TaskProgress = 0;
	private float JobPrice = 0;
	private int IsCompleted = 0;

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

	/**
	 * 
	 * @param TaskData
	 */
	public boolean AddTaskToJob(String TaskData) {
		// TODO - implement Job.AddTaskToJob
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param TaskID
	 */
	public TaskInAJob RetrieveTasks(int TaskID) {
		// TODO - implement Job.RetreiveTasks
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param TaskID
	 */
	public boolean RemoveTask(int TaskID) {
		// TODO - implement Job.RemoveTask
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param TaskData
	 */
	public boolean UpdateTask(String TaskData) {
		// TODO - implement Job.UpdateTask
		throw new UnsupportedOperationException();
	}

	public static int getJobID() {
		return JobID;
	}

	/**
	 * 
	 * @param NewJobID
	 */
	public int setJobID(int NewJobID) {
		// TODO - implement Job.setJobID
		throw new UnsupportedOperationException();
	}

	public String getJobStatus() {
		// TODO - implement Job.getJobStatus
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param NewJobStatus
	 */
	public void setJobStatus(String NewJobStatus) {
		// TODO - implement Job.setJobStatus
		throw new UnsupportedOperationException();
	}

	// TODO set this type (was timestamp)
	public String CalculateDeadline() {
		// TODO - implement Job.CalculateDeadline
		throw new UnsupportedOperationException();
	}

	public String ViewTaskProgress() {
		// TODO - implement Job.ViewTaskProgress
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param NewUrgency
	 */
	public boolean SetUrgency(String NewUrgency) {
		// TODO - implement Job.SetUrgency
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param JobData
	 */
	public boolean StoreJob(String JobData) {
		// TODO - implement Job.StoreJob
		throw new UnsupportedOperationException();
	}

	public float getJobPrice() {
		// TODO - implement Job.getJobPrice
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param JobPrice
	 */
	public void setJobPrice(float JobPrice) {
		// TODO - implement Job.setJobPrice
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean CreateJob(String NewJobData) {
		return false;
	}

	@Override
	public boolean AddNewTask(String TaskDescription, String TaskLocation, float TaskPrice, int TaskDuration) {
		return false;
	}

	@Override
	public void CompleteTask() {

	}

	@Override
	public void StartTask() {

	}

	@Override
	public void GetTaskID() {

	}

	@Override
	public void GetTaskPrice() {

	}

	public int getNumberOfTasks() {
		// TODO - implement Job.getNumberOfTasks
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param NumberOfTasks
	 */
	public void setNumberOfTasks(int NumberOfTasks) {
		// TODO - implement Job.setNumberOfTasks
		throw new UnsupportedOperationException();
	}


	public static int getAccountNumber() {
		return AccountNumber;
	}

	public static String getUrgency() {
		return Urgency;
	}

	public void setAccountNumber(int accountNumber) {
		AccountNumber = accountNumber;
	}

	public void setUrgency(String urgency) {
		Urgency = urgency;
	}

	// 1 INSERT INTO Jobs 2 INSERT INTO TIJ 3 UPDATE TIJ
	public Job(int AccountNumber, String Urgency) throws SQLException{
		this.AccountNumber = AccountNumber;
		this.Urgency = Urgency;
		this.JobDeadline = JobDeadline;
		this.JobStatus = JobStatus;
		this.DateOfJob = DateOfJob;

		java.util.Date date = new java.util.Date();
		DateOfJob = date.toString();

		/**
		 * calendar code built up from https://stackoverflow.com/questions/18733582/calculating-a-future-date#18733637
		 * */
		if (Urgency == "normal"){
			Calendar c = Calendar.getInstance(); // create calendar object
			c.setTime(new java.util.Date()); //
			c.add(Calendar.DATE, 1);
			java.util.Date JobDeadlineDate = c.getTime();
			JobDeadline = JobDeadlineDate.toString();
		}
		else if (Urgency == "urgent"){
			Calendar c = Calendar.getInstance();
			c.setTime(new java.util.Date());
			c.add(Calendar.HOUR, 6);
			java.util.Date JobDeadlineDate = c.getTime();
			JobDeadline = JobDeadlineDate.toString();
		}
		else if (Urgency == "vurgent"){
			Calendar c = Calendar.getInstance();
			c.setTime(new java.util.Date());
			c.add(Calendar.HOUR, 3);
			java.util.Date JobDeadlineDate = c.getTime();
			JobDeadline = JobDeadlineDate.toString();
		}

		String sql =
		"INSERT INTO Jobs (AccountNumber, NumberOfTasks, DateOfJob, JobDeadline, JobUrgency, Price) Values("
		+ AccountNumber + ", " + NumberOfTasks + ", \"" + DateOfJob + "\", \"" + JobDeadline + "\", \"" + Urgency + "\", " + JobPrice + ");";
		System.out.println(sql);
		Statement statement = connection.createStatement();
		statement.executeUpdate(sql);
	}

	public static ArrayList<String[]> GetJobList() throws SQLException {
		Statement statement = connection.createStatement();
		String sql = "SELECT * FROM Jobs;";
		ResultSet resultSet = statement.executeQuery(sql);

		ArrayList<String[]> arrayList = new ArrayList<String[]>();
		String tuple;
		// adding changes to an array list
		while (resultSet.next()){

			int JobID = resultSet.getInt("JobID");
			int AccountNumber = resultSet.getInt("AccountNumber");
			int NumberOfTasks = resultSet.getInt("NumberOfTasks");
			String DateOfJob = resultSet.getString("DateOfJob");
			String JobDeadline = resultSet.getString("JobDeadline");
			String JobUrgency = resultSet.getString("JobUrgency");
			int TasksCompleted = resultSet.getInt("TasksCompleted");
			int IsCompleted = resultSet.getInt("IsCompleted");



			tuple = JobID + "`"
					+ AccountNumber + "`"
					+ NumberOfTasks + "`"
					+ DateOfJob + "`"
					+ JobDeadline + "`"
					+ JobUrgency + "`"
					+ TasksCompleted + "`"
					+ IsCompleted;

			arrayList.add(tuple.split("`"));

		}
		return arrayList;
	}

	public static void main(String[] args) throws SQLException {

		Job job = new Job(7, "normal");

		// adds users to a list
		ArrayList<String[]> al = PROCESS.Job.GetJobList();
		// test to ensure correct alist format
//		for(String[] col: al){
//			for (String a: col){
//				System.out.println(a);
//
//			}
//		}
	}

	/**
	 * 
	 * @param JobData
	 * @param Normal
	 * @param Deadline
	 * @param JobStatus
	 * @param NumberOfTasks
	 * @param JobPrice
	 */
	public static Job Job(int JobData, String Normal, Date Deadline, String JobStatus, int NumberOfTasks, float JobPrice) {
		// TODO - implement Job.Job
		throw new UnsupportedOperationException();
	}



}