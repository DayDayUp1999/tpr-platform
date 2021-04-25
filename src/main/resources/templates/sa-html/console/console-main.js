var app = new Vue({
	el: '.vue-box',
	data: {
		p: { // 查询参数  
		}, 
		dataCount: 0,
		// 统计数据 
		sta: {
			userCount: 0,
			allcaseCount: 0,
			allbugCount: 0,
			solvebugCount: 0,
		},
		// 技术栈集合 
		frameList: [
			{name: 'JS引擎', value: 'Vue @2.6.10', link: 'https://cn.vuejs.org/'},
			{name: 'UI框架', value: 'Element-UI @2.13.0', link: 'https://element.eleme.cn/#/zh-CN'},
			{name: 'web弹层', value: 'layer @3.1.1', link: 'http://layer.layui.com/'},
			{name: '切页动画', value: 'Swiper @4.5.0', link: 'https://www.swiper.com.cn/'},
			{name: '图表引擎', value: 'ECharts @4.2.1', link: 'https://echarts.baidu.com/'},
			{name: '后端框架', value: 'SpringBoot @2.4.4', link: 'https://spring.io/projects/spring-boot/'},
			{name: '权限认证', value: 'sa-token @1.15.0', link: 'http://sa-token.dev33.cn/'},
		],
	},
	methods: {
		// 刷新第一行数据
		f5StaData: function() {
			var that=this;
			$.ajax({
				type: 'get',
				url: '/getsta',
				data: {},
				success: function(res){
					that.sta.userCount=res.userCount;
					that.sta.allcaseCount=res.allcaseCount;
					that.sta.allbugCount=res.allbugCount;
					that.sta.solvebugCount=res.solvedbugCount;
				},
				error: function(){
					layer.alert('获取初始信息失败', {
						skin: 'layui-layer-molv'
						,closeBtn: 0
						,anim: 4
					});
				}
			});
		},

	},
	created: function() {
		// 刷新 
		this.f5StaData();
	}
})