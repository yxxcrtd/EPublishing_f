<div class="mb50 oh">
	<h1 class="indexHtit">
		<span class="titFb"><a class="ico4" href="javascript:;">Total</a></span>
    </h1>
    <p class="mb5">Total <span>${stat.sumCount?c}</span></p>
    <a href="${request.contextPath}/index/advancedSearchSubmit?pubType=1">
	    <p class="data"> 
	        <span class="d_name">Book</span>
	        <span class="d_number">${(stat.bookCountEn + stat.bookCount)?c}</span>
	 	</p>
 	</a>
    <a href="${request.contextPath}/index/advancedSearchSubmit?pubType=2">
	  	<p class="data">   
	        <span class="d_name">Journal</span>
	        <span class="d_number">${stat.journalsCount?c}</span>
	 	</p>
 	</a>
    <a href="${request.contextPath}/index/advancedSearchSubmit?pubType=4">
	  	<p class="data">
	        <span class="d_name">Article</span>
	        <span class="d_number">${stat.articleCount?c}</span>
	 	</p>
 	</a>
</div>