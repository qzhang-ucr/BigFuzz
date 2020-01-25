import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ComplieTool {
    private String[] constLibrary = new String[]{"map","filter","mapValues","reduceByKey","mapToPair","flatMap"};

    private int checkFirst (int[] a){
        int min = 0;
        for (int i=0;i<7;i++)
        {
            if (a[i]<= a[min]){
                min=i;
            }
        }
        if (a[min]!=99999) {return min;}
        else return -1;
    }

    String findVal(String line){
        Pattern val = Pattern.compile("val|JavaRDD|JavaPairRDD");
        Matcher a = val.matcher(line);

        Pattern impor = Pattern.compile("import");
        Matcher b = impor.matcher(line);
        if (!a.find()) return null;
        if (b.find()) return null;


        String valName = "";
        int pointer = a.end();
        while (line.charAt(pointer)!=' ') {pointer=pointer+1;}
        while (line.charAt(pointer)!='=') {
            if (line.charAt(pointer)!=' '){
                valName = valName+line.charAt(pointer);
            }
            pointer = pointer+1;
        }

        return valName;
    }

    String trace(ArrayList<String> mapToVar, String line){
        Pattern val = Pattern.compile("val|JavaRDD|JavaPairRDD");
        Matcher a = val.matcher(line);

        a.find();
        int pointer = a.end();
        String fatherName ="";
        while (line.charAt(pointer)!='=') {pointer=pointer+1;}
        pointer=pointer+1;
        while (line.charAt(pointer)!='.'){
            if (line.charAt(pointer)!=' '){
                fatherName = fatherName+line.charAt(pointer);
            }
            pointer = pointer+1;
        }

        pointer = mapToVar.size()-1;
        while (!mapToVar.get(pointer).equals(fatherName)) {
            pointer=pointer-1;
            if (pointer==0) {
                break;
            }
        }
        return "results"+pointer;
    }

    ArrayList<Integer> DataFlowSequence(String line){

        ArrayList<Integer> Sequence = new ArrayList<>();

        Pattern MapFind = Pattern.compile("\\.map |\\.map\\(|\\.map\\{");
        Pattern FilterFind = Pattern.compile("\\.filter");
        Pattern MapValueFind = Pattern.compile("\\.mapValues");
        Pattern ReduceByKeyFind = Pattern.compile("\\.reduceByKey");
        Pattern MapToPairFind = Pattern.compile("\\.mapToPair");
        Pattern FlatMapFind = Pattern.compile("\\.flatMap");
        Pattern TextFileFind = Pattern.compile("\\.textFile");

        ArrayList<Matcher> matchers = new ArrayList<>();
        matchers.add(MapFind.matcher(line));
        matchers.add(FilterFind.matcher(line));
        matchers.add(MapValueFind.matcher(line));
        matchers.add(ReduceByKeyFind.matcher(line));
        matchers.add(MapToPairFind.matcher(line));
        matchers.add(FlatMapFind.matcher(line));
        matchers.add(TextFileFind.matcher(line));


        int[] position = new int[7];
        for (int i=0;i<7;i++){
            position[i]=99999;
            if (matchers.get(i).find()) position[i] = matchers.get(i).start();
        }
        int k=checkFirst(position);
        while (k!=-1){
            //System.out.println(line);
            position[k] = 99999;
            Sequence.add(k);
            if (matchers.get(k).find()) position[k] = matchers.get(k).start();
            k = checkFirst(position);
        }
        return Sequence;
    }
}
