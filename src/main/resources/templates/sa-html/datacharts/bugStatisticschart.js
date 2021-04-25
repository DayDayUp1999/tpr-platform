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
        bugzmnumber:[],
        bugyznumber:[],
        bugybnumber:[],
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
                url: '/bug/getbuglist',
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
                    for (var i=0;i<datas.buglist.length;i++) {
                        that.names.push(datas.buglist[i].moudlename);
                        that.mydata[i] = {value: datas.buglist[i].bugnumber, name: datas.buglist[i].moudlename};
                    }
                    var myChart = echarts.init(document.getElementById('e-project'));
                    var option = {
                        title: {
                            text: '测试缺陷总数',
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
                        that.updateEchartsbug(params.name);
                    });
                },
                error: function(){
                    console.log("获取数据失败");
                }
            });
        },

        updateEchartsbug:function(data){
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/bug/getbuglistByname',
                data: {
                    projectname:that.selectprojectname,
                },
                success: function(datas){
                    that.moudlenames=[];
                    that.bugzmnumber=[];
                    that. bugyznumber=[];
                    that.bugybnumber=[];
                    for (var i=0;i<datas.buginfolist.length;i++) {
                        that.moudlenames.push(datas.buginfolist[i].moudlename);
                        that.bugzmnumber.push(datas.buginfolist[i].zm);
                        that.bugyznumber.push(datas.buginfolist[i].yz);
                        that.bugybnumber.push(datas.buginfolist[i].yb);
                    }
                    var myChart1 = echarts.init(document.getElementById('e-bug'));
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
                            name:'致命',
                            type: 'bar',
                            stack:'BUG情况',
                            data: that.bugzmnumber,
                            itemStyle:{
                                normal:{color:"#9F353A"},
                            }
                        },{
                            name:'严重',
                            type: 'bar',
                            stack:'BUG情况',
                            data: that.bugyznumber,
                            itemStyle:{
                                normal:{
                                    color:"#C46243"
                                },
                            }
                        },{
                            name:'一般',
                            type: 'bar',
                            stack:'BUG情况',
                            data: that.bugybnumber,
                            itemStyle:{
                                normal:{
                                    color:"#90B44B"
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
        this.f5();
    }
})