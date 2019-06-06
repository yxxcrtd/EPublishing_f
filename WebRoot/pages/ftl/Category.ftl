<#if (categoryList?? && 0 < categoryList?size)>
	<li>
		<#list categoryList as c>
			<p><a href="javascript:;" onclick="advancedSubmit1('${c[0..0]} ${c?substring(1, c?index_of("@@@@@@@@@@"))}')" title="${c[0..0]} ${c?substring(1, c?index_of("@@@@@@@@@@"))}"><b>${c[0..0]}</b> ${c?substring(1, c?index_of("@@@@@@@@@@"))}</a></p>
			<#if ("zh_CN" == lang)>
				<#if (6 == c_index || 14 == c_index)></li><li></#if>
			<#else>
				<#if (6 == c_index || 13 == c_index)></li><li></#if>
			</#if>
		</#list>
	</li>
</#if>