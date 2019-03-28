import java.text.*;

class Coin
{
   int year;
   double value;
   String denom;
   
   Coin(int year, double value, String denom)
   {
      this.year = year;
      this.value = value;
      this.denom = denom;
   }
   
   int getYear()
   {
      return year;
   }
   
   double getValue()
   {
      return value;
   }
   
   String getDenom()
   {
      return denom;
   }
   
   void save(int year, double value, String denom)
   {
      this.year = year;
      this.value = value;
      this.denom = denom;
   }

   public String toString()
   {
      NumberFormat fmt = NumberFormat.getCurrencyInstance();
      return denom + " " + year + " worth " + fmt.format(value);
   }
}
