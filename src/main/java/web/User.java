package web;
import javax.persistence.*;

@Entity @Table(name="users")
public class User {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;

	@Column(unique=true, nullable=false)
	public String email;

	String password;

	@Column(name="first_name", nullable=false)
	public String firstName;
	@Column(name="last_name", nullable=false)
	public String lastName;
	@Column(name="middle_name")
	public String middleName;

	public String getEmail() { return email; }
	public String getFirstName()  { return firstName;  }
	public String getLastName()   { return lastName;   }
	public String getMiddleName() { return middleName; }
}
