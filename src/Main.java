import java.sql.*;
import java.util.Scanner;

public class Main {
	
	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		
		while(true) {
			System.out.print("Select: \n\tA) To add a new contact\n\tB) To remove a contact\n\tC) To show your contact list\n\t--> ");
			String option = scan.nextLine();
			
			switch(option) {
			case "a": addContact(); break;
			case "b": removeContact(); break;
			case "c": printContacts(); break;
			default: System.out.println("Invalid option"); continue;
			}
		}	
	}
	
	public static void addContact() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubrica", "root", "");
			System.out.println("Connected with the DB successfully");		
						
			System.out.print("Insert name: ");
			String name = scan.nextLine();
			if (name.isBlank() || name == null) {
				System.out.println("name cannot be null or blank");
				addContact();
			}
			System.out.print("Insert lastName: ");
			String lastName = scan.nextLine();
			if (lastName.isBlank() || lastName == null) {
				System.out.println("surname cannot be blank or null");
				addContact();
			}
			System.out.print("Insert phone number: ");
			String cellulare = scan.nextLine();
			if (cellulare.isBlank() || cellulare == null) {
				System.out.println("Phone number cannot be blank or null");
				addContact();
			}
			System.out.print("Insert second phone number: ");
			String cellulare2 = scan.nextLine();
			if (cellulare2.isBlank() || cellulare2 == null) {
				cellulare2 = null;
			}
			System.out.print("Insert home number: ");
			String numeroCasa = scan.nextLine();
			if (numeroCasa.isBlank() || numeroCasa == null) {
				numeroCasa = null;
			}
			
			PreparedStatement preparedStatement = conn.prepareStatement("insert into contacts values(?,?,?,?,?)");
			
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, lastName);
			preparedStatement.setString(3, cellulare);
			preparedStatement.setString(4, cellulare2);
			preparedStatement.setString(5, numeroCasa);
			
			preparedStatement.executeUpdate();
			System.out.println("Data inserted successfully\n");
			conn.close();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void removeContact() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubrica", "root", "");
			System.out.println("DB connected successfully");
			
			System.out.println("Insert details of the contact you want to remove:");
			
			System.out.print("\tName: ");
			String name = scan.nextLine();
			System.out.print("\tLast Name: ");
			String lastName = scan.nextLine();
			
			PreparedStatement preparedStatement = conn.prepareStatement("delete from contacts where nome = ? and cognome = ?");
			
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, lastName);
			
			preparedStatement.executeUpdate();
			System.out.println("Contact removed successfully\n");
			conn.close();
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void printContacts() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rubrica", "root", "");
			System.out.println("DB connected successfully");
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("Select * from contacts order by cognome asc");
			
			int colNumber = rs.getMetaData().getColumnCount();
			
			while (rs.next()) {
				for (int i = 1; i< colNumber; i++ ) {
					if (!(rs.getString(i) == null)) {
						System.out.print(rs.getString(i) + " ");		
					}
				}
				System.out.println("\n");
			}
			
			PreparedStatement preparedStatement = conn.prepareStatement("select * from contacts");
			
			conn.close();
			
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
		}
	}
}
