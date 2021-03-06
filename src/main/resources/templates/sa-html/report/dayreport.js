new Vue({
    el: '.vue-box',
    data: {
        reportinfo:{
            projectlist:[],
            selectprojectid:'',
        },
        tableData: [],
        overalllistMap: [],
        caselistMap: [],
        selectprojectname:'',

        projectname:'',
        projectid:'',
    },
    methods: {
        getBriefinfo:function(){
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/report/daygetBriefinfobyprojectname',
                data: {
                    projectname: that.projectname,
                },
                success: function(res){
                    console.log("briefinfo,res:"+res);
                    that.overalllistMap=res.Briefinfo.overalllistMap;
                    that.caselistMap=res.Briefinfo.caselistMap;
                    that.projectid=res.Briefinfo.projectid;
                    console.log("that.overalllistMap:"+that.overalllistMap);
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

        getdayreport:function(){
            var that=this;
            if (that.overalllistMap==""){
                this.$alert('没有找到数据，请重新查询','提示');
                return;
            }

            this.getselectprojectname();
            axios({
                method: "get",
                params:{
                    projectid:that.projectid,
                },
                url:"/report/getdayreport",
                responseType: "blob",
            })
                .then((response) => {
                    const blob = new Blob([response.data]);
                    var objectUrl=window.URL.createObjectURL(blob);
                    var a=document.createElement('a');
                    a.href=objectUrl;
                    let yy = new Date().getFullYear();
                    let mm = new Date().getMonth() + 1;
                    let dd = new Date().getDate();
                    let time=yy + '-' + mm + '-' + dd ;
                    this.open();
                    a.download="【"+that.projectname+"】"+"-"+time+"-"+"测试日报.xlsx";
                    a.click();
                    a.remove();
                    window.URL.revokeObjectURL(objectUrl)
                })
                .catch((error) => {});
        },
        open:function() {
            this.$message({
                message: '测试日报已导出，请在下载完后查看',
                showClose: true,
                type: 'success'
            });
        },

        gettest:function(){
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/report/getinfotest',
                data: {
                    projectid:that.reportinfo.selectprojectid,
                },
                success: function(res){
                    console.log("getinfotestres>>:"+res.datalist);
                },
            });
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
        checkdayreportpermission: function(){
            $.ajax({
                type: 'POST',
                url: '/report/checkdayreportpermission',
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
        }
    },
    created: function(){
        this.checkdayreportpermission();
        this.getprojectlist();
    }

})