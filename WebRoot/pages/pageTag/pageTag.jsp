<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!-- 总页数 -->
<c:set var="tPages">
	<c:if test="${form.count%form.pageCount==0}">
		<fmt:formatNumber groupingUsed="false" type="number" value="${form.count/form.pageCount}" />
	</c:if>
	<c:if test="${form.count%form.pageCount!=0}">
		<fmt:formatNumber groupingUsed="false" type="number" value="${(form.count-form.count%form.pageCount)/form.pageCount+1}" />
	</c:if>
</c:set>
<c:if test="${tPages>1}">
	<div class="pagelink fr noMargin">
		<span class="number tr"> <!-- 上一页开始--> <a href="javascript:;"
			onclick="dividePage('${form.curpage-1}')"><img
				src="${ctx }/images/prev.gif" class="vm" /> <ingenta-tag:LanguageTag sessionKey="lang" key="Global.Page.Prev"/></a> <!-- 上一页结束--> <!-- 首页开始-->
			<a href="javascript:;" onclick="dividePage('0')"
			<c:if test="${form.curpage==0 }">class="now_a"</c:if>>1</a> <!-- 首页结束-->
			<!-- 中间部分开始--> <c:if test="${form.curpage<4 }">
				<c:set var="startTag">
					<c:if test="${ tPages>5}">4</c:if>
					<c:if test="${ tPages<=5}">${tPages-2}</c:if>
				</c:set>
				<c:if test="${tPages>=3}">
					<c:forEach begin="1" end="${ startTag}" varStatus="status">
						<a href="javascript:;" onclick="dividePage('${status.index}')"
							<c:if test="${form.curpage==status.index }" >class="now_a"</c:if>>${status.index+1 }</a>
					</c:forEach>
				</c:if>
				<c:if test="${ tPages>6}">
					<span>...</span>
				</c:if>
			</c:if> <c:if test="${form.curpage>=4 }">
				<span>...</span>
				<c:if test="${tPages-form.curpage<6 }">
					<c:forEach begin="${tPages-5 }" end="${tPages-2 }"
						varStatus="status">
						<a href="javascript:;" onclick="dividePage('${status.index}')"
							<c:if test="${form.curpage==status.index }" >class="now_a"</c:if>>${status.index+1 }</a>
					</c:forEach>
				</c:if>
				<c:if test="${tPages-form.curpage>=6 }">
					<c:forEach begin="${form.curpage-2 }" end="${form.curpage }"
						varStatus="status">
						<a href="javascript:;" onclick="dividePage('${status.index}')"
							<c:if test="${form.curpage==status.index }" >class="now_a"</c:if>>${status.index+1 }</a>
					</c:forEach>
					<c:if test="${tPages-form.curpage>4 }">
						<c:forEach begin="${form.curpage+1}" end="${form.curpage+2}"
							varStatus="status">
							<a href="javascript:;" onclick="dividePage('${status.index}')"
								<c:if test="${form.curpage==status.index }" >class="now_a"</c:if>>${status.index+1 }</a>
						</c:forEach>
						<span>...</span>
					</c:if>
					<c:if test="${tPages-form.curpage<=4 }">
						<c:if test="${tPages-form.curpage>2 }">
							<c:forEach begin="${form.curpage+1 }" end="${tPages-2 }"
								varStatus="status">
								<a href="javascript:;" onclick="dividePage('${status.index}')"
									<c:if test="${form.curpage==status.index }" >class="now_a"</c:if>>${status.index+1 }</a>
							</c:forEach>
						</c:if>
					</c:if>
				</c:if>
			</c:if> <!-- 中间部分结束--> <!-- 尾页开始 --> <c:if test="${tPages>1}">
				<a href="javascript:;" onclick="dividePage('${tPages-1}')"
					<c:if test="${tPages-1==form.curpage}">class="now_a"</c:if>>${tPages }</a>
			</c:if> <!-- 尾页结束 --> <!-- 下一页开始 --> <c:if test="${tPages>form.curpage+1 }">
				<a href="javascript:;" onclick="dividePage('${form.curpage+1}')"><img
					src="${ctx }/images/next.gif" class="vm" /> <ingenta-tag:LanguageTag sessionKey="lang" key="Global.Page.Next"/></a>
			</c:if> <!-- 下一页结束 --> 
			<c:if test="${form.pageCount!=6 }">
			<span><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Page.Display"/> <select onchange="GO(this)">
					<option>10</option>
					<option
						<c:if test="${form.pageCount==20 }">selected="selected"</c:if>>20</option>
					<option
						<c:if test="${form.pageCount==50 }">selected="selected"</c:if>>50</option>
			</select>
		</span></c:if> <span><ingenta-tag:LanguageTag sessionKey="lang" key="Global.Page.Items"/> <ingenta-tag:LanguageTag sessionKey="lang" key="Global.Page.Total"/> ${form.count } <ingenta-tag:LanguageTag sessionKey="lang" key="Global.Page.Item"/></span>


		</span>
	</div>
</c:if>
<c:if test="${tPages<=1}">
	<div class="pagelink fr noMargin">
		<span class="number tr">
			<span>
				<ingenta-tag:LanguageTag sessionKey="lang" key="Global.Page.Total"/> ${form.count } <ingenta-tag:LanguageTag sessionKey="lang" key="Global.Page.Item"/>
			</span>
		</span>
	</div>
</c:if>


