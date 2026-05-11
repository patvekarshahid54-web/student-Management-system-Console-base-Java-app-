package student;
import java.sql.*;
import java.util.*;

public class studentMang {

    static Scanner sc = new Scanner(System.in);

   

    public static void main(String args[]) {

        userActivity ua = new userActivity();
        int ch;

        do {
            System.out.println("------------Menu-----------");
            System.out.println("1.Insert");
            System.out.println("2.Update");
            System.out.println("3.Delete");
            System.out.println("4.Display");
            System.out.println("5.Find by Department");
            System.out.println("6.Exit");
            System.out.print("Enter your choice: ");

            ch = sc.nextInt();
            

            switch (ch) {
                case 1:
                    System.out.print("Enter rollno: ");
                    int rollno = sc.nextInt();
                    System.out.print("Enter name: ");
                    String name = sc.next();
                    System.out.print("Enter department: ");
                    String department = sc.next();
                    System.out.print("Enter percentage: ");
                    double percent = sc.nextDouble();
                    ua.insert(rollno, name, department, percent);
                    break;

                case 2:
                    System.out.print("Enter rollno to update: ");
                    rollno = sc.nextInt();
                    System.out.print("Enter name: ");
                    name = sc.next();
                    System.out.print("Enter department: ");
                    department = sc.next();
                    System.out.print("Enter percentage: ");
                    percent = sc.nextDouble();
                    ua.update(rollno, name, department, percent);
                    break;

                case 3:
                    System.out.print("Enter rollno to delete: ");
                    rollno = sc.nextInt();
                    ua.delete(rollno);
                    break;
                    
                case 4:
                	ua.display();
                	break;

                case 5:
                    System.out.print("Enter department: ");
                    department = sc.next();
                    ua.findByDepart(department);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (ch != 6);
    }
}




class userActivity {

    static final String URL = "jdbc:mysql://localhost:3306/student";
    static final String USER = "root";
    static final String PASS = "shahid@123";

    Connection conn;

    userActivity() {
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connection Established");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insert(int rollno, String name, String department, double percent) {
        String sql = "INSERT INTO students VALUES (?,?,?,?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, rollno);
            pst.setString(2, name);
            pst.setString(3, department);
            pst.setDouble(4, percent);
            pst.executeUpdate();
            System.out.println("Data Inserted");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void update(int rollno, String name, String department, double percent) {
        String sql = "UPDATE students SET name=?, department=?, percentage=? WHERE rollno=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, department);
            pst.setDouble(3, percent);
            pst.setInt(4, rollno);
            pst.executeUpdate();
            System.out.println("Data Updated");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(int rollno) {
        String sql = "DELETE FROM students WHERE rollno=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, rollno);
            pst.executeUpdate();
            System.out.println("Deleted");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void display()
    {
    	String sql = "SELECT * FROM students";
    	try(PreparedStatement pst = conn.prepareStatement(sql))
    	{
    		ResultSet rs = pst.executeQuery();
    		while(rs.next())
    		{
    			 System.out.println(
    		                rs.getInt(1) + "   " +
    		                rs.getString(2) + "   " +
    		                rs.getString(3) + "   " +
    		                rs.getDouble(4)
    		            );
    		}
    	}catch(SQLException ex) {
    		ex.printStackTrace();
    	}
    }

    public void findByDepart(String department) {
        String sql = "SELECT * FROM students WHERE department=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, department);
            ResultSet rs = pst.executeQuery();

            System.out.println("Roll  Name  Dept  Percent");
            while (rs.next()) {
                System.out.println(
                    rs.getInt(1) + " " +
                    rs.getString(2) + " " +
                    rs.getString(3) + " " +
                    rs.getDouble(4)
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
