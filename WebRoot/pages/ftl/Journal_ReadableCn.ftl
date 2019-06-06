<#if (list?? && 0 < list?size)>
	<div class="featureContainer">
		<div class="feature">
			<div class="black h450">
				<div id="botton-scroll1" style="width:730px !important;">
					<ul class=featureUL style="width:3650px !improtant; left:-730px !important;">
	                    <li class=featureBox style="width:730px !important; height:490px !important;">
		                    <div class="oh">
		                    <#list list as p>
			                     <div class="bookBlock" style="margin-bottom: 25px;">
			                     	<a href="${request.contextPath}/pages/publications/form/article/${p.id}">
										<img src="<#if p.cover??>${request.contextPath}/pages/publications/form/cover?t=1&id=${p.id}<#else>${request.contextPath}/images/noimg.jpg</#if>"  width="95" height="130" onerror="this.src='${request.contextPath}/images/noimg.jpg'"/>
									</a>
									<p class="bookTit"><a href="${request.contextPath}/pages/publications/form/article/${p.id}">${p.publisher.name} </a></p>
								</div>
								
								<#if (7 == p_index || 15 == p_index)></div></li><li class=featureBox style="width:730px !important; height:490px !important;"><div class="oh"></#if>
								<#if (23 == p_index)></div></li></#if>
	                  		</#list>
                    </ul>
				</div>
			</div>
			<a class="prev1" href="javascript:;">Previous</a>
			<a class="next1" href="javascript:;">Next</a> 
		</div>
	</div>            
</#if>
