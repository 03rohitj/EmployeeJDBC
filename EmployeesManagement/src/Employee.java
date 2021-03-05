
//Class to hold details of an employee
public class Employee{

   /*
      Note : The prefix of data member denotes data type.
      S : String
      C : char
      L : long
   */
   private String S_eName,S_eDept;   //Employee name and deptartment
   private char C_eGender;   //Employee Gender : M, F or O
   private long L_eSalary;   //Employee Salary
   
   //default constructor
   public Employee(){}

   //Copy constructor
   public Employee(Employee emp){
      this.S_eName = emp.S_eName;
      this.S_eDept = emp.S_eDept;
      this.C_eGender = emp.C_eGender;
      this.L_eSalary = emp.L_eSalary;
   }

   //Accessor and Mutator methods
   public void setName(String S_eName){ this.S_eName = S_eName; }
   public String getName(){ return S_eName; }

   public void setDepartment(String S_eDept){ this.S_eDept = S_eDept; }
   public String getDept(){ return S_eDept; }

   public void setGender(char C_eGender){ this.C_eGender = C_eGender; }
   public char getGender(){ return C_eGender; }

   public void setSalary(long L_eSalary){ this.L_eSalary = L_eSalary; }
   public long getSalary(){ return L_eSalary; }

}//End of class Employee

