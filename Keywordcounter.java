import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Keywordcounter {
    public static void main(String[] args) throws IOException {

        Map<String, HeapNode> wordMap = new HashMap<>();     //hashmap creation
        FibHeap maxFibHeap = new FibHeap();                     //object creation for fibheap

        String fileName = args[0];
        String line;

        BufferedReader br;
        BufferedWriter bw;

        try {
            br = new BufferedReader(new FileReader(fileName));      //reading from imput file
            File file = new File("output_file.txt");            //output file creation
            bw = new BufferedWriter(new FileWriter(file));


            while ((line = br.readLine()) != null){
                if (line.charAt(0) == '$'){                       //when first char is $             
                    String[] input = line.split("\\s+");

                    String word = input[0].substring(1);
                    int count = Integer.parseInt(input[1]);

                    if (wordMap.containsKey(word)){                 //If already contains call increase count
                        maxFibHeap.increaseCount(wordMap.get(word), count);
                    } else {
                        HeapNode node = maxFibHeap.insertNewNode(word, count);  //else insert into heap
                        wordMap.put(word, node);
                    }

                } else if (line.equalsIgnoreCase("stop")){          //stop breaks the execution
                    break;
                } else{
                    int nodesToRemove = Integer.parseInt(line);           //number of nodes to be removed
                    List<HeapNode> nodeList = new ArrayList<>();
                    StringBuilder res = new StringBuilder();

                    for(int i=0; i < nodesToRemove; i++){
                        if (i != 0) res.append(",");                    

                        HeapNode maxNode = maxFibHeap.removeMaxNode();      //removing the requested count nodes
                        nodeList.add(i, maxNode);
                        res.append(maxNode.getName());
                    }

                    for(HeapNode node : nodeList){              //reinserting back the max nodes
                        maxFibHeap.insertNode(node, false);
                    }

                    bw.write(res.toString());               //writing into output file
                    bw.newLine();

                }

            }

            bw.close();
            br.close();

            System.out.println("Output written to file");

        } catch (FileNotFoundException e) {
            e.printStackTrace();                     //Exception handling
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
