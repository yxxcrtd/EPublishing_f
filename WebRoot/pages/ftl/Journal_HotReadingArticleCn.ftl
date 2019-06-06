<#if (hotlist?? && 0 < hotlist?size)>
	<div class="featureContainer">
		<div class="feature">
			<div class="black">
				<div id="botton-scroll3">
					<ul class=featureUL>
	                    <li class=featureBox>
		                    <div class="oh">
		                    <#list hotlist as p>
			                     <div class="bookBlock">
			                     	<#if p.publications.cover??>
				                     	<a href="${request.contextPath}/pages/publications/form/article/${p.publications.id}">
											<img src="<#if p.publications.cover??>${request.contextPath}/pages/publications/form/cover?t=1&id=${p.publications.id}<#else>${request.contextPath}/images/noimg.jpg</#if>"  width="95" height="130" onerror="this.src='${request.contextPath}/images/noimg.jpg'"/>
										</a>
			                     	<#else>
			                     		<a href="${request.contextPath}/pages/publications/form/article/${p.publications.id}">
			                     		<div class="imgMoren">
											<p>${(p.publications.title)!}</p>
										</div>
										</a>
			                     	</#if>
									<p class="bookTit">
										<a class="a_title" title="${(p.publications.title)!}" href="${request.contextPath}/pages/publications/form/article/${p.publications.id}">
											<#if (8 < p.publications.title?length)>${p.publications.title[0..7]}...<#else>${p.publications.title}</#if>
										</a>
									</p>
									<p>${(p.publications.year)!}年 ${(p.publications.volumeCode)!}期</p>
								</div>
								
								<#if (7 == p_index || 15 == p_index)></div></li><li class=featureBox><div class="oh"></#if>
								<#if (23 == p_index)></div></li></#if>
	                  		</#list>
                    </ul>
				</div>
			</div>
			<a class="prev3" href="javascript:;">Previous</a>
			<a class="next3" href="javascript:;">Next</a> 
		</div>
	</div>            
</#if>
