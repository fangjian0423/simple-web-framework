package org.format.framework.config;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class SqlMapConfig {

    private static final SqlSessionFactory sqlSessionFactory;

    static {
        try {
            String resource = "Configuration.xml";
            Reader reader = Resources.getResourceAsReader(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing MyBatis config. Cause: " + e);
        }
    }

    public static SqlSessionFactory getSessionFactory() {
        return sqlSessionFactory;
    }

    public static SqlSession getSession() {
        return sqlSessionFactory.openSession();
    }


}
