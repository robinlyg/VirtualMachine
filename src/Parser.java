import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


/**
 * Parser : Handles the parsing of a single .vm file
 * Reads a VM Command, parses the command into is lexical components,
 * and provides convenient access to these components
 * Ignores all white space and comments
 */
public class Parser {

    private final String ARITHMETIC = "arithmetic";
    private final String PUSH = "push";
    private final String POP = "pop";
    private final String NO_COMMAND = "empty";

    protected Scanner fileScanner;
    // protected String instruction;
    protected int address;
    protected String currentCommand, instruction, segment;


    /**
     * Constructor: Opens the input file/stream and gets ready to parse it
     *
     * @param fileName
     */
    public Parser(String fileName) {
        try {
            fileScanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Error in parser " + e.getMessage());
        }
    }


    /**
     * hasMoreCommands: Are there more commands in the input
     *
     * @return
     */
    public boolean hasMoreCommands() {
        if (!(fileScanner.hasNext())) {
            fileScanner.close();
            return false;
        }
        return true;
    }

    /**
     * advance: reads the next command from the input and makes it the CURRENT COMMAND.
     * Should be called ONLY if hasMoreCommands is true.
     * Initially there is no current command.
     */
    public void advance() {
        if (!(hasMoreCommands())) {
            return;

        }
        currentCommand = fileScanner.nextLine();
        //call parse() to parse out instruction and address
        parse();

    }

    /**
     * @return a constant representing the type of the current command
     * C_ARITHMETIC is returned for all the arithmetic/logic commands
     */
    private String commandType() {

        //if it !startWith a P its an arithmetic command
        if (currentCommand.startsWith("/"))
            return NO_COMMAND;
        if (!(currentCommand.startsWith("p")))
            return ARITHMETIC;
        //if substring 0,2 == po its a pop
        //else its a push
        if (currentCommand.startsWith("po"))
            return POP;
        else return PUSH;

    }

    private void parse() {

        if (!(commandType().equals(NO_COMMAND))) {
           //TODO clean();
            String[] tokens = currentCommand.split(" ");
            //System.out.println(Arrays.toString(tokens));
            instruction = tokens[0];
            if (tokens.length > 1) {
                segment = tokens[1];
                address = Integer.parseInt(tokens[2]);
            }
        }

    }

    public String getInstruction() {
        return instruction;
    }

    public int getAddress() {
        return address;
    }

    public String getSegment() {
        return segment;
    }


}
