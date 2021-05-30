new Vue({
    el: '.vue-box',
    data(){
        return{
            reportinfo:{
                projectlist:[],
                selectprojectid:'',
                time:'',
            },
            projectname:'',
            projectid:'',
            overalllistMap: [],
            caselistMap: [],
            time:'',
            pickerOptions: {
                shortcuts: [{
                    text: '最近一周',
                    onClick(picker) {
                        const end = new Date();
                        const start = new Date();
                        start.setTime(start.getTime() - 3600 * 1000 * 24 * 6);
                        picker.$emit('pick', [start, end]);
                    }
                },{
                    text: '上周',
                    onClick(picker) {
                        const oDate = new Date();
                        oDate.setTime(oDate.getTime() - 3600 * 1000 * 24 * 7);

                        var day = oDate.getDay()

                        var start = new Date(), end = new Date();
                        if (day == 0) {
                            start.setDate(oDate.getDate());
                            end.setDate(oDate.getDate() + 6);
                        } else {
                            start.setTime(oDate.getTime() - 3600 * 1000 * 24 * day);
                            end.setTime(oDate.getTime() + 3600 * 1000 * 24 * (6 - day));
                        }

                        picker.$emit('pick', [start, end]);
                    }
                }]
            }

        }
    },
    methods: {
        getBriefinfo:function(){
            var that=this;
            let time=that.time+"";
            let arr=[];
            arr=time.split(',');
            var opentime = new Date(arr[0]);
            var endtime = new Date(arr[1]);
            var differDay = Math.abs(opentime-endtime)/1000/60/60/24;
            if (time=="" )
            {
                this.$message({
                    showClose: true,
                    message: '请选择时间',
                    type: 'warning'
                });
                return 'false';
            }
            if (differDay!=6)
            {
                this.$message({
                    showClose: true,
                    message: '选择日期间隔错误，请重新选择时间',
                    type: 'warning'
                });
                return 'false';
            }
            $.ajax({
                type: 'POST',
                url: '/report/weekgetBriefinfobyprojectname',
                data: {
                    projectname: that.projectname,
                    opentime:opentime,
                    endtime:endtime,
                },
                success: function(res){
                    console.log("briefinfo,res:"+res);
                    that.overalllistMap=res.Briefinfo.overalllistMap;
                    that.caselistMap=res.Briefinfo.caselistMap;
                    that.projectid=res.Briefinfo.projectid;
                },
            });
        },
        getprojectlist:function(){
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/report/getprojectlist',
                data: {},
                success: function(res){
                    that.reportinfo.projectlist = res.projectlist;
                    console.log("projectlist:"+res.projectlist);
                },
            });
        },
        getweekreport:function(){
            var that=this;
            let time=that.time+"";
            let arr=[];
            arr=time.split(',');
            var opentime = new Date(arr[0]);
            var endtime = new Date(arr[1]);
            if (that.overalllistMap==""){
                this.$alert('没有找到数据，请重新查询','提示');
                return;
            }
            axios({
                method: "get",
                params:{
                    projectid:that.projectid,
                    opentime:opentime,
                    endtime:endtime,
                },
                url:"/report/getweekreport",
                responseType: "blob",
            })
                .then((response) => {
                    let fileName="【"+that.projectname+"】"+"-"+arr[0]+"~"+arr[1]+"-"+"测试周报.xlsx";
                    let url = window.URL.createObjectURL(new Blob([response.data]));
                    let link = document.createElement('a');
                    link.style.display = 'none';
                    link.href = url;
                    link.setAttribute('download', fileName);
                    document.body.appendChild(link);
                    link.click();
                    this.open();
                    document.body.removeChild(link);
                })
                .catch((error) => {});
        },
        getselectprojectname:function(){
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/report/getselectprojectnamebyid',
                data: {
                    projectid:that.reportinfo.selectprojectid,
                },
                success: function(res){
                    that.selectprojectname=res.projectname;
                },
            });
        },
        open:function() {
            this.$message({
                showClose: true,
                message: '测试日报已导出，请在下载完后查看',
                type: 'success'
            });
        },





        getinfotest:function(){
            var that=this;
            let time=this.reportinfo.time+"";
            let arr=[];
            arr=time.split(',');
            var opentime = new Date(arr[0]);
            var endtime = new Date(arr[1]);
            var differDay = Math.abs(opentime-endtime)/1000/60/60/24;

            if (reportinfo.selectprojectid.value=="" )
            {
                this.$message({
                    showClose: true,
                    message: '请选择项目',
                    type: 'warning'
                });
                return 'false';
            }
            if (time=="" )
            {
                this.$message({
                    showClose: true,
                    message: '请选择时间',
                    type: 'warning'
                });
                return 'false';
            }
            if (differDay!=6)
            {
                this.$message({
                    showClose: true,
                    message: '选择日期间隔错误，请重新选择时间',
                    type: 'warning'
                });
                return 'false';
            }
            this.getselectprojectname();
            $.ajax({
                type: 'get',
                url: '/report/getinfotest',
                data: {
                    projectid:that.reportinfo.selectprojectid,
                    opentime:opentime,
                    endtime:endtime,
                },
                success: function(res){
                    console.log("projectlist:"+res.projectlist);
                },
            });

        },
        checkweekreportpermission: function(){
            $.ajax({
                type: 'POST',
                url: '/report/checkweekreportpermission',
                data: {
                },
                success: function(res){
                    if (res.code==403){
                        location.href = "../error-page/403.html";
                        return;
                    }
                    if (res.code==404){
                        location.href = "../error-page/404.html";
                        return;
                    }
                },
            });
        },



    },
    created: function(){
        this.checkweekreportpermission();
        this.getprojectlist();
    }

})