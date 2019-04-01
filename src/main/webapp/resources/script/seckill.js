//存放主要交互逻辑的js代码
// javascript 模块化(package.类.方法)
var basePath = '/'+window.location.pathname.split("/")[1];
var seckill = {

	// 封装秒杀相关ajax的url
	URL : {
		now : function() {
			return basePath + '/seckill/time/now';
		},
		exposer : function(seckillId) {
			return basePath + '/seckill/' + seckillId + '/exposer';
		},
		execution : function(seckillId, md5) {
			return basePath + '/seckill/' + seckillId + '/' + md5 + '/execution';
		}
	},

	// 验证手机号
	validatePhone : function(phone) {
		// 判断对象看对象是否为空,空就是undefine就是false; isNaN 非数字返回true
		if (phone && phone.length == 11 && !isNaN(phone)) {
			return true;
		} else {
			return false;
		}
	},

	// 详情页秒杀逻辑
	detail : {
		// 详情页初始化
		init : function(params) {
			// 手机验证和登录
			// 在cookie中查找手机号
			var userPhone = $.cookie('userPhone');
			// 验证cookie中的手机号
			if (!seckill.validatePhone(userPhone)) {
				// 不通过就弹出来让用户填
				var killPhoneModal = $('#killPhoneModal');
				killPhoneModal.modal({
					show : true,// 显示弹出层,detail.jsp中弹出层默认是隐藏的
					backdrop : 'static',// 禁止位置关闭
					keyboard : false// 关闭键盘事件
				});
				$('#killPhoneBtn').click(function() {
					var inputPhone = $('#killPhoneKey').val();
					console.log("inputPhone: " + inputPhone);
					if (seckill.validatePhone(inputPhone)) {
						// 电话写入cookie(7天过期)
						$.cookie('userPhone', inputPhone, {expires : 7, path : basePath+'/seckill'});
						// 刷新页面
						window.location.reload();
					} else {
						$('#killPhoneMessage')
								.hide()
								.html('<label class="label label-danger">手机号错误!</label>')
								.show(300);
					}
				});
			}

			// 手机验证通过，已经登录，进行计时交互
			var startTime = params['startTime'];
			var endTime = params['endTime'];
			var seckillId = params['seckillId'];
			$.get(seckill.URL.now(), {}, function(result) {
				if (result && result['success']) {
					var nowTime = result['data'];
					// 时间判断 计时交互
					seckill.countDown(seckillId, nowTime, startTime, endTime);
				} else {
					console.log('result: ' + result);
					alert('result: ' + result);
				}
			});
		}
	},

	handlerSeckill : function(seckillId, node) {
		// 获取秒杀地址,控制显示器,执行秒杀
		node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');

		$.post(seckill.URL.exposer(seckillId),{},function(result) {
			// 在回调函数中执行交互流程
			if (result && result['success']) {
				var exposer = result['data'];
				if (exposer['exposed']) {
					// 开启秒杀
					// 获取秒杀地址
					var md5 = exposer['md5'];
					var killUrl = seckill.URL.execution(seckillId, md5);
					console.log("killUrl: " + killUrl);
					// one绑定一次点击事件 click一直绑定点击事件 防止用户一直点击事件向服务器发送大量请求
					$('#killBtn').one('click',function() {
						// 执行秒杀请求
						// 1.先禁用按钮
						$(this).addClass('disabled');
						// 2.发送秒杀请求执行秒杀
						$.post(killUrl, {}, function(result) {
							if (result && result['success']) {
								var killResult = result['data'];
								var state = killResult['state'];
								var stateInfo = killResult['stateInfo'];
								// 3.显示秒杀结果
								node.html('<span class="label label-success">'+ stateInfo + '</span>');
							}
						});
					});
					node.show();
				} else {
					// 未开启秒杀(浏览器计时偏差)
					var now = exposer['now'];
					var start = exposer['start'];
					var end = exposer['end'];
					seckill.countDown(seckillId, now, start, end);
				}
			} else {
				console.log('result: ' + result);
			}
		});

	},
	
	// 将时间的比较封装成一个函数
	countDown : function(seckillId, nowTime, startTime, endTime) {
		console.log(seckillId + '_' + nowTime + '_' + startTime + '_' + endTime);
		var seckillBox = $('#seckill-box');
		if (nowTime > endTime) {
			// 秒杀结束
			seckillBox.html('秒杀结束!');
		} else if (nowTime < startTime) {
			// 秒杀未开始,计时事件绑定
			var killTime = new Date(startTime + 1000);// todo 防止时间偏移
			seckillBox.countdown(killTime, function(event) {
				// 时间格式
				var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒 ');
				seckillBox.html(format);
			}).on('finish.countdown', function() {
				// 时间完成后回调事件
				// 获取秒杀地址,控制显示逻辑,执行秒杀
				console.log('______fininsh.countdown');
				seckill.handlerSeckill(seckillId, seckillBox);
			});
		} else {
			// 秒杀开始
			seckill.handlerSeckill(seckillId, seckillBox);
		}
	}

}