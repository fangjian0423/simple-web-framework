package org.format.framework.base;

import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class BaseDao {

    /**
     * 根据id查询对象对象
     * @param sqlSession
     * @param entityClass
     * @param id
     * @return
     */
    public <T> T getById(SqlSession sqlSession, Class<T> entityClass, int id) {
        return sqlSession.selectOne(entityClass.getSimpleName() + ".getById", id);
    }

    public <T> T getById(SqlSession sqlSession, Class<T> entityClass, String id) {
        return sqlSession.selectOne(entityClass.getSimpleName() + ".getById", id);
    }

    /**
     * 查找全部的实体对象
     * @param sqlSession
     * @param entityClass
     * @return
     */
    public List<?> getAll(SqlSession sqlSession, Class<?> entityClass) {
        return sqlSession.selectList(entityClass.getSimpleName()+".getAll");
    }

    /**
     * 根据条件找到对应的数据集合
     * @param sqlSession
     * @param entityClass
     * @param filter
     * @return
     */
    public List<?> getByCondition(SqlSession sqlSession, Class<?> entityClass, Map<String, Object> filter) {
        return sqlSession.selectList(entityClass.getSimpleName()+".getByCondition", filter);
    }


    /**
     * 添加数据
     * @param sqlSession
     * @param bean
     * @return 成功添加的条数
     */
    public int save(SqlSession sqlSession, Object bean) {
        return sqlSession.insert(bean.getClass().getSimpleName()+".insert", bean);
    }

    /**
     * 更新数据
     * @param sqlSession
     * @param bean
     * @return 成功更新的条数
     */
    public int update(SqlSession sqlSession, Object bean) {
        return sqlSession.update(bean.getClass().getSimpleName()+".update", bean);
    }

    /**
     * 删除数据
     * @param sqlSession
     * @param bean
     * @return 成功删除的条数
     */
    public int delete(SqlSession sqlSession, Object bean) {
        return sqlSession.delete(bean.getClass().getSimpleName(), bean);
    }

    /**
     * 删除数据
     * @param sqlSession
     * @param entityClass
     * @param id
     * @return 成功删除的条数
     */
    public int delete(SqlSession sqlSession, Class<?> entityClass, int id) {
        return sqlSession.delete(entityClass.getSimpleName(), id);
    }


}
