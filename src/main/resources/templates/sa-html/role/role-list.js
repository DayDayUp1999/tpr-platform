var app = new Vue({
    el: '.vue-box',
    data: {
        sa: sa, 	// 超级对象

        p: {	// 查询参数
            findrolename: '',
        },
        //分页信息
        dataCount: 0,
        pageNo: 1,
        pageSize: 10,
        dataList: [],	// 数据集合

        addVisible:false,
        //添加参数
        temp:{
            roleId:"",
            rolename:"",
            info:"",
        }
    },
    methods: {
        // 刷新
        f5: function() {
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/role/getTprRoleList',
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


        del: function (data) {
            layer.confirm("系统角色是系统正常运行的重要标志，确定要删除吗？",function(index){
                $.ajax({
                    type: 'POST',
                    url: '/role/delRole',
                    data: {
                        roleId:data.roleId,
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

        findByname:function(){
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/role/findByname',
                data: {
                    name: that.p.findrolename,
                },
                success: function (res) {
                    that.dataList = res.dataList;
                    that.dataCount=res.dataCount;
                    sa.f5TableHeight();

                },
                error: function () {
                    layer.alert('查询失败', {
                        skin: 'layui-layer-molv'
                        , closeBtn: 0
                        , anim: 4
                    });
                }
            });
        },

        handleadd:function(){
            this.addVisible=true;
        },
        // 添加
        add: function () {
            var that=this;
            if (temp.addroleId.value=="" )
            {
                layer.tips('请输入角色id',$("#addroleId"));
                temp.addroleId.focus();
                return false;
            }
            else if (temp.addname.value=="")
            {
                layer.tips('请输入角色名称',$("#addname"));
                temp.addname.focus();
                return false;
            }else {
                $.ajax({
                    type: 'POST',
                    url: '/role/addRole',
                    data: {
                        roleId: this.temp.roleId,
                        rolename: this.temp.rolename,
                        info: this.temp.info,
                    },
                    success: function (res) {
                        if (res.code==500){
                            sa.alert("该用户id已存在")
                        }
                        else{
                            sa.alert("添加成功");
                            that.f5();
                        }
                    },
                });
            }
            this.addVisible=false;
        },
        // 修改菜单
        menu_setup: function(data){
            var title = '为 ['+data.rolename+'] 分配权限';
            sa.showIframe(title, 'menu-setup.html?roleId=' + data.roleId, '700px', '600px');
        }
    },
    created: function(){
        this.f5();
        sa.onInputEnter();		// 监听输入框的回车事件，执行查询
    }
})