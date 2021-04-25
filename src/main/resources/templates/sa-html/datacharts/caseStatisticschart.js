new Vue({
    el: '.vue-box',
    data: {
        projectinfo:{
            casenumber:"",
            moudle_id:"",
            moudlename:"",

        },
        names:[],
        mydata:[],
        selectprojectname:"",
        moudlenames:[],
        moudlemydatarun:[],
        moudlemydatanorun:[],
    },
    methods: {
        // 数据刷新
        f5: function() {
            this.updateEchartsproject();
        },
        // 更新Echarts, 各种项目案例数目
        updateEchartsproject: function() {
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/case/findallcaseByproject',
                data: {},
                success: function(datas){
                    if (datas.code==403){
                        location.href = "../error-page/403.html";
                        return;
                    }
                    if (datas.code==404){
                        location.href = "../error-page/404.html";
                        return;
                    }
                    for (var i=0;i<datas.caselist.length;i++) {
                        that.names.push(datas.caselist[i].moudlename);
                        that.mydata[i] = {value: datas.caselist[i].casenumber, name: datas.caselist[i].moudlename};
                    }
                    var myChart = echarts.init(document.getElementById('e-project'));
                    var option = {
                        title: {
                            text: '项目用例总数',
                            left: 'right'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: "{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                        },
                        series: [
                            {
                                type: 'pie',
                                radius: '60%',
                                data: that.mydata,
                                emphasis: {
                                    itemStyle: {
                                        shadowBlur: 1,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    },
                                },
                                label: {
                                    normal: {
                                        formatter: '{b|{b}}\n{c}\n{per|{d}%}  ',
                                        rich: {}
                                    }
                                },
                            }
                        ]
                    };
                    myChart.setOption(option);
                    myChart.on('click', function (params) {
                        that.selectprojectname=params.name;
                        that.updateEchartsmoudle(params.name);
                    });
                },
                error: function(){
                    console.log("获取数据失败");
                }
            });
        },

        updateEchartsmoudle:function(data){
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/case/getcaselistByname',
                data: {
                    projectname:that.selectprojectname,
                },
                success: function(datas){
                    that.moudlenames=[];
                    that.moudlemydatarun=[];
                    that.moudlemydatanorun=[];
                    for (var i=0;i<datas.moudleinfolist.length;i++) {
                        that.moudlenames.push(datas.moudleinfolist[i].moudlename);
                        that.moudlemydatarun.push(datas.moudleinfolist[i].run);
                        that.moudlemydatanorun.push(datas.moudleinfolist[i].norun);
                    }
                    var myChart1 = echarts.init(document.getElementById('e-moudle'));
                    myChart1.clear();
                    var option = {
                        title: {
                            text: that.selectprojectname,
                            left: 'center'
                        },
                        tooltip: {
                            trigger: "axis",
                        },
                        legend: {
                            orient: 'vertical',
                            left: 'left',
                        },
                        xAxis: {
                            data: that.moudlenames,
                        },
                        yAxis: {},
                        series: [{
                            name:'已执行',
                            type: 'bar',
                            stack:'使用情况',
                            data: that.moudlemydatarun,
                            itemStyle:{
                                normal:{color:"#79bb53"},
                            }
                        },{
                            name:'未执行',
                            type: 'bar',
                            stack:'使用情况',
                            data: that.moudlemydatanorun,
                            itemStyle:{
                                normal:{
                                    color:"#6283bb"
                                },
                            }
                        }
                        ]
                    };
                    myChart1.setOption(option);
                },
                error: function(){
                    console.log("获取数据失败");
                }
            });
        },

    },

    created: function() {
        setTimeout(function() {
            this.f5();
        }.bind(this), 0)
    }


})