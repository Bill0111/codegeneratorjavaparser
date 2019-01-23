package Code.Controllers;

class Context {
    private final static Context instance = new Context();
    static Context getInstance() {
        return instance;
    }

    private String user;
    void setUser(String user)
    {
        this.user=user;
    }
    String getUser() {
        return user;
    }

    private int sessionDuration;
    void setTime(int sessionDuration)
    {
        this.sessionDuration = sessionDuration;
    }
    int getTime() {
        return sessionDuration;
    }

    private boolean forLoop;
    void setForLoop(boolean forLoop){this.forLoop = forLoop;}
    boolean getForLoop() {return forLoop; }

    private int codeComplexity;
    void setCodeComplexity(int codeComplexity){this.codeComplexity = codeComplexity;}
    int getCodeComplexity(){return codeComplexity;}

    private int logicComplexity;
    void setLogicComplexity(int logicComplexity){this.logicComplexity = logicComplexity;}
    int getLogicComplexity(){return logicComplexity;}

    private boolean ifStatement;
    void  setIfStatement(boolean ifStatement){this.ifStatement = ifStatement;}
    boolean getIfStatement(){return ifStatement;}

    private boolean whileLoop;
    void setWhileLoop(boolean whileLoop){this.whileLoop = whileLoop;}
    boolean getWhileLoop() {return whileLoop; }



}
