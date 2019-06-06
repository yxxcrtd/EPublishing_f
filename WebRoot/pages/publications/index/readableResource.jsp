<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/tools.jsp"%>
<script type="text/javascript">
	$(document).ready(function(e) {
 	  	getCount1();
	});
	function getCount1(){
		var testExp=/^\d+$/;
		$(".chapt_author_p3 ").each(function(i,item){
			var pagNubStr=$(item).text().trim();
			var numbers=pagNubStr.split("-");
			console.info(numbers);
			var range=1;
			if(numbers){
				var spage=numbers[0].trim().match(testExp)?parseInt(numbers[0].trim()):numbers[0];
				if(numbers.length==2){
					var epage= numbers[1].trim().match(testExp)?parseInt(numbers[1].trim()):numbers[1];
					range=epage-spage+1;					
				}
				$(item).text(pagNubStr+" ("+range+")");
			}
		})
	}
</script>




<!-- 热读资源开始 -->
        <div class="oh h450">
        	<h1 class="h1Tit borBot"><a class="ico1" href="javascript:void(0)">热读文章</a></h1>
            <div class="featureContainer">
                <div class="feature">
                    <div class="black h420">
                        <div id="botton-scroll3">
                            <ul class=featureUL>
                            <c:forEach items="${hotlist}" var="p" varStatus="index">
							<c:if test="${index.index % 4==0}">
                              <li class=featureBox>
                                  <!-- 一个滚动条内容开始 -->
                                  <div class="oh">
                            
                                  	<div class="chineseList">
                                  	</c:if>
                                            <div class="mb10">
														<p class="pH1">
															<a class="a_title" title="${index.index}${p.publications.title}"
																href="${ctx}/pages/publications/form/article/${p.publications.id}">
																${fn:replace(fn:replace(fn:replace(p.publications.title,"&lt;","<"),"&gt;",">"),"&amp;","&")}
															</a>
														</p>
														<p>by： ${p.publications.author }</p>
														<p>${p.publications.publisher.name}</p>
														<P><a class="a_title" href="${ctx}/pages/publications/form/journaldetail/${p.publications.id}">${p.publications.title}</a>,
															<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Volume" sessionKey="lang" />
											                ${p.publications.volumeCode },
															<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.Issue" sessionKey="lang" />
															${p.publications.issueCode }, 
															${p.publications.year }-${p.publications.month} ,
															<ingenta-tag:LanguageTag key="Pages.Publications.Journal.Lable.PageRange" sessionKey="lang" />： 
															<span class="chapt_author_p3">
															${p.publications.startPage }-${p.publications.endPage} <span></span>
															</span>
															</P>
													</div>
                           <c:if test="${index.index!=0 && (index.index+1) % 4==0}">
                                     </div>
                                  </div>
                                  <!-- 一个滚动条内容结束 -->
                              </li>
                           </c:if>
                              </c:forEach>
                             </ul>
                           </div>
                          <!-- /botton-scroll -->
                        </div><!-- /block -->
                          <a class="prev3" href="javascript:void(0)">Previous</a>
                          <a class="next3" href="javascript:void(0)">Next</a> 
                 </div>
                        <!-- /feature -->
    		</div>            
        </div>
        <!-- 热读资源结束 -->