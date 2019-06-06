<#if (hotList?? && 0 < hotList?size)>
	<#list hotList as hot>
		<a class="span${hot_index + 1}" href="javascript:;" onclick="searchByCondition('searchValue', '${hot.activity}')" title="${hot.activity}">${hot.activity}</a>
	</#list>
</#if>