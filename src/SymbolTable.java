import java.util.HashMap;

public class SymbolTable {
    HashMap<String, Integer> symbolTable = new HashMap<>();

    public SymbolTable() {

        symbolTable.put("local",    300 );
        symbolTable.put("argument", 400 );
        symbolTable.put("this",     3030);
        symbolTable.put("that",     3040);
        symbolTable.put("temp",     5   );
        symbolTable.put("constant", 0   );
        symbolTable.put("static",   0   );
        symbolTable.put("pointer",   3   );

    }

    public int getValue(String s) {
        return symbolTable.get(s);
    }

}
