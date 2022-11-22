import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Generates asm code from the parsed VM command
 */

public class CodeWriter {

    protected static PrintWriter fileWriter;
    private String asmFileName, file;
    SymbolTable st = new SymbolTable();


    /**
     * opens the output file/stream and gets ready to wrtie into it
     *
     * @param fileName
     */
    public CodeWriter(String fileName) {
        //change file name and open output file
        //open stream to write to

        asmFileName = fileName.replace(".vm", ".asm");
        file = asmFileName.replace("asm", "");

        try {
            fileWriter = new PrintWriter(asmFileName);
           // Scanner fileWriter = new Scanner(new File(file));

        } catch (FileNotFoundException e) {
            System.out.println("Error " + e.getMessage());
        }

    }

    /**
     * Writes to the output file the asm code that implements the given arithmetic command
     *
     * @param command
     */

    private int i =0;
    public void writeArithmetic(String command) {

        StringBuilder s = new StringBuilder();

        switch (command) {
            case "add":
                s.append("""
                        @SP
                        AM = M-1
                        D=M
                        A = A-1
                        M = M+D
                        """);
            break;
            case"sub":
                s.append("""
                        @SP
                        M = M-1
                        A=M
                        D=M
                        A = A-1
                        M = M-D
                        """);
                break;

            case"or":

                s.append("""
                        @SP
                        AM=M-1
                        D=M
                        A=A-1
                        M=D|M""");
            break;

            case "not":
                s.append("""
                        @SP
                        A=M-1
                        M=!M""");
                break;

            case "neg":
                s.append("""
                        
                        @SP
                        A=M-1
                        M=-M
                        
                         """);
                break;

            case "and":
                s.append("""
                        @SP
                        AM=M-1
                        D=M
                        A=A-1
                        M=D&M""");

                break;

           //default:
            //to prevent comments making it through dont make this "defualt"
            case"eq":
            case"gt":
            case "lt":
                i++;
                s.append("\n@SP \nAM=M-1\nD = M\nA=A-1\nD=M-D \n@IF"+i+"\n");

                if (command.equals("eq")) s.append("D;JEQ");
                else if (command.equals("gt")) s.append("D;JGT");
                else s.append("D;JLT");

                s.append("\n@SP\nA=M-1\nM=0\n@ENDIF"+i+"\n0;JMP\n(IF"+i+")\n@SP\nA=M-1 \nM=-1\n(ENDIF"+i+")");

            break;

        }

        fileWriter.println(s);
        System.out.println(s);
        //clear string
        s.setLength(0);
    }

    /**
     * Writes to the output file the assembly code that implements the given command, where
     * command is either C_PUSH or C_POP
     *
     * @param instruction
     * @param segment
     * @param index
     */
    public void writePushPop(String instruction, String segment, int index) {

        if (instruction.equals("pop")) {
            //write @SP
            System.out.println("""
                    @SP
                    AM = M-1
                    D = M""");
            fileWriter.println("""
                    @SP
                    AM = M-1
                    D = M
                    """);

            //write @segment + index
            System.out.println("@" + (segment.equals("static") ? file + index : st.getValue(segment) +  index));
            fileWriter.println("@" + (segment.equals("static") ? file + index : st.getValue(segment) + index));
            //write M = D
            System.out.println("M = D");
            fileWriter.println("M = D");

        } else if (instruction.equals("push")) {
            //wirte @segment + index
            System.out.println("@" + (segment.equals("static") ? file + index : st.getValue(segment) +index));
            fileWriter.println("@" + (segment.equals("static") ? file + index : st.getValue(segment) + index));

            //write D = M or D=A depending on segment
            if(segment.equals("constant")) {
                System.out.println("D = A");
                fileWriter.println("D = A");
            }
            else{
                System.out.println("D = M");
                fileWriter.println("D = M");
            }

            //write @SP
            System.out.println("@" + "SP");
            fileWriter.println("@" + "SP");

            //write M = M + 1 A = M - 1 M = D
            System.out.println("M = M+1\nA = M-1\nM = D");
            fileWriter.println("M = M+1\nA = M-1\nM = D");


        }
    }

    public void infinite(){
        System.out.println("""
                (END)
                @END
                0;JMP""");
        fileWriter.println("""
                (END)
                @END
                0;JMP""");
    }
    /**
     * closes the output file
     */
    public void close() {
        //close output stream
        fileWriter.close();
    }
}
