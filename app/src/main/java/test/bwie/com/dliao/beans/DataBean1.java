package test.bwie.com.dliao.beans;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;


    @Entity(nameInDb = "friendbean")
    public class DataBean1 {

        /**
         * area : 安徽省-安庆市-枞阳县
         * picWidth : 720
         * createtime : 1500016251283
         * picHeight : 720
         * gender : 女
         * lng : 0
         * introduce : 兔兔
         * imagePath : http://qhb.2dyt.com/MyInterface/images/3c878e19-4c5a-4c59-9df4-eba244b6757e.jpg
         * userId : 34
         * yxpassword :
         * relation : 0
         * password :
         * lasttime : 1500016275649
         * phone : 18222222222
         * nickname : 傻傻的
         * age : 25
         * lat : 0
         */
        @Id(autoincrement = true)
        private Long id;
        @NotNull
        @Property
        private String area;
        @NotNull
        @Property
        private int picWidth;
        @NotNull
        @Property
        private long createtime;
        @NotNull
        @Property
        private int picHeight;
        @NotNull
        @Property
        private String gender;
        @NotNull
        @Property
        private Double lng;
        @NotNull
        @Property
        private String introduce;
        @NotNull
        @Property
        private String imagePath;
        @NotNull
        @Property
        private int userId;
        @NotNull
        @Property
        private String yxpassword;
        @NotNull
        @Property
        private int relation;
        @NotNull
        @Property
        private String password;
        @NotNull
        @Property
        private long lasttime;
        @NotNull
        @Property
        private String phone;
        @NotNull
        @Property
        private String nickname;
        @NotNull
        @Property
        private String age;
        @NotNull
        @Property
        private Double lat;
        @NotNull
        @Property
        private long relationtime;
        @Generated(hash = 1597295470)
        public DataBean1(Long id, @NotNull String area, int picWidth, long createtime, int picHeight,
                @NotNull String gender, @NotNull Double lng, @NotNull String introduce,
                @NotNull String imagePath, int userId, @NotNull String yxpassword, int relation,
                @NotNull String password, long lasttime, @NotNull String phone, @NotNull String nickname,
                @NotNull String age, @NotNull Double lat, long relationtime) {
            this.id = id;
            this.area = area;
            this.picWidth = picWidth;
            this.createtime = createtime;
            this.picHeight = picHeight;
            this.gender = gender;
            this.lng = lng;
            this.introduce = introduce;
            this.imagePath = imagePath;
            this.userId = userId;
            this.yxpassword = yxpassword;
            this.relation = relation;
            this.password = password;
            this.lasttime = lasttime;
            this.phone = phone;
            this.nickname = nickname;
            this.age = age;
            this.lat = lat;
            this.relationtime = relationtime;
        }
        @Generated(hash = 338036120)
        public DataBean1() {
        }
        public Long getId() {
            return this.id;
        }
        public void setId(Long id) {
            this.id = id;
        }
        public String getArea() {
            return this.area;
        }
        public void setArea(String area) {
            this.area = area;
        }
        public int getPicWidth() {
            return this.picWidth;
        }
        public void setPicWidth(int picWidth) {
            this.picWidth = picWidth;
        }
        public long getCreatetime() {
            return this.createtime;
        }
        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }
        public int getPicHeight() {
            return this.picHeight;
        }
        public void setPicHeight(int picHeight) {
            this.picHeight = picHeight;
        }
        public String getGender() {
            return this.gender;
        }
        public void setGender(String gender) {
            this.gender = gender;
        }
        public Double getLng() {
            return this.lng;
        }
        public void setLng(Double lng) {
            this.lng = lng;
        }
        public String getIntroduce() {
            return this.introduce;
        }
        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }
        public String getImagePath() {
            return this.imagePath;
        }
        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }
        public int getUserId() {
            return this.userId;
        }
        public void setUserId(int userId) {
            this.userId = userId;
        }
        public String getYxpassword() {
            return this.yxpassword;
        }
        public void setYxpassword(String yxpassword) {
            this.yxpassword = yxpassword;
        }
        public int getRelation() {
            return this.relation;
        }
        public void setRelation(int relation) {
            this.relation = relation;
        }
        public String getPassword() {
            return this.password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public long getLasttime() {
            return this.lasttime;
        }
        public void setLasttime(long lasttime) {
            this.lasttime = lasttime;
        }
        public String getPhone() {
            return this.phone;
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }
        public String getNickname() {
            return this.nickname;
        }
        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
        public String getAge() {
            return this.age;
        }
        public void setAge(String age) {
            this.age = age;
        }
        public Double getLat() {
            return this.lat;
        }
        public void setLat(Double lat) {
            this.lat = lat;
        }
        public long getRelationtime() {
            return this.relationtime;
        }
        public void setRelationtime(long relationtime) {
            this.relationtime = relationtime;
        }


}
