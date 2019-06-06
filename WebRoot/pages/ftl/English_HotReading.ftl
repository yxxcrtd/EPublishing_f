<div class="oh h650">
	<h1 class="h1Tit borBot"><a class="ico1"><#if (lang=="zh_CN")>热读资源<#else>Popular Titles</#if></a></h1>
	<#if (hotList?? && 0 < hotList?size)>
		<div class="featureContainer">
		    <div class="feature">
		        <div class="black h650">
		            <div id="botton-scroll3">
		                <ul class=featureUL>
		                  <li class="featureBox">
		                    <div class="oh">
		                    <#list hotList as p>
		                        <div class="enBigBlock">
			                         <#if p.coverFlag==1>
		                                <a href="${request.contextPath}/pages/publications/form/article/${p.id}">
			                        		<img src="${request.contextPath}/pages/publications/form/cover?t=2&id=${p.id}"  width="167" height="240" class="imgbor"/>
			                           	</a>
			                         <#else>
			                          	<a href="${request.contextPath}/pages/publications/form/article/${p.id}">
				                        	 <div class="imgMorenBig">
				                        	 	<p>${p.title}</p>
				                        	 </div>
			                        	</a>
			                          </#if>
		                            <p>
		                           		<a class="a_title" title="${p.title}" href="${request.contextPath}/pages/publications/form/article/${p.id}">
											<#if (p.title?length > 16)>${p.title[0..15]}...<#else>${p.title}</#if>
										</a>
		                            </p>
		                       </div>
				           		 <#if (5 == p_index || 11 == p_index)></div></li><li class=featureBox><div class="oh"></#if>
									<#if (17 == p_index)></div></li></#if>
		                  		</#list>
	                   </ul>
					</div>
				</div>
	              <a class="prev3 enNext1" href="#">Previous</a>
	              <a class="next3 enNext1" href="#">Next</a> 
		     </div>
	    </div>    
	</#if>  
</div>  
