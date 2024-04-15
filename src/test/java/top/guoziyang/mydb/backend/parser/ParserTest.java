package top.guoziyang.mydb.backend.parser;

import java.util.Arrays;
import java.util.logging.Logger;

import com.google.gson.Gson;

import org.junit.Test;

import top.guoziyang.mydb.backend.parser.statement.Begin;
import top.guoziyang.mydb.backend.parser.statement.Create;
import top.guoziyang.mydb.backend.parser.statement.Delete;
import top.guoziyang.mydb.backend.parser.statement.Insert;
import top.guoziyang.mydb.backend.parser.statement.Select;
import top.guoziyang.mydb.backend.parser.statement.Show;
import top.guoziyang.mydb.backend.parser.statement.Update;
import top.guoziyang.mydb.backend.vm.VersionManagerImpl;

public class ParserTest {
    private static final Logger logger = Logger.getLogger(VersionManagerImpl.class.getName());

    @Test
    public void testCreate() throws Exception {
        String stat = "create table student id int32, name string, uid int64, (index name id uid)";
        Object res = Parser.Parse(stat.getBytes());
        Create create = (Create)res;
        assert "student".equals(create.tableName);
        logger.info("Create");
        for (int i = 0; i < create.fieldName.length; i++) {
            logger.info(create.fieldName[i] + ":" + create.fieldType[i]);
        }
        logger.info(Arrays.toString(create.index));
        logger.info("======================");
    }

    @Test
    public void testBegin() throws Exception {
        String stat = "begin isolation level read committed";
        Object res = Parser.Parse(stat.getBytes());
        Begin begin = (Begin)res;
        assert !begin.isRepeatableRead();

        stat = "begin";
        res = Parser.Parse(stat.getBytes());
        begin = (Begin)res;
        assert !begin.isRepeatableRead();

        stat = "begin isolation level repeatable read";
        res = Parser.Parse(stat.getBytes());
        begin = (Begin)res;
        assert begin.isRepeatableRead();
    }

    @Test
    public void testRead() throws Exception {
        String stat = "select name, id, strudeng from student where id > 1 and id < 4";
        Object res = Parser.Parse(stat.getBytes());
        Select select = (Select)res;
        assert "student".equals(select.tableName);
        Gson gson = new Gson();
        logger.info("Select");
        logger.info(gson.toJson(select.fields));
        logger.info(gson.toJson(select.where));
        logger.info("======================");
    }

    @Test
    public void testInsert() throws Exception {
        String stat = "insert into student values 5 \"Guo Ziyang\" 22";
        Object res = Parser.Parse(stat.getBytes());
        Insert insert = (Insert)res;
        Gson gson = new Gson();
        logger.info("Insert");
        logger.info(gson.toJson(insert));
        logger.info("======================");
    }

    @Test
    public void testDelete() throws Exception {
        String stat = "delete from student where name = \"Guo Ziyang\"";
        Object res = Parser.Parse(stat.getBytes());
        Delete delete = (Delete)res;
        Gson gson = new Gson();
        logger.info("Delete");
        logger.info(gson.toJson(delete));
        logger.info("======================");
    }

    @Test
    public void testShow() throws Exception {
        String stat = "show";
        Object res = Parser.Parse(stat.getBytes());
        Show show = (Show)res;
        Gson gson = new Gson();
        logger.info("Show");
        logger.info(gson.toJson(show));
        logger.info("======================");
    }

    @Test
    public void testUpdate() throws Exception {
        String stat = "update student set name = \"GZY\" where id = 5";
        Object res = Parser.Parse(stat.getBytes());
        Update update = (Update)res;
        Gson gson = new Gson();
        logger.info("Update");
        logger.info(gson.toJson(update));
        logger.info("======================");
    }
}
