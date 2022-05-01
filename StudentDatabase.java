import java.util.*;
import java.io.*;

public class StudentDatabase {
	public static void main(String args[]) throws Exception
	{
		//Keyboard input
		Scanner sc = new Scanner(System.in);

		//Student Database Class
		StudDatabase db = new StudDatabase();

		int choice = -1;
		while (choice != 5)
		{

			//Menu Display
			System.out.println(
					"\n----------------- \n" +
					"Insert ---- ( 1 )\n" +
					"Delete ---- ( 2 )\n" +
					"Display --- ( 3 )\n" +
					"Search ---- ( 4 )\n" +
					"Exit ------ ( 5 )\n" +
					"----------------- \n" +
					"\nEnter your choice: "
					);

			//Choice Input and error handeling
			try 
			{
				choice = sc.nextInt();
			}
			catch(Exception ex) //Making sure int is entered
			{
				System.out.println("[ ERROR ] bad input, int expected");
				String x = sc.next(); //Clearing Input Buffer
				continue;
			}
			
			switch (choice)
			{
				case 1:
					{
						db.insert();
					} break;

				case 2:
					{	
						db.delete();
					} break;

				case 3:
					{
						db.display();
					} break;

				case 4:
					{
						db.search();
					} break;

				case 5:
					{
						System.out.println("GoodByee ^_^\n");
					} break;

				default:
					{
						System.out.println("\nWrong choice, Choice range (int) [1-4]\n");
					} break;
			
			}
		}
	}
}

class St_record implements Serializable{
	String name;
	int gr_no;
	int roll_no;
	float sgpa;

	St_record( String name, int gr_no, int roll_no, float sgpa ) {
		this.name = name;
		this.gr_no = gr_no;
		this.roll_no = roll_no;
		this.sgpa = sgpa;
	}

	void display_data()
	{
		System.out.println("+ --- --- --- --- --- +");
		System.out.println("Name: " + name);
		System.out.println("Gr Number: " + gr_no);
		System.out.println("Roll Number: " + roll_no);
		System.out.println("SGPA: " + sgpa);
		System.out.println("+ --- --- --- --- --- +");
	}
 StudDatabase {
	Scanner sc = new Scanner(System.in);
	
	//For Saving to a file
	File fp = new File("StudentDatabase.txt");

	//File IO ports
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null; 

	private String name;
	private int gr_no;
	private int roll_no;
	private float sgpa;

	ArrayList<St_record> st_recs_mem = new ArrayList<St_record>();

	void insert() throws Exception
	{
		//Checking contents of the file
		if( fp.isFile())
		{
			ois = new ObjectInputStream(new FileInputStream(fp));
			st_recs_mem = (ArrayList<St_record>)ois.readObject();
			ois.close();
		}


		System.out.println(" --- Welcome to insert ---");

		//Taking input for total inserts
		System.out.println("Enter total insertions: ");
		int inserts = sc.nextInt();
		
		for( int i = 0; i < inserts; i++ )
		{
			//Clearing input buffer
			String buff_clr = sc.nextLine();

			//Take inputs and insert
			System.out.println(" ---------------------- ");
			System.out.println("Enter Student [ Name ]: ");
			name = sc.nextLine();

			//Making sure Gr number is unique
			boolean unique = true;
			do	
			{
				System.out.println("Enter Student [ Gr_No ]: ");
				gr_no = sc.nextInt();
				for( int j = 0; j < st_recs_mem.size(); j++ )
				{
					if( st_recs_mem.get(j).gr_no == gr_no )
					{
						System.out.println("[ ERROR ] Gr number aldready exists");
						System.out.println("( Gr number must be unique )");
						System.out.println("Try again....");
						unique = false;
						continue;
					}
				}
				unique = true;

			} while( unique == false );

			System.out.println("Enter Student [ Roll_no ]: ");
			roll_no = sc.nextInt();

			System.out.println("Enter Student [ SGPA ]: ");
			sgpa = sc.nextFloat();

			st_recs_mem.add(new St_record(name, gr_no, roll_no, sgpa));
		}

		//Inserting to  file
		oos = new ObjectOutputStream(new FileOutputStream(fp));
		oos.writeObject(st_recs_mem);
		oos.close();

	}
	
	void display() throws Exception
	{
		if( fp.isFile())
		{
			ois = new ObjectInputStream(new FileInputStream(fp));
			st_recs_mem = (ArrayList<St_record>)ois.readObject();
			ois.close();
		}

		System.out.println(" --- Welcome to display ---");
		
		for( int i = 0; i < st_recs_mem.size(); i++ )
		{
			st_recs_mem.get(i).display_data();
		}

		if( st_recs_mem.size() == 0 )
		{
			System.out.println("[ Database is empty ]");
		}

	}

	void search() throws Exception
	{
		if( fp.isFile())
		{
			ois = new ObjectInputStream(new FileInputStream(fp));
			st_recs_mem = (ArrayList<St_record>)ois.readObject();
			ois.close();
		}

		System.out.println(" --- Welcome to search ---");
		if( st_recs_mem.size() == 0 )
		{
			System.out.println("[ Database is empty ]");
			return;
		}

		System.out.println("Enter Gr_number of record to search: ");
		int to_search_gr = sc.nextInt();
		
		boolean found = false;
		for( int i = 0; i < st_recs_mem.size(); i++ )
		{
			if( st_recs_mem.get(i).gr_no == to_search_gr )
			{
				System.out.println("[ Record Found ]");
				st_recs_mem.get(i).display_data();
				found = true;
			}
		}

		if( found == false )
		{
			System.out.println("[ Record Not found ]");
		}
	}

	void delete() throws Exception
	{
		if( fp.isFile())
		{
			ois = new ObjectInputStream(new FileInputStream(fp));
			st_recs_mem = (ArrayList<St_record>)ois.readObject();
			ois.close();
		}

		System.out.println(" --- Welcome to delete ---");
		if( st_recs_mem.size() == 0 )
		{
			System.out.println("[ Database is empty ]");
			return;
		}

		System.out.println("Enter Gr_number of record to search: ");
		int to_search_gr = sc.nextInt();
		
		boolean found = false;
		for( int i = 0; i < st_recs_mem.size(); i++ )
		{
			if( st_recs_mem.get(i).gr_no == to_search_gr )
			{
				System.out.println("[ Record Found ]");
				System.out.println("Deleting.....");
				st_recs_mem.remove(i); //Removing from memory
				oos = new ObjectOutputStream(new FileOutputStream(fp));
				oos.writeObject(st_recs_mem); //Removing from file
				oos.close();
				System.out.println("[ Record Deleted ]");
				found = true;
			}
		}

		if( found == false )
		{
			System.out.println("[ Record Not found ]");
		}

	}
}
