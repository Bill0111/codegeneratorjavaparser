package com.yourorganization.maven_sample;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.printer.DotPrinter;
import com.github.javaparser.printer.YamlPrinter;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class test {
    public static void main(String[] args) throws Exception
    {
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(LogicPositivizer.class).resolve("src/main/resources"));

        System.out.println(JavaParser.parseStatement ("for (int a=0;a<10;a++) {b = 1;}").asForStmt().setInitialization(new NodeList<Expression>( new AssignExpr(new NameExpr("result"), new IntegerLiteralExpr(0),AssignExpr.Operator.ASSIGN))));
//        System.out.println(cu);
        // Now comes the inspection code:
//        YamlPrinter printer = new YamlPrinter(true);
//        System.out.println(printer.output(cu));

        // Now comes the inspection code:
//        DotPrinter printer = new DotPrinter(true);
//        try (FileWriter fileWriter = new FileWriter("ast.dot");
//             PrintWriter printWriter = new PrintWriter(fileWriter)) {
//            printWriter.print(printer.output(cu));
//
//        }
//        dot -Tpng ast.dot > ast.png




//        cu.setPackageDeclaration("jpexample.model");
//        ClassOrInterfaceDeclaration book = cu.addClass("Book");
//        book.addField("String", "title");
//        book.addField("Person", "author");
//
//        book.addConstructor(Modifier.PUBLIC)
//                .addParameter("String", "title")
//                .addParameter("Person", "author")
//                .setBody(new BlockStmt()
//                        .addStatement(new ExpressionStmt(new AssignExpr(
//                                new FieldAccessExpr(
//                                        new ThisExpr(), "title"),
//                                new NameExpr("title"),
//                                AssignExpr.Operator.ASSIGN)))
//                        .addStatement(new ExpressionStmt(new AssignExpr(
//                                new FieldAccessExpr(
//                                        new ThisExpr(), "author"),
//                                new NameExpr("author"),
//                                AssignExpr.Operator.ASSIGN))));
//        System.out.println(cu.toString());
//
//        book.addMethod("getTitle", Modifier.PUBLIC).setBody(
//                new BlockStmt().addStatement(
//                        new ReturnStmt(new NameExpr("title"))));
//        book.addMethod("getAuthor", Modifier.PUBLIC).setBody(
//                new BlockStmt().addStatement(
//                        new ReturnStmt(new NameExpr("author"))));
//        System.out.println(cu.toString());
//
//        sourceRoot.saveAll(
//                // The path of the Maven module/project which contains the LogicPositivizer class.
//                CodeGenerationUtils.mavenModuleRoot(test.class)
//                        // appended with a path to "output"
//                        .resolve(Paths.get("output")));
    }
}
