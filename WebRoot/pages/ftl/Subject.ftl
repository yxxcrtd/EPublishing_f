<#if (subjectList?? && 0 < subjectList?size)>
	<div class="liClass">
		<ul>
		  	<#list subjectList as s>
				<li>
					<a href="javascript:;" onclick="advancedSubmit1('${s[0..0]} ${s?substring(1, s?index_of("@@@@@@@@@@"))}')" title="${s[0..0]} ${s?substring(1, s?index_of("@@@@@@@@@@"))}">
						<span class="alph"><b>${s[0..0]}</b></span> <span class="write">${s?substring(1, s?index_of("@@@@@@@@@@"))}</span>
					</a>
				</li>
				<#if ("zh_CN" == lang)>
					<#if (6 == s_index || 14 == s_index)></ul></div><div class="liClass"><ul></#if>
				<#else>
					<#if (6 == s_index || 13 == s_index)></ul></div><div class="liClass"><ul></#if>
				</#if>
			</#list>
		</ul>
	</div>
</#if>