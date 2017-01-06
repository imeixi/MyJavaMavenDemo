/**
 * JQuary 遍历填充Table
 */

// 添加应用维度查询事件
$("#appquerybtn").click(
		function() {

			var startTime = $("#startTime2").val();
			var stopTime = $("#stopTime2").val();
			var businessLine = $("#businessLine").val();
			var applicationName = $("#applicationName").val();

			$.ajax({
				url : "../getDeployAppData",
				data : {
					startTime : startTime,
					stopTime : stopTime,
					businessLine : businessLine,
					applicationName : applicationName

				},
				datatype : "json",
				type : "post",
				success : function(result) {

					// 绘制表格 showTable
					$("#showTable tbody").remove();
					var tbody = "";
					$.each(result, function(index, value) {
						var trs = "";
						trs += "<tr><td>" + value.id + "</td>" + " <td>"
								+ value.businessLine + "</td>" + " <td>"
								+ value.applicationName + "</td>" + " <td>"
								+ value.deployFailCount + "</td>" + " <td>"
								+ value.deploySuccessCount + "</td>" + " <td>"
								+ value.deployTotalCount + "</td>" + " <td>"
								+ value.percentSuccess + "</td></tr>";
						tbody += trs;
					});

					$("#showTable").append(tbody);

					// alert(result);
					// 前端分页显示
					console.log(JSON.stringify(result));
					console.log(result[0]);
					console.log(result.length);

				},
				error : function(data) {
					alert("error" + data);
				}
			});

		});