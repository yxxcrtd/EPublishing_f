 <div class="oh h350">
	<h1 class="h1Tit borBot">
		<a class="ico1" href="javascript:void(0)"><#if (lang=="zh_CN")>推荐期刊<#else>Recommend the journal</#if></a>
	</h1>
	<#if (list?? && 0 < list?size)>
	<div class="featureContainer">
		<div class="feature">
			<div class="black h320">
				 <div id="botton-scroll2">
					<ul class=featureUL>
						<li class=featureBox>
							<!-- 一个滚动条内容开始 -->
							<div class="oh">
								<div class="block jourList">
								<#list list as p>
									<div class="oh w350 fl" style="margin-bottom: 7px;">
	                                    <div class="fl w40 mt2">
	                                        <#if ( p.license==1||oa=='2'||free=='2')><img src="${request.contextPath}/images/ico/ico_open.png" width="16" height="16" />
	                                        <#else><img src="${request.contextPath}/images/ico/ico_close.png" width="16" height="16" /></#if> 
	                                        <img src="${request.contextPath}/images/ico/ico3.png" width="13" height="13" />
	                                    </div>
	                                    <div class="fl w300">
	                                        <p class="omit">
											<a class="a_title" title="${p.title}" href="${request.contextPath}/pages/publications/form/article/${p.id}">
															${p.title}
							                </a>
											</p>
	                                        <p class="omit"><a href='${request.contextPath}/index/search?type=2&isAccurate=1&searchValue2=${p.publisher }'>${p.publisher}</a></p>
											<p>
											<#if ( p.startVolume?? &&p.endVolume??)>
												Volume  ${p.startVolume }  -  Volume  ${p.endVolume }
											<#else> 
												</br>
											</#if>
											</p>
										</div>
	                                 </div>
	                               <#if (7 == p_index || 15 == p_index)></div></li><li class=featureBox><div class="block jourList" style="height:450px"></#if>
									<#if (23 == p_index)></div></li></#if>
		                  		</#list>
	                   </ul>
					</div>
				</div>
			 <a class="prev2 chPrev1" href="javascript:void(0)">Previous</a>
	         <a class="next2 chNext1" href="javascript:void(0)">Next</a> 
		</div>
			<!-- /feature -->
	</div>
	</#if>
</div>