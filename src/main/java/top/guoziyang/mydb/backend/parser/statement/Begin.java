package top.guoziyang.mydb.backend.parser.statement;

public class Begin {
    private boolean isRepeatableRead;

    public boolean isRepeatableRead() {
        return isRepeatableRead;
    }

    public void setRepeatableRead(boolean repeatableRead) {
        isRepeatableRead = repeatableRead;
    }
}
