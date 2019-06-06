<#if (serviceList?? && 0 < serviceList?size)>
	<#list serviceList as s>
		${s}
	</#list>
</#if>