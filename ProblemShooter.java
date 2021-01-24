import java.util.*;
import java.io.*;

public class ProblemShooter {
    public static Utility util = new Utility();
    static int number=10;
    
    public static void main (String [] args) {
        
        ArrayList <Problem> problems;
        ArrayList <IterateIndex> indexes;
        boolean [] turnIn;
        int score;
        
        indexes=chooseSmallIndex(number);
        problems = readEachProblem(indexes);
        turnIn = exam(problems);
        score = evaluate(turnIn);
        System.out.println("The Score is : "+score);
        
        /*//test for choosing random smallindexes.
        int flag =0;
        for(IterateIndex i : indexes) {
                System.out.println((flag++)+" ) "+i.toString());
            }
        */
        /*// test for loading problem contents.
        int flag =0;
        for(Problem p : problems) {
            System.out.println((flag++)+")"+p.toString());
        }
        */
        
    }
    /*
    public static String[] readFile(String filename) throws Exception {
        String[] temp = new String [100];
        
            Scanner scan = new Scanner(new File(filename));
            int flag=0;
             while(scan.hasNextLine()){
                 temp[flag++] = scan.nextLine();
             }
        // System.out.println("flag : "+flag);
        String [] result = new String[flag];
        for(int i=0; i<result.length;i++) result[i]=temp[i];
        
        return result;
    }
    */
    
    public static ArrayList<IterateIndex> chooseSmallIndex(int count) { 
        ArrayList<IterateIndex> result = new ArrayList<IterateIndex>(count);
        ArrayList<IterateIndex> smalls = new ArrayList<IterateIndex>();
        ArrayList<String> chapters;
        int indexNum=0;
        
        try { 
            chapters = util.readFile("chapters.txt"); 
            for(int i=0;i<chapters.size();i++) {
                String[] temp = chapters.get(i).split(",");
                int [] jay = new int[temp.length];
                
                for(int k=0;k<jay.length;k++) {
                    jay[k]=Integer.parseInt(temp[k]);
                }
                indexNum+=temp.length;
                
                for(int j=0; j<jay.length;j++) {
                    smalls.add(new IterateIndex(i,j));
                }
            }
            
            for(Integer n : chooseNumbers(indexNum,count)) {
                     result.add(smalls.get(n.intValue()));
                    //System.out.println(n.intValue());
                
                }
            
        
        }  
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            result = new ArrayList<IterateIndex>();
        }
        catch (Exception e1) {
            e1.printStackTrace(); 
            result = new ArrayList<IterateIndex>();
        }
        
        Collections.sort(result);
        return result;
    }
    
    public static ArrayList<Integer> chooseNumbers(int range, int count) {
        ArrayList<Integer> result = new ArrayList<Integer>(count);
        int flag =0;
        int temp;
        
        while(flag<count) {
            temp = new Integer((int)(Math.random()*range));
            if(result.contains(temp)) ;
            else { result.add(temp); flag++;}
            
        }
        
        return result;
    }
    
    
    public static ArrayList<Problem> readEachProblem(ArrayList <IterateIndex> chosen) {
        ArrayList<Problem> result= new ArrayList<Problem>();
        
        for (IterateIndex i : chosen) {
            result.add(i.loadAnyProblem());
        }
        
        return result;
    }
    
    public static boolean[] exam(ArrayList<Problem> problems) {
        boolean[] result = new boolean[problems.size()];
        int flag=0;
        for(Problem p : problems) {
            result[flag]=exam(flag++, p);
            System.out.println("*********************");
        }
        return result;
    }
    
    public static boolean exam(int flag, Problem p) {
        Scanner sc = new Scanner(System.in);
        int r = (int)(Math.random()*5);
        int f=0;
        String [] outof = p.getOutOf(r);
        //System.out.println(p);
        System.out.println((flag+1)+"."+p.getPassage());
        
        
        for(int i=0; i<outof.length;i++) {
            System.out.println("("+(i+1)+") "+outof[i]);
        }
        
        System.out.print("Your answer : ");
        if(sc.nextInt()==r+1)  {System.out.println("right!"); return true; }
        else {System.out.println("Wrong!"); return false; }
    }
    
    public static int evaluate(boolean[] turnIn) {
        int count = 0;
        int result;
        for(int i=0;i<turnIn.length;i++) {
            if(turnIn[i]) count++;
        }
        result = (int)((float)(count)/turnIn.length*100);
        System.out.println("Got "+count+" right out of "+turnIn.length+" problems.");
        
        return result;
    }
}