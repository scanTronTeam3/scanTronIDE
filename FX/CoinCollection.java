import java.util.*;

class CoinCollection
{
   ArrayList<Coin> collection;
   
   CoinCollection()
   {
      collection = new ArrayList<Coin>();
   }
   
   int add(Coin c)
   {
      collection.add(c);
      return collection.size()-1;
   }
   
   Coin getCoin(int index)
   {
      if (index < 0 || index >= collection.size())
         return null;
      else
         return collection.get(index);
   }
}
