<#assign i = 0 />
<#if (list?? && 0 < list?size)>
	<#list list as p>
		<#if !p.id?contains(pubId)>
			<#assign i = i + 1>
			<#if (7 == i)><#break></#if>
			<div class="mt15">
				<img src="<#if p.cover??>${request.contextPath}/pages/publications/form/cover?t=2&id=${p.id}<#else>${request.contextPath}/images/noimg.jpg</#if>" width="95" height="129" onerror="this.src='${request.contextPath}/images/noimg.jpg'" />
				<p><a title="${p.title}" href="${request.contextPath}/pages/publications/form/article/${p.id}">${p.title}</a></p>
			</div>
		</#if>
	</#list>
</#if>