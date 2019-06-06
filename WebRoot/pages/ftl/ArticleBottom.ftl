<#assign i = 0 />
<#if (toplist?? && 0 < toplist?size)>
	<#list toplist as p>
		<#assign license=( (p.publications.subscribedIp??||p.publications.subscribedUser??)&&(p.publications.subscribedIp>0||p.publications.subscribedUser>0) )?string("true","false") />
		<#assign news=(p.publications.latest??&&p.publications.latest>0 )?string("true","false") />
		<#assign oa=(p.publications.oa??&&p.publications.oa==2 )?string("true","false") />
		<#assign free=(p.publications.free??&&p.publications.free==2 )?string("true","false")/>
		<#assign collection=(p.publications.inCollection??&&p.publications.inCollection>0)?string("true", "false")/>
		<#assign add1=((priceList?? && 0 < priceList?size) &&p.oa!=2&&sessionScope.mainUser?? && p.subscribedUser<=0&&(p.buyInDetail<=0&&p.exLicense>=0))?string("true","false")/>
		<#if (add1=='false')><#assign add='false'/></#if>
		<#if (add1=='true' && p.subscribedIp>0)>
			<#if (sessionScope.mainUser.institution.id == sessionScope.institution.id && sessionScope.mainUser.level == 2)><#assign add='false'/></#if>
			<#if (sessionScope.mainUser.institution.id == sessionScope.institution.id && sessionScope.mainUser.level != 2)><#assign add='true'/></#if>
			<#if (sessionScope.mainUser.institution.id != sessionScope.institution.id)><#assign add='true' /></#if>
		</#if>
		<#if (add1=='true' && (p.subscribedIp == null || p.subscribedIp<=0))><#assign add='true'/></#if>
		<#if (add1=='false')><#assign add='false' /></#if>
			
		<#if !p.publications.id?contains(pubId)>
			<#assign i = i + 1>
			<#if (5 == i)><#break></#if>
			
			<div class="enBlock">
			<#if (p.publications.title??)>
				<h1 class="newOmit"><a href="${request.contextPath}/pages/publications/form/article/${p.publications.id}">${p.publications.title}</a></h1>	
				</#if>
				<img src="<#if p.publications.cover??>${request.contextPath}/pages/publications/form/cover?t=1&id=${p.publications.id}<#else>${request.contextPath}/images/noimg.jpg</#if>" width="95" height="129" class="fl mr10" onerror="this.src='${request.contextPath}/images/noimg.jpg'"/>
				<p class="mb10">
					<#if p.publications.type??>
						<#if (p.publications.type != 2)>
							<#if p.publications.author??>By 
							<#if (18 < p.publications.author?length)>${p.publications.author[0..17]}...<#else>${p.publications.author}</#if></#if>
							<#if (p.publications.type == 4)> in <a href="${request.contextPath}/pages/publications/form/article/${p.publications.publications.id}">${p.publications.publications.title}</a></#if>
						</#if>
					</#if>
					<#if (p.publications.type==2)>
						<#if (p.publications.startVolume??&&p.publications.endVolume??)>Volume ${p.publications.startVolume }-Volume ${p.publications.endVolume }</#if>   
					</#if>   
				</p>
				<p class="mb10">
					<#if (p.publications.pubDate??)>
						<#if (4==p.publications.pubDate?length || 6==p.publications.pubDate?length || 8==p.publications.pubDate?length)>
							${p.publications.pubDate?substring(0,4)}<#if (4 < p.publications.pubDate?length)>-${p.publications.pubDate?substring(4,6)}</#if><#if (6 < p.publications.pubDate?length)>-${p.publications.pubDate?substring(6,8)}</#if>
						</#if>
					</#if>
				</p>
				<p class="mb10"><a href="javascript:;" onclick="s('${p.publications.publisher.name}')">${p.publications.publisher.name}</a></p>
			</div>
		</#if>
	</#list>
</#if>
<script type="text/ecmascript">
	function s(s) {
		location.href=encodeURI("${request.contextPath}/index/search?type=2&isAccurate=1&searchValue2=" + encodeURI(s));
	}
</script>