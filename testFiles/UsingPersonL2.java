

class UsingPerson   // driver class
{
	public static void main(String[] args)
	{
		Person me = new Person("J. Tasse");
		
		Person friend = new Person("Amy");
		
		String n = me.getName();
		System.out.println("Name: " + n + " age: " + me.getAge());
		
		me.setAge(45);
		System.out.println("Name: " + n + " age: " + me.getAge());
		
		System.out.println("Hi " + me);
		
		me.celebrateBirthday();
		System.out.println("Hi " + me);
		
		int year = me.calculateYearBorn(2018);
		System.out.println("Born in " + year);
		
		System.out.println("Lucky number: " + me.giveLuckyNumber());
		
		n = friend.getName();
		System.out.println("Name: " + n + " age: " + friend.getAge());
	}
}

