<h1 class="indexHtit">
	<span class="fl titFb"><a class="ico1">Popular Titles</a></span>
    <span class="fr"></span>
</h1>
<#if (pubList?? && 0 < pubList?size)>
<#list pubList as p>
		<#assign license=( p.publications.subscribedIp>0)?string("true","false") />
		<#assign oa=(p.publications.oa == 2)?string("true","false") />
		<#assign free=(p.publications.free==2)?string("true","false")/>
		<div class="block">
        	<div class="fl w40 mt2">
        		<#if (license=='false' && oa=='false' && free=='false')>
                	<img src="${request.contextPath}/images/ico/ico_open.png" width="16" height="16">
                </#if>
                <#if (license=='true'||oa=='true'||free=='true')>
                <img src="${request.contextPath}/images/ico/ico_open.png" width="16" height="16">
                </#if>    
                <#if (p.publications.type==1)> 
                <img width="13" height="13" src="${request.contextPath}/images/ico/ico4.png" />
                </#if>
                <#if (p.publications.type==4 || p.publications.type==2)>
				<img width="13" height="13" src="${request.contextPath}/images/ico/ico5.png" />
				</#if>
            </div>
            <div class="fl w640">
                <p><a title="${p.publications.title}" href="${request.contextPath}/pages/publications/form/article/${p.publications.id}">
                    ${p.publications.title?replace("&lt;", "<")?replace("&gt;", ">")?replace("&amp;", "&")} 
                	</a>
                </p>
                <#if (p.publications.author!='')>
					<p>By 
						<#list p.publications.author?split(",") as x>   
			        		<a href='${request.contextPath}/index/search?type=2&isAccurate=1&searchValue2=${x}'>${x}</a>&nbsp;
			        	</#list>
			        </p>
				</#if>
				
				<#if (p.publications.publisher.name!='')>
					<p><a href='${request.contextPath}/index/search?type=2&isAccurate=1&searchValue2=${p.publications.publisher.name}'>${p.publications.publisher.name}</a>
					<#if (p.publications.pubDate!='' )>(${p.publications.pubDate?substring(0,4)})</#if>
					</p>
				</#if>
				<#if (p.publications.type==2 && p.publications.startVolume!='' && p.publications.endVolume!='')>
					<p>
					vol. ${p.publications.startVolume }-vol. ${p.publications.endVolume }
					</p>
				</#if>
            </div>
        </div>
</#list>
</#if>