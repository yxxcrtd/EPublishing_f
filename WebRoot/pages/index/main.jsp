<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv='X-UA-Compatible' content='IE=edge'/>
		<title>CNPIEC eReading: Introducing CNPIEC eReading</title>
		<%@ include file="/common/tools.jsp"%>
		<script type="text/javascript">
		function execut(url){
			$.ajax({
  					type : "POST",  
			        url: url,
			        data:{
			        	r_ : new Date().getTime()
			        },
			        success : function(data) {  
			         	$("#aaa").html(data);
			        },  
			        error : function(data) {  
			        }  
				});
		}
		</script>
	<%@ include file="/common/ico.jsp"%></head>
	<body>
		<div class="header">
			<div class="leftHeader">
				<ul>
					<li>
						<a title="主页" href="/">主 页</a>
					</li>
					<li>
						<input type="text" name="value1" class="searchcomplete ui-autocomplete-input"/>
					</li>
					<li>
						<a href="javascript:void(0)"><span>搜索</span>
						<img title="搜索" alt="搜索" src="${ctx}/images/search_options_off.png"/>	</a>
					</li>
					<li>
						<a href="javascript:void(0)"><p>高级搜索</p>
							<img title="高级搜索" alt="高级搜索" src="${ctx}/images/picnic_button_right.png" style="margin-top: 10px"/>
						</a>
					</li>
					<!-- 
					<li>
						<input name="已订阅" type="checkbox" value="已订阅" checked="checked" style="width: 10px; border: 0; margin-top: 15px; * margin-top: 10px;" />
						<a href="javascript:void(0)">已订阅</a>
					</li>
					 -->
				</ul>
			</div>
			<div class="rightHeader">
				<ul>
					<li>
						<a href="javascript:void(0)">出版物A-Z</a> |
					</li>
					<li>
						<a href="javascript:void(0)" onclick="execut('${ctx }/pages/subject/form/list?pCode=all');">中图分类法</a> |
					</li>
					<li>
						<a href="javascript:void(0)" onclick="execut('${ctx}/pages/publishers/form/list');">出版社</a> |
					</li>
					<li>
						<a href="javascript:void(0)" onclick="execut('${ctx }/pages/collection/form/manager');">打包集</a>
					</li>
					
					<%-- <li>
						<a href="javascript:void(0)" onclick="execut('${ctx}/pages/user/form/favorites');">收藏夹Test</a>
					</li> --%>
				</ul>
			</div>
			<div class="clear"></div>

			<div class="logo"></div>
			<div class="quickLink">
				<ul>
					<li>
						<a href="javascript:void(0)"><img src="${ctx}/images/icon_shopping.png" style="margin-top: -4px;"/> 购物车</a>
						<img src="${ctx}/images/space.gif" width="2" height="1"/>|
					</li>
					<li>
						<a href="javascript:void(0)">帮助</a><img src="${ctx}/images/space.gif" width="2" height="1"/>|
					</li>
					<li>
						<a href="javascript:void(0)">联系我们</a><img src="${ctx}/images/space.gif" width="2" height="1"/>|
					</li>
					<li>
						<a href="javascript:void(0)">English</a><img src="${ctx}/images/space.gif" width="2" height="1"/>|
					</li>
				</ul>
			</div>
			<div class="clearit"></div>
		</div>
		<div class="clearit"></div>
		<!--定义01 mainContainer 内容区开始-->
		<div class="mainContainer">
			<!--定义 0101 头部边框-->
			<div class="borderContainer">
				<!--定义 0102 左边内容区域 开始-->
				<div id="aaa" style="float: left;" >
				<div class="leftContainer">
					<div class="advPic">
						<a href="javascript:void(0)"><img src="${ctx}/images/intro.gif"/></a>
						<span> 中国图书进出口（集团）总公司图书文献部是集团下属从事海外图书文献等出版物进口业务的专营部门，<a href="javascript:void(0)">……更多内容 ></a> </span>
					</div>
					<div class="clear"></div>

					<div class="publications">
						<h4>
							打包集
						</h4>
						<div class="clearit"></div>
						<div class="packageBook">
							<ul>
								<li>
									<a href="javascript:void(0)"><img src="${ctx}/images/packageBook01.jpg"/></a>
								</li>
								<li>
									<a href="javascript:void(0)"><img src="${ctx}/images/packageBook02.jpg"/></a>
								</li>
								<li>
									<a href="javascript:void(0)"><img src="${ctx}/images/packageBook03.jpg"/></a>
								</li>
								<li>
									<a href="javascript:void(0)"><img src="${ctx}/images/packageBook04.jpg"/></a>
								</li>
								<li>
									<a href="javascript:void(0)"><img src="${ctx}/images/packageBook05.jpg"/></a>
								</li>
								<!-- 
								//产品包数据
								<c:forEach items="${pubCollections}" var="c" varStatus="index">
									<li><a href="javascript:void(0)"><img src="${ctx}/${c.cover}"></a></li>
								</c:forEach>
								 -->
							</ul>
						</div>

					</div>
					<div class="clearit"></div>


					<div class="publications">
						<a href="http://www.dawsonera.com/"><img src="${ctx}/images/dawsonLogo.png"/>
						</a>&nbsp;&nbsp;
						<img width="420" src="http://www.dawsonera.com/fragments/reader/images/ls.jpg"/>
						<br/>
						<span class="fc">点击 开启 发现
							Dawson公司电子图书资源全面登陆中图书苑</span>
					</div>

					<div class="publications">
						<h4>
							精选出版物
						</h4>
						<div class="clearit"></div>
						<h5>
							<a href="/content/serial/487" title="American Journal of Neuroprotection and Neuroregeneration">American Journal of Neuroprotection and Neuroregeneration</a>
							<!-- 
							<a href="/content/serial/487" title="American Journal of Neuroprotection and Neuroregeneration">${form.selected.title}</a>
							 -->
						</h5>
						<div class="clearit"></div>
						<div class="bookImg">
							<img title="image of American Journal of Neuroprotection and Neuroregeneration" alt="image of American Journal of Neuroprotection and Neuroregeneration" src="${ctx}/images/ajnn.gif" class="cover fleft"/>
							<!-- 
							<img title="image of American Journal of Neuroprotection and Neuroregeneration" alt="image of American Journal of Neuroprotection and Neuroregeneration" src="${ctx}/${form.selected.cover}" class="cover fleft">
							 -->
						</div>
						<div class="bookTitle">
							<ul>
								<li>
									<span>ISSN:</span>1947-2951 ${form.selected.code}
								</li>
								<li>
									<span>E-ISSN:</span>1473-8724 ${form.selected.code}
								</li>
								<li>
									<span>作者:</span>${form.selected.author}
								</li>
								<li>
									<span>出版社:</span><a href="javascript:void(0)">American Scientific Publishers ${form.selected.publisher.name}</a>
								</li>
							</ul>
						</div>
						<div class="bookInfo">
							<p>
								This journal aims to focus specifically on the emerging new
								aspects of neuroprotection and neuroregeneration in the widest
								sense of neuroscience. American Journal of Neuroprotection and
								Neuroregeneration (AJNN) deals with research on all the aspects
								of the central nervous system: relevant CNS diseases, their
								processes and their modification with drugs that may have any
								influence and significance in experimental and clinical
								conditions.
								${form.selected.remark}
							</p>
						</div>
					</div>

					<div class="clearit"></div>

					<div class="publications">
						<h4>
							最新出版物
						</h4>
						<div class="clearit"></div>
						<h5>
							<a href="/content/serial/487" title="American Journal of Neuroprotection and Neuroregeneration">Translation and Interpreting Studies</a>
						</h5>
						<div class="clearit"></div>
						<div class="bookImg">
							<img
								title="image of American Journal of Neuroprotection and Neuroregeneration"
								alt="image of American Journal of Neuroprotection and Neuroregeneration"
								src="${ctx}/images/tis.gif" class="cover fleft"/>

						</div>
						<div class="bookTitle">
							<ul>
								<li>
									<span>ISSN:</span>1932-2798
								</li>
								<li>
									<span>E-ISSN:</span>1876-2700
								</li>

							</ul>
						</div>
						<div class="bookInfo">
							<p>
								The Journal of the American Translation and Interpreting Studies
								Association.
							</p>
						</div>
					</div>

					<div class="clearit"></div>

					<div class="publications">
						<h4>
							特别优惠
						</h4>
						<div class="clearit"></div>
						<h5>
							<a href="/content/serial/487"
								title="American Journal of Neuroprotection and Neuroregeneration">Language
								Problems & Language Planning</a>
						</h5>
						<div class="clearit"></div>
						<div class="bookImg">
							<img
								title="image of American Journal of Neuroprotection and Neuroregeneration"
								alt="image of American Journal of Neuroprotection and Neuroregeneration"
								src="${ctx}/images/ijbidm.gif" class="cover fleft"/>

						</div>
						<div class="bookTitle">
							<ul>
								<li>
									<span>ISSN:</span>0272-2690
								</li>
								<li>
									<span>E-ISSN:</span>1569-9889
								</li>

							</ul>
						</div>
						<div class="bookInfo">
							<p>
								Language Problems.
							</p>
						</div>
					</div>

				</div>
				</div>
				<!--定义 0102 左边内容区域 结束-->

				<!--定义 0103 右边内容区域 开始-->
				<div class="rightContainer">
					<div class="formInput">
						<input type="text" name="value1"/>
						<div class="clearit"></div>
						<input type="text" name="value1"/>
						<div class="clearit"></div>
						<ul>
							<li>
								<a href="javascript:void(0)">忘记密码?</a>
							</li>
							<li>
								<span>记住账号?</span>
								<input name="取消" type="checkbox" value="取消" />
								<a href="javascript:void(0)">取消</a>
							</li>
						</ul>
						<div class="clearit"></div>
						<div class="button">
							<p>
								登 录
							</p>
							<img title="高级搜索" alt="高级搜索"
								src="${ctx}/images/picnic_button_right.png"/>
						</div>
					</div>
					<div class="clear"></div>
					<div class="clear"></div>
					<div class="box">
						<div class="boxInner">
							<p>
								注册:
							</p>
							<a href="javascript:void(0)">注册</a>
						</div>
					</div>
					<div class="clear"></div>
					<div class="clear"></div>
					<div class="box">
						<div class="boxInner">
							<p>
								最近更新:
							</p>
							<ul>
								<li>
									2012.03.12 --------- 200
								</li>
								<li>
									2012.02.23 ---------- 60
								</li>
								<li>
									2012.01.12 --------- 100
								</li>
								<li>
									2011.09.12 ---------- 40
								</li>
								<li>
									2011.03.01 --------- 120
								</li>
								<!-- 
								//新产品统计数据
								<c:forEach items="${pubCountList}" var="c" varStatus="index">
									<li>${c.createOn} --------- ${c.id}</li>
								</c:forEach>
								 -->
								<li>
									<a href="javascript:void(0)"><ingenta-tag:LanguageTag key="Page.Publications.DetailList.Link.More" sessionKey="lang"/></a>
								</li>
								<li>
									<span>资源总量: 111,520 ${form.count}</span>
								</li>
							</ul>
							<div class="clear"></div>
						</div>
					</div>


					<div class="clear"></div>
					<div class="clear"></div>
					<div class="box">
						<div class="boxInner">
							<p>
								便捷工具:
							</p>
							<ul>
								<li>
									<a href="javascript:void(0)">设置电子邮件提醒</a>
								</li>
								<li>
									<a href="javascript:void(0)">保存的搜索</a>
								</li>
								<li>
									<a href="javascript:void(0)">收藏夹</a>
								</li>
							</ul>
							<div class="clear"></div>
						</div>
					</div>


					<div class="clear"></div>
					<div class="clear"></div>
					<div class="box">
						<div class="boxInner">
							<p>
								类型:
							</p>
							<ul>
								<li>
									<a href="javascript:void(0)"><img src="${ctx}/images/icon01.jpg"/>最新</a>
								</li>
								<li>
									<a href="javascript:void(0)"><img src="${ctx}/images/icon02.jpg"/>开源</a>
								</li>
								<li>
									<a href="javascript:void(0)"><img src="${ctx}/images/icon03.jpg"/>已订阅</a>
								</li>
								<li>
									<a href="javascript:void(0)"><img src="${ctx}/images/icon04.jpg"/>免费</a>
								</li>
							</ul>
							<div class="clear"></div>
						</div>
					</div>

				</div>
				<!--定义 0103 右边内容区域 结束-->

			</div>
		</div>
		<div class="boderBottom"></div>
		<!--定义01 mainContainer 内容区结束-->
		<div class="clear" style="height: 50px;"></div>

		<!--定义02 footer 底部开始-->
		<div class="bottomContainer">
			<div class="hotLine">
				<a href="javascript:void(0)">客户服务</a>
				<ul>
					<li>
						<a href="mailto:service@cnpiec.com.cn">邮箱：service@cnpiec.com.cn</a>
					</li>
					<li>
						电话 : 8610-65066688
					</li>
					<li>
						营业时间:09:00-11:30, 13:00-17:00（工作日）
					</li>
					<li>
						服务投诉：
						<a href="mailto:problems@cnpiec.com.cn">
							problems@cnpiec.com.cn</a>
					</li>
				</ul>
			</div>
			<div class="footer">

				<ul>
					<li>
						<a href="javascript:void(0)">关于我们</a>
						<ul>
							<li>
								<a href="javascript:void(0)">业务介绍</a>
							</li>
							<li>
								<a href="javascript:void(0)">新浪微博</a>
							</li>
							<li>
								<a href="javascript:void(0)">PSOP</a>
							</li>
						</ul>
					</li>


					<li>
						<a href="javascript:void(0)">帮助</a>
						<ul>
							<li>
								<a href="javascript:void(0)">注册和登录</a>
							</li>
							<li>
								<a href="javascript:void(0)">查看电子资源</a>
							</li>
							<li>
								<a href="javascript:void(0)">充值和支付</a>
							</li>
							<li>
								<a href="javascript:void(0)">下载和查看</a>
							</li>
						</ul>
					</li>


					<li>
						<a href="javascript:void(0)">语种</a>
						<ul>
							<li>
								<a href="javascript:void(0)">English</a>
							</li>
							<li>
								<a href="javascript:void(0)">Chinese</a>
							</li>
						</ul>
					</li>

				</ul>
			</div>



			<div class="copyright">
				<div class="clear" style="height: 30px;"></div>
				Copyright &copy; CNPIEC 2012.
				<a title="" href="/librarian/termscondition">Terms &amp; conditions</a>
				<br>
				<a title="" href="/access">Privacy policy</a> |
				<a title="" href="/licence">Copyright &amp; permissions</a>
				<div class="clear" style="height: 30px;"></div>
			</div>

		</div>


	</body>
</html>
