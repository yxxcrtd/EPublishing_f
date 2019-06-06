 <div class="oh h450">
 	<h1 class="h1Tit borBot"><a class="ico1"><#if (lang=="zh_CN")>热读文章<#else>Hotreading articles</#if></a></h1>
	<#if (hotlist?? && 0 < hotlist?size)>
		<div class="featureContainer">
	        <div class="feature">
	            <div class="black h420">
	                <div id="botton-scroll3">
						<ul class=featureUL>
							<li class=featureBox>
								<#list hotlist as p>
			                        <div class="block">
			                            <div class="fl w40 mt2">
			                              <#if (license=="true")>
			                              	<img src="${request.contextPath}/images/ico/ico_open.png" width="16" height="16" />
			                              </#if>
			                              <#if (license=="false")>
			                              	<img src="${request.contextPath}/images/ico/ico_close.png" width="16" height="16" />
			                              </#if>
			                               <img src="${request.contextPath}/images/ico/ico5.png" width="13" height="13" title="文章"/>
			                            </div>
			                          	<div class="fl w640">
				                           <p class="omit w640">
					                            <a class="a_title" title="${p.title}" href="${request.contextPath}/pages/publications/form/article/${p.id}" >
					                            	<#if (p.publications.title?length < 90)>   
														${p.title}
													<#else> 
														${p.title[0..89]}... 
													</#if>
					                            </a>
				                            </p>
			                                <p>
			                               		<a href="${request.contextPath}/pages/publications/form/journaldetail/${p.parentId}">${p.parentTitle}</a>,
			                                	${p.year },vol ${p.volumeCode },iss ${p.issueCode }, ${p.startPage }-${p.endPage}
			                                </p>
			                                <p>${p.publisher}</p>
			                            </div>
									</div>
		                        	<#if (3 == p_index || 7 == p_index)></li><li class=featureBox><div class="block"></#if>
									<#if (11 == p_index)></li></#if>
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