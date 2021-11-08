package udfExtractor;

import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.Type;

import java.util.HashMap;
import java.util.List;

/**
 * Created by malig on 4/3/18.
 */
public class FunctionStructure {
    List<Modifier> mods;
    Type returnType;
    List parameters;
    Block body;
    HashMap<String, String> map;

    public FunctionStructure(List<Modifier> modifiers, Type rType, List p, Block b) {
        mods = modifiers;
        returnType = rType;
        parameters = p;
        body = b;
    }
}
