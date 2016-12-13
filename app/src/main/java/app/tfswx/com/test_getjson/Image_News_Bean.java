package app.tfswx.com.test_getjson;

import java.util.List;

/**
 * Created by 13 on 2016/12/5.
 */

public class Image_News_Bean {

    /**
     * reason : 成功的返回
     * result : {"stat":"1","data":[{"title":"吃完鸡蛋千万别立刻做7件事","date":"2016-12-06 07:34","author_name":"光明网","thumbnail_pic_s":"http://04.imgmini.eastday.com/mobile/20161206/20161206073417_b50cbd8d099fbb47625239b84491863b_1_mwpm_03200403.jpeg","thumbnail_pic_s02":"http://04.imgmini.eastday.com/mobile/20161206/20161206073417_b50cbd8d099fbb47625239b84491863b_1_mwpl_05500201.jpeg","thumbnail_pic_s03":"http://04.imgmini.eastday.com/mobile/20161206/20161206073417_b50cbd8d099fbb47625239b84491863b_1_mwpl_05500201.jpeg","url":"http://mini.eastday.com/mobile/161206073417192.html?qid=juheshuju","uniquekey":"161206073417192","type":"头条","realtype":"健康"},{"title":"中国少女获赞万年一遇东方美人 网友称难苟同","date":"2016-12-06 00:48","author_name":"参考消息网","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20161206/20161206004832_35697f81d28190c2143239520dfdc9fe_1_mwpm_03200403.jpeg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20161206/20161206004832_35697f81d28190c2143239520dfdc9fe_1_mwpl_05500201.jpeg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20161206/20161206004832_35697f81d28190c2143239520dfdc9fe_1_mwpl_05500201.jpeg","url":"http://mini.eastday.com/mobile/161206004832465.html?qid=juheshuju","uniquekey":"161206004832465","type":"头条","realtype":"社会"},{"title":"东方图秀：这次强拆市民拍手称快，谁让你家的店叫\u201c叫了个鸡\u201d","date":"2016-12-05 14:18","author_name":"东方头条","thumbnail_pic_s":"http://08.imgmini.eastday.com/mobile/20161205/20161205141819_9bef9222dd3e251fd6406c5eb6ae60b1_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://08.imgmini.eastday.com/mobile/20161205/20161205141819_9bef9222dd3e251fd6406c5eb6ae60b1_1_mwpl_05500201.jpg","thumbnail_pic_s03":"http://08.imgmini.eastday.com/mobile/20161205/20161205141819_9bef9222dd3e251fd6406c5eb6ae60b1_1_mwpl_05500201.jpg","url":"http://mini.eastday.com/mobile/161205141819537.html?qid=juheshuju","uniquekey":"161205141819537","type":"头条","realtype":"社会"},{"title":"特朗普三咬中国 白宫紧急灭火","date":"2016-12-06 07:40","author_name":"凤凰国际iMarkets","thumbnail_pic_s":"http://05.imgmini.eastday.com/mobile/20161206/20161206074035_2737f91afacdb042cdfda0b46941bdda_1_mwpm_03200403.jpeg","thumbnail_pic_s02":"http://05.imgmini.eastday.com/mobile/20161206/20161206074035_2737f91afacdb042cdfda0b46941bdda_1_mwpl_05500201.jpeg","thumbnail_pic_s03":"http://05.imgmini.eastday.com/mobile/20161206/20161206074035_2737f91afacdb042cdfda0b46941bdda_1_mwpl_05500201.jpeg","url":"http://mini.eastday.com/mobile/161206074035217.html?qid=juheshuju","uniquekey":"161206074035217","type":"头条","realtype":"财经"}]}
     */

