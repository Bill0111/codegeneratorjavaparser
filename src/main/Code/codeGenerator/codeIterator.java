package Code.codeGenerator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.metamodel.BinaryExprMetaModel;
import com.github.javaparser.metamodel.NodeMetaModel;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.sun.tools.javac.util.GraphUtils;
import sun.jvm.hotspot.opto.Block;
import sun.tools.jstat.Expression;
import sun.tools.jstat.Operator;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

/**
 * Some code that uses JavaParser.
 */
public class codeIterator {
    private CompilationUnit cu;
    public codeIterator(ArrayList<String> loopType, int codeComplexity,int logicComplexity){
        cu = generate(loopType,(int)(Math.random()*codeComplexity)+1,(int)(Math.random()*(logicComplexity+1)));

    }
    public CompilationUnit getCu(){
        return cu;
    }


    private IntegerLiteralExpr randomInt(){
        return new IntegerLiteralExpr( (int)(Math.random()*10));
    }
    private IntegerLiteralExpr randomIntTiny(){
        return new IntegerLiteralExpr( (int)(Math.random()*3)-1);
    }
    private IntegerLiteralExpr randomIntNeg(){
        return new IntegerLiteralExpr( -(int)(Math.random()*10));
    }

    private BinaryExpr.Operator randomOp(){
        ArrayList<BinaryExpr.Operator> a = new ArrayList<BinaryExpr.Operator>();
        a.add(BinaryExpr.Operator.EQUALS);
        a.add(BinaryExpr.Operator.GREATER);
        a.add(BinaryExpr.Operator.GREATER_EQUALS);
        a.add(BinaryExpr.Operator.LESS);
        a.add(BinaryExpr.Operator.LESS_EQUALS);
        return a.get((int)(Math.random()*5));
    }

    private UnaryExpr.Operator randomUex(){
        ArrayList<UnaryExpr.Operator> a = new ArrayList<UnaryExpr.Operator>();
        a.add(UnaryExpr.Operator.POSTFIX_INCREMENT);
        a.add((UnaryExpr.Operator.POSTFIX_DECREMENT));
        return a.get((int)(Math.random()*2));
    }

    private AssignExpr randamAssign(com.github.javaparser.ast.expr.Expression i){
        ArrayList<AssignExpr> a = new ArrayList<AssignExpr>();
        a.add(new AssignExpr(i,new IntegerLiteralExpr(2), AssignExpr.Operator.PLUS));
        a.add(new AssignExpr(i,new IntegerLiteralExpr(2), AssignExpr.Operator.MINUS));
        a.add(new AssignExpr(i,new IntegerLiteralExpr(2), AssignExpr.Operator.MULTIPLY));
        a.add(new AssignExpr(i,new IntegerLiteralExpr(3), AssignExpr.Operator.PLUS));
        a.add(new AssignExpr(i,new IntegerLiteralExpr(3), AssignExpr.Operator.MINUS));
        return a.get((int)(Math.random()*5));
    }

    private AssignExpr.Operator randomAssignOp(){
        ArrayList<AssignExpr.Operator> a = new ArrayList<AssignExpr.Operator>();
        a.add(AssignExpr.Operator.MINUS);
        a.add(AssignExpr.Operator.PLUS);
        a.add(AssignExpr.Operator.MULTIPLY);
        return a.get((int)(Math.random()*3));
    }

    private BinaryExpr.Operator randomBiOp(){
        int rand = (int)(Math.random()*2);
        if(rand ==0)
            return BinaryExpr.Operator.AND;
        else
            return BinaryExpr.Operator.OR;
    }

    private BinaryExpr complexBinaryExpression (com.github.javaparser.ast.expr.Expression i){
        BinaryExpr temp = new BinaryExpr();
        temp.setLeft(i.asBinaryExpr().getLeft());
        temp.setRight(randomInt());
        temp.setOperator(randomOp());

        i.asBinaryExpr().setOperator(randomOp());
        i.asBinaryExpr().setRight(randomInt());

        BinaryExpr result = new BinaryExpr();

        result.setRight(temp);
        result.setLeft(i);
        result.setOperator(randomBiOp());


        return result;
    }

//    ****************
    // i<10

