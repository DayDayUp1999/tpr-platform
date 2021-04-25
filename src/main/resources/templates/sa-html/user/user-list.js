var app = new Vue({
    el: '.vue-box',
    data: {
        p: {	// 查询参数
            username: '',
            rolename:'',
        },
        dataCount: 0,
        dataList: [],
        pageNo: 1,
        pageSize: 10,
        addVisible:false,

        roleoption:[],

        sexoption:[{
            value: '男',
            label: '男'
        }, {
            value: '女',
            label: '女'
        }],
        updateVisible:false,
        updateinfo:{
            userId:"",
            name:"",
            sex:"",
            roleId:"",
            age:"",
            number:"",
            remarks:"",
            userstatus:""
        }
    },
    methods: {
        // 数据刷新
        f5: function() {
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/user/getTprUserList',
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
        findByinput:function(){
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/user/findByinput',
                data: {
                    username:that.p.username,
                    rolename:that.p.rolename,
                },
                success: function(res){
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

        // 删除
        delbyid: function(data) {
            layer.confirm("系统用户一旦删除无法恢复，确定要删除吗？",function(index){
                $.ajax({
                    type: 'POST',
                    url: '/user/deletebyid',
                    data: {
                        userId:data.userId,
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
        // 批量删除
        deleteByIds: function() {
            // 获取选中元素的id列表
            let selection = this.$refs['data-table'].selection;
            let ids = sa.getArrayField(selection, 'userId');
            if(selection.length == 0) {
                return sa.msg('请至少选择一条数据')
            }
            layer.confirm("是否批量删除选中数据？此操作不可撤销",function(index){
                $.ajax({
                    type: 'POST',
                    url: '/user/deleteByIds',
                    data: {
                        ids: ids.join(','),
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
            });

        },

        handleupdate:function(data){
            var that=this;
            this.updateinfo.userId=data.userId,
                this.updateinfo.name=data.name,
                this.updateinfo.sex=data.sex,
                this.updateinfo.roleId=data.roleId,
                this.updateinfo.age=data.age,
                this.updateinfo.number=data.number,
                this.updateinfo.remarks=data.remarks,
                this.updateinfo.userstatus=data.userstatus
            this.updateVisible=true;
            that.getroleoption();
        },
        submitCheck: function() {
            if (updateinfo.name.value=="" )
            {
                layer.tips('姓名',$("#name"));
                updateinfo.name.focus();
                return 'false';
            }else if(updateinfo.sex.value==""){
                layer.tips('请选择性别',$("#sex"));
                updateinfo.sex.focus();
                return 'false';
            }else if (updateinfo.roleId.value==""){
                layer.tips('请选择角色',$("#roleId"));
                updateinfo.roleId.focus();
                return 'false';
            }else if(updateinfo.number.value==""){
                layer.tips('请输入电话号码',$("#number"));
                updateinfo.number.focus();
                return 'false';
            }else if(updateinfo.age.value==""){
                layer.tips('请输入年龄',$("#age"));
                userinfo.age.focus();
                return 'false';
            }else{
                return 'ok';
            }

        },

        roleupdate:function(){
            var that=this;
            that.getroleoption();
            if(this.submitCheck() != 'ok') {
                return;
            }
            $.ajax({
                type: 'POST',
                url: '/user/updateuserinfo',
                data: {
                    userId:that.updateinfo.userId,
                    name:that.updateinfo.name,
                    sex:that.updateinfo.sex,
                    roleId:that.updateinfo.roleId,
                    age:that.updateinfo.age,
                    number:that.updateinfo.number,
                    remarks:that.updateinfo.remarks,
                    userstatus:that.updateinfo.userstatus
                },
                success: function(res){
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
            that.updateVisible=false;
        },

        getroleoption:function(){
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/role/getTprRoleList',
                data: {},
                success: function(res){
                    that.roleoption = res.dataList;
                },
                error: function(){
                    layer.alert('获取角色失败', {
                        skin: 'layui-layer-molv'
                        ,closeBtn: 0
                        ,anim: 4
                    });
                }
            });
        },

        updateuserinfo:function(data){
            $.ajax({
                type: 'POST',
                url: '/user/updateuserinfo',
                data: {
                    userId:data.userId,
                    userstatus:data.userstatus
                },
                success: function(res){

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

        }
    },
    created: function(){
        this.f5();
        this.getroleoption();
        sa.onInputEnter();		// 监听输入框的回车事件，执行查询
    }
})