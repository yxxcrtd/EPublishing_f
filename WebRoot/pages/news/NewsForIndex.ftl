<#import "/spring.ftl" as spring /> 
<h1 class="indexHtit">
	<span class="fl titFb">
		<a class="ico3">==111==<@spring.message "Global.Label.News" />==2==</a>
	</span>
	<span class="fr mt10 mr5 newListA">
		<a href="javascript:;"><img src="images/ico/ico12.png" /></a>
	</span>
</h1>

<ul class="dot">
	<#if (list?? && 0 < list?size)>
		<#list list as l>
			<li>
				<span class="fl omit w210"><a href="${request.contextPath}/pages/news/form/newsList?newsId=${l.id}" title="${l.title}">${l.title}</a></span>
				<span class="fr f10">${l.createDate?string("yyyy-MM-dd")}</span>
			</li>
		</#list>
	</#if>
</ul>