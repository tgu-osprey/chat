export function stringToISOString(d) {
	// 输入的日期时间字符串，形似：2023-11-29T18:22:30.389031742
	// 将日期时间字符串转换为时间戳，结果形似：1606663350389
	var timestamp = new Date(d).getTime();
	return timestamp
}

// 将时间戳转换为日期时间字符串
export function timestampToString(timestamp) {
	// 输入的时间戳，形似：1606663350389
	// 将时间戳转换为北京时间日期时间字符串，结果形似：2023-11-29T18:22:30.389031742
	var d = new Date(timestamp + 8 * 3600 * 1000);
	var datetime = d.toISOString();
	// 判断昨天
	return _GetDateStr(timestamp / 1000) + " " + datetime.slice(11, 16)
}

// 计算今天昨天
function _GetDateStr(sj_str) {
	var data = new Date().toLocaleDateString()
	var dd = Date.parse(data) / 1000
	var iday = Math.floor(parseInt(dd - sj_str) / 60 / 60 / 24);
	if (-1 == iday) {
		return '今天'
	} else if (0 == iday) {
		return '昨天'
	} else {
		var dd = [];
		dd = new Date(sj_str * 1000).toLocaleDateString().split('/');
		var y = dd[0];
		var m = dd[1];//获取当前月份的日期
		var d = dd[2];
		return m + "-" + d;
		// return y+"-"+num(m)+"-"+num(d);
	}
}

// 判断早中晚
export function getTimeState() {
	// 获取当前时间
	let timeNow = new Date();
	// 获取当前小时
	let hours = timeNow.getHours();
	// 设置默认文字
	let state = ``;
	// 判断当前时间段
	if (hours >= 0 && hours <= 5) {
		state = `注意身体噢🧑🏻‍⚕️! `;
	} else if (hours > 5 && hours <= 10) {
		state = `早上好🌱! `;
	} else if (hours > 10 && hours <= 14) {
		state = `中午好☀️! `;
	} else if (hours > 14 && hours <= 18) {
		state = `下午好☕! `;
	} else if (hours > 18 && hours <= 24) {
		state = `晚上好🌕! `;
	}
	return state;
}