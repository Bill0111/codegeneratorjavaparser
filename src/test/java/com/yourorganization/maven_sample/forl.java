package com.yourorganization.maven_sample;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.oracle.deploy.update.Updater;
import sun.tools.tree.BinaryCompareExpression;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;

public class forl {
    public static void main(String[] args) {
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogicPositivizer.class).resolve("src/main/resources"));

        CompilationUnit cu = new CompilationUnit();
        ClassOrInterfaceDeclaration forloop = cu.addClass("forloop");
        forloop.addField("int","result");
        forloop.addMethod("main",Modifier.PUBLIC).setStatic(true).addParameter("String[]","args").setBody
                (new BlockStmt()
                        .addStatement(new ExpressionStmt( new AssignExpr(new NameExpr("result"), new IntegerLiteralExpr(0),AssignExpr.Operator.ASSIGN)) )
                        .addStatement(new ForStmt()
                                .setBody(new BlockStmt().addStatement(new ExpressionStmt(new AssignExpr(new NameExpr("result"), new IntegerLiteralExpr(0),AssignExpr.Operator.ASSIGN))))
//                                .setInitialization())
                        )
                        .addStatement(new ReturnStmt(new NameExpr("result"))));

        System.out.println(cu.toString());
    }
}
