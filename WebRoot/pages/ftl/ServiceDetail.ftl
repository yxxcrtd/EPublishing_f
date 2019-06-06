<#assign content = "" />
<div class="personLeft">
	<div class="perLeftPart">
	    <#if (serviceList?? && 0 < serviceList?size)>
			<#list serviceList as s>
				<p><a href="/service/${s?substring(0, s?index_of("@@@@@id@@@@@"))}" <#if (s?substring(0, s?index_of("@@@@@id@@@@@")) == id)>class="perLiA"</#if>>${s?substring(s?index_of("@@@@@id@@@@@") + 12, s?index_of("@@@@@@@@@@"))}</a></p>
				<#if (s?substring(0, s?index_of("@@@@@id@@@@@")) == id)>
					<#assign content = s?substring(s?index_of("@@@@@@@@@@") + 10, s?length) />
				</#if>
			</#list>
		</#if>
	</div>
</div>
${content!}