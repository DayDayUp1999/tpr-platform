var app = new Vue({
	el: '.vue-box',
	data: {
		p: { // 查询参数  
		}, 
		dataCount: 0,
		// 统计数据 
		sta: {
			userCount: 25,
			allcaseCount: 76,
			allbugCount: 60,
			solvebugCount: 53,

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
		// 数值跳动 
		slowMotion: function(obj, prop, endValue, time) {
			let timeNow = 0; 
			let fn = function() {
				// 如果已经接近 or 时间已到，则立即结束 
				var jdz = Math.abs(obj[prop] - endValue);
				if(jdz < 2 || timeNow >= time) {
					// console.log('到点了');
					obj[prop] = endValue;
				} else {
					if(jdz < 100) {
						obj[prop] += 1;
					} else {
						obj[prop] += parseInt((endValue - obj[prop]) / 10);		 // 平均一下 
					}
					timeNow += 30;
					setTimeout(fn, 30);
				}
			}
			fn();
		},
		// 设置统计数据的数值 
		setStaDataValue: function(staData) {
			for (let key in staData) {
				this.slowMotion(this.sta, key, staData[key], 3000);
			}
		},
		// 刷新第一行数据
		f5StaData: function() {
			// 刷新第一行数据
			this.setStaDataValue({
				userCount: 25,
				allcaseCount: 76,
				allbugCount: 60,
				solvebugCount: 53,
			});
		},

	},
	mounted: function() {
		// 刷新 
		this.f5StaData();
	}
})