    private String reason;
    private ResultBean result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * stat : 1
         * data : [{"title":"吃完鸡蛋千万别立刻做7件事","date":"2016-12-06 07:34","author_name":"光明网","thumbnail_pic_s":"http://04.imgmini.eastday.com/mobile/20161206/20161206073417_b50cbd8d099fbb47625239b84491863b_1_mwpm_03200403.jpeg","thumbnail_pic_s02":"http://04.imgmini.eastday.com/mobile/20161206/20161206073417_b50cbd8d099fbb47625239b84491863b_1_mwpl_05500201.jpeg","thumbnail_pic_s03":"http://04.imgmini.eastday.com/mobile/20161206/20161206073417_b50cbd8d099fbb47625239b84491863b_1_mwpl_05500201.jpeg","url":"http://mini.eastday.com/mobile/161206073417192.html?qid=juheshuju","uniquekey":"161206073417192","type":"头条","realtype":"健康"},{"title":"中国少女获赞万年一遇东方美人 网友称难苟同","date":"2016-12-06 00:48","author_name":"参考消息网","thumbnail_pic_s":"http://02.imgmini.eastday.com/mobile/20161206/20161206004832_35697f81d28190c2143239520dfdc9fe_1_mwpm_03200403.jpeg","thumbnail_pic_s02":"http://02.imgmini.eastday.com/mobile/20161206/20161206004832_35697f81d28190c2143239520dfdc9fe_1_mwpl_05500201.jpeg","thumbnail_pic_s03":"http://02.imgmini.eastday.com/mobile/20161206/20161206004832_35697f81d28190c2143239520dfdc9fe_1_mwpl_05500201.jpeg","url":"http://mini.eastday.com/mobile/161206004832465.html?qid=juheshuju","uniquekey":"161206004832465","type":"头条","realtype":"社会"},{"title":"东方图秀：这次强拆市民拍手称快，谁让你家的店叫\u201c叫了个鸡\u201d","date":"2016-12-05 14:18","author_name":"东方头条","thumbnail_pic_s":"http://08.imgmini.eastday.com/mobile/20161205/20161205141819_9bef9222dd3e251fd6406c5eb6ae60b1_1_mwpm_03200403.jpg","thumbnail_pic_s02":"http://08.imgmini.eastday.com/mobile/20161205/20161205141819_9bef9222dd3e251fd6406c5eb6ae60b1_1_mwpl_05500201.jpg","thumbnail_pic_s03":"http://08.imgmini.eastday.com/mobile/20161205/20161205141819_9bef9222dd3e251fd6406c5eb6ae60b1_1_mwpl_05500201.jpg","url":"http://mini.eastday.com/mobile/161205141819537.html?qid=juheshuju","uniquekey":"161205141819537","type":"头条","realtype":"社会"},{"title":"特朗普三咬中国 白宫紧急灭火","date":"2016-12-06 07:40","author_name":"凤凰国际iMarkets","thumbnail_pic_s":"http://05.imgmini.eastday.com/mobile/20161206/20161206074035_2737f91afacdb042cdfda0b46941bdda_1_mwpm_03200403.jpeg","thumbnail_pic_s02":"http://05.imgmini.eastday.com/mobile/20161206/20161206074035_2737f91afacdb042cdfda0b46941bdda_1_mwpl_05500201.jpeg","thumbnail_pic_s03":"http://05.imgmini.eastday.com/mobile/20161206/20161206074035_2737f91afacdb042cdfda0b46941bdda_1_mwpl_05500201.jpeg","url":"http://mini.eastday.com/mobile/161206074035217.html?qid=juheshuju","uniquekey":"161206074035217","type":"头条","realtype":"财经"}]
         */

        private String stat;
        private List<DataBean> data;

        public String getStat() {
            return stat;
        }

        public void setStat(String stat) {
            this.stat = stat;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * title : 吃完鸡蛋千万别立刻做7件事
             * date : 2016-12-06 07:34
             * author_name : 光明网
             * thumbnail_pic_s : http://04.imgmini.eastday.com/mobile/20161206/20161206073417_b50cbd8d099fbb47625239b84491863b_1_mwpm_03200403.jpeg
             * thumbnail_pic_s02 : http://04.imgmini.eastday.com/mobile/20161206/20161206073417_b50cbd8d099fbb47625239b84491863b_1_mwpl_05500201.jpeg
             * thumbnail_pic_s03 : http://04.imgmini.eastday.com/mobile/20161206/20161206073417_b50cbd8d099fbb47625239b84491863b_1_mwpl_05500201.jpeg
             * url : http://mini.eastday.com/mobile/161206073417192.html?qid=juheshuju
             * uniquekey : 161206073417192
             * type : 头条
             * realtype : 健康
             */

            private String title;
            private String date;
            private String author_name;
            private String thumbnail_pic_s;
            private String thumbnail_pic_s02;
            private String thumbnail_pic_s03;
            private String url;
            private String uniquekey;
            private String type;
            private String realtype;
            public DataBean(String title,String date,String author_name,String thumbnail_pic_s,String url){
                this.title = title;
                this.date = date;
                this.author_name=author_name;
                this.thumbnail_pic_s = thumbnail_pic_s;
                this.url = url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getAuthor_name() {
                return author_name;
            }

            public void setAuthor_name(String author_name) {
                this.author_name = author_name;
            }

            public String getThumbnail_pic_s() {
                return thumbnail_pic_s;
            }

            public void setThumbnail_pic_s(String thumbnail_pic_s) {
                this.thumbnail_pic_s = thumbnail_pic_s;
            }

            public String getThumbnail_pic_s02() {
                return thumbnail_pic_s02;
            }

            public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
                this.thumbnail_pic_s02 = thumbnail_pic_s02;
            }

            public String getThumbnail_pic_s03() {
                return thumbnail_pic_s03;
            }

            public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
                this.thumbnail_pic_s03 = thumbnail_pic_s03;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUniquekey() {
                return uniquekey;
            }

            public void setUniquekey(String uniquekey) {
                this.uniquekey = uniquekey;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getRealtype() {
                return realtype;
            }

            public void setRealtype(String realtype) {
                this.realtype = realtype;
            }
        }
    }
}
