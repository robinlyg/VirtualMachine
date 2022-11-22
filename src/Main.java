import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //input file -> .vm
        //output file -> .asm
        String inputFile, outputFile;

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the VM file name (.vm): ");
        inputFile = keyboard.nextLine();

        //construct Parser to handle the input file
        Parser parser = new Parser(inputFile);

        //Construct a CodeWriter to handle the output file
        CodeWriter codeWriter = new CodeWriter(inputFile);

        //Marches through the input file, parsing each line and generating code from it

        while (parser.hasMoreCommands()) {
            parser.advance();
            String instruction = parser.getInstruction();
            System.out.println("INSTRUCTION : " +instruction);
            if (!(parser.getInstruction() == null)) {
                if(!(instruction.equals("push") || instruction.equals("pop"))){
                    codeWriter.writeArithmetic(parser.getInstruction());
                } else {
                codeWriter.writePushPop(parser.getInstruction(), parser.getSegment(), parser.getAddress());}

            }
        }

        codeWriter.infinite();
        codeWriter.close();

    }
}
