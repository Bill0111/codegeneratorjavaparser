package Code.codeGenerator;




import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.util.ArrayList;


public class codeGenerator {
    public static void main(String[] args) throws IOException {
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(codeIterator.class).resolve("src/main/resources"));



        ArrayList<String> a = new ArrayList<String>();
        a.add("forloop");
        a.add("whileloop");
        CompilationUnit cu = new codeIterator(a,1,3).getCu();
        System.out.println(cu.getType(0).getMember(0).getChildNodes().get(3));

    }
}
