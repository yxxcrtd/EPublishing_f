<div class="oh h460">
	<h1 class="h1Tit borBot"><a class="ico1"><#if (lang=="zh_CN")>编辑推荐<#else>Recommended Resources</#if></a></h1>
	<#if (editorRecommendsList?? && 0 < editorRecommendsList?size)>
		<div class="featureContainer">
			<div class="feature">
				<div class="black h450">
					 <div id="botton-scroll1" style="width:730px !important;">
						<ul class="featureUL" style="width:3650px !improtant; left:-730px !important;">
		                    <li class="featureBox" style="width:730px !important; height:490px !important;">
			                    <div class="oh">
			                    <#list editorRecommendsList as p>
									<div class="enBlock" style="margin-bottom: 25px;">
			                          		<h1>
		                          				<p class="bookTit">
			                          				<a class="a_title" title="${p.title}" href="${request.contextPath}/pages/publications/form/article/${p.id}">
			                          					<#if (20 > p.title?length)>   
															${p.title}
														<#else> 
	     													${p.title[0..19]}... 
														</#if>
			                          				</a>
			                          			</p>
			                          		</h1>
			                          		<#if p.coverFlag==1>
						                     	<a href="${request.contextPath}/pages/publications/form/article/${p.id}">
													<img src="${request.contextPath}/pages/publications/form/cover?t=1&id=${p.id}"  width="95" height="130" class="fl mr10"/>
												</a>
				                     		<#else>
					                     		<div class="imgMoren1" style="float: left; margin-left: 0;">
													<p>${p.title}</p>
												</div>
				                     		</#if>
											<p>By ${p.author }
											</p>
											<p class="mt20">${p.publisher}</p>
		                          	</div>
									<#if (3 == p_index || 7 == p_index)></div></li><li class="featureBox" style="width:730px !important; height:490px !important;"><div class="oh"></#if>
									<#if (11 == p_index)></div></li></#if>
		                  		</#list>
	                    </ul>
					</div>
				</div>
				<a class="prev1" href="javascript:;">Previous</a>
	            <a class="next1" href="javascript:;">Next</a> 
			</div>
		</div>            
	</#if>
</div>