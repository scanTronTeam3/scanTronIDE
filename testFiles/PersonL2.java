
class Person
{
	private String name;
	private int age;
	
	// constructor
	Person(String theName)
	{
		name = theName;
		age = 0;
	}
	
	// accessor method for name
	String getName()
	{
		return name;
	}
	
	int getAge()
	{
		return age;
	}
	
	// mutator method for name
	void setName(String theName)
	{
		name = theName;
	}
	
	void setAge(int newAge)
	{
		age = newAge;
	}
	
	public String toString()
	{
		return name + " (age " + age + ")";
	}
	
	void celebrateBirthday()
	{
		age = age + 1;
	}
	
	int calculateYearBorn(int currentYear)
	{
		int yearBorn = currentYear - age;
		return yearBorn;
	}
	
	int giveLuckyNumber()
	{
		int year = calculateYearBorn(2018);
		int x = year / 100;
		int y = year % 100;
		return x+y;
	}
	
}
