
// 定义菜单列表 
var menuList =	[

	{
		id: '1',
		name: '测试报表生成',
		icon: 'el-icon-document-remove',
		info: '测试报表生成',
		childList: [
			{id: '1-1', name: '测试日报生成', url: 'sa-html/report/dayreport.html'},
			{id: '1-2', name: '测试周报生成', url: 'sa-html/report/weekreport.html'},
		]
	},

	{
		id: '2',
		name: '测试过程数据统计',
		icon: 'el-icon-table-lamp',
		info: '测试过程数据统计',
		childList: [
			{id: '2-1', name: '测试缺陷统计', icon: 'el-icon-pie-chart', url: 'sa-html/datacharts/bugStatisticschart.html'},
			{id: '2-2', name: '已解决缺陷统计', icon: 'el-icon-pie-chart', url: 'sa-html/datacharts/solvedbugStatisticschart.html'},
			{id: '2-3', name: '用例执行结果统计', icon: 'el-icon-pie-chart', url: 'sa-html/datacharts/caseresultStatisticschart.html'},
			{id: '2-4', name: '测试用例统计', icon: 'el-icon-pie-chart', url: 'sa-html/datacharts/caseStatisticschart.html'},
			{id: '2-5', name: '测试缺陷跟踪统计', icon: 'el-icon-pie-chart',url: 'sa-html/datacharts/followbug.html'},
		]
	},
	{
		id: '3',
		name: '权限控制',
		icon: 'el-icon-unlock',
		info: '对系统角色权限的分配等设计，敏感度较高，请谨慎授权',
		childList: [
			{id: '3-1', name: '角色列表', icon: 'el-icon-key', url: 'sa-html/role/role-list.html'},
			{id: '3-2', name: '菜单列表', icon: 'el-icon-magic-stick', url: 'sa-html/role/menu-list.html'}
		]
	},
	{
		id: '4',
		name: '用户管理',
		icon: 'el-icon-user',
		info: '对用户列表、添加、等等...',
		childList: [
			{id: '4-1', name: '用户列表', icon: 'el-icon-document-remove', url: 'sa-html/user/user-list.html'},
			{id: '4-2', name: '用户添加', icon: 'el-icon-plus', url: 'sa-html/user/user-add.html'},
		]
	},
	{
		id: '5',
		name: '测试项目管理',
		icon: 'el-icon-document-copy',
		info: '测试项目管理',
		childList: [
			{id: '5-1', name: '项目列表', url: 'sa-html/project/project-list.html'},
		]
	},

]