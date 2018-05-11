package example.caojiehang.com.graduationproject.DataDb;

public class GreenDaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GreenDaoManager mInstance;

    private GreenDaoManager() {
        if(mInstance == null) {

            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(BaseApplication.getContext(),"Devicedatas.db",null);
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();

        }

    }
    private static class SingleInstanceHolder {
        private static final GreenDaoManager INSTANCE = new GreenDaoManager();
    }

    public static GreenDaoManager getInstance() {
        if(mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if(mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }
        }

        return mInstance;
    }

    public DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }
}
