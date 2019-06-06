<#if (list?? && 0 < list?size)>
	<div class="featureContainer">
		<div class="feature">
			<div class="black h450">
				<div id="botton-scroll2">
					<ul class=featureUL>
	                    <li class=featureBox style="height: 590px;">
		                    <div class="oh">
		                    <#list list as p>
			                     <div class="bookBlock">
								  <#if p.publications.cover??>
									<a href="${request.contextPath}/pages/publications/form/article/${p.publications.id}">
										<img src="${request.contextPath}/pages/publications/form/cover?t=1&id=${p.publications.id}" width="95" height="130"/>
									</a>
								<#else>
									<a href="${request.contextPath}/pages/publications/form/article/${p.publications.id}">
										<div class="imgMoren">
										<p>${p.publications.title}</p>
										</div>
									</a>
								</#if>
									<p class="bookTit"><a class="a_title" title="${(p.publications.title)!}" href="${request.contextPath}/pages/publications/form/article/${p.publications.id}">${p.publications.title} </a></p>
								</div>
								
								<#if (7 == p_index || 15 == p_index)></div></li><li class=featureBox style="height: 590px;"><div class="oh"></#if>
								<#if (23 == p_index)></div></li></#if>
	                  		</#list>
                    </ul>
				</div>
			</div>
			<a class="prev2" href="javascript:;">Previous</a>
			<a class="next2" href="javascript:;">Next</a> 
		</div>
	</div>            
</#if>
