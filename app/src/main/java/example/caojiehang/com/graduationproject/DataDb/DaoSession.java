package example.caojiehang.com.graduationproject.DataDb;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import example.caojiehang.com.graduationproject.DataDb.DeviceData;

import example.caojiehang.com.graduationproject.DataDb.DeviceDataDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig deviceDataDaoConfig;

    private final DeviceDataDao deviceDataDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        deviceDataDaoConfig = daoConfigMap.get(DeviceDataDao.class).clone();
        deviceDataDaoConfig.initIdentityScope(type);

        deviceDataDao = new DeviceDataDao(deviceDataDaoConfig, this);

        registerDao(DeviceData.class, deviceDataDao);
    }
    
    public void clear() {
        deviceDataDaoConfig.clearIdentityScope();
    }

    public DeviceDataDao getDeviceDataDao() {
        return deviceDataDao;
    }

}
