import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    private static String start = "<!DOCTYPE html><html><body><svg height=\"2160\" width=\"3840\">";
    private static String end = "</svg></body></html>";
    private static String middle = "";

    private static int x1;
    private static int y1;
    private static int x2;
    private static int y2;

    private static int cx;
    private static int cy;
    private static int r;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("A gramática deste projeto aceita apenas Σ = A, C");
        Boolean again = false;
        
        do{
            x1 = 0;
            y1 = 0;
            x2 = 0;
            y2 = 0;
            cx = 0;
            cy = 0;
            r = 1;
            System.out.println("Digite o caminho para o arquivo texto que você quer validar: ");

            
            //String path = scanner.nextLine();
            String path = System.console().readLine();
            System.out.println("----------------------------------");
            
            Path caminho = Paths.get(path);
            List<String> list = Files.readAllLines(caminho, StandardCharsets.UTF_8);

            if(list.isEmpty()){
                System.out.println("Não foi possível encontrar o arquivo.");
            }else{
                justDoIt(list);
            }

            System.out.println("Deseja adicionar mais um arquivo?");
            String ans = System.console().readLine();
            if(ans.trim().toUpperCase().equals("Y")){
                again = true;
            }else{
                again = false;
                save();
                System.out.println("Arquivo gerado com sucesso");
            }
        }while(again);
        
        scanner.close();
    }

    private static void justDoIt(List<String> list){
        for (String line : list) {
            line = line.trim();
            char[] chars = line.toUpperCase().toCharArray();
            for (char ch : chars) {
                if(ch != 'A' && ch != 'C'){
                    System.out.println( "A gramática não está correta na linha: " + line);
                }
            }
            create(line);            
            System.out.println(line);
        }
    }

    private static void save(){
        Path path = Paths.get("PurpleLineOrangeCircle.html");
        String finalString = start + middle + end;
        byte[] bytes = finalString.getBytes();

        try {
            Files.write(path,bytes);
        } catch (IOException ex) {
            System.out.println("There was an error while saving your file. Please, try again.");
        }
    }

    private static void create(String st){
        int aux = 0;
        for (char i : st.toCharArray()){
            aux++;
            if(i == 'A'){
                x1 = x2;
                y1 = y2;
                x2 += generateRandom();
                y2 += generateRandom();

                buildLineString();
            }
            else if (i == 'C'){
                if(aux % 2 == 0)
                    r = 15;
                else
                    r = 10;

                int x = generateRandom();
                if(x % 2 == 0)
                    cx = x2 + r;
                else
                    cx = x2 - r;
                
                cy = y2;
                
                buildCircleString();
            }
        }
    }

    private static void buildLineString(){
        String result = "<line ";
        result += "x1=\"" + x1 +"\" ";
        result += "x2=\"" + x2 +"\" ";
        result += "y1=\"" + y1 +"\" ";
        result += "y2=\"" + y2 +"\" ";
        result += "style=\"stroke:rgb(128,0,128);stroke-width:2\" />\"";
        middle += result;
    }

    private static void buildCircleString(){
        String result = "<circle ";
        result += "cx=\"" + cx +"\" ";
        result += "cy=\"" + cy +"\" ";
        result += "r=\"" + r +"\" ";
        result += "fill=\"none\" ";
        result += "style=\"stroke:rgb(255,165,0);stroke-width:2;\" />\"";
        middle += result;
    }

    private static int generateRandom(){
        Random random = new Random();
        return random.nextInt(100 - (-100 - 1) + -100);
    }
}
