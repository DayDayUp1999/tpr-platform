new Vue({
    el: '.vue-box',
    data: {

        dates:[],
        newbugs:[],
        solovedbugs:[],
        newbugslj:[],
        solovedbugslj:[],

    },
    methods: {
        // 数据刷新
        f5: function() {
            this.updateEchartbugfollow();
            this.updateEchartbugfollowlj();
        },
        // 更新Echarts, 各种项目案例数目
        updateEchartbugfollow: function() {
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/bug/bugfollow',
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
                    for (var i=0;i<datas.bugfollowlist.length;i++) {
                        that.dates.push(datas.bugfollowlist[i].date);
                        that.newbugs.push(datas.bugfollowlist[i].newbugnumber);
                        that.solovedbugs.push(datas.bugfollowlist[i].solvedbugnumber);
                    }
                    var myChart = echarts.init(document.getElementById('e-bugfollow'));
                    var option = {
                        title: {

                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['每日新增BUG', '每日解决BUG']
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        toolbox: {
                            feature: {
                                saveAsImage: {}
                            }
                        },
                        xAxis: {
                            type: 'category',
                            boundaryGap: false,
                            data: that.dates,
                        },
                        yAxis: {
                            type: 'value'
                        },
                        dataZoom: [
                            {   // 这个dataZoom组件，默认控制x轴。
                                type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                                start: 10,      // 左边在 10% 的位置。
                                end: 60         // 右边在 60% 的位置。
                            }
                        ],
                        series: [
                            {
                                name: '每日新增BUG',
                                type: 'line',
                                data: that.newbugs,
                                itemStyle: {
                                    normal: {
                                        label : {show: true,
                                            color:"#000000"},   //顶部显示数值
                                        lineStyle: {
                                            color: "#3C76F4"      //改变折线颜色
                                        }
                                    }
                                }
                            },
                            {
                                name: '每日解决BUG',
                                type: 'line',
                                data: that.solovedbugs,
                                itemStyle: {
                                    normal: {
                                        label : {show: true,
                                            color:"#000000"},   //顶部显示数值
                                        lineStyle: {
                                            color: "#f48c03"      //改变折线颜色
                                        }
                                    }
                                }
                            },

                        ]
                    };
                    myChart.setOption(option);
                },
                error: function(){
                    console.log("获取数据失败");
                }
            });
        },

        updateEchartbugfollowlj: function() {
            var that=this;
            $.ajax({
                type: 'POST',
                url: '/bug/bugfollowlj',
                data: {},
                success: function(datas){
                    for (var i=0;i<datas.bugfollowljlist.length;i++) {
                        that.newbugslj.push(datas.bugfollowljlist[i].newbugnumberlj);
                        that.solovedbugslj.push(datas.bugfollowljlist[i].solvedbugnumberlj);
                    }
                    var myChart = echarts.init(document.getElementById('e-bugfollowlj'));
                    var option = {
                        title: {

                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data: ['每日新增BUG累计', '每日解决BUG累计']
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        toolbox: {
                            feature: {
                                saveAsImage: {}
                            }
                        },
                        xAxis: {
                            type: 'category',
                            boundaryGap: false,
                            data: that.dates,
                        },
                        yAxis: {
                            type: 'value'
                        },
                        dataZoom: [
                            {   // 这个dataZoom组件，默认控制x轴。
                                type: 'slider', // 这个 dataZoom 组件是 slider 型 dataZoom 组件
                                start: 10,      // 左边在 10% 的位置。
                                end: 60         // 右边在 60% 的位置。
                            }
                        ],
                        series: [
                            {
                                name: '每日新增BUG累计',
                                type: 'line',
                                data: that.newbugslj,
                                itemStyle: {
                                    normal: {
                                        label : {
                                            show: true,
                                            color:"#000000"

                                        },   //顶部显示数值
                                        lineStyle: {
                                            color: "#3C76F4"      //改变折线颜色
                                        }
                                    }
                                }
                            },
                            {
                                name: '每日解决BUG累计',
                                type: 'line',
                                data: that.solovedbugslj,
                                itemStyle: {
                                    normal: {
                                        label : {show: true,
                                            color:"#000000"},   //顶部显示数值
                                        lineStyle: {
                                            color: "#f4ac05"      //改变折线颜色
                                        }
                                    }
                                }
                            },

                        ]
                    };
                    myChart.setOption(option);
                },
                error: function(){
                    console.log("获取数据失败");
                }
            });
        }
    },
    created: function() {
        this.f5();
    }
})