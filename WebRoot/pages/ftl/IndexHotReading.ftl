<#if (pubList?? && 0 < pubList?size)>
	<#list pubList as p>
		<div class="block">
			<div class="fl w30 mt2"><img width="13" height="13" src="${request.contextPath}/images/ico/ico4.png" title="${obj!}" /></div>
	        <div class="fl w640">
				<p><a class="a_title" href="${request.contextPath}/pages/publications/form/article/${p.id}">${p.title}</a></p>
				<p>
					<#if (p.author??)><p>By <#list p.author?split(",") as x><a href='${request.contextPath}/index/search?type=2&isAccurate=1&searchValue2=${x}'>${x}</a>&nbsp;&nbsp;&nbsp;&nbsp;</#list></#if>
				</p>
				<p>
					<#if (p.publisher??)><a href='${request.contextPath}/index/search?type=2&searchsType=4&searchValue2="${p.publisher}"'>${p.publisher}</a></#if>
					<#if (p.date??)>(${p.date[0..3]})</#if>
				</p>
				<#if (p.type==2 && p.startVolume?? && p.endVolume??)>
					<p>Vol ${p.startVolume} - Vol ${p.endVolume}</p>
				</#if>
			</div>
		</div>
		<#if (3 == p_index)><#break /></#if>
	</#list>
</#if>