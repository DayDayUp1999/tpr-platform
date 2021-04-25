var app = new Vue({
    el: '.vue-box',
    data: {
        p: {	// 查询参数
            moudlename: '',
        },
        dataCount: 0,
        dataList: [],
        pageNo: 1,
        pageSize: 10,
        addprojectVisible:false,
        addmoudleVisible:false,
        updatemoudleVisible:false,
        addmoudleinfo:{
            moudlename:"",
            moudlechargename:"",
            parentid:"",
            moudleopentime:"",
            moudlestatus:"1",
            type:"",
        },

        updateinfo:{
            moudleid:"",
            moudlename:"",
            moudlechargename:"",
            parentid:"",
            moudleopentime:"",
            moudlestatus:"1",
            type:"",
        }

    },
    methods: {
        // 数据刷新
        f5: function() {
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/project/getmoudleinfo',
                data: {},
                success: function(res){
                    if (res.code==403){
                        location.href = "../error-page/403.html";
                        return;
                    }
                    if (res.code==404){
                        location.href = "../error-page/404.html";
                        return;
                    }
                    that.dataList = res.dataList;
                    that.dataCount=res.dataCount;
                    sa.f5TableHeight();
                },
                error: function(){
                    layer.alert('刷新失败', {
                        skin: 'layui-layer-molv'
                        ,closeBtn: 0
                        ,anim: 4
                    });
                }
            });

        },
        expandclick:function(){
            sa.f5TableHeight();
        },
        findBymoudlename:function(){
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/project/findBymoudlename',
                data: {
                    moudlenname:that.p.moudlename,
                },
                success: function(res){
                    that.dataList = res.dataList;
                    that.dataCount=res.dataCount;
                    sa.f5TableHeight();
                },
                error: function(){
                    layer.alert('查找失败', {
                        skin: 'layui-layer-molv'
                        ,closeBtn: 0
                        ,anim: 4
                    });
                }
            });
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
            this.pageNo = 1;
            this.pageSize = val;
            sa.f5TableHeight();
        },
        handleCurrentChange(val) {
            console.log(`当前页: ${val}`);
            this.pageNo = val;
            sa.f5TableHeight();
        },
        handaddproject:function(){
            this.addprojectVisible=true;
            this.addmoudleinfo.type="project";
            this.addmoudleinfo.parentid="0";
            this.addmoudleinfo.moudlestatus="1";
            this.addmoudleinfo.moudleopentime=this.gettime();
        },
        handaddmoudle:function(data){
            this.addmoudleVisible=true;
            this.addmoudleinfo.type="moudle";
            this.addmoudleinfo.parentid=data.moudleid;
            this.addmoudleinfo.moudlestatus="1";
            this.addmoudleinfo.moudleopentime=this.gettime();
        },
        handupdatemoudle:function(data){
            this.updatemoudleVisible=true;
            this.updateinfo.moudleid=data.moudleid;
            this.updateinfo.moudlename=data.moudlename;
            this.updateinfo.moudlechargename=data.name;
            this.updateinfo.parentid=data.parentid;
            this.updateinfo.moudleopentime=data.moudleopentime;
            this.updateinfo.moudlestatus=data.moudlestatus;
            this.updateinfo.type=data.type;
        },

        // 新增项目
        addmoudle: function() {
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/project/addmoudle',
                data: {
                    moudlename:this.addmoudleinfo.moudlename,
                    moudlechargename:this.addmoudleinfo.moudlechargename,
                    parentid:this.addmoudleinfo.parentid,
                    moudleopentime:this.addmoudleinfo.moudleopentime,
                    moudlestatus:this.addmoudleinfo.moudlestatus,
                    type:this.addmoudleinfo.type,
                },
                success: function (res) {
                    if(res.msg !=null){
                        layer.alert('添加失败，该负责人不存在', {
                            skin: 'layui-layer-molv'
                            , closeBtn: 0
                            , anim: 4
                        });
                        return
                    }
                    layer.alert('添加成功', {
                        skin: 'layui-layer-molv'
                        , closeBtn: 0
                        , anim: 4
                    });
                    that.f5();
                },
                error: function () {
                    layer.alert('添加失败', {
                        skin: 'layui-layer-molv'
                        , closeBtn: 0
                        , anim: 4
                    });
                }
            });
            this.addprojectVisible=false;
            this.addmoudleVisible=false;
        },

        gettime:function(){
            //取得系统日期
            var date = new Date();
            var month = (date.getMonth()+1) > 9 ? (date.getMonth()+1) : "0" + (date.getMonth()+1);
            var day = (date.getDate()) > 9 ? (date.getDate()) : "0" + (date.getDate());
            var hours = (date.getHours()) > 9 ? (date.getHours()) : "0" + (date.getHours());
            var minutes = (date.getMinutes()) > 9 ? (date.getMinutes()) : "0" + (date.getMinutes());
            var seconds = (date.getSeconds()) > 9 ? (date.getSeconds()) : "0" + (date.getSeconds());

            var dateString =
                date.getFullYear() + "-" +
                month + "-" +
                day + " " +
                hours + ":" +
                minutes + ":" +
                seconds;
            return dateString;
        },
        // 删除
        delbyid: function(data) {
            layer.confirm("一旦删除无法恢复，确定要删除吗？",function(index){
                $.ajax({
                    type: 'POST',
                    url: '/project/deletebymoudleid',
                    data: {
                        moudleid:data.moudleid,
                    },
                    success: function(res){
                        layer.confirm("删除成功",function(){
                            sa.f5();
                        })
                    },
                    error: function(){
                        layer.alert('删除失败', {
                            skin: 'layui-layer-molv'
                            ,closeBtn: 0
                            ,anim: 4
                        });
                    }
                });
                layer.close(index);
            })
        },

        updatemoudle:function(){
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/project/updatemoudle',
                data: {
                    moudleid:that.updateinfo.moudleid,
                    moudlename:that.updateinfo.moudlename,
                    moudlechargename:that.updateinfo.moudlechargename,
                },
                success: function(res){
                    if(res.code ==500){
                        sa.alert(res.msg)
                        return
                    }
                    layer.alert('修改成功', {
                        skin: 'layui-layer-molv'
                        ,closeBtn: 0
                        ,anim: 4
                    });
                    that.f5();
                },
                error: function(){
                    layer.alert('修改失败', {
                        skin: 'layui-layer-molv'
                        ,closeBtn: 0
                        ,anim: 4
                    });
                }
            });
            that.updatemoudleVisible=false;
        },


        updatemoudleinfo:function(data){
            $.ajax({
                type: 'POST',
                url: '/project/updatemoudleinfo',
                data: {
                    userId:data.userId,
                    userstatus:data.userstatus
                },
                success: function(res){
                    sa.f5TableHeight();
                },
                error: function(){
                    layer.alert('修改失败', {
                        skin: 'layui-layer-molv'
                        ,closeBtn: 0
                        ,anim: 4
                    });
                }
            });

        }
    },
    created: function(){
        this.f5();
        sa.onInputEnter();		// 监听输入框的回车事件，执行查询
    }
})