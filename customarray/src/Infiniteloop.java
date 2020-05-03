
import edu.ucla.cs.bigfuzz.customarray.inapplicable.SymbolicStateOutofBounds.InfiniteloopCustomArray;
import edu.ucla.cs.bigfuzz.customarray.CustomArray;
import edu.ucla.cs.bigfuzz.customarray.inapplicable.SymbolicStateOutofBounds.map2;

import java.io.IOException;
import java.util.ArrayList;

public class Infiniteloop {
public void Infiniteloop(String inputFile) throws IOException {
ArrayList<String> results0 = CustomArray.read(inputFile);
ArrayList<Integer> results1 = InfiniteloopCustomArray.Map1(results0);
//System.out.println(results1);
ArrayList<map2> results2 = InfiniteloopCustomArray.Map2(results1);
//System.out.println(results2);
ArrayList<map2> results3 = InfiniteloopCustomArray.Filter1(results2);

for(int i = 0; i < results3.size(); i++){
    System.out.println(results3.get(i)._1() + " " + results3.get(i)._2());
}

}}