    private void changeBinaryExpr(CompilationUnit cu){
        cu.accept(new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(BinaryExpr n, Void arg) {
                if(!(n.getOperator().equals(BinaryExpr.Operator.AND)) && !(n.getOperator().equals(BinaryExpr.Operator.OR)))
                {
                    if(!(n.getOperator().equals(BinaryExpr.Operator.PLUS))&&!(n.getOperator().equals(BinaryExpr.Operator.MINUS))&&!(n.getOperator().equals(BinaryExpr.Operator.MULTIPLY))&&!(n.getOperator().equals(BinaryExpr.Operator.REMAINDER))) {

                        n.setOperator(randomOp());
                        if(n.getOperator().equals(BinaryExpr.Operator.EQUALS))
                            n.getRight().replace(randomIntTiny());
                        else if (n.getOperator().equals(BinaryExpr.Operator.GREATER)|| n.getOperator().equals(BinaryExpr.Operator.GREATER_EQUALS))
                            n.getRight().replace(randomIntNeg());
                        else if (n.getOperator().equals(BinaryExpr.Operator.LESS)|| n.getOperator().equals(BinaryExpr.Operator.LESS_EQUALS))
                            n.getRight().replace(randomInt());


                    }
                }

                return super.visit(n, arg);
            }
        }, null);
    }


    //    ****************
    // i++

    private void changeUnaryExpr(CompilationUnit cu){
        cu.accept(new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(UnaryExpr n, Void arg) {
                int rand = (int)(Math.random()*2);
                if (rand == 1) {
                    n.setOperator(randomUex());
                }
                else {
                    n.replace(randamAssign(n.getExpression()));
                }
                return super.visit(n, arg);
            }
        }, null);

    }

    //    ****************
    // i+=2

    private void changeAssignExpr(CompilationUnit cu){
        cu.accept(new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(AssignExpr n, Void arg) {
                if (!(n.getOperator().equals(AssignExpr.Operator.ASSIGN))) {
                    n.setOperator(randomAssignOp());
                }
                n.setValue(randomInt());
                return super.visit(n, arg);
            }
        }, null);
    }

    //    ****************
    // ifstmt

    private void changeLogicExpr(CompilationUnit cu, int logicComplexity){
        if(logicComplexity==0)
        {
            cu.accept(new ModifierVisitor<Void>() {
                @Override
                public Visitable visit(IfStmt n, Void arg) {
                    n.replace(n.getThenStmt().getChildNodes().get(0));
                    return super.visit(n, arg);
                }
            }, null);
        }
        else if(logicComplexity==2)
        {
            cu.accept(new ModifierVisitor<Void>() {
                @Override
                public Visitable visit(IfStmt n, Void arg) {
                n.setCondition(complexBinaryExpression(n.getCondition().asBinaryExpr()));
                return super.visit(n, arg);
                }
            }, null);

        }
        else if(logicComplexity==3){
            cu.accept(new ModifierVisitor<Void>() {
                @Override
                public Visitable visit(IfStmt n, Void arg) {
                    n.setElseStmt(JavaParser.parseBlock("{result-=2; break;}"));
                    return super.visit(n, arg);
                }
            }, null);


        }
    }

    //    ****************
    // Block

    private BlockStmt blockStmtGenerator(String loopName, int codeComplexity, int logicComplexoty) {
        ArrayList<BlockStmt> forloops = new ArrayList<BlockStmt>();
        forloops.add(JavaParser.parseBlock("{for(int i=0; i<10;i++)\n" +
                "        {\n" +
                "        \tif(i>result+5)\n" +
                "        \t{\n" +
                "            \tresult+=10;\n" +
                "            }\n" +
                "        }}"));

        forloops.add(JavaParser.parseBlock("{for(int i=0; i<5;i++)\n" +
                "        {\n" +
                "        \tfor(int j=3;j<=9;j+=3)\n" +
                "            {\n" +
                "                if(i+j>10)\n" +
                "                {\n" +
                "            \t   result+=i;\n" +
                "                }\n" +
                "            }\n" +
                "        }}"));
        forloops.add(JavaParser.parseBlock("{for(int i=0; i<5;i++)\n" +
                "        {\n" +
                "        \tfor(int j=3;j<=9;j+=3)\n" +
                "            {\n" +
                "            \tfor(int k=5;k<j;k++)\n" +
                "            \t{\n" +
                "                    if(i+j<k)\n" +
                "                    {\n" +
                "            \t\t  result+=1;\n" +
                "                    }\n" +
                "            \t}\n" +
                "            }\n" +
                "        }}"));

        ArrayList<BlockStmt> whileloops = new ArrayList<BlockStmt>();
        whileloops.add(JavaParser.parseBlock("{while(result<10)\n" +
                "        {\n" +
                "        \tif(result%2==0)\n" +
                "        \t{\n" +
                "\t\t\t\tresult+=3;\n" +
                "        \t}\n" +
                "        }}"));
        whileloops.add(JavaParser.parseBlock("{while(result<10){\n" +
                "            result+=3;\n" +
                "        }\n" +
                "\n" +
                "        double temp = 0;\n" +
                "\n" +
                "        while(result>temp){\n" +
                "            if(temp+result==10)\n" +
                "            {\n" +
                "                break;\n" +
                "            }\n" +
                "        \tresult--;\n" +
                "            temp++;\n" +
                "        }}"));
        whileloops.add(JavaParser.parseBlock("{double temp =0;\n" +
                "        while(result<10)\n" +
                "        {\n" +
                "        \twhile(result+temp>2)\n" +
                "        \t{\n" +
                "        \t\tif(result<temp){\n" +
                "        \t\t\tresult++;\n" +
                "                }\n" +
                "\n" +
                "        \t\ttemp-=1;\n" +
                "        \t}\n" +
                "        \tresult+=2;\n" +
                "        }}"));



        if (loopName.equals("forloop"))
            return forloops.get(codeComplexity - 1);

        else if(loopName.equals("whileloop"))
             return whileloops.get(codeComplexity-1);
        return null;
    }

    //    ****************
    // Code Randomizer

    private BlockStmt generatorANDrandomizer(ArrayList<String> loopname, int codeComplexity, int logicComplexity){

        String name = loopname.get((int)(Math.random()*loopname.size()));
        CompilationUnit cu = new CompilationUnit()
                .addType(new ClassOrInterfaceDeclaration()
                        .addMember(new MethodDeclaration()
                                .setPublic(true)
                                .setStatic(true)
                                .setType("void")
                                .setName("main")
                                .addParameter("String[]","args"))
                        .setPublic(true)
                        .setName(name));


        cu.accept(new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(ClassOrInterfaceDeclaration n, Void arg) {

                BlockStmt start = JavaParser.parseBlock("{double result = 1.0;}");
                start.addStatement(blockStmtGenerator(name,codeComplexity,logicComplexity).getStatements().removeFirst());
                n.getMember(0).asMethodDeclaration().setBody(start);
                return super.visit(n, arg);
            }
        }, null);

        changeLogicExpr(cu,logicComplexity);
        changeAssignExpr(cu);
        changeBinaryExpr(cu);
        changeUnaryExpr(cu);


        BlockStmt result =  (BlockStmt) cu.getType(0).getMember(0).asMethodDeclaration().getBody().get();
        return result;
    }

    private CompilationUnit generate(ArrayList<String> loopname, int codeComplexity, int logicComplexity){
        String name = loopname.get((int)(Math.random()*loopname.size()));

        CompilationUnit cu = new CompilationUnit()
                .addType(new ClassOrInterfaceDeclaration()
                        .addMember(new MethodDeclaration()
                                .setPublic(true)
                                .setStatic(true)
                                .setType("void")
                                .setName("main")
                                .addParameter("String[]","args"))
                        .setPublic(true)
                        .setName("atest"));


        cu.accept(new ModifierVisitor<Void>() {
            @Override
            public Visitable visit(ClassOrInterfaceDeclaration n, Void arg) {

                BlockStmt start = JavaParser.parseBlock("{double result = 1.0;}");
                for(int i=0;i<(int)(Math.random()*codeComplexity+1);i++)
                    start.addStatement((Statement) generatorANDrandomizer(loopname,codeComplexity,logicComplexity).getChildNodes().get(1));

                start.addStatement("System.out.println(result);");
                n.getMember(0).asMethodDeclaration().setBody(start);
                return super.visit(n, arg);
            }
        }, null);

        return cu;
    }



}



//    private void forloop1(int logicComplexity){
//
//        cu.accept(new ModifierVisitor<Void>() {
//            @Override
//            public Visitable visit(ForStmt n, Void arg) {
////                n.setInitialization(new NodeList<Expression>(new NodeList<Expression>( new AssignExpr(new NameExpr("result"), new IntegerLiteralExpr(100),AssignExpr.Operator.ASSIGN))));
//                n.getInitialization().get(0).getChildNodes().get(0).getChildNodes().get(2).replace(randomInt());
//                n.getCompare().get().getChildNodes().get(1).replace(randomInt());
//                n.getCompare().get().asBinaryExpr().setOperator(randomOp());
//                if(rand==0)
//                    n.getUpdate().get(0).asUnaryExpr().setOperator(randomUex());
//                else
//                    n.getUpdate().get(0).replace(randamAssign(n.getUpdate().get(0).asUnaryExpr().getExpression()));
//                return super.visit(n, arg);
//            }
//        }, null);
//
//    }
