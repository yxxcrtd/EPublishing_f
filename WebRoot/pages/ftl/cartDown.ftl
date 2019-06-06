    <div class="oh">
    <#list downList as down>
                <div class="cartListDiv">
                <a href="${request.contextPath}/pages/publications/form/article/${down.id}?fp=3">
                	<img src="${request.contextPath}/pages/publications/form/cover?t=1&id=${down.id}" width="61" height="81" onerror="this.src='${request.contextPath}/images/smallimg.jpg'" class="imgbor fr ml30" />
                </a>
                <p class="pTit"><a name="title" href="${request.contextPath}/pages/publications/form/article/${down.id}?fp=3" title="${down.title }">${down.title }</a></p>
                <#if (down.author?? && down.author!='')>
					<p>By 
						<#list down.author?split(",") as x>   
			        		<a href='${request.contextPath}/index/search?type=2&isAccurate=1&searchValue2=${x}'>${x}</a>&nbsp;
			        	</#list>
			        </p>
				</#if>
                <p>
                <a href='${request.contextPath }/index/search?type=2&isAccurate=1&searchValue2=${down.publisher.name }'>${down.publisher.name }</a>
                   (${down.pubDate?substring(0,4)})   
                </p>
            </div>
    </#list>
    </div>