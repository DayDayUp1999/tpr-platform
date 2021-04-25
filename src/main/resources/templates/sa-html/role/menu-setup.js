var role_id = sa.p('roleId');
var powerlist;
var app = new Vue({
    el: '.vue-box',
    data: {
        p: [],
        dataList: [],	// 数据集合
        selectList: [],	// 默认选中
        ywList: []		// 一维数组
    },
    methods: {
        //获取初始权限列表
        findCheckedPowerList: function(){
            $.ajax({
                type: 'POST',
                url: '/power/findCheckedPowerList',
                async: false,
                data: {
                    roleId:role_id,
                },
                success: function(res){
                    powerlist=res.datalist;
                },
                error: function(){
                    layer.alert('查询权限失败', {
                        skin: 'layui-layer-molv'
                        ,closeBtn: 0
                        ,anim: 4
                    });
                }
            });
        },

        // 保存
        ok: function(){
            var str = '';
            this.$refs.tree.getCheckedKeys().forEach(function(ts){
                str +=ts + ',';
            })
            str=str.substr(0,str.length-1);
            $.ajax({
                type: 'POST',
                url: '/power/updateRolepowerByRoleId',

                data: {
                    powerString:str,
                    roleId:role_id,
                },
                success: function(res){
                    layer.confirm("保存成功",function(){
                        sa.closeCurrIframe();
                        sa.f5();
                    })

                },
                error: function(){
                    layer.alert('保存失败', {
                        skin: 'layui-layer-molv'
                        ,closeBtn: 0
                        ,anim: 4
                    });
                }
            });
        },
        // 点击回调, 处理其子节点跟随父节点的选中
        node_click: function(node) {
            var is_select = this.$refs.tree.getCheckedKeys().indexOf(node.id) != -1;	// 此节点现在是否被选中
            if(node.children){
                node.children.forEach(function(item) {
                    this.$refs.tree.setChecked(item.id, is_select);
                    // 递归
                    if(item.children) {
                        this.node_click(item);
                    }
                }.bind(this))
            }
        },
        // 全选/ 取消全选
        checkedAll: function() {
            if(this.$refs.tree.getCheckedKeys().length != this.ywList.length) {
                this.$refs['tree'].setCheckedNodes(this.ywList);
            } else {
                this.$refs['tree'].setCheckedNodes([]);
            }
        }
    },
    created: function(){
        menuList = sa_admin_code_util.arrayToTree(menuList);	// 一维转tree
        menuList = sa_admin_code_util.refMenuList(menuList);	// 属性处理
        this.dataList = menuList;	// 数据
        this.ywList = sa_admin_code_util.treeToArray(this.dataList);
        this.findCheckedPowerList();
        this.selectList=powerlist;
    },
    mounted: function(){

    }
})