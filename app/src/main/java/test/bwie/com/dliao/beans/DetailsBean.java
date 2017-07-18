package test.bwie.com.dliao.beans;

import java.util.List;

/**
 * Created by lenovo-pc on 2017/7/14.
 */

public class DetailsBean {

    /**
     * result_message : success
     * data : {"area":"安徽省 安庆市 宿松县","lasttime":1500018917981,"createtime":1499957717532,"gender":"男","introduce":"PreferencesUtils","imagePath":"http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_man.jpg","nickname":"木晗兮","userId":2,"relation":0,"photolist":[]}
     * result_code : 200
     */

    private String result_message;
    private DataBean data;
    private int result_code;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public static class DataBean {
        /**
         * area : 安徽省 安庆市 宿松县
         * lasttime : 1500018917981
         * createtime : 1499957717532
         * gender : 男
         * introduce : PreferencesUtils
         * imagePath : http://dyt-pict.oss-cn-beijing.aliyuncs.com/dliao/default_man.jpg
         * nickname : 木晗兮
         * userId : 2
         * relation : 0
         * photolist : []
         */

        private String area;
        private long lasttime;
        private long createtime;
        private String gender;
        private String introduce;
        private String imagePath;
        private String nickname;
        private int userId;
        private int relation;
        private List<?> photolist;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public long getLasttime() {
            return lasttime;
        }

        public void setLasttime(long lasttime) {
            this.lasttime = lasttime;
        }

        public long getCreatetime() {
            return createtime;
        }

        public void setCreatetime(long createtime) {
            this.createtime = createtime;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public List<?> getPhotolist() {
            return photolist;
        }

        public void setPhotolist(List<?> photolist) {
            this.photolist = photolist;
        }
    }
}
