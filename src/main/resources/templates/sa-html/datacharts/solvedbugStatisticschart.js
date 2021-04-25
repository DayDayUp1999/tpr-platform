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
        bugwjjnumber:[],
        bugyjjnumber:[],
        bugjjznumber:[],
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
                url: '/bug/getbugsolvedlist',
                data: {
                    projectname:that.selectprojectname,
                },
                success: function(datas){
                    that.moudlenames=[];
                    that.bugyjjnumber=[];
                    that. bugwjjnumber=[];
                    that.bugjjznumber=[];
                    for (var i=0;i<datas.solvedbuglist.length;i++) {
                        that.moudlenames.push(datas.solvedbuglist[i].moudlename);
                        that.bugyjjnumber.push(datas.solvedbuglist[i].yjj);
                        that.bugwjjnumber.push(datas.solvedbuglist[i].wjj);
                        that.bugjjznumber.push(datas.solvedbuglist[i].jjz);
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
                            name:'已解决',
                            type: 'bar',
                            stack:'BUG情况',
                            data: that.bugyjjnumber,
                            itemStyle:{
                                normal:{color:"#00bb0c"},
                            }
                        },{
                            name:'未解决',
                            type: 'bar',
                            stack:'BUG情况',
                            data: that.bugwjjnumber,
                            itemStyle:{
                                normal:{
                                    color:"#bb4704"
                                },
                            }
                        },{
                            name:'解决中',
                            type: 'bar',
                            stack:'BUG情况',
                            data: that.bugjjznumber,
                            itemStyle:{
                                normal:{
                                    color:"#0059bb"
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