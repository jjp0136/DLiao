package test.bwie.com.dliao.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import test.bwie.com.dliao.beans.DataBean;
import test.bwie.com.dliao.beans.DataBean1;

import test.bwie.com.dliao.dao.DataBeanDao;
import test.bwie.com.dliao.dao.DataBean1Dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig dataBeanDaoConfig;
    private final DaoConfig dataBean1DaoConfig;

    private final DataBeanDao dataBeanDao;
    private final DataBean1Dao dataBean1Dao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        dataBeanDaoConfig = daoConfigMap.get(DataBeanDao.class).clone();
        dataBeanDaoConfig.initIdentityScope(type);

        dataBean1DaoConfig = daoConfigMap.get(DataBean1Dao.class).clone();
        dataBean1DaoConfig.initIdentityScope(type);

        dataBeanDao = new DataBeanDao(dataBeanDaoConfig, this);
        dataBean1Dao = new DataBean1Dao(dataBean1DaoConfig, this);

        registerDao(DataBean.class, dataBeanDao);
        registerDao(DataBean1.class, dataBean1Dao);
    }
    
    public void clear() {
        dataBeanDaoConfig.clearIdentityScope();
        dataBean1DaoConfig.clearIdentityScope();
    }

    public DataBeanDao getDataBeanDao() {
        return dataBeanDao;
    }

    public DataBean1Dao getDataBean1Dao() {
        return dataBean1Dao;
    }

}
