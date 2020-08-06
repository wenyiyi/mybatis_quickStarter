package wyd;

import com.wyd.dao.IUserDao;
import com.wyd.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MybatisTest {

    public SqlSession getSqlSession() throws IOException {
        // Resource 工具类，把配置文件加载成字节输入流
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        // 解析配置文件，创建 SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        // 创建 SqlSession，默认开始事务，但事务不会自动提交，进行增删改时需手动提交
        // true 默认自动提交事务
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        return sqlSession;
    }

    @Test
    public void test1() throws IOException {
        SqlSession sqlSession = getSqlSession();
        List<User> users = sqlSession.selectList("user.findAll");
        System.out.println(users);
    }

    @Test
    public void test2() throws IOException {
        SqlSession sqlSession = getSqlSession();
        User user = new User();
        user.setId(3);
        user.setUsername("kaka");
        sqlSession.insert("user.saveUser",user);
        // 进行增删改需要手动提交事务
        // sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test3() throws IOException {
        SqlSession sqlSession = getSqlSession();
        User user = new User();
        user.setId(4);
        user.setUsername("444");
        sqlSession.insert("user.updateUser",user);
        // 进行增删改需要手动提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void test4() throws IOException {
        SqlSession sqlSession = getSqlSession();
        User user = new User();
        user.setId(4);
        user.setUsername("444");
        sqlSession.insert("user.deleteUser",user);
        // 进行增删改需要手动提交事务
        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 使用动态代理模式
     * @throws IOException
     */
    @Test
    public void test5() throws IOException {
        SqlSession sqlSession = getSqlSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        List<User> users = userDao.findAll();
        System.out.println(users);
    }

    /**
     * @throws IOException
     */
    @Test
    public void test6() throws IOException {
        SqlSession sqlSession = getSqlSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user = new User();
        user.setId(4);
        user.setUsername("kaka");
        List<User> users = userDao.findByCondition(user);
        System.out.println(users);
    }


    /**
     * @throws IOException
     */
    @Test
    public void test7() throws IOException {
        SqlSession sqlSession = getSqlSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        List<User> users = userDao.findByIds(ids);
        System.out.println(users);
    }

}
