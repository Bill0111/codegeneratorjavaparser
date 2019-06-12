package Code.codeGenerator;




import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class codeGenerator {
    public static void main(String[] args) throws IOException {
//        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(codeIterator.class).resolve("src/main/resources"));
        BlockStmt start = JavaParser.parseBlock("{double result = 1.0; result = result + 1}");
        BlockStmt p = JavaParser.parseBlock( "{ System.out.println(result); }");
        System.out.println(p);
        ArrayList<String> a = new ArrayList<String>();
        start.getStatements().stream()
                .map(Node::toString)
                .filter(statement -> statement.contains("result"))
                .map(statement -> statement.split("=")[1])
                .map(statement -> statement.substring(0, statement.length() - 1))
//                .map(statement -> statement.split(" "))
//                .map(statement -> statement.split(" ")[1])
//                .map(statement -> String.join(" , ", statement))
                .forEach(System.out::println);


//
//        Statement test = new ExpressionStmt().setExpression(new Expression() {
//            @Override
//            public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
//                return null;
//            }
//
//            @Override
//            public <A> void accept(VoidVisitor<A> v, A arg) {
//
//            }
//        });
//        System.out.println(test);


//        start.getBody().get().getStatements().stream()
//                .map(Node::toString)
//                .filter(statement -> statement.contains("=") || statement.indexOf(" ") == statement.lastIndexOf(" "))
//                .map(statement -> statement.split("=")[0])
//                .map(statement -> statement.substring(0, statement.length() - 1))
//                .map(statement -> statement.split(" "))
//                .map(statement -> String.join(" // ", statement))
//                .forEach(System.out::println);

//        BlockStmt test = new BlockStmt().addStatement()

//
//        ArrayList<String> a = new ArrayList<String>();
//        a.add("forloop");
//        a.add("whileloop");
//        CompilationUnit cu = new codeIterator(a,1,3).getCu();
//        System.out.println(cu.getType(0).getMember(0).getChildNodes().get(3));

    }
}
