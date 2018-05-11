package example.caojiehang.com.graduationproject.DataDb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class DeviceData {
    @Id
    private Long id;
    private String glovename;
    private String speed;
    private String efficiency;
    private String receiveTime;
    private String Date;
    @Generated(hash = 773805625)
    public DeviceData(Long id, String glovename, String speed, String efficiency,
            String receiveTime, String Date) {
        this.id = id;
        this.glovename = glovename;
        this.speed = speed;
        this.efficiency = efficiency;
        this.receiveTime = receiveTime;
        this.Date = Date;
    }
    @Generated(hash = 929507321)
    public DeviceData() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGlovename() {
        return this.glovename;
    }
    public void setGlovename(String glovename) {
        this.glovename = glovename;
    }
    public String getSpeed() {
        return this.speed;
    }
    public void setSpeed(String speed) {
        this.speed = speed;
    }
    public String getEfficiency() {
        return this.efficiency;
    }
    public void setEfficiency(String efficiency) {
        this.efficiency = efficiency;
    }
    public String getReceiveTime() {
        return this.receiveTime;
    }
    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }
    public String getDate() {
        return this.Date;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }
}
