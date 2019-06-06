<div class="oh h550">
	<h1 class="h1Tit borBot"><a class="ico1"><#if (lang=="zh_CN")>热读资源<#else>Popular Titles</#if></a></h1>
	<#if (hotList?? && 0 < hotList?size)>
	 	<div class="featureContainer">
			<div class="feature">
			    <div class="black">
			        <div id="botton-scroll3">
			            <ul class=featureUL>
			              <li class=featureBox>
				            <div class="oh">
				            	<#list hotList as p>
				                <div class="bookBlock">
				                		 <#if p.coverFlag==1>
					                     	<a href="${request.contextPath}/pages/publications/form/article/${p.id}">
												<img src="<#if p.cover??>${request.contextPath}/pages/publications/form/cover?t=1&id=${p.id}<#else>${request.contextPath}/images/noimg.jpg</#if>"  width="95" height="130" onerror="this.src='${request.contextPath}/images/noimg.jpg'"/>
											</a>
				                     	<#else>
				                     		<div class="imgMoren">
												<p>${p.title}</p>
											</div>
				                     	</#if>
				                     <p class="bookTit">
				                    	<a class="a_title" title="${p.title}" href="${request.contextPath}/pages/publications/form/article/${p.id}">
											<#if ( p.title?length > 8)>${p.title[0..7]}...<#else>${p.title}</#if>
										</a>
									 </p>
				                     <p class="bookTit"><#if (p.author?length >10) >${p.author[0..9]}...<#else> ${p.author }</#if></p>
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
</div>
