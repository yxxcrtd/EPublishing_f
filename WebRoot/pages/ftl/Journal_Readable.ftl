 <div class="oh h350">
	<h1 class="h1Tit borBot">
		<a class="ico1" href="javascript:void(0)"><#if (lang=="zh_CN")>可读资源<#else>Readable Resource</#if></a>
	</h1>
	<#if (list?? && 0 < list?size)>
		<div class="featureContainer">
	                <div class="feature">
	                    <div class="black h320">
	                        <div id="botton-scroll1">
	                            <ul class=featureUL>
	                              <li class=featureBox>
	                                    <div class="block jourList">
	                                    <#list list as p>
	                                    	<div class="oh w350 fl" style="margin-bottom: 7px;">
	                                            <div class="fl w40 mt2">
	                                                <#if (p.free==2 && p.oa==2 || p.free==2)><img src="${request.contextPath}/images/ico/f.png" width="14" height="14"  /></#if>
	                                                <#if (p.oa==2 )><img src="${request.contextPath}/images/ico/o.png" width="14" height="14" /></#if>
	                                                <span><img src="${request.contextPath}/images/ico/ico3.png" width="13" height="13" title="期刊" /></span>
	                                            </div>
	                                             <div class="fl w300">
	                                                <p>
		                                                <a class="a_title" title="${p.title}" href="${request.contextPath}/pages/publications/form/article/${p.id}">
				                          					<#if (20 > p.title?length)>   
																${p.title}
															<#else> 
		     													${p.title[0..19]}... 
															</#if>
				                          				</a>
			                          				</p>
	                                                <p>${p.publisher} </p>
	                                                <p>Volume ${(p.startVolume)! } - Volume ${(p.endVolume)! }</p>
	                                            </div>
	                                   	 	</div>
	                               <#if (7 == p_index || 15 == p_index)></div></li><li class=featureBox><div class="block jourList"></#if>
									<#if (23 == p_index)></div></li></#if>
		                  		</#list>
	                   </ul>
					</div>
				</div>
	              <a class="prev1 chPrev1" href="javascript:;">Previous</a>
	              <a class="next1 chNext1" href="javascript:;">Next</a> 
		 	</div>
	    </div>
	</#if> 
</div>         