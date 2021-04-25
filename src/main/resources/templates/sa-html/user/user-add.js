new Vue({
    el: '.vue-box',
    data: {
        userinfo: {	// 查询参数
            username: '',
            password: '',
            checkpassword: '',
            name:'',
            number: '',
            sex:'',
            age:'',
            roleId:'',
            remarks:'',
            userstatus:1,
        },
        roleoption:[],

        sexoption:[{
            value: '男',
            label: '男'
        }, {
            value: '女',
            label: '女'
        }],
    },
    methods: {
        getRegisterroleoption:function(){
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/user/getRegisterTprRoleList',
                data: {},
                success: function(res){
                    that.roleoption = res.dataList;
                    console.log("roleoption:"+res.dataList.toString());
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
        submitCheck: function() {
            if (userinfo.username.value=="" )
            {
                layer.tips('请输入用户名',$("#username"));
                userinfo.username.focus();
                return 'false';
            }else if(userinfo.password.value==""){
                layer.tips('请输入密码',$("#password"));
                userinfo.password.focus();
                return 'false';
            }else if (userinfo.password.value != userinfo.checkpassword.value){
                layer.tips('两次密码输入不一致',$("#checkpassword"));
                userinfo.checkpassword.focus();
                return 'false';
            }else if (userinfo.name.value==""){
                layer.tips('请输入姓名',$("#name"));
                userinfo.name.focus();
                return 'false';
            }else if(userinfo.number.value==""){
                layer.tips('请输入电话号码',$("#number"));
                userinfo.number.focus();
                return 'false';
            }else if(userinfo.sex.value==""){
                layer.tips('请选择性别',$("#sex"));
                userinfo.sex.focus();
                return 'false';
            }else if(userinfo.age.value==""){
                layer.tips('请输入年龄',$("#age"));
                userinfo.age.focus();
                return 'false';
            }else if(userinfo.roleId.value==""){
                layer.tips('请选择角色',$("#roleId"));
                userinfo.roleId.focus();
                return 'false';
            }else{
                return 'ok';
            }

        },
        userRegister: function() {
            var that=this;
            if(this.submitCheck() != 'ok') {
                return;
            }
            $.ajax({
                type: 'POST',
                url: '/user/userRegister',
                data: {
                    username:that.userinfo.username,
                    password:that.userinfo.password,
                    name:that.userinfo.name,
                    number:that.userinfo.number,
                    sex:that.userinfo.sex,
                    age:that.userinfo.age,
                    roleId:that.userinfo.roleId,
                    remarks:that.userinfo.remarks,
                    userstatus:that.userinfo.userstatus,
                },
                success: function(res){
                    if (res.flag == 'success'){
                        layer.alert('添加成功', {
                            skin: 'layui-layer-molv'
                            ,closeBtn: 0
                            ,anim: 4
                        });
                    }
                    if (res.flag == 'Alreadyexists'){
                        layer.alert('该用户已存在，请重新输入', {
                            skin: 'layui-layer-molv'
                            ,closeBtn: 0
                            ,anim: 4
                        });
                    }

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
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },

        checkpermission: function(){
            $.ajax({
                type: 'POST',
                url: '/user/checkadduserpermission',
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
        this.checkpermission();
        this.getRegisterroleoption();
    }

})